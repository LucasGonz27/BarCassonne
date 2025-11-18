package Epi.BarCassonne.game;

import Epi.BarCassonne.game.Screens.GameScreen;
import Epi.BarCassonne.game.Screens.Menu;
import com.badlogic.gdx.Game;

/**
 * Classe principale du jeu BarCassonne.
 * Point d'entrée de l'application LibGDX.
 */
public class Main extends Game {

    /**
     * Initialise le jeu et affiche l'écran de menu.
     */
    @Override
    public void create() {
        // Lance ton écran de jeu
        setScreen(new Menu(this));
    }

    /**
     * Libère les ressources lors de la fermeture du jeu.
     */
    @Override
    public void dispose() {
        super.dispose();
    }
}
