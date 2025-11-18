package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour d'archer.
 * Tour de base avec 10 dégâts, portée de 200 et prix de 100.
 */
public class TowerArcher extends Tower {
    
    protected static final int PRIX = 100;
    protected static final float PORTEE = 200f;
    protected static final int DEGATS = 10;
    
    /**
     * Constructeur par défaut.
     * Initialise la tour avec niveau 1
     */
    public TowerArcher() {
        super(0f, 0f, 1, 4, DEGATS, PORTEE, PRIX);
    }
}
