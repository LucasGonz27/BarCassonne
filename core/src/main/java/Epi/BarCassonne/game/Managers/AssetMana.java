package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetMana {

    private Animation<TextureRegion> animation;
    private float stateTime;

    public static Animation<TextureRegion> getAnimationMéchant(String name) {
        Texture spriteSheet = new Texture(Gdx.files.internal(name + ".png"));
        int frameCols = 3;
        int frameRows = 8;
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
            spriteSheet.getWidth() / frameCols,
            spriteSheet.getHeight() / frameRows);

        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i=0; i<frameRows; i++) {
            for (int j=0; j<frameCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        return new Animation<TextureRegion>(0.15f, frames);
    }



}
