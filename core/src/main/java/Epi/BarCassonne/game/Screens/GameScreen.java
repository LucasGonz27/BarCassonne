package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.game.Entities.Mechants.RoiGoblin;
import Epi.BarCassonne.game.Managers.AssetMana;
import Epi.BarCassonne.game.Managers.BackgroundManager;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private BackgroundManager background;
    private OrthographicCamera camera;
    private Viewport viewport;

    private RoiGoblin roiGoblin;
    private float x, y;         // position actuelle
    private float destX, destY; // destination
    private float speed = 200f; // pixels/sec

    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 600;

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new BackgroundManager("backgrounds/map.png");

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);

        // Charger les assets et créer le RoiGoblin
        AssetMana.load();
        roiGoblin = new RoiGoblin();

        x = roiGoblin.getPositionX();
        y = roiGoblin.getPositionY();

        destX = 500;
        destY = 300;
    }

    @Override
    public void render(float delta) {
        // Mettre à jour la position du RoiGoblin
        float dx = destX - x;
        float dy = destY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > speed * delta) {
            x += dx / distance * speed * delta;
            y += dy / distance * speed * delta;
        } else {
            x = destX;
            y = destY;
        }

        roiGoblin.setPositionX(x);
        roiGoblin.setPositionY(y);

        // Rendu
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        background.render(batch);
        batch.draw(AssetMana.getSprite("RoiGoblin"), roiGoblin.getPositionX(), roiGoblin.getPositionY(), roiGoblin.getWidht(), roiGoblin.getHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        AssetMana.dispose();
    }
}
