package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour d'archer.
 */
public class TowerArcher extends Tower {

    /** Prix de la tour*/
    protected static final int PRIX = 100;

    /** Portée de la tour*/
    protected static final float PORTEE = 240f;

    /**
     * Constructeur par défaut.
     */
    public TowerArcher() {
        super(0f,0f,1,4, PORTEE, PRIX, TypeTour.ARCHER);
    }
}
