package Epi.BarCassonne.game.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Epi.BarCassonne.game.Config.TowerUpgradeConfig;
import Epi.BarCassonne.game.Entities.Towers.Tower;
import Epi.BarCassonne.game.Entities.Towers.TowerForgeron;
import Epi.BarCassonne.game.Managers.TextureManager;
import Epi.BarCassonne.game.Managers.TowerDataManager;
import Epi.BarCassonne.game.Utils.Button;
import Epi.BarCassonne.game.Utils.Texte;


/**
 * Interface d'amélioration des tours.
 * Affiche un panneau avec les options d'amélioration et de suppression.
 *
 * @author Epi
 */
public class TowerPanelInfo {

    // ========================================================================
    // CONSTANTES - DIMENSIONS DU PANNEAU
    // ========================================================================

    /** Largeur du panneau de fond */
    private static final float LARGEUR_PANNEAU = 650f;

    /** Hauteur du panneau de fond */
    private static final float HAUTEUR_PANNEAU = 220f;

    // ========================================================================
    // CHAMPS - ÉTAT
    // ========================================================================

    /** Indique si le panneau est visible */
    private boolean visible;

    /** Tour actuellement sélectionnée */
    private Tower tourSelectionnee;

    /** Largeur de l'écran */
    private float screenWidth;

    /** Hauteur de l'écran */
    private float screenHeight;

    /** Position X du panneau */
    private float panneauX;

    /** Position Y du panneau */
    private float panneauY;

    // ========================================================================
    // CHAMPS - RESSOURCES
    // ========================================================================

    /** Texture de fond du panneau */
    private Texture textureAmelioration;


    /** Bouton Améliorer */
    private Button boutonAmeliorer;

    /** Bouton Supprimer */
    private Button boutonSupprimer;

    /** Callback pour améliorer la tour */
    private Runnable callbackAmeliorer;

    /** Callback pour supprimer la tour */
    private Runnable callbackSupprimer;

    /** Gestionnaire des données des tours */
    private final TowerDataManager towerDataManager;

    // ========================================================================
    // CONSTRUCTEUR
    // ========================================================================

    /**
     * Crée une nouvelle interface d'amélioration de tours.
     */
    public TowerPanelInfo() {
        this.visible = false;
        this.towerDataManager = new TowerDataManager();
        chargerRessources();
    }

    /**
     * Charge les ressources nécessaires (textures).
     */
    private void chargerRessources() {
        // Charger la texture de fond
        textureAmelioration = TextureManager.chargerTexture("HUD/InterfaceTower.png");
    }

    // ========================================================================
    // GESTION DE L'AFFICHAGE
    // ========================================================================

    /**
     * Affiche le panneau d'amélioration pour une tour.
     *
     * @param tour La tour sélectionnée
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @param callbackAmeliorer Callback à appeler lors du clic sur "Améliorer"
     * @param callbackSupprimer Callback à appeler lors du clic sur "Supprimer"
     */
    public void afficher(Tower tour, float screenWidth, float screenHeight, Runnable callbackAmeliorer, Runnable callbackSupprimer) {
        this.tourSelectionnee = tour;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.callbackAmeliorer = callbackAmeliorer;
        this.callbackSupprimer = callbackSupprimer;
        this.visible = true;
        positionnerPanneau();
        initialiserBoutons();
    }

    /**
     * Masque le panneau d'amélioration.
     */
    public void masquer() {
        this.visible = false;
        this.tourSelectionnee = null;
        this.callbackAmeliorer = null;
        this.callbackSupprimer = null;
        libererBoutons();
    }

    /**
     * Met à jour les boutons (détection des clics).
     * Doit être appelé à chaque frame.
     */
    public void update() {
        if (!visible) {
            return;
        }
        if (boutonAmeliorer != null) {
            boutonAmeliorer.update();
        }
        if (boutonSupprimer != null) {
            boutonSupprimer.update();
        }
    }

