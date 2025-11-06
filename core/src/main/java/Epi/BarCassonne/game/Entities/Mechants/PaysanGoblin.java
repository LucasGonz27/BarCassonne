package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

public class PaysanGoblin extends Mechant {

    public PaysanGoblin(){
        super(50,1f,100f,100f, AssetMana.getSprite(""),50f, 60f);
    }
}
