package Epi.BarCassonne.game.UI;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import Epi.BarCassonne.game.Managers.GameState;
import Epi.BarCassonne.game.Managers.HUDBackgroundManager;

/**
 * Affiche l'interface du jeu (barre de vie, lingots, vie, vague).
 */
public class HUD {

    // ------------------------------------------------------------------------
    // REGION : CONSTANTES
    // ------------------------------------------------------------------------
    // Positions du texte dans le panneau HUD (à droite)
    private static final float LINGOTS_X = 30f;
    private static final float LINGOTS_Y = 200f;
    private static final float VIE_X = 30f;
    private static final float VIE_Y = 100f;
    private static final float VAGUE_X = 30f;
    private static final float VAGUE_Y = 50f;

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    private GameState gameState;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouveau HUD.
     * @param gameState L'état du jeu à afficher
     */
    public HUD(GameState gameState) {
        this.gameState = gameState;
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
        this.shapeRenderer = new ShapeRenderer();
    }

    // ------------------------------------------------------------------------
    // REGION : RENDU
    // ------------------------------------------------------------------------
    /**
     * Dessine tout le HUD (barre de vie horizontale + panneau vertical à droite).
     * @param batch Le SpriteBatch pour le rendu
     * @param hudBackgroundManager Le gestionnaire des images de fond du HUD
     * @param screenWidth La largeur de l'écran
     * @param screenHeight La hauteur de l'écran
     * @param largeurHUD La largeur du panneau HUD à droite
     * @param hauteurBarreVie La hauteur de la barre de vie
     */
    public void render(SpriteBatch batch, HUDBackgroundManager hudBackgroundManager,
                       float screenWidth, float screenHeight, float largeurHUD, float hauteurBarreVie) {
        
        float mapWidth = screenWidth - largeurHUD;
        float barreVieY = screenHeight - hauteurBarreVie;
        float hudX = screenWidth - largeurHUD;
        
        // Images de fond
        batch.begin();
        hudBackgroundManager.renderBarreVie(batch, 0, barreVieY, mapWidth, hauteurBarreVie);
        hudBackgroundManager.renderHUD(batch, hudX, 0, largeurHUD, screenHeight);
        batch.end();

        // Barre de vie
        dessinerBarreVie(batch, 0, barreVieY, mapWidth, hauteurBarreVie);
        
        // Textes
        dessinerTextes(batch, hudX, 0, largeurHUD, screenHeight);
    }

    /**
     * Dessine la barre de vie (rectangle vert sur fond rouge).
     * @param batch Le SpriteBatch pour le rendu
     * @param x Position X
     * @param y Position Y
     * @param width Largeur de la barre
     * @param height Hauteur de la barre
     */
    private void dessinerBarreVie(SpriteBatch batch, float x, float y, float width, float height) {
        float viePourcentage = gameState.getViePourcentage();
        float largeurVie = width * viePourcentage;

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        // Fond rouge
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x, y, width, height);
        
        // Barre verte (vie actuelle)
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x, y, largeurVie, height);
        
        shapeRenderer.end();
    }

    /**
     * Dessine les textes du HUD (lingots, vie, vague).
     * @param batch Le SpriteBatch pour le rendu
     * @param hudX Position X du panneau HUD
     * @param hudY Position Y du panneau HUD
     * @param hudWidth Largeur du panneau HUD
     * @param hudHeight Hauteur du panneau HUD
     */
    private void dessinerTextes(SpriteBatch batch, float hudX, float hudY, float hudWidth, float hudHeight) {
        batch.begin();
        
        font.draw(batch, "Lingots: " + gameState.getLingots(), hudX + LINGOTS_X, hudY + LINGOTS_Y);
        font.draw(batch, "Vie: " + gameState.getVie() + "/" + gameState.getVieMax(), hudX + VIE_X, hudY + VIE_Y);
        font.draw(batch, "Vague: " + gameState.getNumeroVague(), hudX + VAGUE_X, hudY + VAGUE_Y);
        
        batch.end();
    }

    // ------------------------------------------------------------------------
    // REGION : NETTOYAGE
    // ------------------------------------------------------------------------
    /**
     * Libère les ressources utilisées par le HUD.
     */
    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
    }
}
