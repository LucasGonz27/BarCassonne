package Epi.BarCassonne.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilitaire pour dessiner du texte avec différentes polices, couleurs et tailles.
 */
public class Texte {

    /** Générateur de polices FreeType. */
    private static FreeTypeFontGenerator generator = null;
    
    /** Cache des polices générées, indexées par leur taille. */
    private static Map<Integer, BitmapFont> fontCache = new HashMap<>();

    /**
     * Initialise le générateur de font (appelé automatiquement au premier usage).
     */
    private static void initGenerator() {
        if (generator == null) {
            try {
                generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de la font: " + e.getMessage());
            }
        }
    }

    /**
     * Obtient une font avec la taille spécifiée (utilise un cache pour éviter de régénérer).
     * @param size La taille de la police
     * @return La font avec la taille spécifiée
     */
    public static BitmapFont getFont(int size) {
        initGenerator();

        if (generator == null) {
            // Fallback sur la police par défaut
            BitmapFont defaultFont = new BitmapFont();
            defaultFont.getData().setScale(size / 20f);
            return defaultFont;
        }

        // Vérifier si la font est déjà en cache
        if (fontCache.containsKey(size)) {
            return fontCache.get(size);
        }

        // Générer une nouvelle font
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        fontCache.put(size, font);
        return font;
    }

        /**
     * Dessine du texte à l'écran avec la police, la couleur et la taille spécifiées.
     * Note: Le SpriteBatch doit déjà être ouvert (batch.begin() appelé avant).
     * @param batch Le SpriteBatch pour le rendu (déjà ouvert)
     * @param text Le texte à afficher
     * @param x Position X
     * @param y Position Y
     * @param color La couleur du texte
     * @param size La taille de la police
     */
    public static void drawText(SpriteBatch batch, String text, float x, float y, Color color, int size) {
        BitmapFont font = getFont(size);
        font.setColor(color);
        font.draw(batch, text, x, y);
    }
    
    /**
     * Dessine du texte avec la police par défaut de LibGDX.
     * @param batch Le SpriteBatch pour le rendu
     * @param text Le texte à afficher
     * @param x Position X
     * @param y Position Y
     * @param color La couleur du texte
     * @param size La taille de la police
     */
    public static void drawTextDefault(SpriteBatch batch, String text, float x, float y, Color color, int size) {
        BitmapFont defaultFont = new BitmapFont();
        defaultFont.getData().setScale(size / 20f);
        defaultFont.setColor(color);
        defaultFont.draw(batch, text, x, y);
    }

    /**
     * Libère toutes les ressources (à appeler lors de la fermeture du jeu).
     */
    public static void dispose() {
        // Libérer toutes les fonts en cache
        for (BitmapFont font : fontCache.values()) {
            if (font != null) {
                font.dispose();
            }
        }
        fontCache.clear();

        // Libérer le générateur
        if (generator != null) {
            generator.dispose();
            generator = null;
        }
    }


}
