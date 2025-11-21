package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour forgeron.
 */
public class TowerForgeron extends Tower {

    protected static final int PRIX = 1000;
    protected static final float PORTEE = 0f;
    protected static final int APPORT_LINGOTS = 200;
    
    /** Nombre de lingots générés par cette tour. */
    protected int ApportLingots;

    /**
     * Constructeur par défaut.
     */
    public TowerForgeron() {
        super(0f, 0f, 1, 4, PORTEE, PRIX, TypeTour.FORGERON);
        this.ApportLingots = APPORT_LINGOTS;
    }   

    /**
     * Retourne le nombre de lingots générés par cette tour.
     * @return Le nombre de lingots générés
     */
    public int getApportLingots() {
        return ApportLingots;
    }
    
}
