package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Mechant.
 * Utilise une sous-classe de test pour éviter les dépendances d'animation.
 */
public class MechantTest {

    // Sous-classe de test pour Mechant (sans animation)
    private static class TestMechant extends Mechant {
        public TestMechant(int PV, float Vitesse) {
            super(PV, Vitesse, null); // Pas d'animation pour les tests
        }
        
        // Méthode pour tester setResistance (protected)
        public void testerSetResistance(TypeTour typeTour, float resistance) {
            setResistance(typeTour, resistance);
        }
    }

    private Mechant mechant;

    @BeforeEach
    public void setUp() {
        mechant = new TestMechant(100, 50f);
    }

    @Test
    public void testCreationMechant() {
        assertNotNull(mechant, "Mechant ne doit pas être null");
        assertEquals(100, mechant.getPV(), "Les PV doivent être 100");
        assertEquals(0f, mechant.getPositionX(), 0.01f, "La position X initiale doit être 0");
        assertEquals(0f, mechant.getPositionY(), 0.01f, "La position Y initiale doit être 0");
    }

    @Test
    public void testIsEnVie() {
        assertTrue(mechant.isEnVie(), "Le méchant doit être en vie avec 100 PV");
        
        mechant.recevoirDegats(50);
        assertTrue(mechant.isEnVie(), "Le méchant doit être en vie avec 50 PV");
        
        mechant.recevoirDegats(50);
        assertFalse(mechant.isEnVie(), "Le méchant ne doit plus être en vie avec 0 PV");
    }

    @Test
    public void testSetPosition() {
        mechant.setPositionX(150f);
        mechant.setPositionY(200f);
        assertEquals(150f, mechant.getPositionX(), 0.01f, "La position X doit être 150");
        assertEquals(200f, mechant.getPositionY(), 0.01f, "La position Y doit être 200");
    }

    @Test
    public void testRecevoirDegats() {
        int pvInitial = mechant.getPV();
        mechant.recevoirDegats(30);
        assertEquals(pvInitial - 30, mechant.getPV(), "Les PV doivent diminuer de 30");
    }

    @Test
    public void testRecevoirDegatsAvecResistance() {
        // Tester avec résistance par défaut (0%)
        int pvInitial = mechant.getPV();
        mechant.recevoirDegats(50, TypeTour.ARCHER);
        assertEquals(pvInitial - 50, mechant.getPV(), "Les PV doivent diminuer de 50 sans résistance");
    }

    @Test
    public void testRecevoirDegatsMort() {
        mechant.recevoirDegats(100);
        assertFalse(mechant.isEnVie(), "Le méchant doit être mort");
        
        // Tester que recevoir des dégâts quand mort ne change rien
        int pvAvant = mechant.getPV();
        mechant.recevoirDegats(50);
        assertEquals(pvAvant, mechant.getPV(), "Les PV ne doivent pas changer si déjà mort");
    }

    @Test
    public void testRecevoirDegatsNegatif() {
        int pvInitial = mechant.getPV();
        mechant.recevoirDegats(-10); // Dégâts négatifs
        assertEquals(pvInitial + 10, mechant.getPV(), "Les PV doivent augmenter avec des dégâts négatifs");
    }

    @Test
    public void testMourir() {
        mechant.mourir();
        assertFalse(mechant.isEnVie(), "Le méchant doit être mort après mourir()");
        assertEquals(0, mechant.getPV(), "Les PV doivent être 0");
    }

    @Test
    public void testSetChemin() {
        List<Vector2> chemin = new ArrayList<>();
        chemin.add(new Vector2(0f, 0f));
        chemin.add(new Vector2(100f, 100f));
        chemin.add(new Vector2(200f, 200f));
        
        mechant.setChemin(chemin);
        assertNotNull(mechant, "Le chemin doit être défini");
    }

    @Test
    public void testAAtteintFinChemin() {
        // Sans chemin
        assertFalse(mechant.aAtteintFinChemin(), "Ne doit pas avoir atteint la fin sans chemin");
        
        // Avec chemin vide
        mechant.setChemin(new ArrayList<>());
        assertFalse(mechant.aAtteintFinChemin(), "Ne doit pas avoir atteint la fin avec chemin vide");
        
        // Avec chemin et index au-delà
        List<Vector2> chemin = new ArrayList<>();
        chemin.add(new Vector2(0f, 0f));
        mechant.setChemin(chemin);
        mechant.setIndexActuel(1); // Au-delà du chemin
        assertTrue(mechant.aAtteintFinChemin(), "Doit avoir atteint la fin avec index au-delà");
    }

    @Test
    public void testGetDegatsFinChemin() {
        // Tester avec la sous-classe TestMechant (retourne 1 par défaut)
        int degats = mechant.getDegatsFinChemin();
        assertEquals(1, degats, "Les dégâts de fin de chemin doivent être 1 par défaut");
    }

    @Test
    public void testGetResistance() {
        // Résistance par défaut doit être 0%
        assertEquals(0.0f, mechant.getResistance(TypeTour.ARCHER), 0.01f, "La résistance par défaut doit être 0%");
        assertEquals(0.0f, mechant.getResistance(TypeTour.MAGIE), 0.01f, "La résistance par défaut doit être 0%");
        assertEquals(0.0f, mechant.getResistance(TypeTour.CANON), 0.01f, "La résistance par défaut doit être 0%");
    }

    @Test
    public void testUpdateSansChemin() {
        // Tester que update ne lance pas d'exception sans chemin
        assertDoesNotThrow(() -> {
            mechant.update(0.1f);
        }, "update() ne doit pas lancer d'exception sans chemin");
    }

