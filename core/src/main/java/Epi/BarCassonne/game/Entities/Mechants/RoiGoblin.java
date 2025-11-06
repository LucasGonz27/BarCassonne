package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

public class RoiGoblin extends Mechant {

    public RoiGoblin() {
        super(500, 0.5f, 450f, 450f, AssetMana.getSprite("RoiGoblin"), 50f, 60f);
    }
}
