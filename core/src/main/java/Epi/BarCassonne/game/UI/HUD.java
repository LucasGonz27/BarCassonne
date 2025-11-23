package Epi.BarCassonne.game.UI;

import Epi.BarCassonne.game.Utils.Texte;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import Epi.BarCassonne.game.Managers.GameState;
import Epi.BarCassonne.game.Managers.TextureManager;
import Epi.BarCassonne.game.Managers.TowerDataManager;

/**
 * Affiche l'interface du jeu (barre de vie, lingots, vie, vague).
 * Gère le rendu de tous les éléments de l'interface utilisateur.
 */
public class HUD {

    // ------------------------------------------------------------------------
    // REGION : CONSTANTES
    // ------------------------------------------------------------------------
   

    // Largeur du HUD : 20% de la largeur de la width de l'écran 
    private static final float LARGEUR_HUD = 0.2f;

    // Position X des lingots : 15% de la largeur de l'écran depuis le début du HUD
    private static final float LINGOTS_X = 0.15f;

    // Position Y des lingots : 78% de la hauteur de l'écran depuis le bas
    private static final float LINGOTS_Y = 0.782f;

    // Position X du timer : 6.2% de la largeur de l'écran depuis le début du HUD
    private static final float TIMER_JEU_X = 0.062f;

    // Position Y du timer : 78% de la hauteur de l'écran depuis le bas
    private static final float TIMER_JEU_Y = 0.782f;

    // Position X de la vie : 1.56% de la largeur de l'écran depuis le début
    private static final float VIE_X = 0.0156f;

    // Position Y de la vie : 9.26% de la hauteur de l'écran depuis le bas
    private static final float VIE_Y = 0.0926f;

    // Position X de la vague : 10% de la largeur de l'écran depuis le début du HUD
    private static final float VAGUE_X = 0.10f;

    // Position Y de la vague : 90% de la hauteur de l'écran depuis le bas
    private static final float VAGUE_Y = 0.90f;

    // Position X du prix de la tour Archer : 10.3% de la largeur de l'écran depuis le début du HUD
    private static final float PrixTourArcher_X = 0.103f;

    // Position Y du prix de la tour Archer : 59.4% de la hauteur de l'écran depuis le bas
    private static final float PrixTourArcher_Y = 0.594f;

    // Position X du prix de la tour Magie : 10.3% de la largeur de l'écran depuis le début du HUD
    private static final float PrixTourMagie_X = 0.103f;

    // Position Y du prix de la tour Magie : 41.8% de la hauteur de l'écran depuis le bas
    private static final float PrixTourMagie_Y = 0.418f;

    // Position X du prix de la tour Forgeron : 10.3% de la largeur de l'écran depuis le début du HUD
    private static final float PrixTourForgeron_X = 0.103f;

    // Position Y du prix de la tour Forgeron : 23.7% de la hauteur de l'écran depuis le bas
    private static final float PrixTourForgeron_Y = 0.237f;

    // Taille des slots : 5.8% de la largeur de l'écran
    private static final float SLOT_SIZE = 0.058f;

    // Position X du premier slot : 86.8% de la largeur de l'écran depuis le début
    private static final float SLOT1_X = 0.868f;

    // Position Y du premier slot : 62.1% de la hauteur de l'écran depuis le bas
    private static final float SLOT1_Y = 0.621f;

    // Position X du deuxième slot : 87.2% de la largeur de l'écran depuis le début
    private static final float SLOT2_X = 0.872f;

    // Position Y du deuxième slot : 44.1% de la hauteur de l'écran depuis le bas
    private static final float SLOT2_Y = 0.441f;

    // Position X du troisième slot : 87.2% de la largeur de l'écran depuis le début
    private static final float SLOT3_X = 0.872f;

    // Position Y du troisième slot : 26.1% de la hauteur de l'écran depuis le bas
    private static final float SLOT3_Y = 0.261f;

    // Position X du quatrième slot : 87.2% de la largeur de l'écran depuis le début
    private static final float SLOT4_X = 0.872f;