    /**
     * Calcule la position du panneau en bas de l'écran, centré horizontalement.
     */
    private void positionnerPanneau() {
        panneauX = screenWidth * 0.465f;
        panneauY = 0f;
    }

    // ========================================================================
    // INITIALISATION DES BOUTONS
    // ========================================================================

    /**
     * Initialise les boutons Améliorer et Supprimer.
     */
    private void initialiserBoutons() {

        //position du bouton amelioration
        float boutonXAmeliorer = panneauX + LARGEUR_PANNEAU * 0.37f;
        float boutonYAmeliorer = panneauY + HAUTEUR_PANNEAU * 0.36f;
        float largeurBoutonAmeliorer = LARGEUR_PANNEAU * 0.28f;
        float hauteurBoutonAmeliorer = HAUTEUR_PANNEAU * 0.23f;

        String texteAmeliorer = "Améliorer";
        Color couleurAmeliorer = Color.GREEN;
        int taillePoliceAmeliorer = 40;
        String cheminTextureAmeliorer = "skin/SkinBoutonBois.png";

        if (TowerUpgradeConfig.peutEtreAmelioree(tourSelectionnee.getLevel(), tourSelectionnee.getMaxLevel())) {
            if (boutonAmeliorer != null) {
                boutonAmeliorer.dispose();
            }
            boutonAmeliorer = new Button(boutonXAmeliorer, boutonYAmeliorer, largeurBoutonAmeliorer, hauteurBoutonAmeliorer, texteAmeliorer, cheminTextureAmeliorer, couleurAmeliorer, taillePoliceAmeliorer);
            // Définir l'action du bouton Améliorer
            if (callbackAmeliorer != null) {
                boutonAmeliorer.setAction(callbackAmeliorer);
            }
        } else {
            if (boutonAmeliorer != null) {
                boutonAmeliorer.dispose();
                boutonAmeliorer = null;
            }
        }

        //position du bouton supprimer
        float boutonXSupprimer = panneauX + LARGEUR_PANNEAU * 0.37f;
        float boutonYSupprimer = panneauY + HAUTEUR_PANNEAU * 0.11f;
        float largeurBoutonSupprimer = LARGEUR_PANNEAU * 0.28f;
        float hauteurBoutonSupprimer = HAUTEUR_PANNEAU * 0.23f;
        String texteSupprimer = "Supprimer";
        Color couleurSupprimer = Color.RED;
        int taillePoliceSupprimer = 40;
        String cheminTextureSupprimer = "skin/SkinBoutonBois.png";

        if (boutonSupprimer != null) {
            boutonSupprimer.dispose();
        }
        boutonSupprimer = new Button(boutonXSupprimer, boutonYSupprimer, largeurBoutonSupprimer, hauteurBoutonSupprimer, texteSupprimer, cheminTextureSupprimer, couleurSupprimer, taillePoliceSupprimer);

        if (callbackSupprimer != null) {
            boutonSupprimer.setAction(callbackSupprimer);
        }
    }

    /**
     * Libère les boutons.
     */
    private void libererBoutons() {
        if (boutonAmeliorer != null) {
            boutonAmeliorer.dispose();
            boutonAmeliorer = null;
        }
        if (boutonSupprimer != null) {
            boutonSupprimer.dispose();
            boutonSupprimer = null;
        }
    }

    // ========================================================================
    // RENDU
    // ========================================================================

    /**
     * Dessine le panneau d'amélioration.
     *
     * @param batch SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {
        if (!visible || tourSelectionnee == null) {
            return;
        }

        dessinerFond(batch);
        dessinerInfosTour(batch);
        dessinerBoutons(batch);
    }

        /**
     * Dessine les boutons Améliorer et Supprimer.
     *
     * @param batch SpriteBatch pour le rendu
     */
        private void dessinerBoutons(SpriteBatch batch) {
            if (boutonAmeliorer != null) {
                boutonAmeliorer.render(batch);
            }
            if (boutonSupprimer != null) {
                boutonSupprimer.render(batch);
            }
        }

