package Epi.BarCassonne.game.Entities.Projectiles;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Projectile (via Fleche).
 */
public class ProjectileTest {

    private Fleche fleche;
    private Mechant mechantTest;

    private static class TestMechant extends Mechant {
        public TestMechant(int PV, float Vitesse) {
            super(PV, Vitesse, null);
        }
    }

    @BeforeEach
    public void setUp() {
        fleche = new Fleche();
        mechantTest = new TestMechant(100, 50f);
    }

    @Test
    public void testCreationFleche() {
        assertNotNull(fleche, "Fleche ne doit pas être null");
    }

    @Test
    public void testGetPositionX() {
        fleche.setPositionX(100f);
        assertEquals(100f, fleche.getPositionX(), 0.01f, "La position X doit être 100");
    }

    @Test
    public void testGetPositionY() {
        fleche.setPositionY(200f);
        assertEquals(200f, fleche.getPositionY(), 0.01f, "La position Y doit être 200");
    }

    @Test
    public void testSetPositionX() {
        fleche.setPositionX(150f);
        assertEquals(150f, fleche.getPositionX(), 0.01f, "setPositionX doit fonctionner");
    }

    @Test
    public void testSetPositionY() {
        fleche.setPositionY(250f);
        assertEquals(250f, fleche.getPositionY(), 0.01f, "setPositionY doit fonctionner");
    }

    @Test
    public void testGetDegats() {
        assertEquals(10, fleche.getDegats(), "Les dégâts de la flèche doivent être 10");
    }

    @Test
    public void testGetVitesse() {
        assertEquals(400f, fleche.getVitesse(), 0.01f, "La vitesse de la flèche doit être 400");
    }

    @Test
    public void testGetTailleRendu() {
        float taille = fleche.getTailleRendu();
        assertTrue(taille > 0, "La taille de rendu doit être positive");
    }

    @Test
    public void testSetCible() {
        fleche.setCible(mechantTest);
        assertFalse(fleche.doitEtreSupprime(), "Le projectile ne doit pas être supprimé avec une cible vivante");
    }

    @Test
    public void testSetCibleNull() {
        fleche.setCible(null);
        assertTrue(fleche.doitEtreSupprime(), "Le projectile doit être supprimé sans cible");
    }

    @Test
    public void testSetTypeTour() {
        fleche.setTypeTour(TypeTour.ARCHER);
        assertEquals(TypeTour.ARCHER, fleche.getTypeTour(), "Le type de tour doit être ARCHER");
    }

    @Test
    public void testSetNiveauTour() {
        fleche.setNiveauTour(2);
        assertEquals(2, fleche.getNiveauTour(), "Le niveau de la tour doit être 2");
    }

    @Test
    public void testGetTowerTypeNameArcher() {
        fleche.setTypeTour(TypeTour.ARCHER);
        assertEquals("TowerArcher", fleche.getTowerTypeName(), "Le nom doit être TowerArcher");
    }

    @Test
    public void testGetTowerTypeNameMagie() {
        fleche.setTypeTour(TypeTour.MAGIE);
        assertEquals("TowerMagie", fleche.getTowerTypeName(), "Le nom doit être TowerMagie");
    }

    @Test
    public void testGetTowerTypeNameCanon() {
        fleche.setTypeTour(TypeTour.CANON);
        assertEquals("TowerCanon", fleche.getTowerTypeName(), "Le nom doit être TowerCanon");
    }

    @Test
    public void testGetTowerTypeNameNull() {
        fleche.setTypeTour(null);
        assertNull(fleche.getTowerTypeName(), "Le nom doit être null si le type est null");
    }

    @Test
    public void testGetTowerTypeNameForgeron() {
        fleche.setTypeTour(TypeTour.FORGERON);
        assertNull(fleche.getTowerTypeName(), "Le nom doit être null pour FORGERON");
    }

    @Test
    public void testDoitEtreSupprimeSansCible() {
        fleche.setCible(null);
        assertTrue(fleche.doitEtreSupprime(), "Le projectile doit être supprimé sans cible");
    }

    @Test
    public void testDoitEtreSupprimeCibleMorte() {
        fleche.setCible(mechantTest);
        mechantTest.recevoirDegats(100);
        assertTrue(fleche.doitEtreSupprime(), "Le projectile doit être supprimé si la cible est morte");
    }

    @Test
    public void testDoitEtreSupprimeCibleVivante() {
        fleche.setCible(mechantTest);
        assertFalse(fleche.doitEtreSupprime(), "Le projectile ne doit pas être supprimé si la cible est vivante");
    }

    @Test
    public void testGetAngle() {
        float angle = fleche.getAngle();
        assertTrue(angle >= 0 && angle <= 360, "L'angle doit être valide");
    }

    @Test
    public void testUpdate() {
        fleche.setCible(mechantTest);
        fleche.setPositionX(0f);
        fleche.setPositionY(0f);
        mechantTest.setPositionX(100f);
        mechantTest.setPositionY(100f);
        
        assertDoesNotThrow(() -> {
            fleche.update(0.1f);
        }, "update() ne doit pas lever d'exception");
    }
}