    // Position Y du quatrième slot : 8.1% de la hauteur de l'écran depuis le bas
    private static final float SLOT4_Y = 0.081f;

    // Position X du prix de la tour Canon : 10.3% de la largeur de l'écran depuis le début du HUD
    private static final float PrixTourCanon_X = 0.103f;

    // Position Y du prix de la tour Canon : 8.1% de la hauteur de l'écran depuis le bas
    private static final float PrixTourCanon_Y = 0.050f;

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------

    /** État du jeu pour afficher les informations (vie, lingots, vague). */
    private GameState gameState;
    
    /** Texture du panneau HUD. */
    private Texture hudTexture;
    
    /** Temps écoulé depuis le début du jeu en secondes. */
    private float timerJeu;
    
    /** Texture de la tour Archer Level 1. */
    private Texture tourArcherLevel1Texture;
    
    /** Texture de la tour Magie Level 1. */
    private Texture tourMagieLevel1Texture;
    
    /** Texture de la tour Forgeron Level 1. */
    private Texture tourForgeronLevel1Texture;

    /** Texture de la tour Canon Level 1. */
    private Texture tourCanonLevel1Texture;

    /** Gestionnaire des données des tours. */
    private TowerDataManager towerDataManager;



    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouveau HUD avec l'état du jeu.
     * @param gameState L'état du jeu à afficher
     */
    public HUD(GameState gameState) {
        this.gameState = gameState;
        this.towerDataManager = new TowerDataManager();
        this.hudTexture = TextureManager.chargerTexture("HUD/HUD.png");
        this.tourArcherLevel1Texture = TextureManager.chargerTexture("sprites/TourArcherLevel1.png");
        this.tourMagieLevel1Texture = TextureManager.chargerTexture("sprites/TourMagieLevel1.png");
        this.tourForgeronLevel1Texture = TextureManager.chargerTexture("sprites/ForgeronLevel1.png");
        this.tourCanonLevel1Texture = TextureManager.chargerTexture("sprites/CanonLevel1.png");
    }

    // ------------------------------------------------------------------------
    // REGION : RENDU
    // ------------------------------------------------------------------------

    /**
     * Dessine tous les éléments du HUD.
     * @param batch Le SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float largeurHUD = getLargeurHUD(screenWidth);
        float hudX = screenWidth - largeurHUD;

        batch.begin();
        dessinerPanneauHUD(batch, hudX, screenHeight, largeurHUD);
        dessinerTextesHUD(batch, hudX, screenWidth, screenHeight);
        dessinerTours(batch, hudX, screenWidth, screenHeight);
        batch.end();
    }

    /**
     * Dessine le panneau HUD sur le côté droit de l'écran.
     * @param batch Le SpriteBatch pour le rendu
     * @param hudX Position X du panneau HUD
     * @param screenHeight Hauteur de l'écran
     * @param largeurHUD Largeur du HUD
     */
    private void dessinerPanneauHUD(SpriteBatch batch, float hudX, float screenHeight, float largeurHUD) {
        if (hudTexture != null) {
            batch.draw(hudTexture, hudX, 0, largeurHUD, screenHeight);
        }
    }


