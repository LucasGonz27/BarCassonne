package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.game.Entities.Towers.TowerArcher;
import Epi.BarCassonne.game.Entities.Towers.TowerMagie;
import Epi.BarCassonne.game.Entities.Towers.Tower;
import Epi.BarCassonne.game.Managers.AssetMana;
import Epi.BarCassonne.game.Managers.BackgroundManager;
import Epi.BarCassonne.game.Managers.CheminMana;
import Epi.BarCassonne.game.Managers.GameState;
import Epi.BarCassonne.game.Managers.TextureManager;
import Epi.BarCassonne.game.Managers.TowerManager;
import Epi.BarCassonne.game.Managers.VagueMana;
import Epi.BarCassonne.game.UI.HUD;
import Epi.BarCassonne.game.Utils.CollisionValid;
import Epi.BarCassonne.game.Utils.CoordinateConverter;
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
    private static final float TAILLE_APERCU_TOUR = 50f;
    private static final int TYPE_TOUR_ARCHER = 1;
    private static final int TYPE_TOUR_MAGIE = 2;
    private static final float DELAI_FERMETURE_GAME_OVER = 5.0f;

    private boolean enModePlacement;
    private int typeTourAPlacer; // TYPE_TOUR_ARCHER ou TYPE_TOUR_MAGIE
    private Texture tourPreviewTexture;
    private float tourPreviewX;
    private float tourPreviewY;
    private boolean positionValide; // Indique si la position actuelle est valide

    // Mode debug pour définir les zones de collision
    private java.util.List<Vector2> pointsClic; // Stocke les 4 points cliqués

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
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float mapWidth = screenWidth - HUD.getLargeurHUD(screenWidth);
        float mapHeight = screenHeight - HUD.getHauteurBarreVie(screenHeight);

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
        gameState = new GameState(500, 10);
        // Initialiser le chemin avec les dimensions de la map
        cheminManager = new CheminMana(mapWidth, mapHeight);
        vagueManager = new VagueMana(cheminManager, gameState);
        hud = new HUD(gameState);
        towerManager = new TowerManager();
        collisionValid = new CollisionValid(mapWidth, mapHeight);

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

        // Initialiser le mode placement
        enModePlacement = false;
        typeTourAPlacer = 0;
        tourPreviewTexture = null;
        positionValide = false;

    
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
                if (tempsAffichageMessage >= 2f) { // Afficher pendant 2 secondes
                    afficherMessageVague = false;
                }
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

        // Afficher l'aperçu de la tour si on est en mode placement
        if (enModePlacement && tourPreviewTexture != null) {
            // Changer la couleur selon si la position est valide
            if (!positionValide) {
                spriteBatch.setColor(1f, 0.3f, 0.3f, 0.7f); // Rouge transparent si invalide
            } else {
                spriteBatch.setColor(1f, 1f, 1f, 0.7f); // Blanc transparent si valide
            }
            spriteBatch.draw(tourPreviewTexture,
                tourPreviewX - TAILLE_APERCU_TOUR / 2,
                tourPreviewY - TAILLE_APERCU_TOUR / 2,
                TAILLE_APERCU_TOUR,
                TAILLE_APERCU_TOUR);
            spriteBatch.setColor(1f, 1f, 1f, 1f); // Réinitialiser la couleur
        }

        spriteBatch.end();

        // Dessiner les zones non constructibles en rouge
        dessinerZonesNonConstructibles();

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

    /**
     * Dessine les zones non constructibles en rouge avec faible opacité.
     */
    private void dessinerZonesNonConstructibles() {
        if (collisionValid == null) {
            return;
        }

        mapViewport.apply();
        shapeRenderer.setProjectionMatrix(mapCamera.combined);

        // Dessiner uniquement les contours des zones (pas de remplissage pour voir le terrain)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1f, 0f, 0f, 0.6f); // Contours rouges visibles (60%)

        float[][] zones = collisionValid.getZonesNonConstructibles();
        for (float[] zone : zones) {
            float x = zone[0];
            float y = zone[1];
            float width = zone[2];
            float height = zone[3];

            // Dessiner uniquement le contour du rectangle (pas de remplissage)
            shapeRenderer.rect(x, y, width, height);
        }

        shapeRenderer.end();
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
        } else {
            // Mode debug : afficher les coordonnées du clic dans le terminal
            afficherCoordonneesClic(screenX, screenY, screenWidth, screenHeight);
        }
    }

    /**
     * Affiche les coordonnées du clic dans le terminal pour faciliter la définition des zones de collision.
     * Enregistre 4 clics et génère automatiquement les coordonnées du rectangle prêtes à copier-coller.
     * @param screenX Position X du clic (coordonnées écran)
     * @param screenY Position Y du clic (coordonnées écran)
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void afficherCoordonneesClic(float screenX, float screenY, float screenWidth, float screenHeight) {
        Vector3 worldPos = CoordinateConverter.convertirEcranVersMonde(
            screenX, screenY, screenWidth, screenHeight, mapViewport);

        if (worldPos == null || !CoordinateConverter.estDansLimitesMap(worldPos.x, worldPos.y, mapViewport)) {
            return;
        }

        // Ajouter le point cliqué
        pointsClic.add(new Vector2(worldPos.x, worldPos.y));

        // Si on a 4 points, calculer le rectangle et afficher le format prêt à copier
        if (pointsClic.size() == 4) {
            calculerEtAfficherRectangle();
            pointsClic.clear(); // Réinitialiser pour le prochain rectangle
        }
    }

    /**
     * Calcule le rectangle englobant les 4 points et affiche le format prêt à copier-coller.
     */
    private void calculerEtAfficherRectangle() {
        if (pointsClic.size() != 4) {
            return;
        }

        // Trouver les valeurs min et max
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;

        for (Vector2 point : pointsClic) {
            minX = Math.min(minX, point.x);
            minY = Math.min(minY, point.y);
            maxX = Math.max(maxX, point.x);
            maxY = Math.max(maxY, point.y);
        }

        // Calculer les ratios et valeurs de référence
        float mapWidth = mapViewport.getWorldWidth();
        float mapHeight = mapViewport.getWorldHeight();

        float minXRef = (minX / mapWidth) * 1520f;
        float minYRef = (minY / mapHeight) * 930f;
        float maxXRef = (maxX / mapWidth) * 1520f;
        float maxYRef = (maxY / mapHeight) * 930f;

        // Arrondir les valeurs pour avoir des nombres entiers (comme dans CollisionValid)
        int minXRefInt = Math.round(minXRef);
        int minYRefInt = Math.round(minYRef);
        int maxXRefInt = Math.round(maxXRef);
        int maxYRefInt = Math.round(maxYRef);

        // Afficher uniquement le code prêt à copier-coller
        System.out.println("{" + minXRefInt + "f / REF_MAP_WIDTH, " + minYRefInt + "f / REF_MAP_HEIGHT, " +
                          maxXRefInt + "f / REF_MAP_WIDTH, " + maxYRefInt + "f / REF_MAP_HEIGHT},");
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

        // Vérifier si la position est valide avant de placer
        float tourSize = obtenirTailleTour(typeTourAPlacer);
        if (!collisionValid.estPositionValide(worldPos.x, worldPos.y, tourSize, towerManager.getTours())) {
            return; // Position invalide, ne pas placer
        }

        Tower nouvelleTour = creerTour(typeTourAPlacer, worldPos.x, worldPos.y);
        if (nouvelleTour != null) {
            towerManager.ajouterTour(nouvelleTour);
            annulerModePlacement();
        }
    }

    /**
     * Obtient la taille d'une tour selon son type.
     * @param typeTour Le type de tour
     * @return La taille de la tour
     */
    private float obtenirTailleTour(int typeTour) {
        switch (typeTour) {
            case TYPE_TOUR_ARCHER:
                return 90f;
            case TYPE_TOUR_MAGIE:
                return 120f;
            default:
                return 90f;
        }
    }

    /**
     * Crée une tour selon son type.
     * @param typeTour Le type de tour (TYPE_TOUR_ARCHER ou TYPE_TOUR_MAGIE)
     * @param x Position X en coordonnées monde
     * @param y Position Y en coordonnées monde
     * @return La tour créée, ou null si le type est invalide
     */
    private Tower creerTour(int typeTour, float x, float y) {
        switch (typeTour) {
            case TYPE_TOUR_ARCHER:
                return new TowerArcher(x, y, 100f);
            case TYPE_TOUR_MAGIE:
                return new TowerMagie(x, y,  120f);
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

            // Vérifier si la position est valide
            float tourSize = obtenirTailleTour(typeTourAPlacer);
            positionValide = collisionValid.estPositionValide(worldPos.x, worldPos.y, tourSize, towerManager.getTours());
        } else {
            positionValide = false;
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
        float mapWidth = width - HUD.getLargeurHUD(width);
        float mapHeight = height - HUD.getHauteurBarreVie(height);

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
        if (tourPreviewTexture != null) {
            tourPreviewTexture.dispose();
        }
        if (towerManager != null) {
            towerManager.dispose();
        }
        AssetMana.dispose();

    }
}
