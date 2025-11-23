package Epi.BarCassonne.game.Managers;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Vague.Vague;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe VagueMana.
 * Teste la gestion des vagues d'ennemis (sans tester le spawn qui nécessite les animations).
 */
public class VagueManaTest {

    private CheminMana cheminMana;
    private GameState gameState;
    private VagueMana vagueMana;

    @BeforeEach
    public void setUp() {
        GameState.resetInstance();
        
        float mapWidth = 1000f;
        float mapHeight = 800f;
        cheminMana = new CheminMana(mapWidth, mapHeight);
        gameState = GameState.getInstance();
        
        vagueMana = new VagueMana(cheminMana, gameState);
    }

    @Test
    public void testCreationVagueMana() {
        assertNotNull(vagueMana, "VagueMana ne doit pas être null");
        assertNotNull(vagueMana.getVagueActuelle(), "La vague actuelle ne doit pas être null au début");
        assertNotNull(vagueMana.getEnnemisActifs(), "La liste des ennemis actifs ne doit pas être null");
    }

    @Test
    public void testGetVagueActuelle() {
        Vague vagueActuelle = vagueMana.getVagueActuelle();
        assertNotNull(vagueActuelle, "La vague actuelle ne doit pas être null");
        assertEquals(1, vagueActuelle.getNumero(), "La première vague doit être la vague 1");
    }

    @Test
    public void testGetEnnemisActifsInitial() {
        Array<Mechant> ennemisActifs = vagueMana.getEnnemisActifs();
        assertNotNull(ennemisActifs, "La liste des ennemis actifs ne doit pas être null");
        assertEquals(0, ennemisActifs.size, "La liste des ennemis actifs doit être vide au début");
    }

    @Test
    public void testMettreAJourCheminEnnemisSansEnnemis() {
        assertDoesNotThrow(() -> {
            vagueMana.mettreAJourCheminEnnemis();
        }, "mettreAJourCheminEnnemis() ne doit pas lancer d'exception avec une liste vide");
    }

    @Test
    public void testMettreAJourCheminEnnemisAvecNouveauChemin() {
        float nouvelleMapWidth = 1920f;
        float nouvelleMapHeight = 1080f;
        cheminMana.mettreAJourChemin(nouvelleMapWidth, nouvelleMapHeight);
        
        assertDoesNotThrow(() -> {
            vagueMana.mettreAJourCheminEnnemis();
        }, "mettreAJourCheminEnnemis() ne doit pas lancer d'exception");
    }

    @Test
    public void testVagueActuelleApresCreation() {
        Vague vague = vagueMana.getVagueActuelle();
        assertNotNull(vague, "La vague actuelle doit exister");
        assertEquals(1, vague.getNumero(), "La vague actuelle doit être la vague 1 au début");
    }

    @Test
    public void testGetEnnemisActifsEstVide() {
        Array<Mechant> ennemis = vagueMana.getEnnemisActifs();
        assertTrue(ennemis.isEmpty(), "La liste des ennemis actifs doit être vide au début");
        assertEquals(0, ennemis.size, "La taille de la liste doit être 0");
    }

    @Test
    public void testVagueActuelleNonNull() {
        Vague vague = vagueMana.getVagueActuelle();
        assertNotNull(vague, "La vague actuelle ne doit pas être null après création");
    }

    @Test
    public void testMettreAJourCheminEnnemisAvecCheminNull() {
        CheminMana cheminVide = new CheminMana(100f, 100f);
        assertDoesNotThrow(() -> {
            vagueMana.mettreAJourCheminEnnemis();
        }, "mettreAJourCheminEnnemis() doit gérer les cas limites");
    }
}

