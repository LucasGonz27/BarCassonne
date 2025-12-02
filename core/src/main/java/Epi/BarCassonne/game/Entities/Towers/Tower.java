package Epi.BarCassonne.game.Entities.Towers;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Entities.Projectiles.Projectile;
import Epi.BarCassonne.game.Interfaces.Attacker;
import Epi.BarCassonne.game.Managers.ProjectileManager;
import Epi.BarCassonne.game.Managers.SoundManager;
import Epi.BarCassonne.Factory.ProjectileFactory;

/**
 * Classe abstraite représentant une tour défensive.
 * Les tours peuvent attaquer les ennemis dans leur portée (range).
 * Implémente l'interface Attacker pour définir le comportement d'attaque.
 *
 * @author Epi
 */
public abstract class Tower implements Attacker {

    // ========================================================================
    // CONSTANTES
    // ========================================================================

    /** Intervalle entre deux attaques*/
    private static final float INTERVALLE_ATTAQUE = 2f;

    // ========================================================================
    // CHAMPS
    // ========================================================================

    /** Position X de la tour en coordonnées monde */
    protected float positionX;

    /** Position Y de la tour en coordonnées monde */
    protected float positionY;

    /** Niveau actuel de la tour */
    protected int level;

    /** Niveau maximum que la tour peut atteindre */
    protected int maxLevel;

    /** Portée d'attaque de la tour (distance maximale en pixels) */
    protected float portee;

    /** Prix d'achat de la tour */
    protected int prix;

    /** Temps écoulé depuis la dernière attaque*/
    private float tempsDepuisDerniereAttaque;

    /** Type de la tour */
    protected final TypeTour typeTour;

    // ========================================================================
    // CONSTRUCTEUR
    // ========================================================================

