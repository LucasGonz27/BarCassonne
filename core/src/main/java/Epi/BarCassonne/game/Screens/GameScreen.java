package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.game.Managers.AssetMana;
import Epi.BarCassonne.game.Managers.BackgroundManager;
import Epi.BarCassonne.game.Managers.CheminMana;
import Epi.BarCassonne.game.Managers.GameState;
import Epi.BarCassonne.game.Managers.ProjectileManager;
import Epi.BarCassonne.game.Managers.TowerManager;
import Epi.BarCassonne.game.Managers.VagueMana;
import Epi.BarCassonne.game.UI.HUD;
import Epi.BarCassonne.game.Utils.CollisionValid;
import Epi.BarCassonne.game.Utils.Texte;

/**
 * Écran principal du jeu.
 * Gère l'affichage et la mise à jour du jeu.
 */
public class GameScreen implements Screen {

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouvel écran de jeu.
     * @param game L'instance du jeu pour changer d'écran
     */
    public GameScreen(Game game) {
        this.game = game;
    }

    // ------------------------------------------------------------------------
    // REGION : CONSTANTES
    // ------------------------------------------------------------------------
    private static final float DUREE_AFFICHAGE_MESSAGE_VAGUE = 2.0f;
    private static final int TAILLE_POLICE_MESSAGE_VAGUE = 120;

    // ------------------------------------------------------------------------
    // REGION : CHAMPS - RENDU
    // ------------------------------------------------------------------------
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private Viewport mapViewport;
    private Viewport hudViewport;

    // ------------------------------------------------------------------------
    // REGION : CHAMPS - MANAGERS
    // ------------------------------------------------------------------------
    private BackgroundManager backgroundManager;
    private CheminMana cheminManager;
    private VagueMana vagueManager;
    private GameState gameState;
    private HUD hud;
    private TowerManager towerManager;
    private ProjectileManager projectileManager;
    private CollisionValid collisionValid;

    // ------------------------------------------------------------------------
    // REGION : CHAMPS - ÉTAT DU JEU
    // ------------------------------------------------------------------------
    private boolean gameOver;
    private boolean afficherMessageVague;
    private float tempsAffichageMessage;

    /** Instance du jeu pour changer d'écran. */
    private Game game;

    // ------------------------------------------------------------------------
    // REGION : CHAMPS - AUDIO
    // ------------------------------------------------------------------------
    private Sound musiqueJeux;

    // ------------------------------------------------------------------------
    // REGION : INITIALISATION
    // ------------------------------------------------------------------------
    /**
     * Appelé quand l'écran devient actif.
     * Initialise tous les composants du jeu.
     */
    @Override
    public void show() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float mapWidth = screenWidth - HUD.getLargeurHUD(screenWidth);
        float mapHeight = screenHeight;

