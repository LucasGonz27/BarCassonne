package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;

import Epi.BarCassonne.game.Entities.Towers.Tower;
import Epi.BarCassonne.game.Entities.Towers.TowerForgeron;
import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Utils.CollisionValid;
import Epi.BarCassonne.game.Utils.Texte;
import Epi.BarCassonne.game.UI.HUD;
import Epi.BarCassonne.Factory.TowerFactory;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * Gestionnaire des tours placées sur le terrain.
 * Gère la création, le placement, la mise à jour et le rendu des tours.
 * Gère également le mode placement avec aperçu visuel.
 */
public class TowerManager {
    
    // ------------------------------------------------------------------------
    // REGION : CONSTANTES
    // ------------------------------------------------------------------------
     
    /** Taille de l'aperçu de tour lors du placement */
    private static final float TAILLE_APERCU = 70f;
    
    /** Taille d'affichage de la Tower */
    private static final float TOWER_SIZE = 100f;
    
    
    // ------------------------------------------------------------------------
    // REGION : ATTRIBUTS
    // ------------------------------------------------------------------------
    
    /** Liste de toutes les tours placées sur le terrain */
    private List<Tower> tours;
    
    /** Texture de la tour d'archer */
    private Texture tourArcherTexture;
    
    /** Texture de la tour de magie */
    private Texture tourMagieTexture;

    /** Texture de la tour de forgeron */
    private Texture tourForgeronTexture;
    
    
    /** Indique si on est en mode placement de tour */
    private boolean enModePlacement;
    
    /** Type de tour à placer */
    private String towerTypeAPlace;
    
    /** Texture d'aperçu de la tour à placer */
    private Texture tourPreviewTexture;
    
    /** Position X de l'aperçu en coordonnées monde */
    private float tourPreviewX;
    
    /** Position Y de l'aperçu en coordonnées monde */
    private float tourPreviewY;
    
    /** Portée de la tour à placer */
    private float tourPreviewPortee;
    
    /** Indique si la position actuelle de l'aperçu est valide pour placer la tour */
    private boolean positionValide;
    
    /** Validateur de collision pour vérifier les positions valides */
    private CollisionValid collisionValid;
    
    /** Gestionnaire de vagues pour obtenir les ennemis à attaquer */
    private VagueMana vagueManager;
    
    /** GameState pour ajouter les lingots */
    private GameState gameState;
    
    /** Messages flottants : {x, y, temps, lingots} */
    private List<float[]> messages;
    
    /** Temps depuis dernière génération pour chaque tour forgeron */
    private Map<Tower, Float> tempsForgeron;

    /** Texture du sac plein*/
    private Texture textureSacPlein;

    /** Texture du sac vide*/
    private Texture textureSacVide;
    
    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------

    /**
     * Crée un nouveau gestionnaire de tours.
     * @param collisionValid Le validateur de collision pour vérifier les positions
     * @param vagueManager Le gestionnaire de vagues pour les attaques
     * @param gameState L'état du jeu pour ajouter les lingots
     */
    public TowerManager(CollisionValid collisionValid, VagueMana vagueManager, GameState gameState) {
        this.tours = new ArrayList<>();
        this.collisionValid = collisionValid;
        this.vagueManager = vagueManager;
        this.gameState = gameState;
        this.tourArcherTexture = TextureManager.chargerTexture("sprites/TourArcherLevel1.png");
        this.tourMagieTexture = TextureManager.chargerTexture("sprites/TourMagieLevel1.png");
        this.tourForgeronTexture = TextureManager.chargerTexture("sprites/ForgeronLevel1.png");
        this.textureSacPlein = TextureManager.chargerTexture("sprites/SacPleins.png");
        this.textureSacVide = TextureManager.chargerTexture("sprites/SacVide.png");
        this.messages = new ArrayList<>();
        this.tempsForgeron = new HashMap<>();

        this.enModePlacement = false;
        this.towerTypeAPlace = null;
        this.tourPreviewTexture = null;
        this.positionValide = false;
    }
    
    // ------------------------------------------------------------------------
    // REGION : GETTERS
    // ------------------------------------------------------------------------
    
    /**
     * Retourne la liste de toutes les tours.
     * @return La liste des tours
     */
    public List<Tower> getTours() {
        return tours;
    }
    
    /**
     * Vérifie si on est en mode placement.
     * @return true si en mode placement, false sinon
     */
    public boolean estEnModePlacement() {
        return enModePlacement;
    }
    
    // ------------------------------------------------------------------------
    // REGION : PLACEMENT DE TOUR
    // ------------------------------------------------------------------------
    
