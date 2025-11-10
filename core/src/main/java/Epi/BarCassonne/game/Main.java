package Epi.BarCassonne.game;

import Epi.BarCassonne.game.Screens.GameScreen;
import com.badlogic.gdx.Game;

public class Main extends Game {

    @Override
    public void create() {
        // Lance ton écran de jeu
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
