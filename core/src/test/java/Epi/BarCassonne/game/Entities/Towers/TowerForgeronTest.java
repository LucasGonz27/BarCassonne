package Epi.BarCassonne.game.Entities.Towers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe TowerForgeron.
 */
public class TowerForgeronTest {

    private TowerForgeron towerForgeron;

    @BeforeEach
    public void setUp() {
        towerForgeron = new TowerForgeron();
    }

    @Test
    public void testCreationTowerForgeron() {
        assertNotNull(towerForgeron, "TowerForgeron ne doit pas être null");
        assertEquals(1500, towerForgeron.getPrix(), "Le prix doit être 1500");
        assertEquals(0f, towerForgeron.getPortee(), 0.01f, "La portée doit être 0");
        assertEquals(1, towerForgeron.getLevel(), "Le niveau initial doit être 1");
        assertEquals(4, towerForgeron.getMaxLevel(), "Le niveau maximum doit être 4");
        assertEquals(TypeTour.FORGERON, towerForgeron.typeTour, "Le type doit être FORGERON");
        assertEquals(300, towerForgeron.getApportLingots(), "L'apport en lingots doit être 300");
    }

    @Test
    public void testGetApportLingots() {
        assertEquals(300, towerForgeron.getApportLingots(), "L'apport initial doit être 300");
    }

    @Test
    public void testSetPosition() {
        towerForgeron.setPositionX(300f);
        towerForgeron.setPositionY(400f);
        assertEquals(300f, towerForgeron.getPositionX(), 0.01f, "La position X doit être 300");
        assertEquals(400f, towerForgeron.getPositionY(), 0.01f, "La position Y doit être 400");
    }

    @Test
    public void testUpgrade() {
        int niveauInitial = towerForgeron.getLevel();
        int apportInitial = towerForgeron.getApportLingots();
        
        towerForgeron.upgrade();
        
        assertEquals(niveauInitial + 1, towerForgeron.getLevel(), "Le niveau doit augmenter de 1");
        assertEquals(450, towerForgeron.getApportLingots(), "L'apport doit être 450 au niveau 2");
    }

    @Test
    public void testUpgradeMaxLevel() {
        towerForgeron.setLevel(4);
        int apportAvant = towerForgeron.getApportLingots();
        
        towerForgeron.upgrade();
        
        assertEquals(4, towerForgeron.getLevel(), "Le niveau ne doit pas dépasser le maximum");
        assertEquals(apportAvant, towerForgeron.getApportLingots(), "L'apport ne doit pas changer si niveau max");
    }

    @Test
    public void testUpgradeMultiple() {
        int apportInitial = towerForgeron.getApportLingots();
        
        towerForgeron.upgrade();
        towerForgeron.upgrade();
        
        assertEquals(3, towerForgeron.getLevel(), "Le niveau doit être 3");
        assertEquals(600, towerForgeron.getApportLingots(), "L'apport doit être 600 au niveau 3");
    }
}

