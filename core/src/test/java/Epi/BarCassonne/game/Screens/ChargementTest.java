package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Game;
import Epi.BarCassonne.game.Utils.GdxTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChargementTest {

    private Game game;
    private Chargement chargement;

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
        chargement = new Chargement(game);
    }

    @Test
    public void testCreationChargement() {
        assertNotNull(chargement, "Chargement ne doit pas être null");
    }

    @Test
    public void testCreationChargementAvecGameNull() {
        Chargement chargementNull = new Chargement(null);
        assertNotNull(chargementNull, "Chargement peut être créé avec game null");
    }

    @Test
    public void testPauseResumeSansShow() {
        assertDoesNotThrow(() -> {
            chargement.pause();
        }, "pause() ne doit pas lever d'exception");
        
        assertDoesNotThrow(() -> {
            chargement.resume();
        }, "resume() ne doit pas lever d'exception");
    }

    @Test
    public void testHideSansShow() {
        assertDoesNotThrow(() -> {
            chargement.hide();
        }, "hide() ne doit pas lever d'exception");
    }

    @Test
    public void testDisposeSansShow() {
        assertDoesNotThrow(() -> {
            chargement.dispose();
        }, "dispose() ne doit pas lever d'exception");
    }

    @Test
    public void testDisposeMultiple() {
        assertDoesNotThrow(() -> {
            chargement.dispose();
            chargement.dispose();
        }, "dispose() peut être appelé plusieurs fois");
    }
}