    /**
     * Active le mode placement pour un type de tour.
     * @param slot Le numéro du slot (1 = TowerArcher, 2 = TowerMagie, etc.)
     */
    public void activerModePlacement(int slot) {
        String towerType = null;
        if (slot == 1) {
            towerType = "TowerArcher";
        } else if (slot == 2) {
            towerType = "TowerMagie";
        } else if (slot == 3) {
            towerType = "TowerForgeron";
        }
        
        if (towerType != null) {
            towerTypeAPlace = towerType;
            enModePlacement = true;
            tourPreviewTexture = getTexture(towerTypeAPlace);
            // Crée une tour temporaire pour obtenir sa portée
            Tower tourTemp = creerTour(towerType, 0, 0);
            tourPreviewPortee = tourTemp != null ? tourTemp.getPortee() : 0f;
        }
    }
    
    /**
     * Met à jour la position de l'aperçu de la tour en suivant la souris.
     * Convertit les coordonnées écran en coordonnées monde et vérifie si la position est valide.
     */
    public void mettreAJourApercu(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
        if (!enModePlacement || towerTypeAPlace == null) {
            return;
        }

        Vector3 worldPos = convertirEcranVersMonde(screenX, screenY, screenWidth, mapViewport);
        if (worldPos == null) {
            positionValide = false;
            return;
        }

        tourPreviewX = worldPos.x;
        tourPreviewY = worldPos.y;
        positionValide = collisionValid.estPositionValide(worldPos.x, worldPos.y, TOWER_SIZE, tours);
    }
    
    /**
     * Place une tour sur le terrain à la position du clic.
     * Vérifie que la position est valide avant de créer et placer la tour.
     * @return true si la tour a été placée, false sinon
     */
    public boolean placerTour(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
        if (!enModePlacement || towerTypeAPlace == null) {
            return false;
        }

        Vector3 worldPos = convertirEcranVersMonde(screenX, screenY, screenWidth, mapViewport);
        if (worldPos == null) {
            return false;
        }

        if (!collisionValid.estPositionValide(worldPos.x, worldPos.y, TOWER_SIZE, tours)) {
            return false;
        }

        Tower nouvelleTour = creerTour(towerTypeAPlace, worldPos.x, worldPos.y);
        if (nouvelleTour != null) {
            tours.add(nouvelleTour);
            annulerModePlacement();
            return true;
        }
        return false;
    }
    
    /**
     * Annule le mode placement et réinitialise les variables.
     */
    public void annulerModePlacement() {
        enModePlacement = false;
        towerTypeAPlace = null;
        tourPreviewTexture = null;
        tourPreviewPortee = 0f;
    }
    
    // ------------------------------------------------------------------------
    // REGION : MISE À JOUR
    // ------------------------------------------------------------------------
    
    /**
     * Met à jour toutes les tours.
     * Pour chaque tour, met à jour son état et fait attaquer tous les ennemis actifs.
     * @param delta Temps écoulé depuis la dernière frame
     */
    public void update(float delta) {
        for (Tower tour : tours) {
            tour.update(delta);
            if (vagueManager != null && vagueManager.getEnnemisActifs() != null) {
                for (Mechant ennemi : vagueManager.getEnnemisActifs()) {
                    if (ennemi != null && ennemi.isEnVie()) {
                        tour.attacker(ennemi);
                    }
                }
            }
            
            // Génère des lingots pour les tours forgeron toutes les 11 secondes
            if (tour instanceof TowerForgeron) {
                float temps = tempsForgeron.getOrDefault(tour, 0f) + delta;
                if (temps >= 11f) {
                    TowerForgeron forgeron = (TowerForgeron) tour;
                    int lingots = forgeron.getApportLingots();
                    gameState.ajouterLingots(lingots);
                    messages.add(new float[]{tour.getPositionX(), tour.getPositionY() + 80f, 2f, lingots});
                    tempsForgeron.put(tour, 0f);
                } else {
                    tempsForgeron.put(tour, temps);
                }
            }
        }
        
        // Met à jour les messages
        for (int i = messages.size() - 1; i >= 0; i--) {
            float[] msg = messages.get(i);
            msg[1] += 30f * delta; // Monte
            msg[2] -= delta; // Temps restant
            if (msg[2] <= 0) {
                messages.remove(i);
            }
        }
    }
    
    // ------------------------------------------------------------------------
    // REGION : RENDU
    // ------------------------------------------------------------------------
    
