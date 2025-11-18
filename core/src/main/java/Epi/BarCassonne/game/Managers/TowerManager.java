package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.Factory.TowerFactory;
import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Entities.Towers.Tower;
import Epi.BarCassonne.game.Entities.Towers.TowerForgeron;
import Epi.BarCassonne.game.UI.HUD;
import Epi.BarCassonne.game.Utils.CollisionValid;
import Epi.BarCassonne.game.Utils.Texte;

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
    
    /** Taille d'affichage de la tour */
    private static final float TOWER_SIZE = 100f;
    
    /** Intervalle de génération de lingots pour les tours forgeron (en secondes) */
    private static final float INTERVALLE_GENERATION_LINGOTS = 11f;
    
    /** Seuil de temps pour afficher le sac plein (en secondes) */
    private static final float SEUIL_SAC_PLEIN = 10f;
    
    /** Vitesse de montée des messages flottants */
    private static final float VITESSE_MESSAGE = 30f;
    
    /** Durée d'affichage des messages flottants (en secondes) */
    private static final float DUREE_MESSAGE = 2f;
    
    /** Décalage vertical pour l'affichage des messages de lingots */
    private static final float DECALAGE_MESSAGE_Y = 80f;
    
    /** Décalage horizontal pour l'affichage des sacs */
    private static final float DECALAGE_SAC_X = 60f;
    
    /** Taille des sacs décoratifs */
    private static final float TAILLE_SAC = 50f;
    
    /** Décalage pour centrer les sacs */
    private static final float DECALAGE_SAC_CENTRE = 25f;
    
    /** Décalage horizontal pour le texte des messages */
    private static final float DECALAGE_TEXTE_X = 20f;
    
    /** Taille de la police pour les messages de lingots */
    private static final int TAILLE_POLICE_MESSAGE = 30;
    
    /** Opacité de l'aperçu de tour */
    private static final float OPACITE_APERCU = 0.7f;
    
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
    
    /** Texture du sac plein */
    private Texture textureSacPlein;
    
    /** Texture du sac vide */
    private Texture textureSacVide;
    
    /** Indique si on est en mode placement de tour */
    private boolean enModePlacement;
    
    /** Type de tour à placer */
    private String towerTypeToPlace;
    
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
    
    /** GameState pour gérer les lingots */
    private GameState gameState;
    
    /** Messages flottants : {x, y, temps, lingots} */
    private List<float[]> messages;
    
    /** Temps depuis dernière génération pour chaque tour forgeron */
    private Map<Tower, Float> tempsForgeron;
    
    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    
    /**
     * Crée un nouveau gestionnaire de tours.
     * @param collisionValid Le validateur de collision pour vérifier les positions
     * @param vagueManager Le gestionnaire de vagues pour les attaques
     * @param gameState L'état du jeu pour gérer les lingots
     */
    public TowerManager(CollisionValid collisionValid, VagueMana vagueManager, GameState gameState) {
        this.tours = new ArrayList<>();
        this.collisionValid = collisionValid;
        this.vagueManager = vagueManager;
        this.gameState = gameState;
        this.messages = new ArrayList<>();
        this.tempsForgeron = new HashMap<>();
        
        chargerTextures();
        initialiserModePlacement();
    }
    
    /**
     * Charge toutes les textures nécessaires.
     */
    private void chargerTextures() {
        this.tourArcherTexture = TextureManager.chargerTexture("sprites/TourArcherLevel1.png");
        this.tourMagieTexture = TextureManager.chargerTexture("sprites/TourMagieLevel1.png");
        this.tourForgeronTexture = TextureManager.chargerTexture("sprites/ForgeronLevel1.png");
        this.textureSacPlein = TextureManager.chargerTexture("sprites/SacPleins.png");
        this.textureSacVide = TextureManager.chargerTexture("sprites/SacVide.png");
    }
    
    /**
     * Initialise les variables du mode placement.
     */
    private void initialiserModePlacement() {
        this.enModePlacement = false;
        this.towerTypeToPlace = null;
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
     * @param slot Le numéro du slot (1 = TowerArcher, 2 = TowerMagie, 3 = TowerForgeron)
     */
    public void activerModePlacement(int slot) {
        String towerType = convertirSlotEnTypeTour(slot);
        if (towerType == null) {
            return;
        }
        
        towerTypeToPlace = towerType;
        enModePlacement = true;
        tourPreviewTexture = getTexture(towerType);
        
        // Crée une tour temporaire pour obtenir sa portée
        Tower tourTemp = creerTour(towerType, 0, 0);
        tourPreviewPortee = tourTemp != null ? tourTemp.getPortee() : 0f;
    }
    
    /**
     * Convertit un numéro de slot en type de tour.
     * @param slot Le numéro du slot
     * @return Le type de tour correspondant, ou null si invalide
     */
    private String convertirSlotEnTypeTour(int slot) {
        switch (slot) {
            case 1:
                return "TowerArcher";
            case 2:
                return "TowerMagie";
            case 3:
                return "TowerForgeron";
            default:
                return null;
        }
    }
    
    /**
     * Met à jour la position de l'aperçu de la tour en suivant la souris.
     * Convertit les coordonnées écran en coordonnées monde et vérifie si la position est valide.
     * @param screenX Position X du curseur en coordonnées écran
     * @param screenY Position Y du curseur en coordonnées écran
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapViewport Le viewport de la map pour la conversion des coordonnées
     */
    public void mettreAJourApercu(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
        if (!enModePlacement || towerTypeToPlace == null) {
            return;
        }

        Vector3 worldPos = convertirEcranVersMonde(screenX, screenY, screenWidth, screenHeight, mapViewport);
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
     * Vérifie que la position est valide et que le joueur a assez de lingots avant de créer et placer la tour.
     * @param screenX Position X du clic en coordonnées écran
     * @param screenY Position Y du clic en coordonnées écran
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapViewport Le viewport de la map pour la conversion des coordonnées
     * @return true si la tour a été placée, false sinon
     */
    public boolean placerTour(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
        if (!enModePlacement || towerTypeToPlace == null) {
            return false;
        }

        Vector3 worldPos = convertirEcranVersMonde(screenX, screenY, screenWidth, screenHeight, mapViewport);
        if (worldPos == null) {
            return false;
        }

        if (!collisionValid.estPositionValide(worldPos.x, worldPos.y, TOWER_SIZE, tours)) {
            return false;
        }

        int prix = obtenirPrixTour(towerTypeToPlace);
        if (prix == -1 || !gameState.retirerLingots(prix)) {
            return false;
        }

        Tower nouvelleTour = creerTour(towerTypeToPlace, worldPos.x, worldPos.y);
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
        towerTypeToPlace = null;
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
        mettreAJourTours(delta);
        mettreAJourMessages(delta);
    }
    
    /**
     * Met à jour toutes les tours (attaques et génération de lingots).
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void mettreAJourTours(float delta) {
        for (Tower tour : tours) {
            tour.update(delta);
            attaquerEnnemis(tour);
            mettreAJourForgeron(tour, delta);
        }
    }
    
    /**
     * Fait attaquer les ennemis avec une tour.
     * @param tour La tour qui attaque
     */
    private void attaquerEnnemis(Tower tour) {
        if (vagueManager == null || vagueManager.getEnnemisActifs() == null) {
            return;
        }
        
        for (Mechant ennemi : vagueManager.getEnnemisActifs()) {
            if (ennemi != null && ennemi.isEnVie()) {
                tour.attacker(ennemi);
            }
        }
    }
    
    /**
     * Met à jour la génération de lingots pour une tour forgeron.
     * @param tour La tour à mettre à jour
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void mettreAJourForgeron(Tower tour, float delta) {
        if (!(tour instanceof TowerForgeron)) {
            return;
        }
        
        float temps = tempsForgeron.getOrDefault(tour, 0f) + delta;
        if (temps >= INTERVALLE_GENERATION_LINGOTS) {
            TowerForgeron forgeron = (TowerForgeron) tour;
            int lingots = forgeron.getApportLingots();
            gameState.ajouterLingots(lingots);
            creerMessageLingots(tour, lingots);
            tempsForgeron.put(tour, 0f);
        } else {
            tempsForgeron.put(tour, temps);
        }
    }
    
    /**
     * Crée un message flottant pour afficher les lingots générés.
     * @param tour La tour qui génère les lingots
     * @param lingots Le nombre de lingots générés
     */
    private void creerMessageLingots(Tower tour, int lingots) {
        messages.add(new float[]{
            tour.getPositionX(),
            tour.getPositionY() + DECALAGE_MESSAGE_Y,
            DUREE_MESSAGE,
            lingots
        });
    }
    
    /**
     * Met à jour les messages flottants.
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void mettreAJourMessages(float delta) {
        for (int i = messages.size() - 1; i >= 0; i--) {
            float[] msg = messages.get(i);
            msg[1] += VITESSE_MESSAGE * delta; // Monte
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
        dessinerTours(batch);
        dessinerApercu(batch);
        dessinerSacs(batch);
        dessinerMessages(batch);
    }
    
    /**
     * Dessine toutes les tours placées.
     * @param batch Le SpriteBatch pour le rendu
     */
    private void dessinerTours(SpriteBatch batch) {
        for (Tower tour : tours) {
            Texture texture = getTexture(tour.getClass().getSimpleName());
            if (texture != null) {
                float x = tour.getPositionX() - TOWER_SIZE / 2;
                float y = tour.getPositionY() - TOWER_SIZE / 2;
                batch.draw(texture, x, y, TOWER_SIZE, TOWER_SIZE);
            }
        }
    }
    
    /**
     * Dessine l'aperçu de la tour à placer.
     * @param batch Le SpriteBatch pour le rendu
     */
    private void dessinerApercu(SpriteBatch batch) {
        if (!enModePlacement || tourPreviewTexture == null) {
            return;
        }
        
        Color couleur = positionValide ? Color.WHITE : Color.RED;
        batch.setColor(couleur.r, couleur.g, couleur.b, OPACITE_APERCU);
        
        float x = tourPreviewX - TAILLE_APERCU / 2;
        float y = tourPreviewY - TAILLE_APERCU / 2;
        batch.draw(tourPreviewTexture, x, y, TAILLE_APERCU, TAILLE_APERCU);
        
        batch.setColor(Color.WHITE);
    }
    
    /**
     * Dessine les sacs décoratifs à côté des tours forgeron.
     * @param batch Le SpriteBatch pour le rendu
     */
    private void dessinerSacs(SpriteBatch batch) {
        for (Tower tour : tours) {
            if (tour instanceof TowerForgeron) {
                dessinerSacForgeron(batch, tour);
            }
        }
    }
    
    /**
     * Dessine le sac d'une tour forgeron.
     * @param batch Le SpriteBatch pour le rendu
     * @param tour La tour forgeron
     */
    private void dessinerSacForgeron(SpriteBatch batch, Tower tour) {
        float temps = tempsForgeron.getOrDefault(tour, 0f);
        float x = tour.getPositionX() + DECALAGE_SAC_X - DECALAGE_SAC_CENTRE;
        float y = tour.getPositionY() - DECALAGE_SAC_CENTRE;
        
        if (temps >= SEUIL_SAC_PLEIN && textureSacPlein != null) {
            batch.draw(textureSacPlein, x, y, TAILLE_SAC, TAILLE_SAC);
        } else if (textureSacVide != null) {
            batch.draw(textureSacVide, x, y, TAILLE_SAC, TAILLE_SAC);
        }
    }
    
    /**
     * Dessine les messages flottants de lingots.
     * @param batch Le SpriteBatch pour le rendu
     */
    private void dessinerMessages(SpriteBatch batch) {
        for (float[] msg : messages) {
            String texte = "+" + (int)msg[3];
            float x = msg[0] - DECALAGE_TEXTE_X;
            float y = msg[1];
            Texte.drawText(batch, texte, x, y, Color.YELLOW, TAILLE_POLICE_MESSAGE);
        }
    }
    
    /**
     * Dessine la portée de la tour à placer avec le ShapeRenderer.
     * Doit être appelé après avoir fermé le SpriteBatch.
     * @param shapeRenderer Le ShapeRenderer pour dessiner
     * @param camera La caméra pour la projection
     */
    public void renderPortee(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        if (!enModePlacement || tourPreviewPortee <= 0) {
            return;
        }
        
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        
        Color couleur = positionValide ? Color.WHITE : Color.RED;
        shapeRenderer.setColor(couleur.r, couleur.g, couleur.b, OPACITE_APERCU);
        shapeRenderer.circle(tourPreviewX, tourPreviewY, tourPreviewPortee);
        
        shapeRenderer.end();
    }
    
    // ------------------------------------------------------------------------
    // REGION : MÉTHODES PRIVÉES
    // ------------------------------------------------------------------------
    
    /**
     * Retourne la texture correspondant au type de tour.
     * @param towerType Le type de tour (ex: "TowerArcher", "TowerMagie", "TowerForgeron")
     * @return La texture correspondante, ou la texture d'archer par défaut
     */
    private Texture getTexture(String towerType) {
        switch (towerType) {
            case "TowerArcher":
                return tourArcherTexture;
            case "TowerMagie":
                return tourMagieTexture;
            case "TowerForgeron":
                return tourForgeronTexture;
            default:
                return tourArcherTexture;
        }
    }
    
    /**
     * Obtient le prix d'un type de tour sans créer d'instance complète.
     * @param towerType Le type de tour (ex: "TowerArcher", "TowerMagie", "TowerForgeron")
     * @return Le prix de la tour, ou -1 si le type est invalide
     */
    private int obtenirPrixTour(String towerType) {
        try {
            Tower tourTemp = TowerFactory.creerTower(towerType);
            return tourTemp.getPrix();
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    /**
     * Crée une tour via la factory et la positionne.
     * Utilise TowerFactory pour créer la tour selon son type, puis définit sa position.
     * @param towerType Le type de tour à créer
     * @param x Position X où placer la tour
     * @param y Position Y où placer la tour
     * @return La tour créée, ou null si le type est invalide
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
     * @param screenX Position X en coordonnées écran
     * @param screenY Position Y en coordonnées écran
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapViewport Le viewport de la map pour la conversion
     * @return Les coordonnées monde, ou null si invalide (dans le HUD ou hors limites)
     */
    private Vector3 convertirEcranVersMonde(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
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
        if (tourArcherTexture != null) {
            TextureManager.libererTexture(tourArcherTexture);
        }
        if (tourMagieTexture != null) {
            TextureManager.libererTexture(tourMagieTexture);
        }
        if (tourForgeronTexture != null) {
            TextureManager.libererTexture(tourForgeronTexture);
        }
        if (tourPreviewTexture != null) {
            TextureManager.libererTexture(tourPreviewTexture);
        }
    }
}
