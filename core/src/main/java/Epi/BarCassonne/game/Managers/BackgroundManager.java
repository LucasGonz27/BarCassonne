package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundManager {
    private Texture background;

    public BackgroundManager(String path) {
        background = new Texture(Gdx.files.internal(path));
    }

    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, 800, 600); // taille du monde
    }

    public void dispose() {
        background.dispose();
    }
}
