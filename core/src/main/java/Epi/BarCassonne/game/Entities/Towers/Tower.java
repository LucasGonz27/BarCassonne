package Epi.BarCassonne.game.Entities.Towers;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Interfaces.Attacker;

/**
 * Classe abstraite représentant une tour défensive.
 * Les tours peuvent attaquer les ennemis dans leur portée.
 * Implémente l'interface Attacker pour définir le comportement d'attaque.
 */
public abstract class Tower implements Attacker{

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    /** Position X de la tour en coordonnées monde */
    protected float positionX;

    /** Position Y de la tour en coordonnées monde */
    protected float positionY;

    /** Niveau actuel de la tour */
    protected int level;

    /** Niveau maximum que la tour peut atteindre */
    protected int maxLevel;

    /** Dégâts infligés par la tour */
    protected int degats;

    /** Portée d'attaque de la tour (distance maximale) */
    protected float portee;

    /** Prix d'achat de la tour */
    protected int prix;

    /** Temps écoulé depuis la dernière attaque */
    private float tempsDepuisDerniereAttaque;

    /** Intervalle entre deux attaques (en secondes) */
    private static final float INTERVALLE_ATTAQUE = 2f;

    /** Type de la tour (utilisé pour le système de résistances) */
    protected final TypeTour typeTour;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée une nouvelle tour.
     * @param positionX Position X initiale en coordonnées monde
     * @param positionY Position Y initiale en coordonnées monde
     * @param level Niveau initial de la tour
     * @param maxLevel Niveau maximum de la tour
     * @param degats Dégâts infligés par la tour
     * @param portee Portée d'attaque de la tour
     * @param prix Prix d'achat de la tour
     * @param typeTour Type de la tour (doit être défini par chaque sous-classe)
     */
    protected Tower(float positionX, float positionY, int level, int maxLevel, int degats, float portee, int prix, TypeTour typeTour) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.level = level;
        this.maxLevel = maxLevel;
        this.degats = degats;
        this.portee = portee;
        this.prix = prix;
        this.tempsDepuisDerniereAttaque = 0f;
        this.typeTour = typeTour;
    }

    // ------------------------------------------------------------------------
    // REGION : GETTERS & SETTERS
    // ------------------------------------------------------------------------
    /**
     * @return Le prix d'achat de la tour
     */
    public int getPrix() {
        return prix;
    }

    /**
     * Définit le prix de la tour.
     * @param prix Le nouveau prix
     */
    public void setPrix(int prix) {
        this.prix = prix;
    }

    /**
     * @return La position X de la tour
     */
    public float getPositionX() {
        return positionX;
    }

    /**
     * Définit la position X de la tour.
     * @param positionX La nouvelle position X
     */
    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    /**
     * @return La position Y de la tour
     */
    public float getPositionY() {
        return positionY;
    }

    /**
     * Définit la position Y de la tour.
     * @param positionY La nouvelle position Y
     */
    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    /**
     * @return Le niveau actuel de la tour
     */
    public int getLevel() {
        return level;
    }

    /**
     * Définit le niveau de la tour.
     * @param level Le nouveau niveau
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return Le niveau maximum de la tour
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Définit le niveau maximum de la tour.
     * @param maxLevel Le nouveau niveau maximum
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * @return Les dégâts infligés par la tour
     */
    public float getDegats() {
        return degats;
    }

    /**
     * Définit les dégâts de la tour.
     * @param degats Les nouveaux dégâts
     */
    public void setDegats(int degats) {
        this.degats = degats;
    }

    /**
     * @return La portée d'attaque de la tour
     */
    public float getPortee() {
        return portee;
    }

    /**
     * Définit la portée d'attaque de la tour.
     * @param portee La nouvelle portée
     */
    public void setPortee(float portee) {
        this.portee = portee;
    }

    // ------------------------------------------------------------------------
    // REGION : ATTAQUE
    // ------------------------------------------------------------------------

    /**
     * Attaque un ennemi s'il est dans la portée de la tour.
     * Calcule la distance entre la tour et l'ennemi, et inflige des dégâts si
     * l'ennemi est à portée.
     * @param UnMechant L'ennemi à attaquer
     */
    @Override
    public void attacker(Mechant UnMechant) {
        if (UnMechant == null || !peutAttaquer()) {
            return;
        }

        // Calculer la distance euclidienne entre la tour et l'ennemi
        float distance = (float) Math.sqrt(
            Math.pow(UnMechant.getPositionX() - positionX, 2) +
            Math.pow(UnMechant.getPositionY() - positionY, 2)
        );

        if (distance <= portee) {
            // L'ennemi est à portée, infliger les dégâts (en tenant compte des résistances)
            UnMechant.recevoirDegats(this.degats, this.typeTour);
            tempsDepuisDerniereAttaque = 0f;
        
            System.out.println("la tour " + this.getClass().getSimpleName() + " a attaqué l'ennemi " + 
                UnMechant.getClass().getSimpleName() + " de " + this.degats + " dégâts. En vie: " + UnMechant.isEnVie());
        }
    }

    /**
     * Vérifie si la tour peut attaquer (si le cooldown est écoulé).
     * @return true si la tour peut attaquer, false sinon
     */
    public boolean peutAttaquer() {
        return tempsDepuisDerniereAttaque >= INTERVALLE_ATTAQUE;
    }

    /**
     * Met à jour la tour (appelée à chaque frame).
     * @param delta Temps écoulé depuis la dernière frame
     */
    public void update(float delta) {
        tempsDepuisDerniereAttaque += delta;
    }

    // ------------------------------------------------------------------------
    // REGION : AMÉLIORATION
    // ------------------------------------------------------------------------
    /**
     * Améliore la tour en augmentant son niveau, ses dégâts et sa portée.
     * @param deltaTime Temps écoulé depuis la dernière frame (non utilisé dans l'implémentation par défaut)
     * @param prix Prix de l'amélioration (non utilisé dans l'implémentation par défaut)
     */
    public void upgrade(float deltaTime, int prix) {
        if (this.level < this.maxLevel) {
            this.level++;
        }
    }
}
