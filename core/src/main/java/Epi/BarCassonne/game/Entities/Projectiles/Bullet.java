package Epi.BarCassonne.game.Entities.Projectiles;


/**
 * Classe représentant la Bullet.
 */
public class Bullet extends Projectile {

    /** Dégâts de la Bullet. */
    protected static final int DEGATS = 15;

    /** Vitesse de la Bullet. */
    protected static final float VITESSE = 400f;

    /** Constructeur par défaut. */
    public Bullet() {
        super(0f, 0f, null, DEGATS,  VITESSE);
    }
}
