package Epi.BarCassonne.game.Vague;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Entities.Mechants.PaysanGoblin;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Vague.
 */
public class VagueTest {

    private Vague vague;

    @BeforeEach
    public void setUp() {
        vague = new Vague(1);
    }

    @Test
    public void testCreationVague() {
        assertNotNull(vague, "Vague ne doit pas être null");
        assertEquals(1, vague.getNumero(), "Le numéro de la vague doit être 1");
    }

    @Test
    public void testGetNumero() {
        Vague vague2 = new Vague(5);
        assertEquals(5, vague2.getNumero(), "Le numéro doit être 5");
    }

    @Test
    public void testGetIntervalleSpawn() {
        float intervalle = vague.getIntervalleSpawn();
        assertEquals(3f, intervalle, 0.01f, "L'intervalle de spawn doit être 3 secondes");
    }

    @Test
    public void testGetTempsDepuisDernierSpawn() {
        float temps = vague.getTempsDepuisDernierSpawn();
        assertEquals(0f, temps, 0.01f, "Le temps initial doit être 0");
    }

    @Test
    public void testSetTempsDepuisDernierSpawn() {
        vague.setTempsDepuisDernierSpawn(2.5f);
        assertEquals(2.5f, vague.getTempsDepuisDernierSpawn(), 0.01f, "Le temps doit être 2.5");
    }

    @Test
    public void testGetEnnemisActifs() {
        Array<Mechant> ennemis = vague.getEnnemisActifs();
        assertNotNull(ennemis, "La liste des ennemis actifs ne doit pas être null");
        assertEquals(0, ennemis.size, "La liste doit être vide au début");
    }

    @Test
    public void testAjouterEnnemi() {
        vague.ajouterEnnemi(PaysanGoblin.class, 5);
        
        Mechant ennemi = vague.CreerEnnemi();
        assertNotNull(ennemi, "Un ennemi doit être créé");
        assertTrue(ennemi instanceof PaysanGoblin, "L'ennemi doit être un PaysanGoblin");
    }

    @Test
    public void testTousEnnemisSpawnesVide() {
        assertTrue(vague.tousEnnemisSpawnes(), "Si aucun ennemi n'est ajouté, tous sont spawnés");
    }

    @Test
    public void testTousEnnemisSpawnesAvecEnnemis() {
        vague.ajouterEnnemi(PaysanGoblin.class, 3);
        assertFalse(vague.tousEnnemisSpawnes(), "Tous les ennemis ne sont pas spawnés");
    }

    @Test
    public void testTousEnnemisSpawnesApresSpawn() {
        vague.ajouterEnnemi(PaysanGoblin.class, 2);
        
        vague.CreerEnnemi();
        vague.CreerEnnemi();
        
        assertTrue(vague.tousEnnemisSpawnes(), "Tous les ennemis doivent être spawnés");
    }

    @Test
    public void testEstTermineeSansEnnemis() {
        assertTrue(vague.estTerminee(), "Une vague sans ennemis est terminée");
    }

    @Test
    public void testEstTermineeAvecEnnemisNonSpawnes() {
        vague.ajouterEnnemi(PaysanGoblin.class, 2);
        assertFalse(vague.estTerminee(), "La vague n'est pas terminée si des ennemis ne sont pas spawnés");
    }

    @Test
    public void testEstTermineeAvecEnnemisVivants() {
        vague.ajouterEnnemi(PaysanGoblin.class, 1);
        Mechant ennemi = vague.CreerEnnemi();
        vague.getEnnemisActifs().add(ennemi);
        
        assertFalse(vague.estTerminee(), "La vague n'est pas terminée si des ennemis sont vivants");
    }

    @Test
    public void testCreerEnnemi() {
        vague.ajouterEnnemi(PaysanGoblin.class, 2);
        
        Mechant ennemi1 = vague.CreerEnnemi();
        assertNotNull(ennemi1, "Le premier ennemi doit être créé");
        
        Mechant ennemi2 = vague.CreerEnnemi();
        assertNotNull(ennemi2, "Le deuxième ennemi doit être créé");
        
        Mechant ennemi3 = vague.CreerEnnemi();
        assertNull(ennemi3, "Le troisième ennemi doit être null (tous spawnés)");
    }

    @Test
    public void testCreerEnnemiSansEnnemis() {
        Mechant ennemi = vague.CreerEnnemi();
        assertNull(ennemi, "Aucun ennemi ne doit être créé si aucun n'est configuré");
    }

}

