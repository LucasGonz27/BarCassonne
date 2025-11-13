package Epi.BarCassonne.game.UI;

import Epi.BarCassonne.game.Utils.Texte;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import Epi.BarCassonne.game.Managers.GameState;
import Epi.BarCassonne.game.Managers.TextureManager;

/**
 * Affiche l'interface du jeu (barre de vie, lingots, vie, vague).
 * Gère le rendu de tous les éléments de l'interface utilisateur.
 */
public class HUD {

    // ------------------------------------------------------------------------
    // REGION : CONSTANTES
    // ------------------------------------------------------------------------
    public static final float HAUTEUR_BARRE_VIE = 170f;
    public static final float LARGEUR_HUD = 400f;

    // Positions relatives au viewport du HUD (ratios de 0.0 à 1.0)
    // Position X des lingots : 15.4% de la largeur de l'écran depuis le début du HUD

    private static final float LINGOTS_X_RATIO = 0.154f;
    // Position Y des lingots : 78.2% de la hauteur de l'écran depuis le bas
    private static final float LINGOTS_Y_RATIO = 0.782f;

    // Position X du timer : 6.25% de la largeur de l'écran depuis le début du HUD
    private static final float TIMER_JEU_X_RATIO = 0.0625f;

    // Position Y du timer : 78.2% de la hauteur de l'écran depuis le bas
    private static final float TIMER_JEU_Y_RATIO = 0.782f;

    // Position X de la vie : 1.56% de la largeur de l'écran depuis le début
    private static final float VIE_X_RATIO = 0.0156f;
    
    // Position Y de la vie : 9.26% de la hauteur de l'écran depuis le bas
    private static final float VIE_Y_RATIO = 0.0926f;

    // Position X de la vague : 10.4% de la largeur de l'écran depuis le début du HUD
    private static final float VAGUE_X_RATIO = 0.104f;
    // Position Y de la vague : 89.8% de la hauteur de l'écran depuis le bas
    private static final float VAGUE_Y_RATIO = 0.898f;

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    private GameState gameState;
    private Texture barreVieTexture;
    private Texture hudTexture;
    private float timerJeu;
    private Texture tourArcherLevel1Texture;
    private Texture tourMagieLevel1Texture;

    // Positions des slots dans le HUD (ratios relatifs)
    // Position X du premier slot : 86.7% de la largeur de l'écran depuis le début
    private static final float SLOT1_X_RATIO = 0.867f;
    // Position Y du premier slot : 62.5% de la hauteur de l'écran depuis le bas
    private static final float SLOT1_Y_RATIO = 0.625f;
    // Position X du deuxième slot (même X que le premier)
    private static final float SLOT2_X_RATIO = 0.867f;
    // Position Y du deuxième slot : 44.9% de la hauteur de l'écran depuis le bas
    private static final float SLOT2_Y_RATIO = 0.449f;
    // Taille des slots : 5.73% de la largeur de l'écran
    private static final float SLOT_SIZE_RATIO = 0.0573f;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouveau HUD avec l'état du jeu.
     * @param gameState L'état du jeu à afficher
     */
    public HUD(GameState gameState) {
        this.gameState = gameState;
        this.barreVieTexture = TextureManager.chargerTexture("HUD/BarreDeVie.png");
        this.hudTexture = TextureManager.chargerTexture("HUD/HUD.png");
        this.tourArcherLevel1Texture = TextureManager.chargerTexture("sprites/TourArcherLevel1.png");
        this.tourMagieLevel1Texture = TextureManager.chargerTexture("sprites/TourMagieLevel1.png");
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
        float mapWidth = screenWidth - LARGEUR_HUD;
        float barreVieY = screenHeight - HAUTEUR_BARRE_VIE;
        float hudX = screenWidth - LARGEUR_HUD;

        batch.begin();
        dessinerBarreVie(batch, 0, barreVieY, mapWidth, HAUTEUR_BARRE_VIE, screenWidth, screenHeight);
        dessinerPanneauHUD(batch, hudX, screenHeight);
        dessinerTextes(batch, hudX, screenWidth, screenHeight);
        dessinerTours(batch, hudX, screenWidth, screenHeight);
        batch.end();
    }

    /**
     * Dessine le panneau HUD sur le côté droit de l'écran.
     * @param batch Le SpriteBatch pour le rendu
     * @param hudX Position X du panneau HUD
     * @param screenHeight Hauteur de l'écran
     */
    private void dessinerPanneauHUD(SpriteBatch batch, float hudX, float screenHeight) {
        if (hudTexture != null) {
            batch.draw(hudTexture, hudX, 0, LARGEUR_HUD, screenHeight);
        }
    }

