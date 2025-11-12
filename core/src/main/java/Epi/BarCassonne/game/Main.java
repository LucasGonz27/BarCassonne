package Epi.BarCassonne.game;

import Epi.BarCassonne.game.Screens.GameScreen;
import Epi.BarCassonne.game.Screens.Menu;
import com.badlogic.gdx.Game;

public class Main extends Game {

    @Override
    public void create() {
        // Lance ton écran de jeu
        setScreen(new Menu(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
