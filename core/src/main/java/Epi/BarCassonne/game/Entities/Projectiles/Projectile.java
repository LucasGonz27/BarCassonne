package Epi.BarCassonne.game.Entities.Projectiles;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Epi.BarCassonne.game.Config.TowerUpgradeConfig;
/**
 * Classe abstraite représentant un projectile tiré par une tour.
 * Le projectile suit automatiquement sa cible pour ne jamais la rater.
 *
 * @author Epi
 */
public abstract class Projectile {

    // ========================================================================
    // CONSTANTES
    // ========================================================================

    /** Distance seuil pour considérer que le projectile a touché la cible */
    private static final float RAYON_COLLISION = 15f;

    /** Taille d'affichage du projectile à l'écran */
    private static final float TAILLE_RENDU = 18f;

    // ========================================================================
    // CHAMPS
    // ========================================================================

    /** Position actuelle du projectile dans le monde */
    private final Vector2 position;

    /** Vitesse de déplacement du projectile*/
    private final float vitesse;

    /** Direction normalisée vers la cible*/
    private final Vector2 direction;

    /** Angle de rotation pour l'affichage*/
    private float angle;

    /** Dégâts infligés à la cible lors de l'impact */
    private final int degats;

    /** Type de tour qui a tiré ce projectile (pour les résistances) */
    private TypeTour typeTour;

    /** Niveau de la tour qui a tiré ce projectile (pour le multiplicateur de dégâts) */
    private int niveauTour;

    /** Cible que le projectile doit atteindre */
    private Mechant cible;

    /** Indique si le projectile a touché sa cible */
    private boolean aTouche;

    // ========================================================================
    // CONSTRUCTEUR
    // ========================================================================

    /**
     * Crée un nouveau projectile.
     *
     * @param startX Position X de départ du projectile
     * @param startY Position Y de départ du projectile
     * @param cible Cible à atteindre (peut être null, sera définie via setCible)
     * @param degats Dégâts à infliger à la cible
     * @param vitesse Vitesse de déplacement du projectile
     */
    public Projectile(float startX, float startY, Mechant cible, int degats, float vitesse) {
        this.position = new Vector2(startX, startY);
        this.direction = new Vector2();
        this.cible = cible;
        this.degats = degats;
        this.vitesse = vitesse;
        this.angle = 0;
        this.aTouche = false;
        this.niveauTour = 1;
    }

    // ========================================================================
    // MISE À JOUR
    // ========================================================================

    /**
     * Met à jour la position et l'état du projectile.
     * Le projectile suit automatiquement sa cible pour ne jamais la rater.
     *
     * @param delta Temps écoulé depuis la dernière frame (en secondes)
     */
    public void update(float delta) {
        // Vérifier si le projectile doit être arrêté
        if (doitEtreArrete()) {
            aTouche = true;
            return;
        }

        // Calculer la direction vers la cible
        float distance = calculerDirectionVersCible();

        // Vérifier la collision avec la cible
        if (verifierCollision(distance)) {
            infligerDegats();
            aTouche = true;
            return;
        }

        // Mettre à jour la position et l'angle
        mettreAJourPosition(delta);
        mettreAJourAngle();
    }

    /**
     * Vérifie si le projectile doit être arrêté.
     *
     * @return true si le projectile doit être arrêté
     */
    private boolean doitEtreArrete() {
        return aTouche || cible == null || !cible.isEnVie();
    }

    /**
     * Calcule la direction vers la cible et retourne la distance.
     *
     * @return La distance entre le projectile et sa cible
     */
    private float calculerDirectionVersCible() {
        direction.set(cible.getCentreX(), cible.getCentreY()).sub(position);
        float distance = direction.len();
        direction.nor(); // Normaliser la direction
        return distance;
    }

    /**
     * Vérifie si le projectile entre en collision avec sa cible.
     *
     * @param distance Distance entre le projectile et sa cible
     * @return true si une collision est détectée
     */
    private boolean verifierCollision(float distance) {
        return distance <= RAYON_COLLISION;
    }

    /**
     * Inflige les dégâts à la cible en tenant compte du niveau de la tour.
     */
    private void infligerDegats() {
        if (cible != null && cible.isEnVie()) {
            // Calculer les dégâts en fonction du niveau de la tour
            int degatsFinaux = calculerDegatsAvecNiveau();
            cible.recevoirDegats(degatsFinaux, typeTour);
        }
    }

