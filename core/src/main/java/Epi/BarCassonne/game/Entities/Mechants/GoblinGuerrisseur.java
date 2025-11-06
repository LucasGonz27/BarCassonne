package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

public class GoblinGuerrisseur extends Mechant {

    public GoblinGuerrisseur(){
        super(80,1.3f,100f,100f, AssetMana.getSprite(""), 50f, 60f);
    }
}
