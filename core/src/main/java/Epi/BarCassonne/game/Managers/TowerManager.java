package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.Factory.TowerFactory;
import Epi.BarCassonne.game.Config.TowerUpgradeConfig;
import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Entities.Towers.Tower;
import Epi.BarCassonne.game.Entities.Towers.TowerForgeron;
import Epi.BarCassonne.game.UI.HUD;
import Epi.BarCassonne.game.UI.TowerPanelInfo;
import Epi.BarCassonne.game.Utils.CollisionValid;
import Epi.BarCassonne.game.Utils.MessageFlottant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestionnaire des tours placées sur le terrain.
 * Responsable de la création, mise à jour, rendu et gestion des tours.

 */
public class TowerManager {

    // ========================================================================
    // CONSTANTES
    // ========================================================================

    /** Taille de l'aperçu de tour lors du placement */
    private static final float TAILLE_APERCU = 70f;

    /** Taille d'une tour sur le terrain */
    private static final float TOWER_SIZE = 100f;

    /** Intervalle de génération de lingots pour les forgerons (en secondes) */
    private static final float INTERVALLE_GENERATION_LINGOTS = 11f;

    /** Seuil de temps pour afficher le sac plein (en secondes) */
    private static final float SEUIL_SAC_PLEIN = 10f;

    /** Décalage X du sac par rapport à la tour */
    private static final float DECALAGE_SAC_X = 60f;

    /** Taille du sac affiché */
    private static final float TAILLE_SAC = 50f;

    /** Décalage pour centrer le sac */
    private static final float DECALAGE_SAC_CENTRE = 25f;

    /** Opacité de l'aperçu de tour */
    private static final float OPACITE_APERCU = 0.7f;

    /** Décalage Y du message de lingots */
    private static final float DECALAGE_MESSAGE_Y = 80f;

    /** Taille de la police du message de lingots */
    private static final int TAILLE_POLICE_MESSAGE_LINGOTS = 30;

    /** Revenu par lingot généré */
    private static final int REVENUE_LINGOT = 100;

    /** Temps avant la génération des lingots (en secondes) */
    private static final float TEMPS_AVANT_GENERATION_LINGOTS = 20f;

    // ========================================================================
    // CHAMPS
    // ========================================================================

    /** Liste de toutes les tours placées sur le terrain */
    private final List<Tower> tours;

    /** Gestionnaire des données des tours (textures, prix, portée) */
    private final TowerDataManager towerDataManager;

    /** Gestionnaire des messages flottants */
    private final MessageFlottant messageFlottant;

    /** Map associant chaque forgeron à son temps écoulé depuis la dernière génération */
    private final Map<Tower, Float> tempsForgeron;

    /** Temps écoulé depuis la dernière génération de lingots automatique */
    private float tempsEcouleGenererLingots;

    /** Validateur de collisions pour le placement des tours */
    private final CollisionValid collisionValid;

    /** Gestionnaire des vagues d'ennemis */
    private final VagueMana vagueManager;

    /** État du jeu (pour gérer les lingots) */
    private final GameState gameState;

    /** Gestionnaire des projectiles */
    private final ProjectileManager projectileManager;

    /** Texture du sac plein */
    private Texture textureSacPlein;

    /** Texture du sac vide */
    private Texture textureSacVide;

    /** Interface d'amélioration des tours */
    private TowerPanelInfo towerPanelInfo;

    /** Tour actuellement sélectionnée */
    private Tower tourSelectionnee;

    // État du mode placement
    /** Indique si on est en mode placement de tour */
    private boolean enModePlacement;

    /** Type de tour à placer */
    private String towerTypeToPlace;

    /** Texture de l'aperçu de tour */
    private Texture tourPreviewTexture;

    /** Position X de l'aperçu de tour */
    private float tourPreviewX;

    /** Position Y de l'aperçu de tour */
    private float tourPreviewY;

    /** Portée de l'aperçu de tour */
    private float tourPreviewPortee;