    /**
     * Dessine tous les textes du HUD (lingots, numéro de vague).
     * @param batch Le SpriteBatch pour le rendu
     * @param hudX Position X du panneau HUD
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void dessinerTextesHUD(SpriteBatch batch, float hudX, float screenWidth, float screenHeight) {
  
        float lingotsX = hudX + (LINGOTS_X * screenWidth);
        float lingotsY = LINGOTS_Y* screenHeight;
        float vagueX = hudX + (VAGUE_X * screenWidth);
        float vagueY = VAGUE_Y * screenHeight;
        float timerJeuX = hudX + (TIMER_JEU_X * screenWidth);
        float timerJeuY = TIMER_JEU_Y * screenHeight;
        float prixTourArcherX = hudX + (PrixTourArcher_X * screenWidth);
        float prixTourArcherY = PrixTourArcher_Y * screenHeight;
        float prixTourMagieX = hudX + (PrixTourMagie_X * screenWidth);
        float prixTourMagieY = PrixTourMagie_Y * screenHeight;
        float prixTourForgeronX = hudX + (PrixTourForgeron_X * screenWidth);
        float prixTourForgeronY = PrixTourForgeron_Y * screenHeight;
        float prixTourCanonX = hudX + (PrixTourCanon_X * screenWidth);
        float prixTourCanonY = PrixTourCanon_Y * screenHeight;
        float vieX = VIE_X * screenWidth;
        float vieY = VIE_Y * screenHeight;
        timerJeu = timerJeu + Gdx.graphics.getDeltaTime();

        String texteVie = "Vie: " + gameState.getVie() + "/" + gameState.getVieMax();
        Texte.drawText(batch, texteVie, vieX, vieY, Color.BLACK, 30);
        Texte.drawText(batch, Integer.toString((int)timerJeu), timerJeuX, timerJeuY, Color.BLACK, 20);
        Texte.drawText(batch, Integer.toString(gameState.getLingots()), lingotsX, lingotsY, Color.BLACK, 20);
        Texte.drawText(batch, Integer.toString(gameState.getNumeroVague()), vagueX, vagueY, Color.BLACK, 50);;
        Texte.drawText(batch, Integer.toString(towerDataManager.getPrix("TowerArcher")), prixTourArcherX, prixTourArcherY, Color.BLACK, 22);
        Texte.drawText(batch, Integer.toString(towerDataManager.getPrix("TowerMagie")), prixTourMagieX, prixTourMagieY, Color.BLACK, 22);
        Texte.drawText(batch, Integer.toString(towerDataManager.getPrix("TowerForgeron")), prixTourForgeronX, prixTourForgeronY, Color.BLACK, 22);
        Texte.drawText(batch, Integer.toString(towerDataManager.getPrix("TowerCanon")), prixTourCanonX, prixTourCanonY, Color.BLACK, 22);
    }

    /**
     * Dessine les tours disponibles dans les slots du HUD.
     * @param batch Le SpriteBatch pour le rendu
     * @param hudX Position X du panneau HUD
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void dessinerTours(SpriteBatch batch, float hudX, float screenWidth, float screenHeight) {
        
        // Calculer la taille du slot en fonction de la résolution
        float slotSize = SLOT_SIZE * screenWidth;

        // Calculer la position du premier slot
        float slot1X = SLOT1_X * screenWidth;
        float slot1Y = SLOT1_Y * screenHeight;

        // Dessiner la TourArcherLevel1 dans le premier slot
        if (tourArcherLevel1Texture != null) {
            batch.draw(tourArcherLevel1Texture, slot1X, slot1Y, slotSize, slotSize);
        }

        // Calculer la position du deuxième slot
        float slot2X = SLOT2_X * screenWidth;
        float slot2Y = SLOT2_Y * screenHeight;

        // Dessiner la TourMagieLevel1 dans le deuxième slot
        if (tourMagieLevel1Texture != null) {
            batch.draw(tourMagieLevel1Texture, slot2X, slot2Y, slotSize, slotSize);
        }

        // Calculer la position du premier slot
        float slot3X = SLOT3_X * screenWidth;
        float slot3Y = SLOT3_Y * screenHeight;

        // Dessiner la TourForgeronLevel1 dans le troisième slot
        if (tourForgeronLevel1Texture != null) {
            batch.draw(tourForgeronLevel1Texture, slot3X, slot3Y, slotSize, slotSize);
        }

        // Calculer la position du quatrième slot   
        float slot4X = SLOT4_X * screenWidth;
        float slot4Y = SLOT4_Y * screenHeight;

        // Dessiner la TourCanonLevel1 dans le quatrième slot
        if (tourCanonLevel1Texture != null) {
            batch.draw(tourCanonLevel1Texture, slot4X, slot4Y, slotSize, slotSize);
        }

        
    }

    // ------------------------------------------------------------------------
    // REGION : MÉTHODES STATIQUES
    // ------------------------------------------------------------------------



    /**
     * Obtient la largeur du HUD en fonction de la largeur de l'écran.
     * @param screenWidth Largeur de l'écran
     * @return La largeur du HUD
     */
    public static float getLargeurHUD(float screenWidth) {
        return LARGEUR_HUD * screenWidth;
    }



