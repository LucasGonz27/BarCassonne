package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.BitmapFont;



/**
 * Écran principal du jeu.
 * Gère l'affichage et la mise à jour du jeu.
 */
public class GameScreen implements Screen {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    // Rendu
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera mapCamera;
    private OrthographicCamera hudCamera;
    private Viewport mapViewport;
    private Viewport hudViewport;
    // Managers
    private BackgroundManager backgroundManager;
    private CheminMana cheminManager;
    private VagueMana vagueManager;
    private GameState gameState;
    private HUD hud;
    private TowerManager towerManager;
    private CollisionValid collisionValid;

    // Game Over
    private boolean gameOver;
    private float tempsGameOver;

    // Message de vague
    private float tempsAffichageMessage;
    private boolean afficherMessageVague;
    // Placement de tour
    private static final float DELAI_FERMETURE_GAME_OVER = 5.0f;


    //musique
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

        //on crée le pinceau pour dessiner
        spriteBatch = new SpriteBatch();

        //on crée le pinceau pour dessiner les zones non constructibles
        shapeRenderer = new ShapeRenderer();

        //on charge le fond de la map
        backgroundManager = new BackgroundManager("backgrounds/map.png");

        //on crée la caméra et le viewport pour la map
        mapCamera = new OrthographicCamera();
        mapViewport = new StretchViewport(mapWidth, mapHeight, mapCamera);
        mapViewport.apply();
        mapCamera.position.set(mapWidth / 2, mapHeight / 2, 0);
        mapCamera.update();

        //on crée la caméra et le viewport pour le HUD
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
        // AssetMana.loadAnimation("GoblinGuerrisseur");
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
        towerManager = new TowerManager(collisionValid, vagueManager);

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


        // Quitter le jeu si la touche ESCAPE est pressée
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        // Vérifier si le joueur a perdu
        if (!gameState.estEnVie() && !gameOver) {
            gameOver = true;
            tempsGameOver = 0f;
        }

        // Gérer les entrées utilisateur
        gererEntrees();

        // Mettre à jour le jeu seulement si le joueur est encore en vie
        if (gameState.estEnVie()) {
            vagueManager.update(delta);
            towerManager.update(delta);
            if (vagueManager.getVagueActuelle() != null) {
                int nouveauNumeroVague = vagueManager.getVagueActuelle().getNumero();
                int ancienNumeroVague = gameState.getNumeroVague();

                // Détecter le début d'une nouvelle vague avant de mettre à jour
                if (nouveauNumeroVague != ancienNumeroVague) {
                    afficherMessageVague = true;
                    tempsAffichageMessage = 0f;
                }
                gameState.setNumeroVague(nouveauNumeroVague);
            }

            // Gérer l'affichage du message de vague
            if (afficherMessageVague) {
                tempsAffichageMessage += delta;
                if (tempsAffichageMessage >= 2f) { 
                    afficherMessageVague = false;
                }
            }
        } else if (gameOver) {
            //on compte le temps depuis le game over
            tempsGameOver += delta;

            //on ferme le jeu après le délai défini
            if (tempsGameOver >= DELAI_FERMETURE_GAME_OVER) {
                Gdx.app.exit();
            }
        }

        // Toujours dessiner (même en game over pour afficher l'écran de défaite)
        dessiner();
    }

    /**
     * Dessine tous les éléments à l'écran.
     */
    private void dessiner() {

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Map
        mapViewport.apply();
        mapCamera.update();
        spriteBatch.setProjectionMatrix(mapCamera.combined);
        spriteBatch.begin();
        backgroundManager.renderFillScreen(spriteBatch, mapViewport.getWorldWidth(), mapViewport.getWorldHeight());
        vagueManager.render(spriteBatch);
        towerManager.render(spriteBatch);
        spriteBatch.end();

        // HUD
        hudViewport.apply();
        hudCamera.update();
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        hud.render(spriteBatch);

        // Afficher le message de vague
        if (afficherMessageVague && vagueManager.getVagueActuelle() != null) {
            spriteBatch.begin();
            float screenWidth = hudViewport.getWorldWidth();
            float screenHeight = hudViewport.getWorldHeight();

            String message = "Vague " + vagueManager.getVagueActuelle().getNumero();
            int taillePolice = 120;
            BitmapFont font = Texte.getFont(taillePolice);
            GlyphLayout layout = new GlyphLayout();
            layout.setText(font, message);

            // Centrer le message
            float texteVaguex = (screenWidth / 2f) - (layout.width / 2f);
            float texteVaguey = (screenHeight / 2f) + (layout.height / 2f);

            Texte.drawText(spriteBatch, message, texteVaguex, texteVaguey, Color.BLACK, taillePolice);
            spriteBatch.end();
        }

        // Afficher le message Game Over
        if (gameOver) {
            spriteBatch.begin();
            float screenWidth = hudViewport.getWorldWidth();
            float screenHeight = hudViewport.getWorldHeight();

            String message = "GAME OVER";
            float texteGameOverx = (screenWidth / 4);
            float texteGameOvery = (screenHeight / 2);
            Texte.drawText(spriteBatch, message, texteGameOverx, texteGameOvery, Color.RED, 100);
            spriteBatch.end();
        }
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

        // Si c'est un clic droit, annuler le placement
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            if (towerManager.estEnModePlacement()) {
                towerManager.annulerModePlacement();
            }
            return;
        }

        // Ignorer les autres boutons que le clic gauche
        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Vérifier si on clique sur un slot de tour dans le HUD
        int slotClic = hud.getSlotClic(screenX, screenY, screenWidth, screenHeight);
        if (slotClic > 0) {
            towerManager.activerModePlacement(slotClic);
            return;
        }

        // Si on est en mode placement, placer la tour sur le terrain
        if (towerManager.estEnModePlacement()) {
            towerManager.placerTour(screenX, screenY, screenWidth, screenHeight, mapViewport);
        }
    }



    /**
     * Gère l'annulation du placement (ESC).
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

        // Mettre à jour le viewport de la map
        mapViewport.update((int)mapWidth, (int)mapHeight);
        mapCamera.position.set(mapWidth / 2, mapHeight / 2, 0);
        mapCamera.update();

        // Mettre à jour le viewport du HUD
        hudViewport.update(width, height);
        hudCamera.position.set(width / 2, height / 2, 0);
        hudCamera.update();

        // Mettre à jour le chemin avec les nouvelles dimensions de la map
        if (cheminManager != null) {
            cheminManager.mettreAJourChemin(mapWidth, mapHeight);
        }

        // Mettre à jour le validateur de collision
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
        spriteBatch.dispose();
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        backgroundManager.dispose();
        hud.dispose();
        if (towerManager != null) {
            towerManager.dispose();
        }
        AssetMana.dispose();

    }
}
