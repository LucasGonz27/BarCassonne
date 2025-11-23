package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Utils.GdxTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MechantSpecificTest {

    @BeforeAll
    public static void setUpClass() {
        GdxTestUtils.initializeGdx();
    }

    @Test
    public void testCreationCochon() {
        Cochon cochon = new Cochon();
        assertNotNull(cochon, "Cochon ne doit pas être null");
        assertTrue(cochon.isEnVie(), "Le cochon doit être en vie après création");
        assertTrue(cochon.getPV() > 0, "Le cochon doit avoir des PV");
    }

    @Test
    public void testCreationGuerrierGoblin() {
        GuerrierGoblin guerrier = new GuerrierGoblin();
        assertNotNull(guerrier, "GuerrierGoblin ne doit pas être null");
        assertTrue(guerrier.isEnVie(), "Le guerrier doit être en vie après création");
        assertTrue(guerrier.getPV() > 0, "Le guerrier doit avoir des PV");
    }

    @Test
    public void testCreationGoblinGuerrisseur() {
        GoblinGuerrisseur guerrisseur = new GoblinGuerrisseur();
        assertNotNull(guerrisseur, "GoblinGuerrisseur ne doit pas être null");
        assertTrue(guerrisseur.isEnVie(), "Le guérisseur doit être en vie après création");
        assertTrue(guerrisseur.getPV() > 0, "Le guérisseur doit avoir des PV");
    }

    @Test
    public void testCreationGoblinBomb() {
        GoblinBomb bomb = new GoblinBomb();
        assertNotNull(bomb, "GoblinBomb ne doit pas être null");
        assertTrue(bomb.isEnVie(), "Le goblin bomb doit être en vie après création");
        assertTrue(bomb.getPV() > 0, "Le goblin bomb doit avoir des PV");
    }

    @Test
    public void testCreationChevalier() {
        Chevalier chevalier = new Chevalier();
        assertNotNull(chevalier, "Chevalier ne doit pas être null");
        assertTrue(chevalier.isEnVie(), "Le chevalier doit être en vie après création");
        assertTrue(chevalier.getPV() > 0, "Le chevalier doit avoir des PV");
    }

    @Test
    public void testCreationBossChevalier() {
        BossChevalier boss = new BossChevalier();
        assertNotNull(boss, "BossChevalier ne doit pas être null");
        assertTrue(boss.isEnVie(), "Le boss doit être en vie après création");
        assertTrue(boss.getPV() > 0, "Le boss doit avoir des PV");
    }

    @Test
    public void testCreationGolem() {
        Golem golem = new Golem();
        assertNotNull(golem, "Golem ne doit pas être null");
        assertTrue(golem.isEnVie(), "Le golem doit être en vie après création");
        assertTrue(golem.getPV() > 0, "Le golem doit avoir des PV");
    }

    @Test
    public void testCreationRoiGoblin() {
        RoiGoblin roi = new RoiGoblin();
        assertNotNull(roi, "RoiGoblin ne doit pas être null");
        assertTrue(roi.isEnVie(), "Le roi doit être en vie après création");
        assertTrue(roi.getPV() > 0, "Le roi doit avoir des PV");
    }

    @Test
    public void testCochonRecevoirDegats() {
        Cochon cochon = new Cochon();
        int pvInitiaux = cochon.getPV();
        
        cochon.recevoirDegats(50);
        
        assertTrue(cochon.getPV() < pvInitiaux, "Les PV doivent diminuer après avoir reçu des dégâts");
        assertTrue(cochon.getPV() > 0, "Le cochon doit encore être en vie");
    }

    @Test
    public void testGuerrierGoblinRecevoirDegats() {
        GuerrierGoblin guerrier = new GuerrierGoblin();
        int pvInitiaux = guerrier.getPV();
        
        guerrier.recevoirDegats(30);
        
        assertTrue(guerrier.getPV() < pvInitiaux, "Les PV doivent diminuer après avoir reçu des dégâts");
    }
}

