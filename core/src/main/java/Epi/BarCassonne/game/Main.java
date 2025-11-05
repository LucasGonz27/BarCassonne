package Epi.BarCassonne.game;

import com.badlogic.gdx.Game;
import Epi.BarCassonne.game.Screens.GameScreen;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
