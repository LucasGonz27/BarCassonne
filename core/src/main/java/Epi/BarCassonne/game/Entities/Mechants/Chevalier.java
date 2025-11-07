package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

public class Chevalier extends Mechant{
    public Chevalier(){
        super(250,1.6f,100f,100f, AssetMana.getSprite(""), 50f, 60f);
    }
}