    /** Indique si la position de l'aperçu est valide */
    private boolean positionValide;

    // ========================================================================
    // CONSTRUCTEUR
    // ========================================================================

    /**
     * Crée un nouveau gestionnaire de tours.
     *
     * @param collisionValid Validateur de collisions
     * @param vagueManager Gestionnaire des vagues d'ennemis
     * @param gameState État du jeu
     * @param projectileManager Gestionnaire des projectiles
     */
    public TowerManager(CollisionValid collisionValid, VagueMana vagueManager, GameState gameState, ProjectileManager projectileManager) {
        this.tours = new ArrayList<>();
        this.collisionValid = collisionValid;
        this.vagueManager = vagueManager;
        this.gameState = gameState;
        this.projectileManager = projectileManager;
        this.tempsForgeron = new HashMap<>();
        this.towerDataManager = new TowerDataManager();
        this.messageFlottant = new MessageFlottant();
        this.tempsEcouleGenererLingots = 0f;
        this.towerPanelInfo = new TowerPanelInfo();
        this.tourSelectionnee = null;

        chargerTextures();
    }

    /**
     * Charge les textures nécessaires.
     */
    private void chargerTextures() {
        textureSacPlein = TextureManager.chargerTexture("sprites/SacPleins.png");
        textureSacVide = TextureManager.chargerTexture("sprites/SacVide.png");
    }

    // ========================================================================
    // MISE À JOUR
    // ========================================================================

    /**
     * Met à jour toutes les tours et leurs états.
     *
     * @param delta Temps écoulé depuis la dernière frame (en secondes)
     */
    public void update(float delta) {
        for (Tower tour : tours) {
            tour.update(delta);
            attaquerEnnemis(tour);
            mettreAJourForgeron(tour, delta);
        }
        genererLingots(delta);
        messageFlottant.update(delta);
    }

    /**
     * Fait attaquer une tour sur tous les ennemis actifs.
     *
     * @param tour La tour qui attaque
     */
    private void attaquerEnnemis(Tower tour) {
        if (vagueManager == null || vagueManager.getEnnemisActifs() == null) {
            return;
        }

        for (Mechant ennemi : vagueManager.getEnnemisActifs()) {
            if (ennemi != null && ennemi.isEnVie()) {
                tour.attacker(ennemi, projectileManager);
            }
        }
    }

    /**
     * Met à jour le système de génération de lingots pour un forgeron.
     *
     * @param tour La tour à mettre à jour
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void mettreAJourForgeron(Tower tour, float delta) {
        if (!(tour instanceof TowerForgeron)) {
            return;
        }

        float temps = tempsForgeron.getOrDefault(tour, 0f) + delta;
        if (temps >= INTERVALLE_GENERATION_LINGOTS) {
            genererLingots((TowerForgeron) tour);
            tempsForgeron.put(tour, 0f);
        } else {
            tempsForgeron.put(tour, temps);
        }
    }

    /**
     * Génère les lingots pour un forgeron et affiche un message.
     *
     * @param forgeron Le forgeron qui génère les lingots
     */
    private void genererLingots(TowerForgeron forgeron) {
        int lingots = forgeron.getApportLingots();
        gameState.ajouterLingots(lingots);
        afficherMessageLingots(forgeron, lingots);
    }

    /**
     * Génère les lingots automatiquement toutes les 20 secondes.
     *
     * @param delta Temps écoulé depuis la dernière frame (en secondes)
     */
    private void genererLingots(float delta) {
        tempsEcouleGenererLingots += delta;

        if (tempsEcouleGenererLingots >= TEMPS_AVANT_GENERATION_LINGOTS) {
            gameState.ajouterLingots(REVENUE_LINGOT);
            tempsEcouleGenererLingots = 0f;
        }
    }



