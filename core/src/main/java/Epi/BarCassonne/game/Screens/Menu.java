package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import Epi.BarCassonne.game.Managers.BackgroundManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import Epi.BarCassonne.game.Utils.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Game;
import Epi.BarCassonne.game.Screens.GameScreen;

/**
 * Écran du menu principal du jeu.
 * Gère l'affichage du menu et la navigation vers le jeu.
 */
public class Menu implements Screen {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    private Game game;
    private SpriteBatch batch;
    private BackgroundManager backgroundManager;
    private Viewport viewport;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Button boutonJouer;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouvel écran de menu.
     * @param game L'instance du jeu pour changer d'écran
     */
    public Menu(Game game) {
        this.game = game;
    }

    // ------------------------------------------------------------------------
    // REGION : INITIALISATION
    // ------------------------------------------------------------------------
    /**
     * Appelé lorsque l'écran devient actif.
     */
    @Override
    public void show() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        initialiserRendu(screenWidth, screenHeight);
    }

    /**
     * Initialise tous les composants de rendu (caméra, viewport, textures, boutons).
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    public void initialiserRendu(float screenWidth, float screenHeight) {
        batch = new SpriteBatch();
        backgroundManager = new BackgroundManager("backgrounds/Menu.png");
        viewport = new StretchViewport(screenWidth, screenHeight);
        viewport.apply();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        
        // Créer le bouton "Jouer" centré
        float boutonWidth = 200f;
        float boutonHeight = 60f;
        float boutonX = (screenWidth / 2) - (boutonWidth / 2);
        float boutonY = (screenHeight / 2) - (boutonHeight / 2);
        boutonJouer = new Button(boutonX, boutonY, boutonWidth, boutonHeight, "Jouer", Color.WHITE, Color.BLACK, 32);
        boutonJouer.setAction(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new GameScreen());
            }
        });
    }

    // ------------------------------------------------------------------------
    // REGION : RENDU
    // ------------------------------------------------------------------------
    /**
     * Méthode principale appelée à chaque frame pour le rendu.
     * @param delta Temps écoulé depuis la dernière frame
     */
    @Override
    public void render(float delta) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        
        // Mettre à jour le bouton
        boutonJouer.update();
        
        // Dessiner le fond
        batch.begin();
        backgroundManager.renderFillScreen(batch, screenWidth, screenHeight);
        batch.end();
        
        // Dessiner le bouton
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        batch.begin();
        boutonJouer.render(batch, shapeRenderer);
        batch.end();
        shapeRenderer.end();
    }
    
    // ------------------------------------------------------------------------
    // REGION : GESTION D'ÉCRAN
    // ------------------------------------------------------------------------
    /**
     * Appelé lorsque la fenêtre est redimensionnée.
     * @param width Nouvelle largeur
     * @param height Nouvelle hauteur
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.setToOrtho(false, width, height);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    /**
     * Appelé lorsque l'application est mise en pause.
     */
    @Override
    public void pause() {
        // Pas d'action nécessaire
    }

    /**
     * Appelé lorsque l'application reprend après une pause.
     */
    @Override
    public void resume() {
        // Pas d'action nécessaire
    }

    /**
     * Appelé lorsque l'écran n'est plus actif.
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
        if (batch != null) {
            batch.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (backgroundManager != null) {
            backgroundManager.dispose();
        }
    }
}
