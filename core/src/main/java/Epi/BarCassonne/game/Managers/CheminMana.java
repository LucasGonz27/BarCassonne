package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire du chemin que suivent les ennemis.
 * Définit les points de passage sur la carte.
 */
public class CheminMana {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    /** Liste des points du chemin principal que suivent les ennemis. */
    protected List<Vector2> cheminPrincipal;

    /** Points du chemin en coordonnées relatives*/
    private static final float[][] CHEMIN_POINTS_RELATIFS = {
        {0.0f, 0.376f},
        {0.494f, 0.430f},
        {0.494f, 0.634f},
        {0.961f, 0.634f}
    };

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée le chemin principal avec les points de passage.
     * @param mapWidth Largeur de la map actuelle
     * @param mapHeight Hauteur de la map actuelle
     */
    public CheminMana(float mapWidth, float mapHeight) {
        cheminPrincipal = new ArrayList<>();
        mettreAJourChemin(mapWidth, mapHeight);
    }

    /**
     * Met à jour le chemin en fonction des nouvelles dimensions de la map.
     * @param mapWidth Largeur de la map
     * @param mapHeight Hauteur de la map
     */
    public void mettreAJourChemin(float mapWidth, float mapHeight) {
        cheminPrincipal.clear();

        // Convertir les coordonnées relatives en coordonnées réelles
        for (float[] point : CHEMIN_POINTS_RELATIFS) {
            float x = point[0] * mapWidth;
            float y = point[1] * mapHeight;
            cheminPrincipal.add(new Vector2(x, y));
        }
    }

    // ------------------------------------------------------------------------
    // REGION : GETTERS
    // ------------------------------------------------------------------------
    /**
     * @return La liste des points du chemin principal
     */
    public List<Vector2> getCheminPrincipal() {
        return cheminPrincipal;
    }
}
