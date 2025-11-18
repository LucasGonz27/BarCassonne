package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour canon.
 * Tour avec 10 dégâts, portée de 50 et prix de 200.
 */
public class TowerCanon extends Tower {
    
    /**
     * Constructeur par défaut.
     * Initialise la tour avec niveau 1, max niveau 4, 10 dégâts, portée 50f et prix 200.
     */
    public TowerCanon() {
        super(0f, 0f, 1, 4, 10, 50f, 200, TypeTour.CANON);
    }
}
