package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

public class RoiGoblin extends Mechant {
    public RoiGoblin() {
        super(500, 0.5f, AssetMana.getAnimation("RoiGoblin"));
    }
}
