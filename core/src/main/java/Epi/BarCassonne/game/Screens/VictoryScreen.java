package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import Epi.BarCassonne.game.Managers.BackgroundManager;
import Epi.BarCassonne.game.Utils.Texte;

/**
 * Écran affiché quand le joueur gagne la partie.
 * Affiche un message "VICTOIRE" et retourne au menu après un délai.
 */
public class VictoryScreen implements Screen {

    // ------------------------------------------------------------------------
    // REGION : CONSTANTES
    // ------------------------------------------------------------------------
    /** Délai en secondes avant le retour au menu. */
    private static final float DELAI_RETOUR_MENU = 5.0f;
    
    /** Taille de la police pour le message "VICTOIRE". */
    private static final int TAILLE_POLICE_VICTOIRE = 120;

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    /** Instance du jeu pour changer d'écran. */
    private Game game;
    
    /** SpriteBatch pour le rendu. */
    private SpriteBatch spriteBatch;
    
    /** Gestionnaire de l'image de fond. */
    private BackgroundManager backgroundManager;
    
    /** Caméra orthographique pour le rendu. */
    private OrthographicCamera camera;
    
    /** Viewport pour gérer le redimensionnement. */
    private Viewport viewport;
    
    /** Temps écoulé depuis l'affichage de la victoire. */
    private float tempsVictoire;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouvel écran de victoire.
     * @param game L'instance du jeu pour changer d'écran
     */
    public VictoryScreen(Game game) {
        this.game = game;
    }

    // ------------------------------------------------------------------------
    // REGION : INITIALISATION
    // ------------------------------------------------------------------------
    /**
     * Appelé quand l'écran devient actif.
     * Initialise les composants de rendu.
     */
    @Override
    public void show() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        
        spriteBatch = new SpriteBatch();
        // Utiliser le fond du menu ou un fond spécifique si disponible
        backgroundManager = new BackgroundManager("backgrounds/victoire.png");
        camera = new OrthographicCamera();
        viewport = new StretchViewport(screenWidth, screenHeight, camera);
        
        tempsVictoire = 0f;
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
        gererVictoire(delta);
        dessiner();
    }

    // ------------------------------------------------------------------------
    // REGION : GESTION DE LA VICTOIRE
    // ------------------------------------------------------------------------
    /**
     * Gère la victoire et retourne au menu après le délai.
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void gererVictoire(float delta) {
        tempsVictoire += delta;
        if (tempsVictoire >= DELAI_RETOUR_MENU) {
            if (game != null) {
                game.setScreen(new Menu(game));
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

        // Configurer la caméra
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        viewport.apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        dessinerFond();
        dessinerMessageVictoire();
    }

    /**
     * Dessine le fond de l'écran.
     */
    private void dessinerFond() {
        spriteBatch.begin();
        if (backgroundManager != null) {
            backgroundManager.renderFillScreen(spriteBatch, viewport.getWorldWidth(), viewport.getWorldHeight());
        }
        spriteBatch.end();
    }

    /**
     * Dessine le message "VICTOIRE".
     */
    private void dessinerMessageVictoire() {
        spriteBatch.begin();
        float screenWidth = viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();
        String message = "VICTOIRE !";
        float texteVictoirex = screenWidth / 2;
        float texteVictoirey = screenHeight / 2;
        
        Texte.drawText(spriteBatch, message, texteVictoirex, texteVictoirey, Color.WHITE, TAILLE_POLICE_VICTOIRE);
        spriteBatch.end();
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
        viewport.update(width, height);
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
        if (backgroundManager != null) {
            backgroundManager.dispose();
        }
    }
}

