package Epi.BarCassonne.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import Epi.BarCassonne.game.Managers.GameState;
import Epi.BarCassonne.game.Managers.TextureManager;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Affiche l'interface du jeu (barre de vie, lingots, vie, vague).
 */
public class HUD {

    public static final float HAUTEUR_BARRE_VIE = 150f;
    public static final float LARGEUR_HUD = 400f;

    private static final float LINGOTS_X = 295f;
    private static final float LINGOTS_Y = 845f;

    private static final float VIE_X = 30f;
    private static final float VIE_Y = 100f;

    private static final float VAGUE_X = 200f;
    private static final float VAGUE_Y = 970f;

    private GameState gameState;
    private BitmapFont font;
    private BitmapFont fontLingots;
    private BitmapFont fontVague;
    private Texture barreVieTexture;
    private Texture hudTexture;

    public HUD(GameState gameState) {
        this.gameState = gameState;
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);



        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Pieces of Eight.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.color = Color.BLACK;
            parameter.size = 20;
            this.fontLingots = generator.generateFont(parameter);
            generator.dispose();
        } catch (Exception e) {
            this.fontLingots = new BitmapFont();
            this.fontLingots.setColor(Color.BLACK);
            this.fontLingots.getData().setScale(1.5f);
        }

        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Pieces of Eight.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.color = Color.BLACK;
            parameter.size = 50;
            this.fontVague = generator.generateFont(parameter);
            generator.dispose();
        } catch (Exception e) {
            this.fontVague = new BitmapFont();
            this.fontVague.setColor(Color.BLACK);
            this.fontVague.getData().setScale(4.0f);
        }

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
        dessinerBarreVie(batch, 0, barreVieY, mapWidth, HAUTEUR_BARRE_VIE);
        dessinerPanneauHUD(batch, hudX, screenHeight);
        dessinerTextes(batch, hudX);
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
    private void dessinerBarreVie(SpriteBatch batch, float x, float y, float width, float height) {
        if (barreVieTexture == null) {
            return;
        }
        font.draw(batch, "Vie: " + gameState.getVie() + "/" + gameState.getVieMax(), VIE_X, VIE_Y);
        batch.draw(barreVieTexture, x, y, width, height);
    }

    private void dessinerTextes(SpriteBatch batch, float hudX) {
        fontLingots.draw(batch, Integer.toString(gameState.getLingots()), hudX + LINGOTS_X, LINGOTS_Y);
        fontVague.draw(batch, Integer.toString(gameState.getNumeroVague()), hudX + VAGUE_X, VAGUE_Y);
    }

    public void dispose() {
        font.dispose();
        fontLingots.dispose();
        fontVague.dispose();
        if (barreVieTexture != null) {
            barreVieTexture.dispose();
        }
        if (hudTexture != null) {
            hudTexture.dispose();
        }
    }
}