    /**
     * Dessine toutes les tours et l'aperçu de la tour à placer.
     * @param batch Le SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {
        // Dessine toutes les tours placées
        for (Tower tour : tours) {
            Texture texture = getTexture(tour.getClass().getSimpleName());
            if (texture != null) {
                // Dessine la tour à la position de la tour (divise par 2 pour centrer la tour)
                float x = tour.getPositionX() - TOWER_SIZE / 2;
                float y = tour.getPositionY() - TOWER_SIZE / 2;
                batch.draw(texture, x, y, TOWER_SIZE, TOWER_SIZE);
            }
        }
        
        // Dessine l'aperçu de la tour à placer
        if (enModePlacement && tourPreviewTexture != null) {
            Color couleur = positionValide ? Color.WHITE : Color.RED;
            batch.setColor(couleur.r, couleur.g, couleur.b, 0.7f);
            
            // Dessine l'aperçu
            batch.draw(tourPreviewTexture, 
                      tourPreviewX - TAILLE_APERCU / 2,  // Position X (centrée)
                      tourPreviewY - TAILLE_APERCU / 2,  // Position Y (centrée)
                      TAILLE_APERCU,                      // Largeur
                      TAILLE_APERCU);                     // Hauteur
            
            // Réinitialise la couleur à blanc pour ne pas affecter les autres dessins
            batch.setColor(Color.WHITE);
        }
        
        // Dessine les sacs décoratifs à côté des tours forgeron
        for (Tower tour : tours) {
            if (tour instanceof TowerForgeron) {
                float temps = tempsForgeron.getOrDefault(tour, 0f);
                if (temps >= 10f && textureSacPlein != null) {
                    batch.draw(textureSacPlein, tour.getPositionX() + 60f - 25f, tour.getPositionY() - 25f, 50f, 50f);
                } else if (textureSacVide != null) {
                    batch.draw(textureSacVide, tour.getPositionX() + 60f - 25f, tour.getPositionY() - 25f, 50f, 50f);
                }
            }
        }
        
        // Dessine les messages
        for (float[] msg : messages) {
            Color couleur = Color.YELLOW;
            //le 3 -> le nombre de lingots
            String texte = "+" + (int)msg[3];
            //le 0 -> la position x et
            Texte.drawText(batch, texte, msg[0] - 20f, msg[1], couleur, 30);
        }
    }
    
    /**
     * Dessine la portée de la tour à placer avec le ShapeRenderer.
     * Doit être appelé après avoir fermé le SpriteBatch.
     * @param shapeRenderer Le ShapeRenderer pour dessiner
     * @param camera La caméra pour la projection
     */
    public void renderPortee(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        if (enModePlacement && tourPreviewPortee > 0) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Color couleur = positionValide ? Color.WHITE : Color.RED;
            shapeRenderer.setColor(couleur.r, couleur.g, couleur.b, 0.7f);
            shapeRenderer.circle(tourPreviewX, tourPreviewY, tourPreviewPortee);
            shapeRenderer.end();
        }
    }
    
    // ------------------------------------------------------------------------
    // REGION : MÉTHODES PRIVÉES
    // ------------------------------------------------------------------------
    
    /**
     * Retourne la texture correspondant au type de tour.
     */
    private Texture getTexture(String towerType) {
        if ("TowerArcher".equals(towerType)) return tourArcherTexture;
        if ("TowerMagie".equals(towerType)) return tourMagieTexture;
        if ("TowerForgeron".equals(towerType)) return tourForgeronTexture;
        // Pour les autres types, on utilise la texture par défaut (TowerArcher)
        return tourArcherTexture;
    }
    
    /**
     * Crée une tour via la factory et la positionne.
     * Utilise TowerFactory pour créer la tour selon son type, puis définit sa position.
     */
    private Tower creerTour(String towerType, float x, float y) {
        try {
            Tower tour = TowerFactory.creerTower(towerType);
            tour.setPositionX(x);
            tour.setPositionY(y);
            return tour;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    /**
     * Convertit les coordonnées écran en coordonnées monde.
     * Vérifie que le curseur n'est pas dans le HUD et que la position est dans les limites de la map.
     * @return Les coordonnées monde, ou null si invalide
     */
    private Vector3 convertirEcranVersMonde(float screenX, float screenY, float screenWidth, Viewport mapViewport) {
        // Vérifie que le curseur n'est pas dans le HUD
        float largeurHUD = HUD.getLargeurHUD(screenWidth);
        if (screenX >= screenWidth - largeurHUD) {
            return null;
        }

        // Convertit les coordonnées écran en coordonnées monde
        mapViewport.apply();
        Vector3 worldPos = mapViewport.unproject(new Vector3(screenX, screenY, 0));

        // Vérifie les limites de la map
        if (worldPos.x < 0 || worldPos.x > mapViewport.getWorldWidth() || 
            worldPos.y < 0 || worldPos.y > mapViewport.getWorldHeight()) {
            return null;
        }

        return worldPos;
    }
    
    
    // ------------------------------------------------------------------------
    // REGION : NETTOYAGE
    // ------------------------------------------------------------------------
    
    /**
     * Libère les ressources utilisées par le gestionnaire.
     */
    public void dispose() {
        if (tourArcherTexture != null) TextureManager.libererTexture(tourArcherTexture);
        if (tourMagieTexture != null) TextureManager.libererTexture(tourMagieTexture);
        if (tourForgeronTexture != null) TextureManager.libererTexture(tourForgeronTexture);
        if (tourPreviewTexture != null) TextureManager.libererTexture(tourPreviewTexture);
    }
}