    // ------------------------------------------------------------------------
    // REGION : INTERACTION
    // ------------------------------------------------------------------------
    /**
     * Vérifie si un clic a été effectué sur un slot de tour dans le HUD.
     * @param screenX Position X du clic (coordonnées écran)
     * @param screenY Position Y du clic (coordonnées écran)
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @return Le numéro du slot cliqué (1 = Archer, 2 = Magie, 3 = Forgeron), ou 0 si aucun slot n'a été cliqué
     */
    public int getSlotClic(float screenX, float screenY, float screenWidth, float screenHeight) {
        // Calculer la taille du slot en fonction de la résolution
        float slotSize = SLOT_SIZE * screenWidth;

        // Inverser Y car LibGDX a l'origine en haut, mais le HUD en bas
        float yInverse = screenHeight - screenY;

        // Vérifier le premier slot (TourArcher)
        float slot1X = SLOT1_X * screenWidth;
        float slot1Y = SLOT1_Y * screenHeight;
        if (screenX >= slot1X && screenX <= slot1X + slotSize &&
            yInverse >= slot1Y && yInverse <= slot1Y + slotSize) {
            return 1;
        }

        // Vérifier le deuxième slot (TourMagie)
        float slot2X = SLOT2_X * screenWidth;
        float slot2Y = SLOT2_Y * screenHeight;
        if (screenX >= slot2X && screenX <= slot2X + slotSize &&
            yInverse >= slot2Y && yInverse <= slot2Y + slotSize) {
            return 2;
        }

        // Vérifier le troisième slot (TourForgeron)
        float slot3X = SLOT3_X * screenWidth;
        float slot3Y = SLOT3_Y * screenHeight;
        if (screenX >= slot3X && screenX <= slot3X + slotSize &&
            yInverse >= slot3Y && yInverse <= slot3Y + slotSize) {
            return 3;
        }

        // Vérifier le quatrième slot (TourCanon)
        float slot4X = SLOT4_X * screenWidth;
        float slot4Y = SLOT4_Y * screenHeight;
        if (screenX >= slot4X && screenX <= slot4X + slotSize &&
            yInverse >= slot4Y && yInverse <= slot4Y + slotSize) {
            return 4;
        }

        return 0; // Aucun slot cliqué
    }

    /**
     * Vérifie si un clic a été effectué sur une tour dans le HUD.
     * @param screenX Position X du clic (coordonnées écran)
     * @param screenY Position Y du clic (coordonnées écran)
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @return true si le clic est sur une tour, false sinon
     * @deprecated Utilisez getSlotClic() à la place pour identifier quel slot est cliqué
     */
    @Deprecated
    public boolean estClicSurTour(float screenX, float screenY, float screenWidth, float screenHeight) {
        return getSlotClic(screenX, screenY, screenWidth, screenHeight) > 0;
    }

        // ------------------------------------------------------------------------
    // REGION : NETTOYAGE
    // ------------------------------------------------------------------------
    /**
     * Libère toutes les ressources utilisées par le HUD.
     */
    public void dispose() {
        // Libérer les textures
        if (hudTexture != null) {
            hudTexture.dispose();
        }
        if (tourArcherLevel1Texture != null) {
            tourArcherLevel1Texture.dispose();
        }
        if (tourMagieLevel1Texture != null) {
            tourMagieLevel1Texture.dispose();
        }
        if (tourForgeronLevel1Texture != null) {
            tourForgeronLevel1Texture.dispose();
        }
        if (tourCanonLevel1Texture != null) {
            tourCanonLevel1Texture.dispose();
        }
    }
}
