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
import com.badlogic.gdx.audio.Sound;



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
    private Button boutonJouer;
    private Button boutonOptionsMenu;
    private Button boutonQuitter;
    private static Sound sound;

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

       

        // Créer le bouton "Jouer"
        float boutonWidthJouer = 400f;
        float boutonHeightJouer = 300f;
        float boutonXJouer = (screenWidth * 0.65f); 
        float boutonYJouer = (screenHeight * 0.35f); 
        
        boutonJouer = new Button(boutonXJouer, boutonYJouer, boutonWidthJouer, boutonHeightJouer, "Jouer", "skin/SkinBoutonBois.png", Color.WHITE, 45);
        boutonJouer.setAction(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new Chargement(game));
            }
        });

        // Créer le bouton "Options" (en dessous du bouton Jouer)
        float boutonWidthOptions = 400f;
        float boutonHeightOptions = 300f;
        float boutonXOptions = boutonXJouer; 
        float boutonYOptions = boutonYJouer - 100f; 
         boutonOptionsMenu = new Button(boutonXOptions, boutonYOptions, boutonWidthOptions, boutonHeightOptions, "Options", "skin/SkinBoutonBois.png", Color.WHITE, 45);
         boutonOptionsMenu.setAction(new Runnable() {
             @Override
             public void run() {
                 //game.setScreen(new OptionsScreen());
             }
         });

        // Créer le bouton "Quitter" (en dessous du bouton options)
        float boutonWidthQuitter = 400f;
        float boutonHeightQuitter = 300f;
        float boutonXQuitter = boutonXJouer; 
        float boutonYQuitter = boutonYOptions - 100f; 
        
        boutonQuitter = new Button(boutonXQuitter, boutonYQuitter, boutonWidthQuitter, boutonHeightQuitter, "Quitter", "skin/SkinBoutonBois.png", Color.WHITE, 45);
        boutonQuitter.setAction(new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        });

        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/musiqueJeux.mp3"));
        if (sound != null) {
            sound.loop();
        }
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
        
        // Mettre à jour les boutons
        boutonJouer.update();
        boutonOptionsMenu.update();
        boutonQuitter.update();
        
        // Dessiner le fond
        batch.begin();
        backgroundManager.renderFillScreen(batch, screenWidth, screenHeight);
        batch.end();
        
        // Dessiner les boutons et le titre
        batch.begin();
        boutonJouer.render(batch);
        boutonOptionsMenu.render(batch);
        boutonQuitter.render(batch);
        
      
      
        
        // Dessiner le titre du jeu (centré en haut)
        float titreX = (screenWidth * 0.50f);
        float titreY = screenHeight * 0.71f; 
        Texte.drawText(batch, "BarCassonne", titreX, titreY, Color.WHITE, 126);

        float titreSousX = (screenWidth * 0.55f);
        float titreSousY = screenHeight * 0.62f; 
        Texte.drawText(batch, "Défendez votre chateau !", titreSousX, titreSousY, Color.WHITE, 46);
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
    
    /**
     * Arrête la musique du menu.
     * Peut être appelée depuis d'autres écrans.
     */
    public static void stopMusic() {
        if (sound != null) {
            sound.stop();
        }
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
        if (boutonJouer != null) {
            boutonJouer.dispose();
        }
        if (boutonOptionsMenu != null) {
            boutonOptionsMenu.dispose();
        }
        if (boutonQuitter != null) {
            boutonQuitter.dispose();
        }
    }
}
