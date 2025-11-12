package Epi.BarCassonne.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import Epi.BarCassonne.game.Managers.GameState;
import Epi.BarCassonne.game.Managers.TextureManager;
import Epi.BarCassonne.game.Utils.Texte;

/**
 * Affiche l'interface du jeu (barre de vie, lingots, vie, vague).
 */
public class HUD {

    public static final float HAUTEUR_BARRE_VIE = 150f;
    public static final float LARGEUR_HUD = 400f;

    // Résolution de référence pour les coordonnées (basée sur une résolution typique)
    private static final float REF_WIDTH = 1920f;
    private static final float REF_HEIGHT = 1080f;

    // Coordonnées relatives (0.0 à 1.0) ou en pixels de référence
    // Positions relatives au viewport du HUD
    private static final float LINGOTS_X_RATIO = 295f / REF_WIDTH;  // Ratio de la position X
    private static final float LINGOTS_Y_RATIO = 845f / REF_HEIGHT; // Ratio de la position Y

    private static final float VIE_X_RATIO = 30f / REF_WIDTH;
    private static final float VIE_Y_RATIO = 100f / REF_HEIGHT;

    private static final float VAGUE_X_RATIO = 200f / REF_WIDTH;
    private static final float VAGUE_Y_RATIO = 970f / REF_HEIGHT;

    private GameState gameState;
    private Texture barreVieTexture;
    private Texture hudTexture;

    public HUD(GameState gameState) {
        this.gameState = gameState;
        this.barreVieTexture = TextureManager.chargerTexture("HUD/BarreDeVie.png");
        this.hudTexture = TextureManager.chargerTexture("HUD/HUD.png");
    }

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
        batch.end();
    }

    private void dessinerPanneauHUD(SpriteBatch batch, float hudX, float screenHeight) {
        if (hudTexture != null) {
            batch.draw(hudTexture, hudX, 0, LARGEUR_HUD, screenHeight);
        }
    }

    /**
     * Dessine la barre de vie
     */
    private void dessinerBarreVie(SpriteBatch batch, float x, float y, float width, float height, float screenWidth, float screenHeight) {
        if (barreVieTexture == null) {
            return;
        }
        String texteVie = "Vie: " + gameState.getVie() + "/" + gameState.getVieMax();
        // Convertir les coordonnées relatives en coordonnées réelles
        float vieX = VIE_X_RATIO * screenWidth;
        float vieY = VIE_Y_RATIO * screenHeight;
        Texte.drawText(batch, texteVie, vieX, vieY, Color.BLACK, 20);
        batch.draw(barreVieTexture, x, y, width, height);
    }

    private void dessinerTextes(SpriteBatch batch, float hudX, float screenWidth, float screenHeight) {
        // Convertir les coordonnées relatives en coordonnées réelles
        float lingotsX = hudX + (LINGOTS_X_RATIO * screenWidth);
        float lingotsY = LINGOTS_Y_RATIO * screenHeight;
        float vagueX = hudX + (VAGUE_X_RATIO * screenWidth);
        float vagueY = VAGUE_Y_RATIO * screenHeight;
        
        Texte.drawText(batch, Integer.toString(gameState.getLingots()), lingotsX, lingotsY, Color.BLACK, 20);
        Texte.drawText(batch, Integer.toString(gameState.getNumeroVague()), vagueX, vagueY, Color.BLACK, 50);
    }

    public void dispose() {
        if (barreVieTexture != null) {
            barreVieTexture.dispose();
        }
        if (hudTexture != null) {
            hudTexture.dispose();
        }
    }
}
