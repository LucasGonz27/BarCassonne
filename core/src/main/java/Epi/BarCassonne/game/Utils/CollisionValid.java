package Epi.BarCassonne.game.Utils;

import com.badlogic.gdx.math.Vector2;
import Epi.BarCassonne.game.Batiments.Batiment;
import Epi.BarCassonne.game.Batiments.TourArcher;
import Epi.BarCassonne.game.Batiments.TourMagie;
import java.util.ArrayList;
import java.util.List;

/**
 * Valide si une position est valide pour placer une tour.
 * Système basé sur des vecteurs (Vector2) pour définir les zones non constructibles.
 */
public class CollisionValid {

    // Résolution de référence pour les coordonnées de la map
    private static final float REF_MAP_WIDTH = 1520f;
    private static final float REF_MAP_HEIGHT = 930f;
    
    // Distance minimale entre deux tours (en unités monde)
    // Modifier cette valeur pour ajuster l'espacement entre les tours
    private static final float DISTANCE_MINIMALE_ENTRE_TOURS = 10f;

    // Zones non constructibles définies par des vecteurs (coin inférieur gauche et coin supérieur droit)
    // Format : {minX_ratio, minY_ratio, maxX_ratio, maxY_ratio}
    // Les valeurs sont en ratios (0.0 à 1.0) pour s'adapter à la taille de l'écran
    private static final float[][] ZONES_NON_CONSTRUCTIBLES_RATIOS = {

        // Arbre milieu gauche
        {510f / REF_MAP_WIDTH, 573f / REF_MAP_HEIGHT, 753f / REF_MAP_WIDTH, 758f / REF_MAP_HEIGHT},

        // Route Gauche droit début
        {0f / REF_MAP_WIDTH, 346f / REF_MAP_HEIGHT, 922f / REF_MAP_WIDTH, 487f / REF_MAP_HEIGHT},

        // Route intersection haut
        {720f / REF_MAP_WIDTH, 347f / REF_MAP_HEIGHT, 950f / REF_MAP_WIDTH, 677f / REF_MAP_HEIGHT},

        // Route Gauche droit fin
        {941f / REF_MAP_WIDTH, 539f / REF_MAP_HEIGHT, 1514f / REF_MAP_WIDTH, 696f / REF_MAP_HEIGHT},

        // delimitation map
        {0f / REF_MAP_WIDTH, 4f / REF_MAP_HEIGHT, 1516f / REF_MAP_WIDTH, 182f / REF_MAP_HEIGHT},

        // arbre haut droite
        {1122f / REF_MAP_WIDTH, 735f / REF_MAP_HEIGHT, 1370f / REF_MAP_WIDTH, 928f / REF_MAP_HEIGHT},

        // delimitation map 2
        {3f / REF_MAP_WIDTH, 788f / REF_MAP_HEIGHT, 856f / REF_MAP_WIDTH, 929f / REF_MAP_HEIGHT},

        // delimitation map 3
        {2f / REF_MAP_WIDTH, 758f / REF_MAP_HEIGHT, 658f / REF_MAP_WIDTH, 929f / REF_MAP_HEIGHT},


        {67f / REF_MAP_WIDTH, 127f / REF_MAP_HEIGHT, 341f / REF_MAP_WIDTH, 317f / REF_MAP_HEIGHT},

        {1f / REF_MAP_WIDTH, 597f / REF_MAP_HEIGHT, 512f / REF_MAP_WIDTH, 763f / REF_MAP_HEIGHT},

        {1f / REF_MAP_WIDTH, 582f / REF_MAP_HEIGHT, 446f / REF_MAP_WIDTH, 600f / REF_MAP_HEIGHT},

        {1f / REF_MAP_WIDTH, 553f / REF_MAP_HEIGHT, 389f / REF_MAP_WIDTH, 595f / REF_MAP_HEIGHT},

        {0f / REF_MAP_WIDTH, 536f / REF_MAP_HEIGHT, 150f / REF_MAP_WIDTH, 562f / REF_MAP_HEIGHT},

        {0f / REF_MAP_WIDTH, 515f / REF_MAP_HEIGHT, 105f / REF_MAP_WIDTH, 546f / REF_MAP_HEIGHT},
    };

    private float mapWidth;
    private float mapHeight;
    private List<ZoneCollision> zonesCollision;

    /**
     * Classe interne pour représenter une zone de collision avec des vecteurs.
     */
    private static class ZoneCollision {
        Vector2 min; // Coin inférieur gauche
        Vector2 max; // Coin supérieur droit

        ZoneCollision(Vector2 min, Vector2 max) {
            this.min = min;
            this.max = max;
        }

        /**
         * Vérifie si un point est dans cette zone.
         */
        boolean contient(Vector2 point) {
            return point.x >= min.x && point.x <= max.x &&
                   point.y >= min.y && point.y <= max.y;
        }
    }

