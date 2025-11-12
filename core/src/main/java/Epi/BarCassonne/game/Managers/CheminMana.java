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
    
    // Résolution de référence pour les coordonnées de la map
    // (1920 - 400 pour le HUD, 1080 - 170 pour la barre de vie)
    private static final float REF_MAP_WIDTH = 1520f;  // 1920 - 400 (HUD)
    private static final float REF_MAP_HEIGHT = 910f;  // 1080 - 170 (barre de vie)
    
    // Coordonnées relatives du chemin (basées sur la résolution de référence)
    private static final float[][] CHEMIN_POINTS_RELATIFS = {
        {0f / REF_MAP_WIDTH, 350f / REF_MAP_HEIGHT},      // Point 0
        {750f / REF_MAP_WIDTH, 400f / REF_MAP_HEIGHT},   // Point 1
        {750f / REF_MAP_WIDTH, 590f / REF_MAP_HEIGHT},   // Point 2
        {1460f / REF_MAP_WIDTH, 590f / REF_MAP_HEIGHT}   // Point 3
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
