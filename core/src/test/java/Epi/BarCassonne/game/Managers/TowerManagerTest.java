package Epi.BarCassonne.game.Managers;

import Epi.BarCassonne.game.Utils.CollisionValid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe TowerManager.
 * Teste la gestion des tours sans dépendances complexes (rendu, Viewport, etc.).
 */
public class TowerManagerTest {

    private CollisionValid collisionValid;
    private VagueMana vagueMana;
    private GameState gameState;
    private TowerManager towerManager;

    @BeforeEach
    public void setUp() {
        // Réinitialiser GameState pour chaque test
        GameState.resetInstance();
        
        // Créer les dépendances nécessaires
        float mapWidth = 1000f;
        float mapHeight = 800f;
        collisionValid = new CollisionValid(mapWidth, mapHeight);
        CheminMana cheminMana = new CheminMana(mapWidth, mapHeight);
        vagueMana = new VagueMana(cheminMana, GameState.getInstance());
        gameState = GameState.getInstance();
        
        // Créer TowerManager
        towerManager = new TowerManager(collisionValid, vagueMana, gameState);
    }

    @Test
    public void testCreationTowerManager() {
        assertNotNull(towerManager, "TowerManager ne doit pas être null");
        assertNotNull(towerManager.getTours(), "La liste des tours ne doit pas être null");
        assertEquals(0, towerManager.getTours().size(), "La liste des tours doit être vide au début");
    }

    @Test
    public void testGetToursInitial() {
        assertNotNull(towerManager.getTours(), "getTours() ne doit pas retourner null");
        assertTrue(towerManager.getTours().isEmpty(), "La liste des tours doit être vide au début");
        assertEquals(0, towerManager.getTours().size(), "La taille de la liste doit être 0");
    }

    @Test
    public void testEstEnModePlacementInitial() {
        assertFalse(towerManager.estEnModePlacement(), "Le mode placement doit être désactivé au début");
    }

    @Test
    public void testActiverModePlacementSlot1() {
        towerManager.activerModePlacement(1);
        assertTrue(towerManager.estEnModePlacement(), "Le mode placement doit être activé avec le slot 1");
    }

    @Test
    public void testActiverModePlacementSlot2() {
        towerManager.activerModePlacement(2);
        assertTrue(towerManager.estEnModePlacement(), "Le mode placement doit être activé avec le slot 2");
    }

    @Test
    public void testActiverModePlacementSlot3() {
        towerManager.activerModePlacement(3);
        assertTrue(towerManager.estEnModePlacement(), "Le mode placement doit être activé avec le slot 3");
    }

    @Test
    public void testActiverModePlacementSlotInvalide() {
        // Tester avec un slot invalide (0, 4, -1, etc.)
        towerManager.activerModePlacement(0);
        assertFalse(towerManager.estEnModePlacement(), "Le mode placement ne doit pas être activé avec un slot invalide");
        
        towerManager.activerModePlacement(4);
        assertFalse(towerManager.estEnModePlacement(), "Le mode placement ne doit pas être activé avec un slot invalide");
        
        towerManager.activerModePlacement(-1);
        assertFalse(towerManager.estEnModePlacement(), "Le mode placement ne doit pas être activé avec un slot invalide");
    }

    @Test
    public void testAnnulerModePlacement() {
        // Activer le mode placement
        towerManager.activerModePlacement(1);
        assertTrue(towerManager.estEnModePlacement(), "Le mode placement doit être activé");
        
        // Annuler le mode placement
        towerManager.annulerModePlacement();
        assertFalse(towerManager.estEnModePlacement(), "Le mode placement doit être désactivé après annulation");
    }

    @Test
    public void testAnnulerModePlacementSansActivation() {
        // Annuler sans avoir activé le mode placement
        assertDoesNotThrow(() -> {
            towerManager.annulerModePlacement();
        }, "annulerModePlacement() ne doit pas lancer d'exception même si le mode n'est pas activé");
        
        assertFalse(towerManager.estEnModePlacement(), "Le mode placement doit rester désactivé");
    }

    @Test
    public void testActiverEtAnnulerModePlacement() {
        // Activer avec slot 1
        towerManager.activerModePlacement(1);
        assertTrue(towerManager.estEnModePlacement());
        
        // Annuler
        towerManager.annulerModePlacement();
        assertFalse(towerManager.estEnModePlacement());
        
        // Réactiver avec slot 2
        towerManager.activerModePlacement(2);
        assertTrue(towerManager.estEnModePlacement());
        
        // Annuler à nouveau
        towerManager.annulerModePlacement();
        assertFalse(towerManager.estEnModePlacement());
    }

    @Test
    public void testGetToursApresCreation() {
        // La liste doit rester vide si aucune tour n'est placée
        assertEquals(0, towerManager.getTours().size(), "La liste doit rester vide");
        assertTrue(towerManager.getTours().isEmpty(), "La liste doit être vide");
    }
}

