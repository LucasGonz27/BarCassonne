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
    protected float damage;
    
    /** Portée d'attaque de la tour (distance maximale) */
    protected float portee;
    
    /** Prix d'achat de la tour */
    protected int prix;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée une nouvelle tour.
     * @param positionX Position X initiale en coordonnées monde
     * @param positionY Position Y initiale en coordonnées monde
     * @param level Niveau initial de la tour
     * @param maxLevel Niveau maximum de la tour
     * @param damage Dégâts infligés par la tour
     * @param portee Portée d'attaque de la tour
     * @param prix Prix d'achat de la tour
     */
    public Tower(float positionX, float positionY, int level, int maxLevel, float damage, float portee, int prix) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.level = level;
        this.maxLevel = maxLevel;
        this.damage = damage;
        this.portee = portee;
        this.prix = prix;
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
    public float getDamage() {
        return damage;
    }

    /**
     * Définit les dégâts de la tour.
     * @param damage Les nouveaux dégâts
     */
    public void setDamage(float damage) {
        this.damage = damage;
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
        if (UnMechant == null) return;

        // Calculer la distance euclidienne entre la tour et l'ennemi
        float distance = (float) Math.sqrt(Math.pow(UnMechant.getPositionX() - positionX, 2) + Math.pow(UnMechant.getPositionY() - positionY, 2));

        if (distance <= portee) {
            // L'ennemi est à portée, infliger les dégâts
            UnMechant.recevoirDegats((int) this.damage);
            System.out.println(distance + " " + UnMechant.getPositionX() + " " + UnMechant.getPositionY());
        }
        else {
            System.out.println("Mechant hors de portée");
        }
    }

    // ------------------------------------------------------------------------
    // REGION : AMÉLIORATION
    // ------------------------------------------------------------------------
    /**
     * Améliore la tour en augmentant son niveau, ses dégâts et sa portée.
     * L'amélioration n'est possible que si le niveau actuel est inférieur au niveau maximum.
     */
    public void upgrade() {
        if (this.level < this.maxLevel) {
            this.level++;
            this.damage = 1.5f;
            this.portee = 1.5f;
            System.out.println("La tour a été améliorée");
        }
        else {
            System.out.println("Argent Manquant");
        }
    }

    // ------------------------------------------------------------------------
    // REGION : MÉTHODES ABSTRAITES / À IMPLÉMENTER
    // ------------------------------------------------------------------------
    /**
     * Affiche la tour (méthode à implémenter dans les sous-classes si nécessaire).
     */
    public void display() {
    }

    /**
     * Met à jour la tour (méthode à implémenter dans les sous-classes si nécessaire).
     * Appelée à chaque frame pour mettre à jour l'état de la tour.
     */
    public void update() {
    }
 
}