    @Test
    public void testUpdateMort() {
        mechant.mourir();
        // Tester que update ne fait rien si mort
        assertDoesNotThrow(() -> {
            mechant.update(0.1f);
        }, "update() ne doit pas lancer d'exception si mort");
    }

    @Test
    public void testSetIndexActuel() {
        List<Vector2> chemin = new ArrayList<>();
        chemin.add(new Vector2(0f, 0f));
        mechant.setChemin(chemin);
        
        mechant.setIndexActuel(1); // Au-delà du chemin (taille 1, index 0)
        assertTrue(mechant.aAtteintFinChemin(), "Doit avoir atteint la fin avec index 1 sur un chemin de taille 1");
    }

    @Test
    public void testSetCheminReinitialiseIndex() {
        List<Vector2> chemin1 = new ArrayList<>();
        chemin1.add(new Vector2(0f, 0f));
        mechant.setChemin(chemin1);
        mechant.setIndexActuel(1);
        
        List<Vector2> chemin2 = new ArrayList<>();
        chemin2.add(new Vector2(0f, 0f));
        chemin2.add(new Vector2(100f, 100f));
        mechant.setChemin(chemin2);
        
        assertFalse(mechant.aAtteintFinChemin(), "L'index doit être réinitialisé à 0");
    }

    @Test
    public void testRecevoirDegatsAvecResistancePositive() {
        // Tester avec résistance positive (50% de résistance = 50% de dégâts)
        TestMechant testMechant = (TestMechant) mechant;
        testMechant.testerSetResistance(TypeTour.ARCHER, 0.5f);
        
        int pvInitial = mechant.getPV();
        mechant.recevoirDegats(100, TypeTour.ARCHER);
        // Avec 50% de résistance, 100 dégâts deviennent 50 dégâts
        assertEquals(pvInitial - 50, mechant.getPV(), "Les PV doivent diminuer de 50 avec 50% de résistance");
    }

    @Test
    public void testRecevoirDegatsAvecResistanceNegative() {
        // Tester avec résistance négative (vulnérabilité) : -0.25 = +25% de dégâts
        TestMechant testMechant = (TestMechant) mechant;
        testMechant.testerSetResistance(TypeTour.MAGIE, -0.25f);
        
        int pvInitial = mechant.getPV();
        mechant.recevoirDegats(100, TypeTour.MAGIE);
        // Avec -25% de résistance (vulnérabilité), 100 dégâts deviennent 125 dégâts
        assertEquals(pvInitial - 125, mechant.getPV(), "Les PV doivent diminuer de 125 avec -25% de résistance");
    }

    @Test
    public void testRecevoirDegatsAvecResistanceMax() {
        // Tester avec résistance maximale (100% = immunité totale)
        TestMechant testMechant = (TestMechant) mechant;
        testMechant.testerSetResistance(TypeTour.CANON, 1.0f);
        
        int pvInitial = mechant.getPV();
        mechant.recevoirDegats(100, TypeTour.CANON);
        // Avec 100% de résistance, 0 dégâts
        assertEquals(pvInitial, mechant.getPV(), "Les PV ne doivent pas changer avec 100% de résistance");
    }

    @Test
    public void testSetResistanceLimites() {
        TestMechant testMechant = (TestMechant) mechant;
        
        // Tester limite inférieure (-1.0)
        testMechant.testerSetResistance(TypeTour.ARCHER, -2.0f);
        assertEquals(-1.0f, mechant.getResistance(TypeTour.ARCHER), 0.01f, "La résistance doit être limitée à -1.0");
        
        // Tester limite supérieure (1.0)
        testMechant.testerSetResistance(TypeTour.MAGIE, 2.0f);
        assertEquals(1.0f, mechant.getResistance(TypeTour.MAGIE), 0.01f, "La résistance doit être limitée à 1.0");
    }

    @Test
    public void testMoveSansChemin() {
        float posXAvant = mechant.getPositionX();
        float posYAvant = mechant.getPositionY();
        
        mechant.move(0.1f);
        
        assertEquals(posXAvant, mechant.getPositionX(), 0.01f, "La position X ne doit pas changer sans chemin");
        assertEquals(posYAvant, mechant.getPositionY(), 0.01f, "La position Y ne doit pas changer sans chemin");
    }

    @Test
    public void testGetMessageFlottant() {
        assertNotNull(mechant.getMessageFlottant(), "getMessageFlottant() ne doit pas retourner null");
    }

    @Test
    public void testRecevoirDegatsAvecResistanceZero() {
        // Tester explicitement avec résistance 0%
        TestMechant testMechant = (TestMechant) mechant;
        testMechant.testerSetResistance(TypeTour.ARCHER, 0.0f);
        
        int pvInitial = mechant.getPV();
        mechant.recevoirDegats(50, TypeTour.ARCHER);
        assertEquals(pvInitial - 50, mechant.getPV(), "Les PV doivent diminuer de 50 avec 0% de résistance");
    }

    @Test
    public void testRecevoirDegatsAvecResistanceMort() {
        TestMechant testMechant = (TestMechant) mechant;
        testMechant.testerSetResistance(TypeTour.ARCHER, 0.5f);
        
        mechant.recevoirDegats(200, TypeTour.ARCHER); // 100 dégâts réels avec 50% résistance
        assertFalse(mechant.isEnVie(), "Le méchant doit être mort");
        assertEquals(0, mechant.getPV(), "Les PV doivent être 0");
    }
}

