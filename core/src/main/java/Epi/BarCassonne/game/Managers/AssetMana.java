package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetMana {

    private static AssetManager manager = new AssetManager();

    public static void load() {
        manager.load("sprites/RoiGoblin.png", Texture.class);
        manager.finishLoading();
    }

    public static Texture getSprite(String name) {
        return manager.get("sprites/" + name + ".png", Texture.class);
    }

    public static void dispose() {
        manager.dispose();
    }
}
