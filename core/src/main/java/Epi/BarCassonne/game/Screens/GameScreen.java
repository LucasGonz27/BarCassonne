package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.game.Managers.AssetMana;
import Epi.BarCassonne.game.Managers.BackgroundManager;
import Epi.BarCassonne.game.Managers.CheminMana;
import Epi.BarCassonne.game.Managers.GameState;
import Epi.BarCassonne.game.Managers.HUDBackgroundManager;
import Epi.BarCassonne.game.Managers.VagueMana;
import Epi.BarCassonne.game.UI.HUD;

/**
 * Écran principal du jeu.
 * Gère l'affichage et la mise à jour du jeu.
 */
public class GameScreen implements Screen {

    // ------------------------------------------------------------------------
    // REGION : CONSTANTES
    // ------------------------------------------------------------------------
    private static final float HAUTEUR_BARRE_VIE = 50f;
    private static final float LARGEUR_HUD = 400f;
    private static final int LINGOTS_INITIAUX = 500;
    private static final int VIE_INITIALE = 100;

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
    private HUDBackgroundManager hudBackgroundManager;
    private CheminMana cheminManager;
    private VagueMana vagueManager;
    private GameState gameState;
    private HUD hud;

    // ------------------------------------------------------------------------
    // REGION : INITIALISATION
    // ------------------------------------------------------------------------
    /**
     * Appelé quand l'écran devient actif.
     * Initialise tous les composants du jeu.
     */
    @Override
    public void show() {
        initialiserRendu();
        chargerAssets();
        initialiserJeu();
    }

    /**
     * Initialise les composants de rendu (caméras, viewports, textures).
     */
    private void initialiserRendu() {
        spriteBatch = new SpriteBatch();
        backgroundManager = new BackgroundManager("backgrounds/map.png");
        hudBackgroundManager = new HUDBackgroundManager("HUD/BarreDeVie.png", "HUD/HUD.png");

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Caméra et viewport pour la map
        float mapWidth = screenWidth - LARGEUR_HUD;
        float mapHeight = screenHeight - HAUTEUR_BARRE_VIE;
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
        // AssetMana.loadAnimation("GoblinBomb");
        // AssetMana.loadAnimation("Cochon");
        // AssetMana.loadAnimation("Chevalier");
        // AssetMana.loadAnimation("BossChevalier");
        // AssetMana.loadAnimation("Golem");
        // AssetMana.loadAnimation("RoiGoblin");
    }

    /**
     * Initialise les composants du jeu (état, managers, HUD).
     */
    private void initialiserJeu() {
        gameState = new GameState(LINGOTS_INITIAUX, VIE_INITIALE);
        cheminManager = new CheminMana();
        vagueManager = new VagueMana(cheminManager);
        hud = new HUD(gameState);
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
        gererInput();
        mettreAJourJeu(delta);
        dessiner();
    }

    /**
     * Gère les entrées utilisateur (clavier, souris, etc.).
     */
    private void gererInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    /**
     * Met à jour la logique du jeu.
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void mettreAJourJeu(float delta) {
        vagueManager.update(delta);
        
        // Mettre à jour le numéro de vague dans l'état du jeu
        if (vagueManager.getVagueActuelle() != null) {
            gameState.setNumeroVague(vagueManager.getVagueActuelle().getNumero());
        }
    }

    /**
     * Dessine tous les éléments à l'écran.
     */
    private void dessiner() {
        effacerEcran();
        dessinerMap();
        dessinerHUD();
    }

    /**
     * Efface l'écran avec une couleur de fond.
     */
    private void effacerEcran() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Dessine la map et les ennemis.
     */
    private void dessinerMap() {
        mapViewport.apply();
        mapCamera.update();
        spriteBatch.setProjectionMatrix(mapCamera.combined);

        spriteBatch.begin();
        backgroundManager.renderFillScreen(spriteBatch, mapViewport.getWorldWidth(), mapViewport.getWorldHeight());
        vagueManager.render(spriteBatch);
        spriteBatch.end();
    }

    /**
     * Dessine le HUD (barre de vie et panneau vertical à droite).
     */
    private void dessinerHUD() {
        hudViewport.apply();
        hudCamera.update();
        spriteBatch.setProjectionMatrix(hudCamera.combined);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        hud.render(spriteBatch, hudBackgroundManager, screenWidth, screenHeight, LARGEUR_HUD, HAUTEUR_BARRE_VIE);
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
        float mapWidth = width - LARGEUR_HUD;
        float mapHeight = height - HAUTEUR_BARRE_VIE;
        
        // Mettre à jour le viewport de la map
        mapViewport.update((int)mapWidth, (int)mapHeight);
        mapCamera.position.set(mapWidth / 2, mapHeight / 2, 0);
        mapCamera.update();

        // Mettre à jour le viewport du HUD
        hudViewport.update(width, height);
        hudCamera.position.set(width / 2, height / 2, 0);
        hudCamera.update();
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
        hudBackgroundManager.dispose();
        hud.dispose();
        AssetMana.dispose();
    }
}