    /**
     * Affiche un message flottant indiquant les lingots générés.
     *
     * @param tour La tour qui génère les lingots
     * @param lingots Le nombre de lingots générés
     */
    private void afficherMessageLingots(Tower tour, int lingots) {
        float x = tour.getPositionX();
        float y = tour.getPositionY() + DECALAGE_MESSAGE_Y;
        String message = "+" + lingots;
        messageFlottant.creerMessage(x, y, message, Color.YELLOW, TAILLE_POLICE_MESSAGE_LINGOTS, 2f);
    }

    // ========================================================================
    // PLACEMENT DE TOURS
    // ========================================================================

    /**
     * Active le mode placement pour un type de tour donné.
     *
     * @param slot Le slot de la tour (1: Archer, 2: Magie, 3: Forgeron)
     */
    public void activerModePlacement(int slot) {
        String towerType = convertirSlotEnTypeTour(slot);
        if (towerType == null) {
            return;
        }

        initialiserModePlacement(towerType);
    }

    /**
     * Initialise le mode placement avec un type de tour.
     *
     * @param towerType Le type de tour à placer
     */
    private void initialiserModePlacement(String towerType) {
        towerTypeToPlace = towerType;
        enModePlacement = true;
        tourPreviewTexture = towerDataManager.getTexture(towerType);
        tourPreviewPortee = towerDataManager.getPortee(towerType);
    }

    /**
     * Convertit un slot en type de tour.
     *
     * @param slot Le slot (1: Archer, 2: Magie, 3: Forgeron, 4: Canon)
     * @return Le type de tour correspondant, ou null si le slot est invalide
     */
    private String convertirSlotEnTypeTour(int slot) {
        switch (slot) {
            case 1:
                return "TowerArcher";
            case 2:
                return "TowerMagie";
            case 3:
                return "TowerForgeron";
            case 4:
                return "TowerCanon";
            default:
                return null;
        }
    }

    /**
     * Met à jour la position de l'aperçu de tour lors du placement.
     *
     * @param screenX Position X de l'écran
     * @param screenY Position Y de l'écran
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapViewport Viewport de la carte
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
     * Place une tour à la position donnée si les conditions sont remplies.
     *
     * @param screenX Position X de l'écran
     * @param screenY Position Y de l'écran
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapViewport Viewport de la carte
     * @return true si la tour a été placée, false sinon
     */
    public boolean placerTour(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
        if (!enModePlacement || towerTypeToPlace == null) {
            return false;
        }

        Vector3 worldPos = convertirEcranVersMonde(screenX, screenY, screenWidth, screenHeight, mapViewport);
        if (!estPositionValidePourPlacement(worldPos)) {
            return false;
        }

        // Vérifier si on a assez d'argent avant d'essayer d'acheter
        if (!aAssezDArgent()) {
            afficherMessageArgentInsuffisant(worldPos.x, worldPos.y);
            return false;
        }

        if (!peutAcheterTour()) {
            return false;
        }

        Tower nouvelleTour = creerTour(towerTypeToPlace, worldPos.x, worldPos.y);
        if (nouvelleTour == null) {
            return false;
        }

        tours.add(nouvelleTour);
        annulerModePlacement();
        return true;
    }

    /**
     * Vérifie si une position est valide pour placer une tour.
     *
     * @param worldPos Position dans le monde
     * @return true si la position est valide, false sinon
     */
    private boolean estPositionValidePourPlacement(Vector3 worldPos) {
        return worldPos != null && collisionValid.estPositionValide(worldPos.x, worldPos.y, TOWER_SIZE, tours);
    }

    /**
     * Vérifie si le joueur a assez d'argent pour acheter la tour.
     *
     * @return true si le joueur a assez d'argent, false sinon
     */
    private boolean aAssezDArgent() {
        Integer prix = towerDataManager.getPrix(towerTypeToPlace);
        return prix != null && gameState.getLingots() >= prix;
    }

    /**
     * Vérifie si le joueur peut acheter la tour et retire les lingots.
     *
     * @return true si le joueur peut acheter la tour, false sinon
     */
    private boolean peutAcheterTour() {
        Integer prix = towerDataManager.getPrix(towerTypeToPlace);
        return prix != null && gameState.retirerLingots(prix);
    }

