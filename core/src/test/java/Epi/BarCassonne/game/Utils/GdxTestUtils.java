package Epi.BarCassonne.game.Utils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

/**
 * Utilitaires pour initialiser LibGDX dans les tests unitaires.
 */
public class GdxTestUtils {
    
    private static boolean initialized = false;
    
    public static void initializeGdx() {
        if (!initialized) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            new HeadlessApplication(new ApplicationAdapter() {}, config);
            initialized = true;
        }
    }
}

