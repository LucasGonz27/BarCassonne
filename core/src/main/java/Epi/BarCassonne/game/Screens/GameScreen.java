package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.game.Managers.AssetMana;
import Epi.BarCassonne.game.Managers.BackgroundManager;
import Epi.BarCassonne.game.Managers.CheminMana;
import Epi.BarCassonne.game.Managers.GameState;
import Epi.BarCassonne.game.Managers.VagueMana;
import Epi.BarCassonne.game.UI.HUD;
import Epi.BarCassonne.game.Utils.Texte;
import Epi.BarCassonne.game.Screens.Menu;

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

    // Game Over
    private boolean gameOver;
    private float tempsGameOver;

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
        float mapWidth = screenWidth - HUD.LARGEUR_HUD;
        float mapHeight = screenHeight - HUD.HAUTEUR_BARRE_VIE;

        initialiserRendu(screenWidth, screenHeight, mapWidth, mapHeight);
        chargerAssets();
        initialiserJeu(mapWidth, mapHeight);
    }

    /**
     * Initialise les composants de rendu (caméras, viewports, textures).
     */
    private void initialiserRendu(float screenWidth, float screenHeight, float mapWidth, float mapHeight) {

        spriteBatch = new SpriteBatch();
        backgroundManager = new BackgroundManager("backgrounds/map.png");

        // Caméra et viewport pour la map
        mapCamera = new OrthographicCamera();
        mapViewport = new StretchViewport(mapWidth, mapHeight, mapCamera);
        mapViewport.apply();
        mapCamera.position.set(mapWidth / 2, mapHeight / 2, 0);
        mapCamera.update();

        // Caméra et viewport pour le HUD
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
        // AssetMana.loadAnimation("Cochon");
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
        gameState = new GameState(500, 10);
        // Initialiser le chemin avec les dimensions de la map
        cheminManager = new CheminMana(mapWidth, mapHeight);
        vagueManager = new VagueMana(cheminManager, gameState);
        hud = new HUD(gameState);

        // Initialiser le game over
        gameOver = false;
        tempsGameOver = 0f;
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
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        // Vérifier si le joueur a perdu
        if (!gameState.estEnVie() && !gameOver) {
            gameOver = true;
            tempsGameOver = 0f;
        }

        // Mettre à jour le jeu seulement si le joueur est encore en vie
        if (gameState.estEnVie()) {
            vagueManager.update(delta);
            if (vagueManager.getVagueActuelle() != null) {
                gameState.setNumeroVague(vagueManager.getVagueActuelle().getNumero());
            }
        } else if (gameOver) {
            // Compter le temps depuis le game over
            tempsGameOver += delta;

            // Fermer le jeu après 5 secondes
            if (tempsGameOver >= 5.0f) {
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
        spriteBatch.end();

        // HUD
        hudViewport.apply();
        hudCamera.update();
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        hud.render(spriteBatch);

        // Afficher le message Game Over
        if (gameOver) {
            spriteBatch.begin();
            float screenWidth = hudViewport.getWorldWidth();
            float screenHeight = hudViewport.getWorldHeight();
        
            String message = "GAME OVER";
            float x = (screenWidth / 4);
            float y = (screenHeight / 2);
            Texte.drawText(spriteBatch, message, x, y, Color.RED, 100);
            spriteBatch.end();
        }
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
        float mapWidth = width - HUD.LARGEUR_HUD;
        float mapHeight = height - HUD.HAUTEUR_BARRE_VIE;

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
        backgroundManager.dispose();
        hud.dispose();
        AssetMana.dispose();
        
    }
}