    /**
     * Affiche un message indiquant que le joueur n'a pas assez d'argent.
     *
     * @param x Position X dans le monde
     * @param y Position Y dans le monde
     */
    private void afficherMessageArgentInsuffisant(float x, float y) {
        String message = "Argent insuffisant !";
        messageFlottant.creerMessage(x, y + DECALAGE_MESSAGE_Y, message, Color.RED, TAILLE_POLICE_MESSAGE_LINGOTS, 2f);
    }

    /**
     * Annule le mode placement et réinitialise les variables d'aperçu.
     */
    public void annulerModePlacement() {
        enModePlacement = false;
        towerTypeToPlace = null;
        tourPreviewTexture = null;
        tourPreviewPortee = 0f;
    }

    // ========================================================================
    // RENDU
    // ========================================================================

    /**
     * Dessine toutes les tours et les éléments visuels associés.
     *
     * @param batch SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {
        dessinerTours(batch);
        dessinerApercu(batch);
        dessinerSacs(batch);
        messageFlottant.render(batch);
        towerPanelInfo.render(batch);
    }

    /**
     * Dessine toutes les tours placées sur le terrain.
     * Utilise la texture appropriée selon le niveau de la tour.
     *
     * @param batch SpriteBatch pour le rendu
     */
    private void dessinerTours(SpriteBatch batch) {
        for (Tower tour : tours) {
            // Récupérer la texture selon le niveau de la tour
            Texture texture = towerDataManager.getTextureWithLevel(tour.getClass().getSimpleName(), tour.getLevel());
            if (texture != null) {
                float x = tour.getPositionX() - TOWER_SIZE / 2;
                float y = tour.getPositionY() - TOWER_SIZE / 2;
                batch.draw(texture, x, y, TOWER_SIZE, TOWER_SIZE);
            }
        }
    }

    /**
     * Dessine l'aperçu de tour lors du placement.
     *
     * @param batch SpriteBatch pour le rendu
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
     * Dessine les sacs des forgerons pour indiquer leur état.
     *
     * @param batch SpriteBatch pour le rendu
     */
    private void dessinerSacs(SpriteBatch batch) {
        for (Tower tour : tours) {
            if (tour instanceof TowerForgeron) {
                dessinerSacForgeron(batch, tour);
            }
        }
    }

    /**
     * Dessine le sac d'un forgeron.
     *
     * @param batch SpriteBatch pour le rendu
     * @param tour La tour forgeron
     */
    private void dessinerSacForgeron(SpriteBatch batch, Tower tour) {
        float temps = tempsForgeron.getOrDefault(tour, 0f);
        Texture textureSac = (temps >= SEUIL_SAC_PLEIN) ? textureSacPlein : textureSacVide;

        if (textureSac != null) {
            float x = tour.getPositionX() + DECALAGE_SAC_X - DECALAGE_SAC_CENTRE;
            float y = tour.getPositionY() - DECALAGE_SAC_CENTRE;
            batch.draw(textureSac, x, y, TAILLE_SAC, TAILLE_SAC);
        }
    }

    /**
     * Dessine la portée de l'aperçu de tour lors du placement.
     *
     * @param shapeRenderer ShapeRenderer pour dessiner les formes
     * @param camera Caméra orthographique
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

    // ========================================================================
    // UTILITAIRES
    // ========================================================================

    /**
     * Crée une nouvelle tour à la position donnée.
     *
     * @param towerType Le type de tour à créer
     * @param x Position X dans le monde
     * @param y Position Y dans le monde
     * @return La tour créée, ou null en cas d'erreur
     */
    private Tower creerTour(String towerType, float x, float y) {
        try {
            Tower tour = TowerFactory.creerTower(towerType);
            tour.setPositionX(x);
            tour.setPositionY(y);
            return tour;
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur lors de la création de la tour: " + e.getMessage());
            return null;
        }
    }

