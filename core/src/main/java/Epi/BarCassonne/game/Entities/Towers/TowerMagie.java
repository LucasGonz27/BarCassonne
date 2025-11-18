package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour de magie.
 * Tour puissante avec 50 dégâts, portée de 100 et prix de 500.
 */
public class TowerMagie extends Tower {
    
    /**
     * Constructeur par défaut.
     * Initialise la tour avec niveau 1, max niveau 4, 50 dégâts, portée 100f et prix 500.
     */
    public TowerMagie() {
        super(0f, 0f, 1, 4, 50, 100f, 500);
    }
}
