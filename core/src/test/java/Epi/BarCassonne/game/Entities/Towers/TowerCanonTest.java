package Epi.BarCassonne.game.Entities.Towers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe TowerCanon.
 */
public class TowerCanonTest {

    private TowerCanon towerCanon;

    @BeforeEach
    public void setUp() {
        towerCanon = new TowerCanon();
    }

    @Test
    public void testCreationTowerCanon() {
        assertNotNull(towerCanon, "TowerCanon ne doit pas être null");
        assertEquals(600, towerCanon.getPrix(), "Le prix doit être 600");
        assertEquals(110f, towerCanon.getPortee(), 0.01f, "La portée doit être 110");
        assertEquals(1, towerCanon.getLevel(), "Le niveau initial doit être 1");
        assertEquals(4, towerCanon.getMaxLevel(), "Le niveau maximum doit être 4");
        assertEquals(TypeTour.CANON, towerCanon.typeTour, "Le type doit être CANON");
    }

    @Test
    public void testSetPosition() {
        towerCanon.setPositionX(200f);
        towerCanon.setPositionY(300f);
        assertEquals(200f, towerCanon.getPositionX(), 0.01f, "La position X doit être 200");
        assertEquals(300f, towerCanon.getPositionY(), 0.01f, "La position Y doit être 300");
    }

    @Test
    public void testPeutAttaquerInitial() {
        assertFalse(towerCanon.peutAttaquer(), "La tour ne doit pas pouvoir attaquer immédiatement");
    }

    @Test
    public void testPeutAttaquerApresUpdate() {
        towerCanon.update(2.1f);
        assertTrue(towerCanon.peutAttaquer(), "La tour doit pouvoir attaquer après 2 secondes");
    }

    @Test
    public void testUpgrade() {
        int niveauInitial = towerCanon.getLevel();
        towerCanon.upgrade();
        assertEquals(niveauInitial + 1, towerCanon.getLevel(), "Le niveau doit augmenter de 1");
    }

    @Test
    public void testUpgradeMaxLevel() {
        towerCanon.setLevel(4);
        towerCanon.upgrade();
        assertEquals(4, towerCanon.getLevel(), "Le niveau ne doit pas dépasser le maximum");
    }
}

