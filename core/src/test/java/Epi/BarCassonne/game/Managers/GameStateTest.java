package Epi.BarCassonne.game.Managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {
    @BeforeEach
    public void setUp() {
        GameState.resetInstance();
    }

    @Test
    public void testGetInstance() {
        GameState instance1 = GameState.getInstance();
        GameState instance2 = GameState.getInstance();
        assertSame(instance1, instance2, "Le getInstance doit retourner la même instance");

        assertEquals(300, GameState.getInstance().getLingots());
        assertEquals(100, GameState.getInstance().getVie());
        assertEquals(0, GameState.getInstance().getNumeroVague());
    }

    @Test
    public void testAjouterLingots() {
        GameState gameState = GameState.getInstance();
        gameState.ajouterLingots(100);
        assertEquals(400, gameState.getLingots());
    }

    @Test
    public void testRetirerLingots() {
        GameState gameState = GameState.getInstance();
        gameState.setLingots(100);
        boolean resultat = gameState.retirerLingots(100);
        assertTrue(resultat, "retirerLingots doit réussir avec le montant exact");
        assertEquals(0, gameState.getLingots());
    }

    @Test
    public void testRetirerLingotsEchec() {
        GameState gameState = GameState.getInstance();
        gameState.setLingots(50);

        boolean result = gameState.retirerLingots(100);

        assertFalse(result, "retirerLingots doit toujours retourner false si pas assez de lingots");
        assertEquals(50, GameState.getInstance().getLingots(), "Les lingots ne doivent pas changer en cas d'échec");
    }

    @Test
    public void testSetLingotsNegatif() {
        GameState gameState = GameState.getInstance();
        gameState.setLingots(-10);
        assertEquals(0, gameState.getLingots(), "Les lingots ne peuvent pas être négatifs");
    }

    @Test
    public void testRecevoirDegats() {
        GameState gameState = GameState.getInstance();
        gameState.recevoirDegats(50);
        assertEquals(50, gameState.getVie());
    }

    @Test
    public void testResetInstance() {
        GameState instance1 = GameState.getInstance();
        instance1.setLingots(500);
        instance1.setVie(50);
        instance1.setNumeroVague(4);

        GameState.resetInstance();

        GameState instance2 = GameState.getInstance();
        assertNotSame(instance1, instance2, "resetInstance doit créer une nouvelle instance");
        assertEquals(300, GameState.getInstance().getLingots());
        assertEquals(100, GameState.getInstance().getVie());
        assertEquals(0, GameState.getInstance().getNumeroVague());

    }

    @Test
    public void testEstEnVie() {
        GameState gameState = GameState.getInstance();
        gameState.setVie(50);
        assertTrue(gameState.estEnVie());
    }

    @Test
    public void testEstEnVieEchec() {
        GameState gameState = GameState.getInstance();
        gameState.recevoirDegats(100);
        assertFalse(gameState.estEnVie());
    }

    @Test
    public void testSetVieNegatif() {
        GameState gameState = GameState.getInstance();
        gameState.setVie(-10);
        assertEquals(0, gameState.getVie());
    }

    @Test
    public void testSetVieMax() {
        GameState gameState = GameState.getInstance();
        gameState.setVie(150);
        assertEquals(100, gameState.getVie(), "La vie ne peut pas pas dépaser la vie max.");
    }

    @Test
    public void testGetVieMax() {
        GameState gameState = GameState.getInstance();
        assertEquals(100, gameState.getVieMax(), "La vie maximum doit toujours être à 100.");
    }

}