    /**
     * Calcule les dégâts finaux en appliquant le multiplicateur du niveau.
     *
     * @return Les dégâts finaux après application du multiplicateur
     */
    private int calculerDegatsAvecNiveau() {
        float multiplicateur = TowerUpgradeConfig.getMultiplicateurDegats(niveauTour);
        return (int) (degats * multiplicateur);
    }

    /**
     * Met à jour la position du projectile en fonction de sa direction et de sa vitesse.
     *
     * @param delta Temps écoulé depuis la dernière frame
     */
    private void mettreAJourPosition(float delta) {
        float deplacementX = direction.x * vitesse * delta;
        float deplacementY = direction.y * vitesse * delta;
        position.add(deplacementX, deplacementY);
    }

    /**
     * Met à jour l'angle de rotation du projectile pour l'affichage.
     */
    private void mettreAJourAngle() {
        this.angle = (float) Math.toDegrees(Math.atan2(direction.y, direction.x));
    }

    // ========================================================================
    // RENDU
    // ========================================================================

    /**
     * Dessine le projectile à l'écran.
     *
     * @param batch SpriteBatch pour le rendu
     * @param texture Texture du projectile à dessiner
     */
    public void render(SpriteBatch batch, Texture texture) {
        if (texture == null || doitEtreSupprime()) {
            return;
        }

        float x = getPositionX() - getTailleRendu() / 2;
        float y = getPositionY() - getTailleRendu() / 2;
        float origineX = getTailleRendu() / 2;
        float origineY = getTailleRendu() / 2;
        float largeur = getTailleRendu();
        float hauteur = getTailleRendu();

        batch.draw(texture, x, y, origineX, origineY, largeur, hauteur, 1, 1, getAngle(),0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    // ========================================================================
    // GETTERS
    // ========================================================================

    /**
     * Vérifie si le projectile doit être supprimé.
     *
     * @return true si le projectile doit être supprimé
     */
    public boolean doitEtreSupprime() {
        return aTouche || cible == null || !cible.isEnVie();
    }

    /**
     * @return La position X du projectile
     */
    public float getPositionX() {
        return position.x;
    }

    /**
     * @return La position Y du projectile
     */
    public float getPositionY() {
        return position.y;
    }

    /**
     * @return L'angle de rotation du projectile (en degrés)
     */
    public float getAngle() {
        return angle;
    }

    /**
     * @return Les dégâts infligés par le projectile
     */
    public int getDegats() {
        return degats;
    }

    /**
     * @return La vitesse de déplacement du projectile
     */
    public float getVitesse() {
        return vitesse;
    }

    /**
     * @return La taille d'affichage du projectile
     */
    public float getTailleRendu() {
        return TAILLE_RENDU;
    }

    /**
     * @return Le type de tour qui a tiré ce projectile
     */
    public TypeTour getTypeTour() {
        return typeTour;
    }

    /**
     * Convertit le TypeTour en nom de tour pour récupérer la texture.
     *
     * @return Le nom de la tour (ex: "TowerArcher"), ou null si le type est inconnu
     */
    public String getTowerTypeName() {
        if (typeTour == null) {
            return null;
        }

        switch (typeTour) {
            case ARCHER:
                return "TowerArcher";
            case MAGIE:
                return "TowerMagie";
            case CANON:
                return "TowerCanon";
            default:
                return null;
        }
    }

    // ========================================================================
    // SETTERS
    // ========================================================================

    /**
     * Définit la position X du projectile.
     *
     * @param positionX Nouvelle position X
     */
    public void setPositionX(float positionX) {
        this.position.x = positionX;
    }

    /**
     * Définit la position Y du projectile.
     *
     * @param positionY Nouvelle position Y
     */
    public void setPositionY(float positionY) {
        this.position.y = positionY;
    }

    /**
     * Définit la cible du projectile.
     *
     * @param cible La nouvelle cible à atteindre
     */
    public void setCible(Mechant cible) {
        this.cible = cible;
    }

    /**
     * Définit le type de tour du projectile.
     *
     * @param typeTour Le nouveau type de tour
     */
    public void setTypeTour(TypeTour typeTour) {
        this.typeTour = typeTour;
    }

    /**
     * Définit le niveau de la tour qui a tiré ce projectile.
     *
     * @param niveauTour Le niveau de la tour
     */
    public void setNiveauTour(int niveauTour) {
        this.niveauTour = niveauTour;
    }

    /**
     * @return Le niveau de la tour qui a tiré ce projectile
     */
    public int getNiveauTour() {
        return niveauTour;
    }
}
