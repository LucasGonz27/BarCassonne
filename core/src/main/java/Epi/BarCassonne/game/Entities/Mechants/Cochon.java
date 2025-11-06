package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;
import com.badlogic.gdx.assets.AssetManager;

public class Cochon extends Mechant{

    public Cochon(){
        super(100,0.7f,100f,100f, AssetMana.getSprite(""), 50f, 60f);
    }
}
