package Epi.BarCassonne.game.Managers;

import Epi.BarCassonne.game.Utils.GdxTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe TowerDataManager.
 * Teste la récupération des données des tours (prix, portée, textures).
 */
public class TowerDataManagerTest {

    private TowerDataManager towerDataManager;

    @BeforeAll
    public static void setUpClass() {
        GdxTestUtils.initializeGdx();
    }

    @BeforeEach
    public void setUp() {
        towerDataManager = new TowerDataManager();
    }

    @Test
    public void testGetPrixTowerArcher() {
        int prix = towerDataManager.getPrix("TowerArcher");
        assertEquals(100, prix, "Le prix de TowerArcher doit être 100");
    }

    @Test
    public void testGetPrixTowerMagie() {
        int prix = towerDataManager.getPrix("TowerMagie");
        assertEquals(1000, prix, "Le prix de TowerMagie doit être 1000");
    }

    @Test
    public void testGetPrixTowerForgeron() {
        int prix = towerDataManager.getPrix("TowerForgeron");
        assertEquals(1500, prix, "Le prix de TowerForgeron doit être 1500");
    }

    @Test
    public void testGetPrixTypeInconnu() {
        int prix = towerDataManager.getPrix("TypeInconnu");
        assertEquals(-1, prix, "Le prix d'un type inconnu doit être -1");
    }

    @Test
    public void testGetPrixNull() {
        int prix = towerDataManager.getPrix(null);
        assertEquals(-1, prix, "Le prix d'un type null doit être -1");
    }

    @Test
    public void testGetPorteeTowerArcher() {
        float portee = towerDataManager.getPortee("TowerArcher");
        assertEquals(200f, portee, 0.01f, "La portée de TowerArcher doit être 200");
    }

    @Test
    public void testGetPorteeTowerMagie() {
        float portee = towerDataManager.getPortee("TowerMagie");
        assertEquals(150f, portee, 0.01f, "La portée de TowerMagie doit être 150");
    }

    @Test
    public void testGetPorteeTowerForgeron() {
        float portee = towerDataManager.getPortee("TowerForgeron");
        assertEquals(0f, portee, 0.01f, "La portée de TowerForgeron doit être 0");
    }

    @Test
    public void testGetPorteeTypeInconnu() {
        float portee = towerDataManager.getPortee("TypeInconnu");
        assertEquals(-1f, portee, 0.01f, "La portée d'un type inconnu doit être -1");
    }

    @Test
    public void testGetPorteeNull() {
        float portee = towerDataManager.getPortee(null);
        assertEquals(-1f, portee, 0.01f, "La portée d'un type null doit être -1");
    }
}

