package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Game;
import Epi.BarCassonne.game.Utils.GdxTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameOverTest {

    private Game game;
    private GameOver gameOver;

    @BeforeAll
    public static void setUpClass() {
        GdxTestUtils.initializeGdx();
    }

    @BeforeEach
    public void setUp() {
        game = new Game() {
            @Override
            public void create() {}
        };
        gameOver = new GameOver(game);
    }

    @Test
    public void testCreationGameOver() {
        assertNotNull(gameOver, "GameOver ne doit pas être null");
    }

    @Test
    public void testCreationGameOverAvecGameNull() {
        GameOver gameOverNull = new GameOver(null);
        assertNotNull(gameOverNull, "GameOver peut être créé avec game null");
    }

    @Test
    public void testPauseResumeSansShow() {
        assertDoesNotThrow(() -> {
            gameOver.pause();
        }, "pause() ne doit pas lever d'exception");
        
        assertDoesNotThrow(() -> {
            gameOver.resume();
        }, "resume() ne doit pas lever d'exception");
    }

    @Test
    public void testHideSansShow() {
        assertDoesNotThrow(() -> {
            gameOver.hide();
        }, "hide() ne doit pas lever d'exception");
    }

    @Test
    public void testDisposeSansShow() {
        assertDoesNotThrow(() -> {
            gameOver.dispose();
        }, "dispose() ne doit pas lever d'exception");
    }

    @Test
    public void testDisposeMultiple() {
        assertDoesNotThrow(() -> {
            gameOver.dispose();
            gameOver.dispose();
        }, "dispose() peut être appelé plusieurs fois");
    }
}

