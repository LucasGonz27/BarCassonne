package Epi.BarCassonne.game.Utils;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import Epi.BarCassonne.game.UI.HUD;

/**
 * Utilitaire pour convertir les coordonnées entre différents systèmes de coordonnées.
 * Gère la conversion entre les coordonnées écran (origine en haut à gauche)
 * et les coordonnées monde (système de jeu).
 */
public class CoordinateConverter {
    
    /**
     * Convertit les coordonnées écran en coordonnées monde de la map.
     * 
     * @param screenX Coordonnée X de l'écran (origine en haut à gauche)
     * @param screenY Coordonnée Y de l'écran (origine en haut à gauche)
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapViewport Le viewport de la map pour obtenir les dimensions du monde
     * @return Les coordonnées monde, ou null si hors limites de la zone de jeu
     */
    public static Vector3 convertirEcranVersMonde(
            float screenX, 
            float screenY, 
            float screenWidth, 
            float screenHeight, 
            Viewport mapViewport) {
        
        // Calculer les dimensions de la zone de jeu (sans HUD et barre de vie)
        float hauteurBarreVie = HUD.getHauteurBarreVie(screenHeight);
        float largeurHUD = HUD.getLargeurHUD(screenWidth);
        float mapWidth = screenWidth - largeurHUD;
        float mapHeight = screenHeight - hauteurBarreVie;
        
        // Vérifier que les coordonnées sont dans la zone de la map
        if (screenX < mapWidth && screenY > hauteurBarreVie) {
            // Calculer les coordonnées dans le système du viewport
            // Le viewport s'étire pour remplir l'espace disponible
            // Pour X: screenX est déjà dans le bon système (0 à mapWidth)
            // Pour Y: screenY a l'origine en haut, viewport a l'origine en bas
            float relativeY = screenY - hauteurBarreVie; // Position relative dans la zone de la map (origine en haut)
            float viewportY = mapHeight - relativeY; // Inverser pour viewport (origine en bas)
            
            // Obtenir les dimensions du monde du viewport
            mapViewport.apply();
            float worldWidth = mapViewport.getWorldWidth();
            float worldHeight = mapViewport.getWorldHeight();
            
            // Convertir manuellement les coordonnées viewport en coordonnées monde
            // Le viewport s'étire, donc on fait une conversion proportionnelle
            float worldX = (screenX / mapWidth) * worldWidth;
            float worldY = (viewportY / mapHeight) * worldHeight;
            
            return new Vector3(worldX, worldY, 0);
        }
        return null;
    }
    
    /**
     * Vérifie si des coordonnées monde sont dans les limites de la map.
     * 
     * @param worldX Coordonnée X monde
     * @param worldY Coordonnée Y monde
     * @param mapViewport Le viewport de la map
     * @return true si les coordonnées sont dans les limites, false sinon
     */
    public static boolean estDansLimitesMap(float worldX, float worldY, Viewport mapViewport) {
        mapViewport.apply();
        return worldX >= 0 && worldX <= mapViewport.getWorldWidth() && 
               worldY >= 0 && worldY <= mapViewport.getWorldHeight();
    }
}

