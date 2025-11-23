package Epi.BarCassonne.game.Entities.Towers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe TowerMagie.
 */
public class TowerMagieTest {

    private TowerMagie towerMagie;

    @BeforeEach
    public void setUp() {
        towerMagie = new TowerMagie();
    }

    @Test
    public void testCreationTowerMagie() {
        assertNotNull(towerMagie, "TowerMagie ne doit pas être null");
        assertEquals(1000, towerMagie.getPrix(), "Le prix doit être 1000");
        assertEquals(150f, towerMagie.getPortee(), 0.01f, "La portée doit être 150");
        assertEquals(1, towerMagie.getLevel(), "Le niveau initial doit être 1");
        assertEquals(4, towerMagie.getMaxLevel(), "Le niveau maximum doit être 4");
        assertEquals(TypeTour.MAGIE, towerMagie.typeTour, "Le type doit être MAGIE");
    }

    @Test
    public void testSetPosition() {
        towerMagie.setPositionX(150f);
        towerMagie.setPositionY(250f);
        assertEquals(150f, towerMagie.getPositionX(), 0.01f, "La position X doit être 150");
        assertEquals(250f, towerMagie.getPositionY(), 0.01f, "La position Y doit être 250");
    }

    @Test
    public void testPeutAttaquerInitial() {
        assertFalse(towerMagie.peutAttaquer(), "La tour ne doit pas pouvoir attaquer immédiatement");
    }

    @Test
    public void testPeutAttaquerApresUpdate() {
        towerMagie.update(2.1f);
        assertTrue(towerMagie.peutAttaquer(), "La tour doit pouvoir attaquer après 2 secondes");
    }

    @Test
    public void testUpgrade() {
        int niveauInitial = towerMagie.getLevel();
        towerMagie.upgrade();
        assertEquals(niveauInitial + 1, towerMagie.getLevel(), "Le niveau doit augmenter de 1");
    }

    @Test
    public void testUpgradeMaxLevel() {
        towerMagie.setLevel(4);
        towerMagie.upgrade();
        assertEquals(4, towerMagie.getLevel(), "Le niveau ne doit pas dépasser le maximum");
    }
}