    /**
     * Dessine la barre de vie en bas de l'écran avec le texte de vie.
     * @param batch Le SpriteBatch pour le rendu
     * @param x Position X de la barre
     * @param y Position Y de la barre
     * @param width Largeur de la barre
     * @param height Hauteur de la barre
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void dessinerBarreVie(SpriteBatch batch, float x, float y, float width, float height, float screenWidth, float screenHeight) {
        if (barreVieTexture == null) {
            return;
        }
        String texteVie = "Vie: " + gameState.getVie() + "/" + gameState.getVieMax();
        // Convertir les coordonnées relatives en coordonnées réelles
        float vieX = VIE_X_RATIO * screenWidth;
        float vieY = VIE_Y_RATIO * screenHeight;
        Texte.drawText(batch, texteVie, vieX, vieY, Color.BLACK, 30);
        batch.draw(barreVieTexture, x, y, width, height);
    }

    /**
     * Dessine tous les textes du HUD (lingots, numéro de vague).
     * @param batch Le SpriteBatch pour le rendu
     * @param hudX Position X du panneau HUD
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     */
    private void dessinerTextes(SpriteBatch batch, float hudX, float screenWidth, float screenHeight) {
        // Convertir les coordonnées relatives en coordonnées réelles
        float lingotsX = hudX + (LINGOTS_X_RATIO * screenWidth);
        float lingotsY = LINGOTS_Y_RATIO * screenHeight;
        float vagueX = hudX + (VAGUE_X_RATIO * screenWidth);
        float vagueY = VAGUE_Y_RATIO * screenHeight;
        float timerJeuX = hudX + (TIMER_JEU_X_RATIO * screenWidth);
        float timerJeuY = TIMER_JEU_Y_RATIO * screenHeight;
        timerJeu = timerJeu + Gdx.graphics.getDeltaTime();

        Texte.drawText(batch, Integer.toString((int)timerJeu), timerJeuX, timerJeuY, Color.BLACK, 20);

        Texte.drawText(batch, Integer.toString(gameState.getLingots()), lingotsX, lingotsY, Color.BLACK, 20);
        Texte.drawText(batch, Integer.toString(gameState.getNumeroVague()), vagueX, vagueY, Color.BLACK, 50);
    }

    // ------------------------------------------------------------------------
    // REGION : NETTOYAGE
    // ------------------------------------------------------------------------
    /**
     * Libère toutes les ressources utilisées par le HUD.
     */
    /**
     * Dessine les tours disponibles dans les slots du HUD.
     */
    private void dessinerTours(SpriteBatch batch, float hudX, float screenWidth, float screenHeight) {
        // Calculer la taille du slot en fonction de la résolution
        float slotSize = SLOT_SIZE_RATIO * screenWidth;

        // Calculer la position du premier slot
        float slot1X = SLOT1_X_RATIO * screenWidth;
        float slot1Y = SLOT1_Y_RATIO * screenHeight;

        // Dessiner la TourArcherLevel1 dans le premier slot
        if (tourArcherLevel1Texture != null) {
            batch.draw(tourArcherLevel1Texture, slot1X, slot1Y, slotSize, slotSize);
        }

        // Calculer la position du deuxième slot
        float slot2X = SLOT2_X_RATIO * screenWidth;
        float slot2Y = SLOT2_Y_RATIO * screenHeight;

        // Dessiner la TourMagieLevel1 dans le deuxième slot
        if (tourMagieLevel1Texture != null) {
            batch.draw(tourMagieLevel1Texture, slot2X, slot2Y, slotSize, slotSize);
        }
    }

    /**
     * Vérifie si un clic a été effectué sur un slot de tour dans le HUD.
     * @param screenX Position X du clic (coordonnées écran)
     * @param screenY Position Y du clic (coordonnées écran)
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @return Le numéro du slot cliqué (1 ou 2), ou 0 si aucun slot n'a été cliqué
     */
    public int getSlotClic(float screenX, float screenY, float screenWidth, float screenHeight) {
        // Calculer la taille du slot en fonction de la résolution
        float slotSize = SLOT_SIZE_RATIO * screenWidth;

        // Inverser Y car LibGDX a l'origine en haut, mais le HUD en bas
        float yInverse = screenHeight - screenY;

        // Vérifier le premier slot (TourArcher)
        float slot1X = SLOT1_X_RATIO * screenWidth;
        float slot1Y = SLOT1_Y_RATIO * screenHeight;
        if (screenX >= slot1X && screenX <= slot1X + slotSize &&
            yInverse >= slot1Y && yInverse <= slot1Y + slotSize) {
            return 1;
        }

        // Vérifier le deuxième slot (TourMagie)
        float slot2X = SLOT2_X_RATIO * screenWidth;
        float slot2Y = SLOT2_Y_RATIO * screenHeight;
        if (screenX >= slot2X && screenX <= slot2X + slotSize &&
            yInverse >= slot2Y && yInverse <= slot2Y + slotSize) {
            return 2;
        }

        return 0; // Aucun slot cliqué
    }

    /**
     * Vérifie si un clic a été effectué sur la tour dans le HUD.
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

    public void dispose() {
        if (barreVieTexture != null) {
            barreVieTexture.dispose();
        }
        if (hudTexture != null) {
            hudTexture.dispose();
        }
        if (tourArcherLevel1Texture != null) {
            tourArcherLevel1Texture.dispose();
        }
        if (tourMagieLevel1Texture != null) {
            tourMagieLevel1Texture.dispose();
        }
    }
}
