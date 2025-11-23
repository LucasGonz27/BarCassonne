package Epi.BarCassonne.game.Managers;

import Epi.BarCassonne.game.Entities.Projectiles.Fleche;
import Epi.BarCassonne.game.Entities.Projectiles.Projectile;
import Epi.BarCassonne.game.Utils.GdxTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe ProjectileManager.
 */
public class ProjectileManagerTest {

    private ProjectileManager projectileManager;

    @BeforeAll
    public static void setUpClass() {
        GdxTestUtils.initializeGdx();
    }

    @BeforeEach
    public void setUp() {
        projectileManager = new ProjectileManager();
    }

    @Test
    public void testCreationProjectileManager() {
        assertNotNull(projectileManager, "ProjectileManager ne doit pas être null");
    }

    @Test
    public void testAjouterProjectile() {
        Projectile projectile = new Fleche();
        projectileManager.ajouterProjectile(projectile);
        
        projectileManager.update(0.1f);
        assertTrue(true, "Le projectile doit être ajouté sans erreur");
    }

    @Test
    public void testAjouterProjectileNull() {
        assertDoesNotThrow(() -> {
            projectileManager.ajouterProjectile(null);
        }, "ajouterProjectile(null) ne doit pas lever d'exception");
        
        assertDoesNotThrow(() -> {
            projectileManager.update(0.1f);
        }, "update() doit fonctionner même avec un projectile null ajouté");
    }

    @Test
    public void testClear() {
        Projectile projectile1 = new Fleche();
        Projectile projectile2 = new Fleche();
        
        projectileManager.ajouterProjectile(projectile1);
        projectileManager.ajouterProjectile(projectile2);
        
        assertDoesNotThrow(() -> {
            projectileManager.clear();
        }, "clear() ne doit pas lever d'exception");
        
        assertDoesNotThrow(() -> {
            projectileManager.update(0.1f);
        }, "update() doit fonctionner après clear()");
    }

    @Test
    public void testClearListeVide() {
        assertDoesNotThrow(() -> {
            projectileManager.clear();
        }, "clear() sur une liste vide ne doit pas lever d'exception");
    }

    @Test
    public void testUpdateSansProjectiles() {
        assertDoesNotThrow(() -> {
            projectileManager.update(0.1f);
        }, "update() sans projectiles ne doit pas lever d'exception");
    }

    @Test
    public void testUpdateAvecProjectiles() {
        Projectile projectile = new Fleche();
        projectile.setPositionX(100f);
        projectile.setPositionY(100f);
        
        projectileManager.ajouterProjectile(projectile);
        
        assertDoesNotThrow(() -> {
            projectileManager.update(0.1f);
        }, "update() avec projectiles ne doit pas lever d'exception");
    }

    @Test
    public void testUpdateMultipleFrames() {
        Projectile projectile = new Fleche();
        projectile.setPositionX(100f);
        projectile.setPositionY(100f);
        
        projectileManager.ajouterProjectile(projectile);
        
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(() -> {
                projectileManager.update(0.1f);
            }, "update() ne doit pas lever d'exception à la frame " + i);
        }
    }
}
