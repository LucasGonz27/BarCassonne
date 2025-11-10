package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundManager {
    private Texture background;

    public BackgroundManager(String path) {
        try {
            background = new Texture(Gdx.files.internal(path));
            background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du background: " + path);
            e.printStackTrace();
            background = new Texture(800, 600, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
        }
    }

    public void render(SpriteBatch batch) {
        if (background != null) {
            batch.draw(background, 0, 0, 800, 600);
        }
    }

    public void dispose() {
        background.dispose();
    }
}