    /**
     * Crée une nouvelle tour.
     *
     * @param positionX Position X initiale en coordonnées monde
     * @param positionY Position Y initiale en coordonnées monde
     * @param level Niveau initial de la tour
     * @param maxLevel Niveau maximum de la tour
     * @param portee Portée d'attaque de la tour (distance maximale)
     * @param prix Prix d'achat de la tour
     * @param typeTour Type de la tour
     */
    protected Tower(float positionX, float positionY, int level, int maxLevel, float portee, int prix, TypeTour typeTour) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.level = level;
        this.maxLevel = maxLevel;
        this.portee = portee;
        this.prix = prix;
        this.typeTour = typeTour;
        this.tempsDepuisDerniereAttaque = 0f;
    }

    // ========================================================================
    // MISE À JOUR
    // ========================================================================

    /**
     * Met à jour l'état de la tour.
     * Incrémente le temps depuis la dernière attaque.
     *
     * @param delta Temps écoulé depuis la dernière frame (en secondes)
     */
    public void update(float delta) {
        tempsDepuisDerniereAttaque += delta;
    }

    // ========================================================================
    // ATTAQUE
    // ========================================================================

    /**
     * Attaque un ennemi s'il est dans la portée de la tour en créant un projectile.
     *
     * @param ennemi L'ennemi à attaquer
     * @param projectileManager Le gestionnaire de projectiles
     */
    public void attacker(Mechant ennemi, ProjectileManager projectileManager) {
        // Vérifier les préconditions
        if (!peutAttaquer(ennemi, projectileManager)) {
            return;
        }

        // Vérifier si l'ennemi est dans la portée
        if (!estDansPortee(ennemi)) {
            return;
        }

        // Créer et lancer le projectile
        creerEtLancerProjectile(ennemi, projectileManager);

        // Réinitialiser le cooldown d'attaque
        reinitialiserCooldown();
    }

    /**
     * Vérifie si la tour peut attaquer (préconditions).
     *
     * @param ennemi L'ennemi ciblé
     * @param projectileManager Le gestionnaire de projectiles
     * @return true si la tour peut attaquer, false sinon
     */
    private boolean peutAttaquer(Mechant ennemi, ProjectileManager projectileManager) {
        return ennemi != null
            && ennemi.isEnVie()
            && projectileManager != null
            && peutAttaquer();
    }

    /**
     * Vérifie si le cooldown d'attaque est écoulé.
     *
     * @return true si la tour peut attaquer, false sinon
     */
    public boolean peutAttaquer() {
        return tempsDepuisDerniereAttaque >= INTERVALLE_ATTAQUE;
    }

    /**
     * Vérifie si un ennemi est dans la portée de la tour.
     *
     * @param ennemi L'ennemi à vérifier
     * @return true si l'ennemi est dans la portée, false sinon
     */
    private boolean estDansPortee(Mechant ennemi) {
        float distance = calculerDistance(ennemi);
        return distance <= portee;
    }

    /**
     * Calcule la distance euclidienne entre la tour et un ennemi.
     *
     * @param ennemi L'ennemi ciblé
     * @return La distance entre la tour et l'ennemi
     */
    private float calculerDistance(Mechant ennemi) {
        float deltaX = ennemi.getPositionX() - positionX;
        float deltaY = ennemi.getPositionY() - positionY;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Crée un projectile et le lance vers l'ennemi.
     *
     * @param ennemi L'ennemi ciblé
     * @param projectileManager Le gestionnaire de projectiles
     */
    private void creerEtLancerProjectile(Mechant ennemi, ProjectileManager projectileManager) {
        try {
            // Créer le projectile via la factory
            String towerType = this.getClass().getSimpleName();
            Projectile projectile = ProjectileFactory.creerProjectile(towerType);

            // Initialiser le projectile
            initialiserProjectile(projectile, ennemi);

            // Ajouter le projectile au gestionnaire
            projectileManager.ajouterProjectile(projectile);

            // Jouer le son correspondant au type de tour
            SoundManager.jouerSonTour(typeTour, 0.5f);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur lors de la création du projectile: " + e.getMessage());
        }
    }

    /**
     * Initialise un projectile avec les valeurs de la tour.
     *
     * @param projectile Le projectile à initialiser
     * @param ennemi L'ennemi ciblé
     */
    private void initialiserProjectile(Projectile projectile, Mechant ennemi) {
        float projectileX = positionX;
        float projectileY = positionY;
        
        // Pour les tours Archer, le projectile part de la position de l'archer
        if (typeTour == TypeTour.ARCHER) {
            float archerSize = 100f * 0.4f; // TOWER_SIZE * 0.4f (même calcul que dans TowerManager)
            projectileY = positionY + archerSize * 0.8f;
        }
        
        projectile.setPositionX(projectileX);
        projectile.setPositionY(projectileY);
        projectile.setCible(ennemi);
        projectile.setTypeTour(typeTour);
        projectile.setNiveauTour(level); 
    }

    /**
     * Réinitialise le cooldown d'attaque.
     */
    private void reinitialiserCooldown() {
        tempsDepuisDerniereAttaque = 0f;
    }

    // ========================================================================
    // AMÉLIORATION
    // ========================================================================

    /**
     * Améliore la tour en augmentant son niveau.
     * Les sous-classes peuvent surcharger cette méthode pour ajouter des effets spécifiques.
     */
    public void upgrade() {
        if (this.level < this.maxLevel) {
            this.level++;
        }
    }

    // ========================================================================
    // GETTERS
    // ========================================================================

    /**
     * @return La position X de la tour
     */
    public float getPositionX() {
        return positionX;
    }

    /**
     * @return La position Y de la tour
     */
    public float getPositionY() {
        return positionY;
    }

    /**
     * @return Le niveau actuel de la tour
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return Le niveau maximum de la tour
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * @return La portée d'attaque de la tour (distance maximale)
     */
    public float getPortee() {
        return portee;
    }

    /**
     * @return Le prix d'achat de la tour
     */
    public int getPrix() {
        return prix;
    }

    /**
     * @return Le type de la tour
     */
    public TypeTour getTypeTour() {
        return typeTour;
    }

    // ========================================================================
    // SETTERS
    // ========================================================================

    /**
     * Définit la position X de la tour.
     *
     * @param positionX La nouvelle position X
     */
    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    /**
     * Définit la position Y de la tour.
     *
     * @param positionY La nouvelle position Y
     */
    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    /**
     * Définit le niveau de la tour.
     *
     * @param level Le nouveau niveau
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Définit le niveau maximum de la tour.
     *
     * @param maxLevel Le nouveau niveau maximum
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * Définit la portée d'attaque de la tour.
     *
     * @param portee La nouvelle portée
     */
    public void setPortee(float portee) {
        this.portee = portee;
    }

    /**
     * Définit le prix de la tour.
     *
     * @param prix Le nouveau prix
     */
    public void setPrix(int prix) {
        this.prix = prix;
    }
}
