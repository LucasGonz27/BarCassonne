package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.game.Managers.BackgroundManager;
import Epi.BarCassonne.game.Managers.SoundManager;
import Epi.BarCassonne.game.Utils.Button;
import Epi.BarCassonne.game.Utils.Texte;

/**
 * Écran de chargement du jeu.
 * Affiche un écran de chargement avant de lancer le jeu.
 */
public class Chargement implements Screen {

    // ------------------------------------------------------------------------
    // REGION : CONSTANTES
    // ------------------------------------------------------------------------
    private static final float DELAI_AFFICHAGE_BOUTON = 4f;
    private static final float LARGEUR_BOUTON = 450f;
    private static final float HAUTEUR_BOUTON = 150f;
    private static final float DECALAGE_Y_BOUTON = -100f;
    private static final int TAILLE_POLICE_CHARGEMENT = 155;
    private static final int TAILLE_POLICE_BOUTON = 45;
    private static final String TEXTE_CHARGEMENT = "Chargement...";
    private static final String TEXTE_BOUTON = "Commencer la partie";
    private static final String CHEMIN_BACKGROUND = "backgrounds/Chargement.png";
    private static final String CHEMIN_SKIN_BOUTON = "skin/SkinBoutonBois.png";

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    private final Game game;
    private SpriteBatch batch;
    private BackgroundManager backgroundManager;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Button boutonCommencer;
    private float time;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouvel écran de chargement.
     * @param game L'instance du jeu pour changer d'écran
     */
    public Chargement(Game game) {
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
        time = 0f; // Initialiser le temps à 0
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        initialiserRendu(screenWidth, screenHeight);
        SoundManager.demarrerMusiqueMenu(0.7f);
    }

    /**
     * Initialise tous les composants de rendu (caméra, viewport, textures, boutons).
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void initialiserRendu(float screenWidth, float screenHeight) {
        initialiserCameraEtViewport(screenWidth, screenHeight);
        initialiserBackground();
        initialiserBouton(screenWidth, screenHeight);
    }

    /**
     * Initialise la caméra et le viewport.
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void initialiserCameraEtViewport(float screenWidth, float screenHeight) {
        batch = new SpriteBatch();
        viewport = new StretchViewport(screenWidth, screenHeight);
        viewport.apply();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    /**
     * Initialise le gestionnaire de fond d'écran.
     */
    private void initialiserBackground() {
        backgroundManager = new BackgroundManager(CHEMIN_BACKGROUND);
    }

    /**
     * Initialise le bouton de démarrage.
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void initialiserBouton(float screenWidth, float screenHeight) {
        float boutonX = (screenWidth / 2f) - (LARGEUR_BOUTON / 2f);
        float boutonY = (screenHeight / 2f) - (HAUTEUR_BOUTON / 2f) + DECALAGE_Y_BOUTON;

        boutonCommencer = new Button(
            boutonX, 
            boutonY, 
            LARGEUR_BOUTON, 
            HAUTEUR_BOUTON, 
            TEXTE_BOUTON, 
            CHEMIN_SKIN_BOUTON, 
            Color.WHITE, 
            TAILLE_POLICE_BOUTON
        );
        
        boutonCommencer.setAction(() -> {
            SoundManager.arreterMusiqueMenu();
            game.setScreen(new GameScreen(game));
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
        
        mettreAJourTemps(delta);
        mettreAJourBouton();
        
        dessinerScene(screenWidth, screenHeight);
    }

    /**
     * Met à jour le temps écoulé.
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void mettreAJourTemps(float delta) {
        time += delta;
    }

    /**
     * Met à jour le bouton si le délai est écoulé.
     */
    private void mettreAJourBouton() {
        if (estBoutonVisible()) {
            boutonCommencer.update();
        }
    }

    /**
     * Vérifie si le bouton doit être visible.
     * @return true si le bouton doit être visible
     */
    private boolean estBoutonVisible() {
        return time > DELAI_AFFICHAGE_BOUTON;
    }

    /**
     * Dessine tous les éléments de la scène.
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void dessinerScene(float screenWidth, float screenHeight) {
        batch.begin();
        dessinerBackground(screenWidth, screenHeight);
        
        if (estBoutonVisible()) {
            dessinerBouton();
        } else {
            dessinerTexteChargement(screenWidth, screenHeight);
        }
        
        batch.end();
    }

    /**
     * Dessine le fond d'écran.
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void dessinerBackground(float screenWidth, float screenHeight) {
        backgroundManager.renderFillScreen(batch, screenWidth, screenHeight);
    }

    /**
     * Dessine le bouton de démarrage.
     */
    private void dessinerBouton() {
        boutonCommencer.render(batch);
    }

    /**
     * Dessine le texte de chargement.
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void dessinerTexteChargement(float screenWidth, float screenHeight) {
        BitmapFont font = Texte.getFont(TAILLE_POLICE_CHARGEMENT);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, TEXTE_CHARGEMENT);
        
        float texteX = (screenWidth / 2f) - (layout.width / 2f);
        float texteY = (screenHeight / 2f) + (layout.height / 2f);
        
        Texte.drawText(batch, TEXTE_CHARGEMENT, texteX, texteY, Color.WHITE, TAILLE_POLICE_CHARGEMENT);
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
        if (backgroundManager != null) {
            backgroundManager.dispose();
        }
        if (boutonCommencer != null) {
            boutonCommencer.dispose();
        }
    }
}
