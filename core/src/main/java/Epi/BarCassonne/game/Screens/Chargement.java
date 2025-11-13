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
import com.badlogic.gdx.Game;
import Epi.BarCassonne.game.Utils.Texte;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.BitmapFont;



/**
 * Écran du menu principal du jeu.
 * Gère l'affichage du menu et la navigation vers le jeu.
 */
public class Chargement implements Screen {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    private Game game;
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
     * Crée un nouvel écran de menu.
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
    }

    /**
     * Initialise tous les composants de rendu (caméra, viewport, textures, boutons).
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    public void initialiserRendu(float screenWidth, float screenHeight) {
        batch = new SpriteBatch();
        backgroundManager = new BackgroundManager("backgrounds/Chargement.png");
        viewport = new StretchViewport(screenWidth, screenHeight);
        viewport.apply();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

       

        // Créer le bouton "Jouer"
        float boutonWidthJouer = 650f;
        float boutonHeightCommencer = 300f;
        float boutonXCommencer = (screenWidth / 2f - boutonWidthJouer / 2f); 
        float boutonYCommencer = (screenHeight / 2f - boutonHeightCommencer / 2f - 200f); 

        boutonCommencer = new Button(boutonXCommencer, boutonYCommencer, boutonWidthJouer, boutonHeightCommencer, "Commencer la partie", "skin/SkinBoutonBois.png", Color.WHITE, 45);
        boutonCommencer.setAction(new Runnable() {
            @Override
            public void run() {
                // Arrêter la musique du menu
                Menu.stopMusic();
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
        
        // Mettre à jour le temps
        time += delta;
        
        // Mettre à jour les boutons seulement si le temps est écoulé
        if (time > 4f) {
            boutonCommencer.update();
        }
       
        
        // Dessiner le fond, les boutons et le titre
        batch.begin();
        backgroundManager.renderFillScreen(batch, screenWidth, screenHeight);
        
        if (time > 4f) {
            boutonCommencer.render(batch);
        }
        
        String texteChargement = "Chargement...";
        int taillePolice = 155;
        BitmapFont font = Texte.getFont(taillePolice);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, texteChargement);
        
        float texteX = (screenWidth / 2f) - (layout.width / 2f);
        float texteY = (screenHeight / 2f) + (layout.height / 2f);
        
        Texte.drawText(batch, texteChargement, texteX, texteY, Color.WHITE, taillePolice);
        batch.end();
        
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
