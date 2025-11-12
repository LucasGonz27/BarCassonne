package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.game.Batiments.Batiment;
import Epi.BarCassonne.game.Batiments.TourArcher;
import Epi.BarCassonne.game.Batiments.TourMagie;
import Epi.BarCassonne.game.Managers.AssetMana;
import Epi.BarCassonne.game.Managers.BackgroundManager;
import Epi.BarCassonne.game.Managers.CheminMana;
import Epi.BarCassonne.game.Managers.GameState;
import Epi.BarCassonne.game.Managers.TextureManager;
import Epi.BarCassonne.game.Managers.TowerManager;
import Epi.BarCassonne.game.Managers.VagueMana;
import Epi.BarCassonne.game.UI.HUD;
import Epi.BarCassonne.game.Utils.CoordinateConverter;
import Epi.BarCassonne.game.Utils.Texte;

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
    private TowerManager towerManager;

    // Game Over
    private boolean gameOver;
    private float tempsGameOver;
    
    // Placement de tour
    private static final float TAILLE_APERCU_TOUR = 50f;
    private static final int TYPE_TOUR_ARCHER = 1;
    private static final int TYPE_TOUR_MAGIE = 2;
    private static final float DELAI_FERMETURE_GAME_OVER = 5.0f;
    
    private boolean enModePlacement;
    private int typeTourAPlacer; // TYPE_TOUR_ARCHER ou TYPE_TOUR_MAGIE
    private Texture tourPreviewTexture;
    private float tourPreviewX;
    private float tourPreviewY;

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
        // AssetMana.loadAnimation("GoblinBomb");
        // AssetMana.loadAnimation("Cochon");
        // AssetMana.loadAnimation("Chevalier");
        // AssetMana.loadAnimation("BossChevalier");
        // AssetMana.loadAnimation("Golem");
        // AssetMana.loadAnimation("RoiGoblin");
    }

    /**
     * Initialise les composants du jeu (état, managers, HUD).
     * @param mapWidth Largeur de la map
     * @param mapHeight Hauteur de la map
     */
    private void initialiserJeu(float mapWidth, float mapHeight) {
        gameState = new GameState(500, 100);
        // Initialiser le chemin avec les dimensions de la map
        cheminManager = new CheminMana(mapWidth, mapHeight);
        vagueManager = new VagueMana(cheminManager, gameState);
        hud = new HUD(gameState);
        towerManager = new TowerManager();

        // Initialiser le game over
        gameOver = false;
        tempsGameOver = 0f;
        
        // Initialiser le mode placement
        enModePlacement = false;
        typeTourAPlacer = 0;
        tourPreviewTexture = null;
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

        // Gérer les entrées utilisateur
        gererEntrees();
        
        // Mettre à jour le jeu seulement si le joueur est encore en vie
        if (gameState.estEnVie()) {
            vagueManager.update(delta);
            towerManager.update(delta);
            if (vagueManager.getVagueActuelle() != null) {
                gameState.setNumeroVague(vagueManager.getVagueActuelle().getNumero());
            }
        } else if (gameOver) {
            // Compter le temps depuis le game over
            tempsGameOver += delta;

            // Fermer le jeu après le délai défini
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
        // Map
        mapViewport.apply();
        mapCamera.update();
        spriteBatch.setProjectionMatrix(mapCamera.combined);
        spriteBatch.begin();
        backgroundManager.renderFillScreen(spriteBatch, mapViewport.getWorldWidth(), mapViewport.getWorldHeight());
        vagueManager.render(spriteBatch);
        towerManager.render(spriteBatch);
        
        // Afficher l'aperçu de la tour si on est en mode placement
        if (enModePlacement && tourPreviewTexture != null) {
            spriteBatch.draw(tourPreviewTexture, 
                tourPreviewX - TAILLE_APERCU_TOUR / 2, 
                tourPreviewY - TAILLE_APERCU_TOUR / 2, 
                TAILLE_APERCU_TOUR, 
                TAILLE_APERCU_TOUR);
        }
        
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
            // Calculer la position centrée en utilisant les dimensions du viewport
            // Approximation de la largeur du texte (environ 60% de la taille de la font)
            float textWidth = message.length() * 60 * 0.6f;
            float x = (screenWidth - textWidth) / 2;
            float y = screenHeight / 2;
            Texte.drawText(spriteBatch, message, x, y, Color.RED, 60);
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
            if (enModePlacement) {
                annulerModePlacement();
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
            activerModePlacement(slotClic);
            return;
        }
        
        // Si on est en mode placement, placer la tour sur le terrain
        if (enModePlacement) {
            placerTour(screenX, screenY, screenWidth, screenHeight);
        }
    }
    
    /**
     * Active le mode placement pour un type de tour donné.
     * @param typeTour Le type de tour (1 = TourArcher, 2 = TourMagie)
     */
    private void activerModePlacement(int typeTour) {
        enModePlacement = true;
        typeTourAPlacer = typeTour;
        tourPreviewTexture = chargerTextureApercu(typeTour);
    }
    
    /**
     * Charge la texture d'aperçu pour un type de tour.
     * @param typeTour Le type de tour (TYPE_TOUR_ARCHER ou TYPE_TOUR_MAGIE)
     * @return La texture d'aperçu, ou null si le type est invalide
     */
    private Texture chargerTextureApercu(int typeTour) {
        switch (typeTour) {
            case TYPE_TOUR_ARCHER:
                return TextureManager.chargerTexture("sprites/TourArcherLevel1.png");
            case TYPE_TOUR_MAGIE:
                return TextureManager.chargerTexture("sprites/TourMagieLevel1.png");
            default:
                return null;
        }
    }
    
    /**
     * Place une tour sur le terrain à la position du clic.
     */
    private void placerTour(float screenX, float screenY, float screenWidth, float screenHeight) {
        Vector3 worldPos = CoordinateConverter.convertirEcranVersMonde(
            screenX, screenY, screenWidth, screenHeight, mapViewport);
        
        if (worldPos == null || !CoordinateConverter.estDansLimitesMap(worldPos.x, worldPos.y, mapViewport)) {
            return;
        }
        
        Batiment nouvelleTour = creerTour(typeTourAPlacer, worldPos.x, worldPos.y);
        if (nouvelleTour != null) {
            towerManager.ajouterTour(nouvelleTour);
            annulerModePlacement();
        }
    }
    
    /**
     * Crée une tour selon son type.
     * @param typeTour Le type de tour (TYPE_TOUR_ARCHER ou TYPE_TOUR_MAGIE)
     * @param x Position X en coordonnées monde
     * @param y Position Y en coordonnées monde
     * @return La tour créée, ou null si le type est invalide
     */
    private Batiment creerTour(int typeTour, float x, float y) {
        switch (typeTour) {
            case TYPE_TOUR_ARCHER:
                return new TourArcher(x, y, 1, 4, 10f, 100f);
            case TYPE_TOUR_MAGIE:
                return new TourMagie(x, y, 1, 4, 15f, 120f);
            default:
                return null;
        }
    }
    
    /**
     * Annule le mode placement.
     */
    private void annulerModePlacement() {
        enModePlacement = false;
        typeTourAPlacer = 0;
        tourPreviewTexture = null;
    }
    
    /**
     * Gère l'annulation du placement (ESC).
     * Note: Le clic droit est géré dans gererClics().
     */
    private void gererAnnulationPlacement() {
        if (enModePlacement && Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            annulerModePlacement();
        }
    }
    
    /**
     * Met à jour la position de l'aperçu de la tour si on est en mode placement.
     */
    private void mettreAJourApercu() {
        if (!enModePlacement) {
            return;
        }
        
        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        
        Vector3 worldPos = CoordinateConverter.convertirEcranVersMonde(
            screenX, screenY, screenWidth, screenHeight, mapViewport);
        
        if (worldPos != null) {
            tourPreviewX = worldPos.x;
            tourPreviewY = worldPos.y;
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
        if (tourPreviewTexture != null) {
            tourPreviewTexture.dispose();
        }
        if (towerManager != null) {
            towerManager.dispose();
        }
        AssetMana.dispose();
        Texte.dispose();
    }
}
