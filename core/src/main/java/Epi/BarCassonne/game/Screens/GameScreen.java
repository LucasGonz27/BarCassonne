package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.game.Managers.AssetMana;
import Epi.BarCassonne.game.Managers.BackgroundManager;
import Epi.BarCassonne.game.Managers.CheminMana;
import Epi.BarCassonne.game.Managers.GameState;
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
    // REGION : CONSTANTES
    // ------------------------------------------------------------------------
    private static final float DELAI_FERMETURE_GAME_OVER = 5.0f;
    private static final float DUREE_AFFICHAGE_MESSAGE_VAGUE = 2.0f;
    private static final int TAILLE_POLICE_MESSAGE_VAGUE = 120;
    private static final int TAILLE_POLICE_GAME_OVER = 100;

    // ------------------------------------------------------------------------
    // REGION : CHAMPS - RENDU
    // ------------------------------------------------------------------------
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera mapCamera;
    private OrthographicCamera hudCamera;
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
    private CollisionValid collisionValid;

    // ------------------------------------------------------------------------
    // REGION : CHAMPS - ÉTAT DU JEU
    // ------------------------------------------------------------------------
    private boolean gameOver;
    private float tempsGameOver;
    private boolean afficherMessageVague;
    private float tempsAffichageMessage;

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

        //taille de l'écran (width)
        float screenWidth = Gdx.graphics.getWidth();

        //taille de l'écran (height)
        float screenHeight = Gdx.graphics.getHeight();

        //taille de la map (width)
        float mapWidth = screenWidth - HUD.getLargeurHUD(screenWidth);

        //taille de la map (height)
        float mapHeight = screenHeight;

        initialiserRendu(screenWidth, screenHeight, mapWidth, mapHeight);
        chargerAssets();
        initialiserJeu(mapWidth, mapHeight);
    }

    /**
     * Initialise les composants de rendu (caméras, viewports, textures).
     */
    private void initialiserRendu(float screenWidth, float screenHeight, float mapWidth, float mapHeight) {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        backgroundManager = new BackgroundManager("backgrounds/map.png");

        // Initialiser la caméra et le viewport pour la map
        mapCamera = new OrthographicCamera();
        mapViewport = new StretchViewport(mapWidth, mapHeight, mapCamera);
        mapViewport.apply();
        mapCamera.position.set(mapWidth / 2, mapHeight / 2, 0);
        mapCamera.update();

        // Initialiser la caméra et le viewport pour le HUD
        hudCamera = new OrthographicCamera(screenWidth, screenHeight);
        hudViewport = new StretchViewport(screenWidth, screenHeight, hudCamera);
        hudViewport.apply();
        hudCamera.position.set(screenWidth / 2, screenHeight / 2, 0);
        hudCamera.update();
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
        // AssetMana.loadAnimation("Chevalier");
        // AssetMana.loadAnimation("BossChevalier");
        // AssetMana.loadAnimation("Golem");
        AssetMana.loadAnimation("RoiGoblin");
    }

    /**
     * Initialise les composants du jeu (état, managers, HUD).
     * @param mapWidth Largeur de la map
     * @param mapHeight Hauteur de la map
     */
    private void initialiserJeu(float mapWidth, float mapHeight) {

        //on crée l'état du jeu
        gameState = new GameState(500, 100);

        //on crée le gestionnaire de chemin
        cheminManager = new CheminMana(mapWidth, mapHeight);

        //on crée le gestionnaire de vague
        vagueManager = new VagueMana(cheminManager, gameState);

        //on crée le HUD
        hud = new HUD(gameState);

        //on crée le validateur de collision
        collisionValid = new CollisionValid(mapWidth, mapHeight);

        //on crée le gestionnaire de tour
        towerManager = new TowerManager(collisionValid, vagueManager, gameState);

        // Initialiser le game over
        gameOver = false;
        tempsGameOver = 0f;

        // Initialiser le message de vague
        tempsAffichageMessage = 0f;
        afficherMessageVague = false;

        // Initialiser la musique de jeu
        musiqueJeux = Gdx.audio.newSound(Gdx.files.internal("sounds/musiqueDurantJeux.mp3"));
        if (musiqueJeux != null) {
            musiqueJeux.loop();
        }


    
    }

    // ------------------------------------------------------------------------
    // REGION : RENDU
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
        } else if (gameOver) {
            gererGameOver(delta);
        }

        dessiner();
    }

    /**
     * Gère la sortie du jeu avec la touche ESCAPE.
     */
    private void gererQuitterJeu() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    /**
     * Vérifie si le joueur a perdu et initialise le game over.
     */
    private void verifierGameOver() {
        if (!gameState.estEnVie() && !gameOver) {
            gameOver = true;
            tempsGameOver = 0f;
        }
    }

    /**
     * Met à jour tous les composants du jeu.
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void mettreAJourJeu(float delta) {
        vagueManager.update(delta);
        towerManager.update(delta);
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

    /**
     * Gère le game over et ferme le jeu après le délai.
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void gererGameOver(float delta) {
        tempsGameOver += delta;
        if (tempsGameOver >= DELAI_FERMETURE_GAME_OVER) {
            Gdx.app.exit();
        }
    }

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
        mapViewport.apply();
        mapCamera.update();
        spriteBatch.setProjectionMatrix(mapCamera.combined);
        spriteBatch.begin();
        backgroundManager.renderFillScreen(spriteBatch, mapViewport.getWorldWidth(), mapViewport.getWorldHeight());
        vagueManager.render(spriteBatch);
        towerManager.render(spriteBatch);
        spriteBatch.end();
        
        towerManager.renderPortee(shapeRenderer, mapCamera);
    }

    /**
     * Dessine le HUD.
     */
    private void dessinerHUD() {
        hudViewport.apply();
        hudCamera.update();
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        hud.render(spriteBatch);
    }

    /**
     * Dessine les messages (vague, game over).
     */
    private void dessinerMessages() {
        if (afficherMessageVague && vagueManager.getVagueActuelle() != null) {
            dessinerMessageVague();
        }
        if (gameOver) {
            dessinerMessageGameOver();
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
        
        BitmapFont font = Texte.getFont(TAILLE_POLICE_MESSAGE_VAGUE);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, message);

        float texteVaguex = (screenWidth / 2f) - (layout.width / 2f);
        float texteVaguey = (screenHeight / 2f) + (layout.height / 2f);

        Texte.drawText(spriteBatch, message, texteVaguex, texteVaguey, Color.BLACK, TAILLE_POLICE_MESSAGE_VAGUE);
        spriteBatch.end();
    }

    /**
     * Dessine le message Game Over.
     */
    private void dessinerMessageGameOver() {
        spriteBatch.begin();
        float screenWidth = hudViewport.getWorldWidth();
        float screenHeight = hudViewport.getWorldHeight();
        String message = "GAME OVER";
        float texteGameOverx = screenWidth / 4;
        float texteGameOvery = screenHeight / 2;
        
        Texte.drawText(spriteBatch, message, texteGameOverx, texteGameOvery, Color.RED, TAILLE_POLICE_GAME_OVER);
        spriteBatch.end();
    }


    // ------------------------------------------------------------------------
    // REGION : GESTION DES ENTRÉES
    // ------------------------------------------------------------------------
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
     * Note: Le clic droit est géré dans gererClics().
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
     * Met à jour le viewport et la caméra de la map.
     */
    private void mettreAJourViewportMap(float mapWidth, float mapHeight) {
        mapViewport.update((int)mapWidth, (int)mapHeight);
        mapCamera.position.set(mapWidth / 2, mapHeight / 2, 0);
        mapCamera.update();
    }

    /**
     * Met à jour le viewport et la caméra du HUD.
     */
    private void mettreAJourViewportHUD(int width, int height) {
        hudViewport.update(width, height);
        hudCamera.position.set(width / 2, height / 2, 0);
        hudCamera.update();
    }

    /**
     * Met à jour les managers avec les nouvelles dimensions.
     */
    private void mettreAJourManagers(float mapWidth, float mapHeight) {
        if (cheminManager != null) {
            cheminManager.mettreAJourChemin(mapWidth, mapHeight);
        }
        if (collisionValid != null) {
            collisionValid.mettreAJourDimensions(mapWidth, mapHeight);
        }
    }

    /**
     * Appelé quand l'application est mise en pause.
     */
    @Override
    public void pause() {}

    /**
     * Appelé quand l'application reprend après une pause.
     */
    @Override
    public void resume() {}

    /**
     * Appelé quand l'écran n'est plus actif.
     */
    @Override
    public void hide() {}

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
