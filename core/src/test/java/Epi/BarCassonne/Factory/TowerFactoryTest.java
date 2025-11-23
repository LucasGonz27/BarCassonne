package Epi.BarCassonne.Factory;

import Epi.BarCassonne.game.Entities.Towers.*;
import Epi.BarCassonne.game.Utils.GdxTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TowerFactoryTest {

    @BeforeAll
    public static void setUpClass() {
        GdxTestUtils.initializeGdx();
    }

    @Test
    public void testCreerTowerArcher() {
        Tower tower = TowerFactory.creerTower("TowerArcher");
        assertNotNull(tower, "La tour créée ne doit pas être null");
        assertTrue(tower instanceof TowerArcher, "La tour doit être une TowerArcher");
    }

    @Test
    public void testCreerTowerMagie() {
        Tower tower = TowerFactory.creerTower("TowerMagie");
        assertNotNull(tower, "La tour créée ne doit pas être null");
        assertTrue(tower instanceof TowerMagie, "La tour doit être une TowerMagie");
    }

    @Test
    public void testCreerTowerCanon() {
        Tower tower = TowerFactory.creerTower("TowerCanon");
        assertNotNull(tower, "La tour créée ne doit pas être null");
        assertTrue(tower instanceof TowerCanon, "La tour doit être une TowerCanon");
    }

    @Test
    public void testCreerTowerForgeron() {
        Tower tower = TowerFactory.creerTower("TowerForgeron");
        assertNotNull(tower, "La tour créée ne doit pas être null");
        assertTrue(tower instanceof TowerForgeron, "La tour doit être une TowerForgeron");
    }

    @Test
    public void testCreerTowerTypeInconnu() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> TowerFactory.creerTower("TypeInconnu"),
            "Une exception doit être levée pour un type inconnu"
        );
        assertTrue(exception.getMessage().contains("Type de tours inconnu"), 
            "Le message d'erreur doit indiquer que le type est inconnu");
    }

    @Test
    public void testCreerTowerNull() {
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> TowerFactory.creerTower(null),
            "Une exception doit être levée pour null"
        );
        assertNotNull(exception, "L'exception ne doit pas être null");
    }

    @Test
    public void testCreerTowerVide() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> TowerFactory.creerTower(""),
            "Une exception doit être levée pour une chaîne vide"
        );
        assertTrue(exception.getMessage().contains("Type de tours inconnu"), 
            "Le message d'erreur doit indiquer que le type est inconnu");
    }
}

