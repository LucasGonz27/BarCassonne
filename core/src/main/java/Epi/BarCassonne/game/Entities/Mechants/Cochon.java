package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

public class Cochon extends Mechant {
    public Cochon() {
        super(100, 50f, AssetMana.getAnimation("Cochon"));
    }
}
