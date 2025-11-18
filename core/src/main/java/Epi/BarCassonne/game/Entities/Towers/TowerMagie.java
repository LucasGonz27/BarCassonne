package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour de magie.
 * Tour puissante avec 50 dégâts, portée de 100 et prix de 500.
 */
public class TowerMagie extends Tower {
    
    protected static final int PRIX = 500;
    protected static final float PORTEE = 100f;
    protected static final int DEGATS = 50;
    
    /**
     * Constructeur par défaut.
     * Initialise la tour avec niveau 1
     */
    public TowerMagie() {
        super(0f, 0f, 1, 4, DEGATS, PORTEE, PRIX);
    }
}
