package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour d'archer.
 * Tour de base avec 10 dégâts, portée de 200 et prix de 100.
 */
public class TowerArcher extends Tower {
    
    /**
     * Constructeur par défaut.
     * Initialise la tour avec niveau 1, max niveau 4, 10 dégâts, portée 200f et prix 100.
     */
    public TowerArcher() {
        super(0f, 0f, 1, 4, 10, 200f, 100, TypeTour.ARCHER);
    }
}