    /**
     * Convertit les coordonnées écran en coordonnées monde.
     *
     * @param screenX Position X de l'écran
     * @param screenY Position Y de l'écran
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapViewport Viewport de la carte
     * @return Les coordonnées monde, ou null si la position est invalide
     */
    private Vector3 convertirEcranVersMonde(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
        float largeurHUD = HUD.getLargeurHUD(screenWidth);
        if (screenX >= screenWidth - largeurHUD) {
            return null;
        }

        mapViewport.apply();
        Vector3 worldPos = mapViewport.unproject(new Vector3(screenX, screenY, 0));

        if (estHorsLimites(worldPos, mapViewport)) {
            return null;
        }

        return worldPos;
    }

    /**
     * Vérifie si une position est hors des limites du viewport.
     *
     * @param worldPos Position dans le monde
     * @param mapViewport Viewport de la carte
     * @return true si la position est hors limites, false sinon
     */
    private boolean estHorsLimites(Vector3 worldPos, Viewport mapViewport) {
        return worldPos.x < 0 || worldPos.x > mapViewport.getWorldWidth() || worldPos.y < 0 || worldPos.y > mapViewport.getWorldHeight();
    }

    // ========================================================================
    // GETTERS
    // ========================================================================

    /**
     * @return La liste de toutes les tours placées
     */
    public List<Tower> getTours() {
        return tours;
    }

    /**
     * @return true si on est en mode placement, false sinon
     */
    public boolean estEnModePlacement() {
        return enModePlacement;
    }

    // ========================================================================
    // NETTOYAGE
    // ========================================================================

    /**
     * Libère toutes les ressources utilisées par le gestionnaire.
     */
    public void dispose() {
        towerDataManager.dispose();
        libererTextures();
    }

    /**
     * Libère les textures chargées.
     */
    private void libererTextures() {
        if (textureSacPlein != null) {
            TextureManager.libererTexture(textureSacPlein);
        }
        if (textureSacVide != null) {
            TextureManager.libererTexture(textureSacVide);
        }
        if (towerPanelInfo != null) {
            towerPanelInfo.dispose();
        }
    }

    // ========================================================================
    // SYSTÈME D'AMÉLIORATION DES TOURS
    // ========================================================================

    /**
     * Gère le clic sur une tour ou sur l'interface d'amélioration.
     *
     * @param screenX Position X du clic à l'écran
     * @param screenY Position Y du clic à l'écran
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param mapViewport Viewport de la carte
     * @return true si le clic a été traité, false sinon
     */
    public boolean gererClicTour(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
        // Convertir les coordonnées écran en coordonnées monde
        Vector3 worldPos = convertirEcranVersMonde(screenX, screenY, screenWidth, screenHeight, mapViewport);
        if (worldPos == null) {
            return false;
        }

        // Vérifier si le panneau est visible et si on clique sur un bouton
        if (towerPanelInfo.estVisible()) {
            if (towerPanelInfo.clicSurBoutonAmeliorer(worldPos.x, worldPos.y)) {
                ameliorerTour(towerPanelInfo.getTourSelectionnee());
                return true;
            }
            if (towerPanelInfo.clicSurBoutonSupprimer(worldPos.x, worldPos.y)) {
                supprimerTour(towerPanelInfo.getTourSelectionnee());
                return true;
            }
            // Si le clic est sur le panneau mais pas sur un bouton, on ne fait rien
            if (towerPanelInfo.clicSurPanneau(worldPos.x, worldPos.y)) {
                return true;
            }
            // Si le clic est en dehors du panneau, on le ferme
            towerPanelInfo.masquer();
            tourSelectionnee = null;
        }

        // Vérifier si on clique sur une tour
        Tower tourCliquee = trouverTourAPosition(worldPos.x, worldPos.y);
        if (tourCliquee != null) {
            tourSelectionnee = tourCliquee;
            towerPanelInfo.afficher(tourCliquee);
            return true;
        }

        return false;
    }

