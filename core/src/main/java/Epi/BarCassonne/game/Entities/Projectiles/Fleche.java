package Epi.BarCassonne.game.Entities.Projectiles;


/**
 * Classe représentant la Fleche.
 */
public class Fleche extends Projectile {

    /** Dégâts de la Fleche. */
    protected static final int DEGATS = 10;

    /** Vitesse de la Fleche. */
    protected static final float VITESSE = 400f;

    /** Constructeur par défaut. */
    public Fleche() {
        super(0f, 0f, null, DEGATS, VITESSE);
    }
}
