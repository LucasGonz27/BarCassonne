package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour canon.
 */
public class TowerCanon extends Tower {

    protected static final int PRIX = 600;
    protected static final float PORTEE = 110f;
    /**
     * Constructeur par défaut.
     */
    public TowerCanon() {
        super(0f, 0f, 1, 4, PORTEE, PRIX, TypeTour.CANON);
    }
}
