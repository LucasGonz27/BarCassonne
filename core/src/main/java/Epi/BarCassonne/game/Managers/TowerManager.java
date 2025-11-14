package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.game.Entities.Towers.TowerArcher;
import Epi.BarCassonne.game.Entities.Towers.TowerMagie;
import Epi.BarCassonne.game.Entities.Towers.Tower;
import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Utils.CollisionValid;
import Epi.BarCassonne.game.Utils.CoordinateConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire des tours placées sur le terrain.
 * Gère la création, le placement, la mise à jour et le rendu des tours.
 * Gère également le mode placement avec aperçu visuel.
 */
public class TowerManager {
    
    // ------------------------------------------------------------------------
    // REGION : CONSTANTES
    // ------------------------------------------------------------------------
    
    /** Type de tour : Tour d'archer */
    public static final int TYPE_TOUR_ARCHER = 1;
    
    /** Type de tour : Tour de magie */
    public static final int TYPE_TOUR_MAGIE = 2;
    
    /** Taille de l'aperçu de tour lors du placement */
    private static final float TAILLE_APERCU_TOUR = 50f;
    
    /** Taille d'affichage de la Tour d'archer */
    private static final float TOUR_ARCHER_SIZE = 90f; 
    
    /** Taille d'affichage de la Tour de magie */
    private static final float TOUR_MAGIE_SIZE = 120f; 
    
    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------

    /** Liste de toutes les tours placées sur le terrain */
    private List<Tower> tours;
    
    /** Texture de la tour d'archer */
    private Texture tourArcherTexture;
    
    /** Texture de la tour de magie */
    private Texture tourMagieTexture;
    
    // Mode placement
    /** Indique si on est en mode placement de tour */
    private boolean enModePlacement;
    
    /** Type de tour à placer (TYPE_TOUR_ARCHER ou TYPE_TOUR_MAGIE) */
    private int typeTourAPlacer;
    
    /** Texture d'aperçu de la tour à placer */
    private Texture tourPreviewTexture;
    
    /** Position X de l'aperçu en coordonnées monde */
    private float tourPreviewX;
    
    /** Position Y de l'aperçu en coordonnées monde */
    private float tourPreviewY;
    
    /** Indique si la position actuelle de l'aperçu est valide pour placer la tour */
    private boolean positionValide;
    
    // Dépendances
    /** Validateur de collision pour vérifier les positions valides */
    private CollisionValid collisionValid;
    
    /** Gestionnaire de vagues pour obtenir les ennemis à attaquer */
    private VagueMana vagueManager;
    
    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouveau gestionnaire de tours.
     * @param collisionValid Le validateur de collision pour vérifier les positions
     * @param vagueManager Le gestionnaire de vagues pour les attaques
     */
    public TowerManager(CollisionValid collisionValid, VagueMana vagueManager) {
        this.tours = new ArrayList<>();
        this.collisionValid = collisionValid;
        this.vagueManager = vagueManager;

        this.tourArcherTexture = TextureManager.chargerTexture("sprites/TourArcherLevel1.png");
        this.tourMagieTexture = TextureManager.chargerTexture("sprites/TourMagieLevel1.png");
        
        // Initialiser le mode placement
        this.enModePlacement = false;
        this.typeTourAPlacer = 0;
        this.tourPreviewTexture = null;
        this.positionValide = false;
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
        
        // Afficher l'aperçu de la tour si on est en mode placement
        if (enModePlacement && tourPreviewTexture != null) {
            // Changer la couleur selon si la position est valide
            if (!positionValide) {
                batch.setColor(1f, 0.3f, 0.3f, 0.7f); // Rouge transparent si invalide
            } else {
                batch.setColor(1f, 1f, 1f, 0.7f); // Blanc transparent si valide
            }
            batch.draw(tourPreviewTexture,
                tourPreviewX - TAILLE_APERCU_TOUR / 2,
                tourPreviewY - TAILLE_APERCU_TOUR / 2,
                TAILLE_APERCU_TOUR,
                TAILLE_APERCU_TOUR);
            batch.setColor(1f, 1f, 1f, 1f); // Réinitialiser la couleur
        }
    }
    
    /**
     * Met à jour toutes les tours.
     * Pour chaque tour, met à jour son état et fait attaquer tous les ennemis actifs.
     * @param delta Temps écoulé depuis la dernière frame
     */
    public void update(float delta) {
        for (Tower tour : tours) {
            tour.update();
            // Faire attaquer la tour si des ennemis sont disponibles
            if (vagueManager != null && vagueManager.getEnnemisActifs() != null) {
                for (int i = 0; i < vagueManager.getEnnemisActifs().size; i++) {
                    Mechant ennemi = vagueManager.getEnnemisActifs().get(i);
                    if (ennemi != null && ennemi.isEnVie()) {
                        tour.attacker(ennemi);
                    }
                }
            }
        }
    }
    
