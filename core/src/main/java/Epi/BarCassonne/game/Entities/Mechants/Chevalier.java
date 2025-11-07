package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

public class Chevalier extends Mechant {
    public Chevalier() {
        super(250, 1.6f, AssetMana.getAnimation("Chevalier"));
    }
}