    /**
     * Crée un validateur de collision.
     * @param mapWidth Largeur de la map
     * @param mapHeight Hauteur de la map
     */
    public CollisionValid(float mapWidth, float mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        initialiserZones();
    }

    /**
     * Initialise les zones de collision à partir des ratios.
     */
    private void initialiserZones() {
        zonesCollision = new ArrayList<>();

        for (float[] zoneRatio : ZONES_NON_CONSTRUCTIBLES_RATIOS) {
            // Convertir les ratios en coordonnées réelles
            float minX = zoneRatio[0] * mapWidth;
            float minY = zoneRatio[1] * mapHeight;
            float maxX = zoneRatio[2] * mapWidth;
            float maxY = zoneRatio[3] * mapHeight;

            Vector2 min = new Vector2(minX, minY);
            Vector2 max = new Vector2(maxX, maxY);

            zonesCollision.add(new ZoneCollision(min, max));
        }
    }

    /**
     * Met à jour les dimensions de la map et recalcule les zones.
     * @param mapWidth Nouvelle largeur
     * @param mapHeight Nouvelle hauteur
     */
    public void mettreAJourDimensions(float mapWidth, float mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        initialiserZones(); // Recalculer les zones avec les nouvelles dimensions
    }

    /**
     * Vérifie si une position est valide pour placer une tour.
     * @param x Position X en coordonnées monde
     * @param y Position Y en coordonnées monde
     * @param tourSize Taille de la tour (pour vérifier les collisions)
     * @param toursExistantes Liste des tours déjà placées sur le terrain
     * @return true si la position est valide, false sinon
     */
    public boolean estPositionValide(float x, float y, float tourSize, List<Batiment> toursExistantes) {
        Vector2 position = new Vector2(x, y);

        // Vérifier si la position est dans une zone de collision
        for (ZoneCollision zone : zonesCollision) {
            if (zone.contient(position)) {
                return false;
            }
        }
        
        // Vérifier la distance minimale avec les tours existantes
        if (toursExistantes != null && !toursExistantes.isEmpty()) {
            if (!estDistanceValideAvecTours(x, y, tourSize, toursExistantes)) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Vérifie si la position est à une distance suffisante des tours existantes.
     * @param x Position X de la nouvelle tour
     * @param y Position Y de la nouvelle tour
     * @param nouvelleTourSize Taille de la nouvelle tour
     * @param toursExistantes Liste des tours déjà placées
     * @return true si la distance est suffisante, false sinon
     */
    private boolean estDistanceValideAvecTours(float x, float y, float nouvelleTourSize, List<Batiment> toursExistantes) {
        // Tailles des tours selon leur type (doivent correspondre à TowerManager)
        final float TOUR_ARCHER_SIZE = 90f;
        final float TOUR_MAGIE_SIZE = 120f;
        
        for (Batiment tour : toursExistantes) {
            float tourX = tour.getPositionX();
            float tourY = tour.getPositionY();
            
            // Déterminer la taille de la tour existante selon son type
            float tailleTourExistante;
            if (tour instanceof TourArcher) {
                tailleTourExistante = TOUR_ARCHER_SIZE;
            } else if (tour instanceof TourMagie) {
                tailleTourExistante = TOUR_MAGIE_SIZE;
            } else {
                // Par défaut, utiliser la taille de TourArcher
                tailleTourExistante = TOUR_ARCHER_SIZE;
            }
            
            // Calculer la distance euclidienne entre les centres des tours
            float distanceX = x - tourX;
            float distanceY = y - tourY;
            float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            
            // Distance minimale = rayon de la nouvelle tour + rayon de la tour existante + marge
            float rayonNouvelleTour = nouvelleTourSize / 2f;
            float rayonTourExistante = tailleTourExistante / 2f;
            float distanceMinimale = rayonNouvelleTour + rayonTourExistante + DISTANCE_MINIMALE_ENTRE_TOURS;
            
            // Si la distance est inférieure à la distance minimale, la position est invalide
            if (distance < distanceMinimale) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Retourne les zones de collision pour le rendu visuel.
     * @return Liste des zones au format {minX, minY, maxX, maxY} en coordonnées monde
     */
    public float[][] getZonesNonConstructibles() {
        float[][] zones = new float[zonesCollision.size()][4];
        for (int i = 0; i < zonesCollision.size(); i++) {
            ZoneCollision zone = zonesCollision.get(i);
            zones[i][0] = zone.min.x;  // minX
            zones[i][1] = zone.min.y;  // minY
            zones[i][2] = zone.max.x - zone.min.x;  // width
            zones[i][3] = zone.max.y - zone.min.y;  // height
        }
        return zones;
    }
}
