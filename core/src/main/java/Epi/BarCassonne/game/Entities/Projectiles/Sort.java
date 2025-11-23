package Epi.BarCassonne.game.Entities.Projectiles;


/**
 * Classe représentant le Sort.
 */
public class Sort extends Projectile {

    /** Dégâts du Sort. */
    protected static final int DEGATS = 20;

    /** Vitesse du Sort. */
    protected static final float VITESSE = 350f;

    /** Constructeur par défaut. */
    public Sort() {
        super(0f, 0f, null, DEGATS, VITESSE);
    }
}
