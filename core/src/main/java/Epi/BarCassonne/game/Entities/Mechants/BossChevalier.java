package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

public class BossChevalier extends Mechant {

    public BossChevalier() {
        super(300, 0.9f,100f,100f, AssetMana.getSprite(""), 50f, 60f);
    }
}
