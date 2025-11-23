package Epi.BarCassonne.Factory;

import Epi.BarCassonne.game.Entities.Mechants.*;
import Epi.BarCassonne.game.Utils.GdxTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MechantFactoryTest {

    @BeforeAll
    public static void setUpClass() {
        GdxTestUtils.initializeGdx();
    }

    @Test
    public void testCreerPaysanGoblin() {
        Mechant mechant = MechantFactory.creerMechant("PaysanGoblin");
        assertNotNull(mechant, "Le méchant créé ne doit pas être null");
        assertTrue(mechant instanceof PaysanGoblin, "Le méchant doit être un PaysanGoblin");
    }

    @Test
    public void testCreerGuerrierGoblin() {
        Mechant mechant = MechantFactory.creerMechant("GuerrierGoblin");
        assertNotNull(mechant, "Le méchant créé ne doit pas être null");
        assertTrue(mechant instanceof GuerrierGoblin, "Le méchant doit être un GuerrierGoblin");
    }

    @Test
    public void testCreerGoblinGuerrisseur() {
        Mechant mechant = MechantFactory.creerMechant("GoblinGuerrisseur");
        assertNotNull(mechant, "Le méchant créé ne doit pas être null");
        assertTrue(mechant instanceof GoblinGuerrisseur, "Le méchant doit être un GoblinGuerrisseur");
    }

    @Test
    public void testCreerGoblinBomb() {
        Mechant mechant = MechantFactory.creerMechant("GoblinBomb");
        assertNotNull(mechant, "Le méchant créé ne doit pas être null");
        assertTrue(mechant instanceof GoblinBomb, "Le méchant doit être un GoblinBomb");
    }

    @Test
    public void testCreerCochon() {
        Mechant mechant = MechantFactory.creerMechant("Cochon");
        assertNotNull(mechant, "Le méchant créé ne doit pas être null");
        assertTrue(mechant instanceof Cochon, "Le méchant doit être un Cochon");
    }

    @Test
    public void testCreerChevalier() {
        Mechant mechant = MechantFactory.creerMechant("Chevalier");
        assertNotNull(mechant, "Le méchant créé ne doit pas être null");
        assertTrue(mechant instanceof Chevalier, "Le méchant doit être un Chevalier");
    }

    @Test
    public void testCreerBossChevalier() {
        Mechant mechant = MechantFactory.creerMechant("BossChevalier");
        assertNotNull(mechant, "Le méchant créé ne doit pas être null");
        assertTrue(mechant instanceof BossChevalier, "Le méchant doit être un BossChevalier");
    }

    @Test
    public void testCreerGolem() {
        Mechant mechant = MechantFactory.creerMechant("Golem");
        assertNotNull(mechant, "Le méchant créé ne doit pas être null");
        assertTrue(mechant instanceof Golem, "Le méchant doit être un Golem");
    }

    @Test
    public void testCreerRoiGoblin() {
        Mechant mechant = MechantFactory.creerMechant("RoiGoblin");
        assertNotNull(mechant, "Le méchant créé ne doit pas être null");
        assertTrue(mechant instanceof RoiGoblin, "Le méchant doit être un RoiGoblin");
    }

    @Test
    public void testCreerMechantTypeInconnu() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> MechantFactory.creerMechant("TypeInconnu"),
            "Une exception doit être levée pour un type inconnu"
        );
        assertTrue(exception.getMessage().contains("Type de mechant inconnu"), 
            "Le message d'erreur doit indiquer que le type est inconnu");
    }

    @Test
    public void testCreerMechantNull() {
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> MechantFactory.creerMechant(null),
            "Une exception doit être levée pour null"
        );
        assertNotNull(exception, "L'exception ne doit pas être null");
    }

    @Test
    public void testCreerMechantVide() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> MechantFactory.creerMechant(""),
            "Une exception doit être levée pour une chaîne vide"
        );
        assertTrue(exception.getMessage().contains("Type de mechant inconnu"), 
            "Le message d'erreur doit indiquer que le type est inconnu");
    }
}

