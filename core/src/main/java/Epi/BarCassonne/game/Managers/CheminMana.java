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
    protected List<Vector2> cheminPrincipal;

    private static final float[][] CHEMIN_POINTS_RELATIFS = {
        {0.0f, 0.376f},      // Point 0 : début à gauche, 37.6% de la hauteur
        {0.494f, 0.430f},    // Point 1 : 49.4% de la largeur, 43.0% de la hauteur
        {0.494f, 0.634f},    // Point 2 : 49.4% de la largeur, 63.4% de la hauteur
        {0.961f, 0.634f}     // Point 3 : 96.1% de la largeur, 63.4% de la hauteur
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
