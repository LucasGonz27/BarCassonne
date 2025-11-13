package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Epi.BarCassonne.game.Entities.Towers.TowerArcher;
import Epi.BarCassonne.game.Entities.Towers.TowerMagie;
import Epi.BarCassonne.game.Entities.Towers.Tower;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère les tours placées sur le terrain.
 */
public class TowerManager {
    
    private List<Tower> tours;
    private Texture tourArcherTexture;
    private Texture tourMagieTexture;
    private static final float TOUR_ARCHER_SIZE = 90f; // Taille d'affichage de la TourArcher
    private static final float TOUR_MAGIE_SIZE = 120f; // Taille d'affichage de la TourMagie (plus grande)
    
    public TowerManager() {
        this.tours = new ArrayList<>();
        // Charger les textures des tours
        this.tourArcherTexture = TextureManager.chargerTexture("sprites/TourArcherLevel1.png");
        this.tourMagieTexture = TextureManager.chargerTexture("sprites/TourMagieLevel1.png");
    }
    
    /**
     * Ajoute une tour au gestionnaire.
     * @param tour La tour à ajouter
     */
    public void ajouterTour(Tower tour) {
        tours.add(tour);
    }
    
    /**
     * Retourne la liste de toutes les tours.
     * @return La liste des tours
     */
    public List<Tower> getTours() {
        return tours;
    }
    
    /**
     * Dessine toutes les tours.
     * @param batch Le SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {
        for (Tower tour : tours) {
            // Déterminer quelle texture et quelle taille utiliser selon le type de tour
            Texture texture = null;
            float tourSize = TOUR_ARCHER_SIZE; // Taille par défaut
            
            if (tour instanceof TowerArcher) {
                texture = tourArcherTexture;
                tourSize = TOUR_ARCHER_SIZE;
            } else if (tour instanceof TowerMagie) {
                texture = tourMagieTexture;
                tourSize = TOUR_MAGIE_SIZE; // TourMagie est plus grande
            }
            
            // Dessiner la tour à sa position si la texture est disponible
            if (texture != null) {
                float x = tour.getPositionX() - tourSize / 2;
                float y = tour.getPositionY() - tourSize / 2;
                batch.draw(texture, x, y, tourSize, tourSize);
            }
        }
    }
    
    /**
     * Met à jour toutes les tours.
     * @param delta Temps écoulé depuis la dernière frame
     */
    public void update(float delta) {
        for (Tower tour : tours) {
            tour.update();
        }
    }
    
    /**
     * Libère les ressources utilisées par le gestionnaire.
     */
    public void dispose() {
        if (tourArcherTexture != null) {
            TextureManager.libererTexture(tourArcherTexture);
        }
        if (tourMagieTexture != null) {
            TextureManager.libererTexture(tourMagieTexture);
        }
    }
}