    /**
     * Active le mode placement pour un type de tour donné.
     * @param typeTour Le type de tour (TYPE_TOUR_ARCHER ou TYPE_TOUR_MAGIE)
     */
    public void activerModePlacement(int typeTour) {
        enModePlacement = true;
        typeTourAPlacer = typeTour;
        tourPreviewTexture = chargerTextureApercu(typeTour);
    }
    
    /**
     * Charge la texture d'aperçu pour un type de tour.
     * @param typeTour Le type de tour (TYPE_TOUR_ARCHER ou TYPE_TOUR_MAGIE)
     * @return La texture d'aperçu, ou null si le type est invalide
     */
    private Texture chargerTextureApercu(int typeTour) {
        switch (typeTour) {
            case TYPE_TOUR_ARCHER:
                return TextureManager.chargerTexture("sprites/TourArcherLevel1.png");
            case TYPE_TOUR_MAGIE:
                return TextureManager.chargerTexture("sprites/TourMagieLevel1.png");
            default:
                return null;
        }
    }
    
    /**
     * Annule le mode placement.
     */
    public void annulerModePlacement() {
        enModePlacement = false;
        typeTourAPlacer = 0;
        tourPreviewTexture = null;
    }
    
    /**
     * Met à jour la position de l'aperçu de la tour si on est en mode placement.
     * @param screenX Coordonnée X de l'écran
     * @param screenY Coordonnée Y de l'écran
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapViewport Le viewport de la map
     */
    public void mettreAJourApercu(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
        if (!enModePlacement) {
            return;
        }

        Vector3 worldPos = CoordinateConverter.convertirEcranVersMonde(
            screenX, screenY, screenWidth, screenHeight, mapViewport);

        if (worldPos != null) {
            tourPreviewX = worldPos.x;
            tourPreviewY = worldPos.y;

            // Vérifier si la position est valide
            float tourSize = obtenirTailleTour(typeTourAPlacer);
            positionValide = collisionValid.estPositionValide(worldPos.x, worldPos.y, tourSize, tours);
        } else {
            positionValide = false;
        }
    }
    
    /**
     * Place une tour sur le terrain à la position du clic.
     * @param screenX Coordonnée X de l'écran
     * @param screenY Coordonnée Y de l'écran
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapViewport Le viewport de la map
     * @return true si la tour a été placée, false sinon
     */
    public boolean placerTour(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
        if (!enModePlacement) {
            return false;
        }
        
        Vector3 worldPos = CoordinateConverter.convertirEcranVersMonde(
            screenX, screenY, screenWidth, screenHeight, mapViewport);

        if (worldPos == null || !CoordinateConverter.estDansLimitesMap(worldPos.x, worldPos.y, mapViewport)) {
            return false;
        }

        // Vérifier si la position est valide avant de placer
        float tourSize = obtenirTailleTour(typeTourAPlacer);
        if (!collisionValid.estPositionValide(worldPos.x, worldPos.y, tourSize, tours)) {
            return false; // Position invalide, ne pas placer
        }

        Tower nouvelleTour = creerTour(typeTourAPlacer, worldPos.x, worldPos.y);
        if (nouvelleTour != null) {
            ajouterTour(nouvelleTour);
            annulerModePlacement();
            return true;
        }
        return false;
    }
    
    /**
     * Obtient la taille d'une tour selon son type.
     * @param typeTour Le type de tour
     * @return La taille de la tour
     */
    private float obtenirTailleTour(int typeTour) {
        switch (typeTour) {
            case TYPE_TOUR_ARCHER:
                return TOUR_ARCHER_SIZE;
            case TYPE_TOUR_MAGIE:
                return TOUR_MAGIE_SIZE;
            default:
                return TOUR_ARCHER_SIZE;
        }
    }
    
    /**
     * Crée une tour selon son type.
     * @param typeTour Le type de tour (TYPE_TOUR_ARCHER ou TYPE_TOUR_MAGIE)
     * @param x Position X en coordonnées monde
     * @param y Position Y en coordonnées monde
     * @return La tour créée, ou null si le type est invalide
     */
    private Tower creerTour(int typeTour, float x, float y) {
        switch (typeTour) {
            case TYPE_TOUR_ARCHER:
                return new TowerArcher(x, y);
            case TYPE_TOUR_MAGIE:
                return new TowerMagie(x, y);
            default:
                return null;
        }
    }
    
    /**
     * Vérifie si on est en mode placement.
     * @return true si en mode placement, false sinon
     */
    public boolean estEnModePlacement() {
        return enModePlacement;
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
        if (tourPreviewTexture != null) {
            TextureManager.libererTexture(tourPreviewTexture);
        }
    }
}