    /**
     * Dessine le fond du panneau.
     *
     * @param batch SpriteBatch pour le rendu
     */
    private void dessinerFond(SpriteBatch batch) {
        if (textureAmelioration != null) {
            batch.draw(textureAmelioration, panneauX, panneauY, LARGEUR_PANNEAU, HAUTEUR_PANNEAU);
        }
    }

    /**
     * Dessine les informations de la tour (niveau, type, coût).
     *
     * @param batch SpriteBatch pour le rendu
     */
    private void dessinerInfosTour(SpriteBatch batch) {

        //on recupere le type
        String towerType = tourSelectionnee.getClass().getSimpleName();

        // Récupérer le niveau
        int niveau = tourSelectionnee.getLevel();

        // Récupérer l'image de la tour depuis TowerDataManager (coordonnées relatives)
        Texture imageTour = towerDataManager.getTextureWithLevel(towerType, niveau);
        if (imageTour != null) {
            float imageSize = HAUTEUR_PANNEAU * 0.45f; // ~45% de la hauteur du panneau
            batch.draw(imageTour, panneauX + LARGEUR_PANNEAU * 0.12f, panneauY + HAUTEUR_PANNEAU * 0.27f, imageSize, imageSize);
        }

        if (tourSelectionnee instanceof TowerForgeron) {
            // Afficher le nombre de lingots générés par la tour
            int lingots = ((TowerForgeron) tourSelectionnee).getApportLingots();
            Texte.drawText(batch, "Lingots: " + lingots, panneauX + LARGEUR_PANNEAU * 0.38f, panneauY + HAUTEUR_PANNEAU * 0.82f, Color.BLACK, 33);
        } else {
            // Afficher les dégâts (toujours affichés, même au niveau max) - coordonnées relatives
            Texte.drawText(batch, "Dégats: ", panneauX + LARGEUR_PANNEAU * 0.38f, panneauY + HAUTEUR_PANNEAU * 0.82f, Color.BLACK, 33);
            int degats = (int) (towerDataManager.getDegats(towerType) * TowerUpgradeConfig.getMultiplicateurDegats(niveau));
            Texte.drawText(batch, "" + degats, panneauX + LARGEUR_PANNEAU * 0.60f, panneauY + HAUTEUR_PANNEAU * 0.82f, Color.BLACK, 30);
        }

        // Texte qui affiche le niveau actuel de la tour - coordonnées relatives
        if (TowerUpgradeConfig.peutEtreAmelioree(niveau, tourSelectionnee.getMaxLevel())) {
            // Afficher le niveau normal si on peut encore améliorer
            Texte.drawText(batch, "Niveau " + niveau, panneauX + LARGEUR_PANNEAU * 0.76f, panneauY + HAUTEUR_PANNEAU * 0.86f, Color.WHITE, 35);
            // Afficher le coût d'amélioration
            int coutAmelioration = TowerUpgradeConfig.getCoutAmelioration(towerType, niveau + 1);
            Texte.drawText(batch, "(" + coutAmelioration + " lingots)", panneauX + LARGEUR_PANNEAU * 0.70f, panneauY + HAUTEUR_PANNEAU * 0.56f, Color.WHITE, 28);
        } else {
            // Afficher "Niveau MAX" si on est au niveau maximum
            Texte.drawText(batch, "Niveau MAX", panneauX + LARGEUR_PANNEAU * 0.76f, panneauY + HAUTEUR_PANNEAU * 0.86f, Color.WHITE, 25);
        }
    }

    // ========================================================================
    // GETTERS
    // ========================================================================

    /**
     * @return true si le panneau est visible
     */
    public boolean estVisible() {
        return visible;
    }

    /**
     * @return La tour actuellement sélectionnée
     */
    public Tower getTourSelectionnee() {
        return tourSelectionnee;
    }

    // ========================================================================
    // NETTOYAGE
    // ========================================================================

    /**
     * Libère les ressources utilisées.
     */
    public void dispose() {
        if (textureAmelioration != null) {
            TextureManager.libererTexture(textureAmelioration);
        }
        libererBoutons();
    }
}