    /**
     * Trouve une tour à une position donnée.
     *
     * @param x Position X dans le monde
     * @param y Position Y dans le monde
     * @return La tour trouvée, ou null si aucune tour n'est à cette position
     */
    private Tower trouverTourAPosition(float x, float y) {
        for (Tower tour : tours) {
            float distance = calculerDistance(x, y, tour.getPositionX(), tour.getPositionY());
            if (distance <= TOWER_SIZE / 2) {
                return tour;
            }
        }
        return null;
    }

    /**
     * Calcule la distance euclidienne entre deux points.
     *
     * @param x1 Position X du premier point
     * @param y1 Position Y du premier point
     * @param x2 Position X du deuxième point
     * @param y2 Position Y du deuxième point
     * @return La distance entre les deux points
     */
    private float calculerDistance(float x1, float y1, float x2, float y2) {
        float deltaX = x2 - x1;
        float deltaY = y2 - y1;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Améliore une tour au niveau supérieur.
     *
     * @param tour La tour à améliorer
     */
    private void ameliorerTour(Tower tour) {
        if (tour == null) {
            return;
        }

        String towerType = tour.getClass().getSimpleName();
        int niveauActuel = tour.getLevel();
        int niveauCible = niveauActuel + 1;

        // Vérifier si la tour peut être améliorée
        if (!TowerUpgradeConfig.peutEtreAmelioree(niveauActuel, tour.getMaxLevel())) {
            afficherMessageErreur(tour, "Niveau maximum atteint !");
            return;
        }

        // Vérifier si le joueur a assez d'argent
        int coutAmelioration = TowerUpgradeConfig.getCoutAmelioration(towerType, niveauCible);
        if (gameState.getLingots() < coutAmelioration) {
            afficherMessageErreur(tour, "Argent insuffisant !");
            return;
        }

        // Retirer l'argent et améliorer la tour
        if (gameState.retirerLingots(coutAmelioration)) {
            tour.upgrade();
            afficherMessageSucces(tour, "Tour améliorée !");
            towerPanelInfo.masquer();
            tourSelectionnee = null;
        }
    }

    /**
     * Supprime une tour et rembourse une partie de l'argent investi.
     *
     * @param tour La tour à supprimer
     */
    private void supprimerTour(Tower tour) {
        if (tour == null) {
            return;
        }

        String towerType = tour.getClass().getSimpleName();
        int prixInitial = tour.getPrix();
        int niveauActuel = tour.getLevel();

        // Calculer le remboursement
        int remboursement = TowerUpgradeConfig.calculerRemboursement(towerType, prixInitial, niveauActuel);

        // Rembourser le joueur
        gameState.ajouterLingots(remboursement);

        // Afficher un message
        afficherMessageSucces(tour, "+" + remboursement + " lingots");

        // Supprimer la tour
        tours.remove(tour);
        tempsForgeron.remove(tour);

        // Fermer le panneau
        towerPanelInfo.masquer();
        tourSelectionnee = null;
    }

    /**
     * Affiche un message d'erreur au-dessus d'une tour.
     *
     * @param tour La tour concernée
     * @param message Le message à afficher
     */
    private void afficherMessageErreur(Tower tour, String message) {
        messageFlottant.creerMessage(
            tour.getPositionX(),
            tour.getPositionY() + DECALAGE_MESSAGE_Y,
            message,
            Color.RED,
            TAILLE_POLICE_MESSAGE_LINGOTS,
            2f
        );
    }

    /**
     * Affiche un message de succès au-dessus d'une tour.
     *
     * @param tour La tour concernée
     * @param message Le message à afficher
     */
    private void afficherMessageSucces(Tower tour, String message) {
        messageFlottant.creerMessage(
            tour.getPositionX(),
            tour.getPositionY() + DECALAGE_MESSAGE_Y,
            message,
            Color.GREEN,
            TAILLE_POLICE_MESSAGE_LINGOTS,
            2f
        );
    }
}
