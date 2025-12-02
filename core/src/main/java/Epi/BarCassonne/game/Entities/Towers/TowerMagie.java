package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour de magie.
 */
public class TowerMagie extends Tower {

    /** Prix de la tour*/
    protected static final int PRIX = 1000;

    /** Portée de la tour*/
    protected static final float PORTEE = 200f;

    /**
     * Constructeur par défaut.
     */
    public TowerMagie() {
        super(0f, 0f, 1, 4, PORTEE, PRIX, TypeTour.MAGIE);
    }
}
