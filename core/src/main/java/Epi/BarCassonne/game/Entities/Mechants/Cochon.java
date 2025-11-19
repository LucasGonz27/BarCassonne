package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le Cochon.
 */
public class Cochon extends Mechant {
    
    /**
     * Constructeur par défaut.
     */
    public Cochon() {
        super(100, 50f, AssetMana.getAnimation("Cochon"));
    }
}
