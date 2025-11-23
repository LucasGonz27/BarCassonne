package Epi.BarCassonne.game.Entities.Towers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe TowerArcher.
 */
public class TowerArcherTest {

    private TowerArcher towerArcher;

    @BeforeEach
    public void setUp() {
        towerArcher = new TowerArcher();
    }

    @Test
    public void testCreationTowerArcher() {
        assertNotNull(towerArcher, "TowerArcher ne doit pas être null");
        assertEquals(100, towerArcher.getPrix(), "Le prix doit être 100");
        assertEquals(200f, towerArcher.getPortee(), 0.01f, "La portée doit être 200");
        assertEquals(1, towerArcher.getLevel(), "Le niveau initial doit être 1");
        assertEquals(4, towerArcher.getMaxLevel(), "Le niveau maximum doit être 4");
        assertEquals(TypeTour.ARCHER, towerArcher.typeTour, "Le type doit être ARCHER");
    }

    @Test
    public void testSetPosition() {
        towerArcher.setPositionX(100f);
        towerArcher.setPositionY(200f);
        assertEquals(100f, towerArcher.getPositionX(), 0.01f, "La position X doit être 100");
        assertEquals(200f, towerArcher.getPositionY(), 0.01f, "La position Y doit être 200");
    }

    @Test
    public void testSetLevel() {
        towerArcher.setLevel(2);
        assertEquals(2, towerArcher.getLevel(), "Le niveau doit être 2");
    }

    @Test
    public void testSetLevelMax() {
        towerArcher.setLevel(4);
        assertEquals(4, towerArcher.getLevel(), "Le niveau peut être au maximum 4");
    }

    @Test
    public void testSetPrix() {
        towerArcher.setPrix(150);
        assertEquals(150, towerArcher.getPrix(), "Le prix doit être 150");
    }

    @Test
    public void testSetPortee() {
        towerArcher.setPortee(250f);
        assertEquals(250f, towerArcher.getPortee(), 0.01f, "La portée doit être 250");
    }

    @Test
    public void testPeutAttaquerInitial() {
        assertFalse(towerArcher.peutAttaquer(), "La tour ne doit pas pouvoir attaquer immédiatement");
    }

    @Test
    public void testPeutAttaquerApresUpdate() {
        towerArcher.update(2.1f);
        assertTrue(towerArcher.peutAttaquer(), "La tour doit pouvoir attaquer après 2 secondes");
    }

    @Test
    public void testPeutAttaquerAvantIntervalle() {
        towerArcher.update(1.5f);
        assertFalse(towerArcher.peutAttaquer(), "La tour ne doit pas pouvoir attaquer avant 2 secondes");
    }

    @Test
    public void testUpdate() {
        towerArcher.update(1.0f);
        assertFalse(towerArcher.peutAttaquer(), "Après 1 seconde, la tour ne peut pas encore attaquer");
        
        towerArcher.update(1.5f);
        assertTrue(towerArcher.peutAttaquer(), "Après 2.5 secondes, la tour peut attaquer");
    }

    @Test
    public void testUpgrade() {
        int niveauInitial = towerArcher.getLevel();
        towerArcher.upgrade();
        assertEquals(niveauInitial + 1, towerArcher.getLevel(), "Le niveau doit augmenter de 1");
    }

    @Test
    public void testUpgradeMaxLevel() {
        towerArcher.setLevel(4);
        towerArcher.upgrade();
        assertEquals(4, towerArcher.getLevel(), "Le niveau ne doit pas dépasser le maximum");
    }
}

