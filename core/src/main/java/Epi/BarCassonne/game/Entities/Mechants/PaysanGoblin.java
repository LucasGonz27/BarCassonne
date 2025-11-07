package Epi.BarCassonne.game.Entities.Mechants;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import Epi.BarCassonne.game.Managers.AssetMana;

public class PaysanGoblin extends Mechant {

    // Animation du goblin
    private Animation<TextureRegion> animation;

    public PaysanGoblin(Animation<TextureRegion> animation) {
        super(50, 1f, 100f, 100f, null);
        this.animation = animation;
    }

}
