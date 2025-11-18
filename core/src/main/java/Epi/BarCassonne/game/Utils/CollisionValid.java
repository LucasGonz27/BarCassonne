package Epi.BarCassonne.game.Utils;

import Epi.BarCassonne.game.Entities.Towers.Tower;
import Epi.BarCassonne.game.Entities.Towers.TowerMagie;
import java.util.List;

/**
 * Valide si une position est valide pour placer une tour.
 */
public class CollisionValid {

    /** Distance minimale entre deux tours. */
    private static final float DISTANCE_MIN = 10f;

    /** Taille d'une tour d'archer. */
    private static final float TOUR_ARCHER_SIZE = 90f;

    /** Taille d'une tour de magie. */
    private static final float TOUR_MAGIE_SIZE = 120f;

    /** Zones non constructibles en ratios (0.0 à 1.0) : {minX, minY, maxX, maxY}. */
    private static final float[][] ZONES = {
        {0.3355f, 0.6161f, 0.4954f, 0.8151f}, // Arbre milieu gauche
        {0.0f, 0.3720f, 0.6066f, 0.5237f},   // Route début
        {0.4737f, 0.3731f, 0.6250f, 0.7280f}, // Route intersection
        {0.6191f, 0.5796f, 0.9961f, 0.7484f}, // Route fin
        {0.0f, 0.0043f, 0.9974f, 0.1957f},    // Bordure 1
        {0.7382f, 0.7903f, 0.9013f, 0.9978f}, // Arbre droite
        {0.0020f, 0.8473f, 0.5632f, 0.9989f}, // Bordure 2
        {0.0013f, 0.8151f, 0.4332f, 0.9989f}, // Bordure 3
        {0.0441f, 0.1366f, 0.2243f, 0.3409f},
        {0.0007f, 0.6419f, 0.3368f, 0.8204f},
        {0.0007f, 0.6263f, 0.2934f, 0.6452f},
        {0.0007f, 0.5952f, 0.2561f, 0.6402f},
        {0.0f, 0.5763f, 0.0987f, 0.6043f},
        {0.0f, 0.5538f, 0.0691f, 0.5871f},
    };

    /** Largeur de la carte. */
    private float mapWidth;

    /** Hauteur de la carte. */
    private float mapHeight;

    /**
     * Crée un validateur de collision pour une carte de dimensions données.
     * @param mapWidth Largeur de la carte
     * @param mapHeight Hauteur de la carte
     */
    public CollisionValid(float mapWidth, float mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    /**
     * Met à jour les dimensions de la carte.
     * @param mapWidth Nouvelle largeur de la carte
     * @param mapHeight Nouvelle hauteur de la carte
     */
    public void mettreAJourDimensions(float mapWidth, float mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    /**
     * Vérifie si une position est valide pour placer une tour.
     * @param x Position X de la tour
     * @param y Position Y de la tour
     * @param tourSize Taille de la tour à placer
     * @param toursExistantes Liste des tours déjà placées
     * @return true si la position est valide, false sinon
     */
    public boolean estPositionValide(float x, float y, float tourSize, List<Tower> toursExistantes) {
        // Vérifier les zones interdites
        for (float[] zone : ZONES) {
            float minX = zone[0] * mapWidth;
            float minY = zone[1] * mapHeight;
            float maxX = zone[2] * mapWidth;
            float maxY = zone[3] * mapHeight;

            if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                return false;
            }
        }

        // Vérifier la distance avec les autres tours
        if (toursExistantes != null) {
            for (Tower tour : toursExistantes) {
                float tailleTour = (tour instanceof TowerMagie) ? TOUR_MAGIE_SIZE : TOUR_ARCHER_SIZE;
                float distance = (float) Math.sqrt(
                    (x - tour.getPositionX()) * (x - tour.getPositionX()) +
                    (y - tour.getPositionY()) * (y - tour.getPositionY())
                );
                float distanceMin = (tourSize + tailleTour) / 2f + DISTANCE_MIN;

                if (distance < distanceMin) {
                    return false;
                }
            }
        }

        return true;
    }

}
