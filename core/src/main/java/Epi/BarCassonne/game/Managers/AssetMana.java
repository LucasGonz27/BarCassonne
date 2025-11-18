package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.assets.AssetManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire des assets (animations, textures).
 * Charge et stocke les animations des ennemis.
 */
public class AssetMana {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    /** Gestionnaire d'assets LibGDX pour charger les textures. */
    private static AssetManager manager = new AssetManager();
    
    /** Map stockant toutes les animations chargées, indexées par leur nom. */
    private static Map<String, Animation<TextureRegion>> animations = new HashMap<>();

    // ------------------------------------------------------------------------
    // REGION : CHARGEMENT
    // ------------------------------------------------------------------------
    /**
     * Charge un sprite sheet et crée une animation.
     * Le sprite sheet doit être organisé en 3 colonnes x 8 lignes.
     * @param name Le nom du fichier (sans extension)
     */
    public static void loadAnimation(String name) {
        String path = "sprites/" + name + ".png";
        
        // Charger la texture si pas déjà chargée
        if (!manager.isLoaded(path)) {
            manager.load(path, Texture.class);
            manager.finishLoading();
        }

        Texture spriteSheet = manager.get(path, Texture.class);

        // Découper le sprite sheet (3 colonnes, 8 lignes)
        TextureRegion[][] tmp = TextureRegion.split(
            spriteSheet,
            spriteSheet.getWidth() / 3,
            spriteSheet.getHeight() / 8
        );

        // Convertir en tableau 1D (tmp est [lignes][colonnes] donc [8][3])
        TextureRegion[] frames = new TextureRegion[3 * 8];
        int index = 0;
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 3; colonne++) {
                frames[index++] = tmp[ligne][colonne];
            }
        }

        // Créer l'animation (0.15s par frame, en boucle)
        Animation<TextureRegion> animation = new Animation<>(0.15f, frames);
        animations.put(name, animation);
    }

    // ------------------------------------------------------------------------
    // REGION : ACCÈS
    // ------------------------------------------------------------------------
    /**
     * Récupère une animation chargée.
     * @param name Le nom de l'animation
     * @return L'animation, ou null si non trouvée
     */
    public static Animation<TextureRegion> getAnimation(String name) {
        return animations.get(name);
    }

    // ------------------------------------------------------------------------
    // REGION : NETTOYAGE
    // ------------------------------------------------------------------------
    /**
     * Libère tous les assets chargés.
     */
    public static void dispose() {
        manager.dispose();
        animations.clear();
    }
}
