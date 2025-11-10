package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Gestionnaire de l'image de fond.
 * Charge et affiche l'image de fond du jeu.
 */
public class BackgroundManager {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    private Texture background;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouveau gestionnaire de fond.
     * @param path Le chemin vers l'image de fond
     */
    public BackgroundManager(String path) {
        background = TextureManager.chargerTexture(path);
    }

    // ------------------------------------------------------------------------
    // REGION : RENDU
    // ------------------------------------------------------------------------
    /**
     * Dessine l'image de fond pour remplir l'écran.
     * @param batch Le SpriteBatch pour le rendu
     * @param screenWidth La largeur de l'écran
     * @param screenHeight La hauteur de l'écran
     */
    public void renderFillScreen(SpriteBatch batch, float screenWidth, float screenHeight) {
        if (background != null) {
            batch.draw(background, 0, 0, screenWidth, screenHeight);
        }
    }

    // ------------------------------------------------------------------------
    // REGION : NETTOYAGE
    // ------------------------------------------------------------------------
    /**
     * Libère les ressources utilisées par le gestionnaire.
     */
    public void dispose() {
        TextureManager.libererTexture(background);
    }
}
