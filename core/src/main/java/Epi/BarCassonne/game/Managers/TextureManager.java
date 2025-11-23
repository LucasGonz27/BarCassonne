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
     * @param path Le chemin vers la texture
     * @return La texture chargée, ou null si le chargement échoue ou si LibGDX n'est pas initialisé
     */
    public static Texture chargerTexture(String path) {
        try {
            // Vérifier si LibGDX est initialisé avec un contexte OpenGL
            if (Gdx.files == null || Gdx.gl == null) {
                // En mode test (headless), on retourne null car on ne peut pas créer de textures sans OpenGL
                return null;
            }
            Texture texture = new Texture(Gdx.files.internal(path));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return texture;
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la texture: " + path);
            // Essayer de créer une texture par défaut seulement si OpenGL est disponible
            try {
                if (Gdx.gl != null) {
                    return new Texture(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
                }
            } catch (Exception e2) {
                // Ignorer
            }
            return null;
        }
    }

    /**
     * Libère une texture de manière sécurisée.
     * @param texture La texture à libérer (peut être null)
     */
    public static void libererTexture(Texture texture) {
        if (texture != null) {
            texture.dispose();
        }
    }
}
