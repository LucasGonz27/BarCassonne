package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour canon.
 */
public class TowerCanon extends Tower {

    /** Prix de la tour*/
    protected static final int PRIX = 600;
    /** Portée de la tour*/
    protected static final float PORTEE = 140f;
    /**
     * Constructeur par défaut.
     */
    public TowerCanon() {
        super(0f, 0f, 1, 4, PORTEE, PRIX, TypeTour.CANON);
    }
}
