package Epi.BarCassonne.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import Epi.BarCassonne.game.Entities.Mechants.RoiGoblin;
import Epi.BarCassonne.game.Managers.AssetMana;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private RoiGoblin roiGoblin;

    private float x, y;         // position actuelle
    private float destX, destY; // destination
    private float speed = 200f; // pixels/sec

    @Override
    public void create() {

        batch = new SpriteBatch();
        AssetMana.load();
        roiGoblin = new RoiGoblin();

        x = roiGoblin.getPositionX();
        y = roiGoblin.getPositionY();

        destX = 500;
        destY = 300;
    }

    @Override
    public void render() {

        // Calcul du déplacement vers la destination
        float delta = Gdx.graphics.getDeltaTime();
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

        x = roiGoblin.getPositionX();
        y = roiGoblin.getPositionY();

        ScreenUtils.clear(0, 0, 0, 1);


        batch.begin();
        batch.draw(AssetMana.getSprite("RoiGoblin"), roiGoblin.getPositionX(), roiGoblin.getPositionY());
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        AssetMana.dispose();
    }
}