        initialiserRendu(screenWidth, screenHeight, mapWidth, mapHeight);
        chargerAssets();
        GameState.resetInstance();
        initialiserJeu(mapWidth, mapHeight);
    }

    /**
     * Initialise les composants de rendu (caméras, viewports, textures).
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapWidth Largeur de la map
     * @param mapHeight Hauteur de la map
     */
    private void initialiserRendu(float screenWidth, float screenHeight, float mapWidth, float mapHeight) {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        backgroundManager = new BackgroundManager("backgrounds/map.png");

        // Initialiser la caméra unique (partagée entre map et HUD)
        camera = new OrthographicCamera();

        // Initialiser les viewports (ils gèrent la projection de la caméra)
        mapViewport = new StretchViewport(mapWidth, mapHeight, camera);
        hudViewport = new StretchViewport(screenWidth, screenHeight, camera);
    }

    /**
     * Charge tous les assets nécessaires (animations, etc.).
     */
    private void chargerAssets() {
        AssetMana.loadAnimation("PaysanGoblin");
        AssetMana.loadAnimation("GuerrierGoblin");
        AssetMana.loadAnimation("GoblinGuerrisseur");
        AssetMana.loadAnimation("GoblinBomb");
        AssetMana.loadAnimation("Cochon");
        AssetMana.loadAnimation("RoiGoblin");
        AssetMana.loadAnimation("Chevalier");
        AssetMana.loadAnimation("Golem");
    }

    /**
     * Initialise les composants du jeu (état, managers, HUD).
     * @param mapWidth Largeur de la map
     * @param mapHeight Hauteur de la map
     */
    private void initialiserJeu(float mapWidth, float mapHeight) {
        // Créer l'état du jeu
        gameState = GameState.getInstance();

        // Créer le gestionnaire de chemin
        cheminManager = new CheminMana(mapWidth, mapHeight);

        // Créer le gestionnaire de vague
        vagueManager = new VagueMana(cheminManager, gameState);

        // Créer le HUD
        hud = new HUD(gameState);

        // Créer le validateur de collision
        collisionValid = new CollisionValid(mapWidth, mapHeight);

        // Créer le gestionnaire de projectiles
        projectileManager = new ProjectileManager();

        // Créer le gestionnaire de tour
        towerManager = new TowerManager(collisionValid, vagueManager, gameState, projectileManager);

        // Initialiser l'état du game over
        gameOver = false;

        // Initialiser le message de vague
        afficherMessageVague = false;
        tempsAffichageMessage = 0f;

        // Initialiser la musique de jeu
        musiqueJeux = Gdx.audio.newSound(Gdx.files.internal("sounds/musiqueDurantJeux.mp3"));
        if (musiqueJeux != null) {
            musiqueJeux.loop();
        }
    }

    // ------------------------------------------------------------------------
    // REGION : BOUCLE PRINCIPALE
    // ------------------------------------------------------------------------
    /**
     * Méthode principale appelée à chaque frame.
     * @param delta Temps écoulé depuis la dernière frame
     */
    @Override
    public void render(float delta) {
        gererQuitterJeu();
        verifierGameOver();
        gererEntrees();

        if (gameState.estEnVie()) {
            mettreAJourJeu(delta);
        }

        dessiner();
    }

    // ------------------------------------------------------------------------
    // REGION : MISE À JOUR DU JEU
    // ------------------------------------------------------------------------
    /**
     * Met à jour tous les composants du jeu.
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void mettreAJourJeu(float delta) {
        vagueManager.update(delta);
        towerManager.update(delta);
        projectileManager.update(delta);
        mettreAJourVague();
        mettreAJourMessageVague(delta);
    }

    /**
     * Met à jour le numéro de vague et détecte les nouvelles vagues.
     */
    private void mettreAJourVague() {
        if (vagueManager.getVagueActuelle() != null) {
            int nouveauNumeroVague = vagueManager.getVagueActuelle().getNumero();
            int ancienNumeroVague = gameState.getNumeroVague();

            if (nouveauNumeroVague != ancienNumeroVague) {
                afficherMessageVague = true;
                tempsAffichageMessage = 0f;
            }
            gameState.setNumeroVague(nouveauNumeroVague);
        }
    }

    /**
     * Met à jour l'affichage du message de vague.
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void mettreAJourMessageVague(float delta) {
        if (afficherMessageVague) {
            tempsAffichageMessage += delta;
            if (tempsAffichageMessage >= DUREE_AFFICHAGE_MESSAGE_VAGUE) {
                afficherMessageVague = false;
            }
        }
    }

    // ------------------------------------------------------------------------
    // REGION : GESTION DU GAME OVER
    // ------------------------------------------------------------------------
    /**
     * Vérifie si le joueur a perdu et change d'écran vers GameOver.
     */
    private void verifierGameOver() {
        if (!gameState.estEnVie() && !gameOver) {
            gameOver = true;
            if (game != null) {
                game.setScreen(new GameOver(game));
            }
        }
    }

    // ------------------------------------------------------------------------
    // REGION : RENDU
    // ------------------------------------------------------------------------
    /**
     * Dessine tous les éléments à l'écran.
     */
    private void dessiner() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        dessinerMap();
        dessinerHUD();
        dessinerMessages();
    }

    /**
     * Dessine la map avec le fond, les ennemis et les tours.
     */
    private void dessinerMap() {
        // Configurer la caméra pour la map
        camera.position.set(mapViewport.getWorldWidth() / 2, mapViewport.getWorldHeight() / 2, 0);
        mapViewport.apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        backgroundManager.renderFillScreen(spriteBatch, mapViewport.getWorldWidth(), mapViewport.getWorldHeight());
        vagueManager.render(spriteBatch);
        towerManager.render(spriteBatch);
        projectileManager.render(spriteBatch);
        spriteBatch.end();

        towerManager.renderPortee(shapeRenderer, camera);
    }

    /**
     * Dessine le HUD.
     */
    private void dessinerHUD() {
        // Configurer la caméra pour le HUD
        camera.position.set(hudViewport.getWorldWidth() / 2, hudViewport.getWorldHeight() / 2, 0);
        hudViewport.apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        hud.render(spriteBatch);
    }

    /**
     * Dessine les messages (vague).
     */
    private void dessinerMessages() {
        if (afficherMessageVague && vagueManager.getVagueActuelle() != null) {
            dessinerMessageVague();
        }
    }

    /**
     * Dessine le message de vague.
     */
    private void dessinerMessageVague() {
        spriteBatch.begin();
        float screenWidth = hudViewport.getWorldWidth();
        float screenHeight = hudViewport.getWorldHeight();
        String message = "Vague " + vagueManager.getVagueActuelle().getNumero();

        // Calculer la position centrée du texte
        GlyphLayout layout = new GlyphLayout();
        layout.setText(Texte.getFont(TAILLE_POLICE_MESSAGE_VAGUE), message);
        float texteVaguex = (screenWidth / 2f) - (layout.width / 2f);
        float texteVaguey = (screenHeight / 2f) + (layout.height / 2f);

        Texte.drawText(spriteBatch, message, texteVaguex, texteVaguey, Color.BLACK, TAILLE_POLICE_MESSAGE_VAGUE);
        spriteBatch.end();
    }


    // ------------------------------------------------------------------------
    // REGION : GESTION DES ENTRÉES
    // ------------------------------------------------------------------------
    /**
     * Gère la sortie du jeu avec la touche ESCAPE.
     */
    private void gererQuitterJeu() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !towerManager.estEnModePlacement()) {
            Gdx.app.exit();
        }
    }

    /**
     * Gère les entrées utilisateur (clics, etc.).
     */
    private void gererEntrees() {
        gererClics();
        gererAnnulationPlacement();
        mettreAJourApercu();
    }

    /**
     * Gère les clics de souris (sélection de tour ou placement).
     */
    private void gererClics() {
        if (!Gdx.input.justTouched()) {
            return;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            annulerPlacementSiActif();
            return;
        }

        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }


        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        int slotClic = hud.getSlotClic(screenX, screenY, screenWidth, screenHeight);
        if (slotClic > 0) {
            towerManager.activerModePlacement(slotClic);
            return;
        }

        // Mettre à jour la caméra pour la map avant toute interaction
        camera.position.set(mapViewport.getWorldWidth() / 2, mapViewport.getWorldHeight() / 2, 0);
        mapViewport.apply();
        camera.update();

        // Vérifier d'abord si on clique sur une tour ou l'interface d'amélioration
        if (towerManager.gererClicTour(screenX, screenY, screenWidth, screenHeight, mapViewport)) {
            return; // Le clic a été traité par le système d'amélioration
        }

        // Sinon, gérer le placement de tour si en mode placement
        if (towerManager.estEnModePlacement()) {
            towerManager.placerTour(screenX, screenY, screenWidth, screenHeight, mapViewport);
        }
    }

    /**
     * Annule le placement de tour si actif.
     */
    private void annulerPlacementSiActif() {
        if (towerManager.estEnModePlacement()) {
            towerManager.annulerModePlacement();
        }
    }

    /**
     * Gère l'annulation du placement avec la touche ESCAPE.
     */
    private void gererAnnulationPlacement() {
        if (towerManager.estEnModePlacement() && Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            towerManager.annulerModePlacement();
        }
    }

    /**
     * Met à jour la position de l'aperçu de la tour si on est en mode placement.
     */
    private void mettreAJourApercu() {
        if (!towerManager.estEnModePlacement()) {
            return;
        }

        // Mettre à jour la caméra pour la map avant la conversion des coordonnées
        camera.position.set(mapViewport.getWorldWidth() / 2, mapViewport.getWorldHeight() / 2, 0);
        mapViewport.apply();
        camera.update();

        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        towerManager.mettreAJourApercu(screenX, screenY, screenWidth, screenHeight, mapViewport);
    }

    // ------------------------------------------------------------------------
    // REGION : GESTION D'ÉVÉNEMENTS
    // ------------------------------------------------------------------------
    /**
     * Appelé quand la fenêtre est redimensionnée.
     * @param width Nouvelle largeur
     * @param height Nouvelle hauteur
     */
    @Override
    public void resize(int width, int height) {
        float mapWidth = width - HUD.getLargeurHUD(width);
        float mapHeight = height;

        mettreAJourViewportMap(mapWidth, mapHeight);
        mettreAJourViewportHUD(width, height);
        mettreAJourManagers(mapWidth, mapHeight);
    }

    /**
     * Met à jour le viewport de la map.
     * @param mapWidth Nouvelle largeur de la map
     * @param mapHeight Nouvelle hauteur de la map
     */
    private void mettreAJourViewportMap(float mapWidth, float mapHeight) {
        mapViewport.update((int)mapWidth, (int)mapHeight);
    }

    /**
     * Met à jour le viewport du HUD.
     * @param width Nouvelle largeur de l'écran
     * @param height Nouvelle hauteur de l'écran
     */
    private void mettreAJourViewportHUD(int width, int height) {
        hudViewport.update(width, height);
    }

    /**
     * Met à jour les managers avec les nouvelles dimensions.
     * @param mapWidth Nouvelle largeur de la map
     * @param mapHeight Nouvelle hauteur de la map
     */
    private void mettreAJourManagers(float mapWidth, float mapHeight) {
        if (cheminManager != null) {
            cheminManager.mettreAJourChemin(mapWidth, mapHeight);
            // Mettre à jour le chemin de tous les ennemis actifs après le recalcul du chemin
            if (vagueManager != null) {
                vagueManager.mettreAJourCheminEnnemis();
            }
        }
        if (collisionValid != null) {
            collisionValid.mettreAJourDimensions(mapWidth, mapHeight);
        }
    }

    /**
     * Appelé quand l'application est mise en pause.
     */
    @Override
    public void pause() {
        // Pas d'action nécessaire
    }

    /**
     * Appelé quand l'application reprend après une pause.
     */
    @Override
    public void resume() {
        // Pas d'action nécessaire
    }

    /**
     * Appelé quand l'écran n'est plus actif.
     */
    @Override
    public void hide() {
        // Pas d'action nécessaire
    }

    // ------------------------------------------------------------------------
    // REGION : NETTOYAGE
    // ------------------------------------------------------------------------
    /**
     * Libère toutes les ressources utilisées par l'écran.
     */
    @Override
    public void dispose() {
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (backgroundManager != null) {
            backgroundManager.dispose();
        }
        if (hud != null) {
            hud.dispose();
        }
        if (towerManager != null) {
            towerManager.dispose();
        }
        if (musiqueJeux != null) {
            musiqueJeux.dispose();
        }
        AssetMana.dispose();
    }
}
