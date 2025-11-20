package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour canon.
 */
public class TowerCanon extends Tower {

    protected static final int PRIX = 400;
    protected static final float PORTEE = 150f;
    protected static final int DEGATS = 10;
    
    /**
     * Constructeur par défaut.
     */
    public TowerCanon() {
        super(0f, 0f, 1, 4, PORTEE, PRIX, TypeTour.CANON);
    }
}
