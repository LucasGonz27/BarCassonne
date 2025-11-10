package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Gestionnaire de l'image de fond du HUD.
 * Charge et affiche les images du HUD (barre de vie et panneau HUD).
 */
public class HUDBackgroundManager {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    private Texture barreVieTexture;
    private Texture hudTexture;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouveau gestionnaire de fond HUD.
     * @param barreViePath Le chemin vers l'image de la barre de vie
     * @param hudPath Le chemin vers l'image du HUD à droite
     */
    public HUDBackgroundManager(String barreViePath, String hudPath) {
        barreVieTexture = TextureManager.chargerTexture(barreViePath);
        hudTexture = TextureManager.chargerTexture(hudPath);
    }

    // ------------------------------------------------------------------------
    // REGION : RENDU
    // ------------------------------------------------------------------------
    /**
     * Dessine l'image de la barre de vie.
     * @param batch Le SpriteBatch pour le rendu
     * @param x Position X
     * @param y Position Y
     * @param width Largeur
     * @param height Hauteur
     */
    public void renderBarreVie(SpriteBatch batch, float x, float y, float width, float height) {
        if (barreVieTexture != null) {
            batch.draw(barreVieTexture, x, y, width, height);
        }
    }

    /**
     * Dessine l'image du HUD à droite.
     * @param batch Le SpriteBatch pour le rendu
     * @param x Position X
     * @param y Position Y
     * @param width Largeur
     * @param height Hauteur
     */
    public void renderHUD(SpriteBatch batch, float x, float y, float width, float height) {
        if (hudTexture != null) {
            batch.draw(hudTexture, x, y, width, height);
        }
    }

    // ------------------------------------------------------------------------
    // REGION : NETTOYAGE
    // ------------------------------------------------------------------------
    /**
     * Libère les ressources utilisées par le gestionnaire.
     */
    public void dispose() {
        TextureManager.libererTexture(barreVieTexture);
        TextureManager.libererTexture(hudTexture);
    }
}

