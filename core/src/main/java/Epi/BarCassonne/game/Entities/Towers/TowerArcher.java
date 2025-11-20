package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour d'archer.
 */
public class TowerArcher extends Tower {
    
    
    protected static final int PRIX = 100;
    protected static final float PORTEE = 200f;
    
    /**
     * Constructeur par défaut.
     */
    public TowerArcher() {
        super(0f,0f,1,4, PORTEE, PRIX, TypeTour.ARCHER);
    }
}
