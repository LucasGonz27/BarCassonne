package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Gestionnaire utilitaire pour charger et gérer les textures.
 * Évite la duplication de code pour le chargement de textures.
 */
public class TextureManager {

    // ------------------------------------------------------------------------
    // REGION : MÉTHODES UTILITAIRES
    // ------------------------------------------------------------------------
    /**
     * Charge une texture depuis un chemin de fichier.
     * Gère les erreurs et retourne une texture par défaut si le chargement échoue.
     * 
     * @param path Le chemin vers la texture
     * @return La texture chargée, ou une texture par défaut si le chargement échoue
     */
    public static Texture chargerTexture(String path) {
        return chargerTexture(path, true);
    }

    /**
     * Charge une texture depuis un chemin de fichier.
     * 
     * @param path Le chemin vers la texture
     * @param creerFallback Si true, crée une texture par défaut en cas d'erreur
     * @return La texture chargée, ou null/fallback selon le paramètre
     */
    public static Texture chargerTexture(String path, boolean creerFallback) {
        if (path == null || path.isEmpty()) {
            if (creerFallback) {
                return new Texture(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
            }
            return null;
        }

        try {
            Texture texture = new Texture(Gdx.files.internal(path));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return texture;
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la texture: " + path);
            if (creerFallback) {
                return new Texture(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
            }
            return null;
        }
    }

    /**
     * Libère une texture de manière sécurisée.
     * 
     * @param texture La texture à libérer (peut être null)
     */
    public static void libererTexture(Texture texture) {
        if (texture != null) {
            texture.dispose();
        }
    }
}

