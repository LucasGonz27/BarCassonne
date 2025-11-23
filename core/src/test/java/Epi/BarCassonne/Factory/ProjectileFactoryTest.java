package Epi.BarCassonne.Factory;

import Epi.BarCassonne.game.Entities.Projectiles.*;
import Epi.BarCassonne.game.Utils.GdxTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectileFactoryTest {

    @BeforeAll
    public static void setUpClass() {
        GdxTestUtils.initializeGdx();
    }

    @Test
    public void testCreerProjectileTowerArcher() {
        Projectile projectile = ProjectileFactory.creerProjectile("TowerArcher");
        assertNotNull(projectile, "Le projectile créé ne doit pas être null");
        assertTrue(projectile instanceof Fleche, "Le projectile doit être une Fleche");
    }

    @Test
    public void testCreerProjectileTowerCanon() {
        Projectile projectile = ProjectileFactory.creerProjectile("TowerCanon");
        assertNotNull(projectile, "Le projectile créé ne doit pas être null");
        assertTrue(projectile instanceof Bullet, "Le projectile doit être une Bullet");
    }

    @Test
    public void testCreerProjectileTowerMagie() {
        Projectile projectile = ProjectileFactory.creerProjectile("TowerMagie");
        assertNotNull(projectile, "Le projectile créé ne doit pas être null");
        assertTrue(projectile instanceof Sort, "Le projectile doit être un Sort");
    }

    @Test
    public void testCreerProjectileTypeInconnu() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ProjectileFactory.creerProjectile("TypeInconnu"),
            "Une exception doit être levée pour un type inconnu"
        );
        assertTrue(exception.getMessage().contains("Type de projectile inconnu"), 
            "Le message d'erreur doit indiquer que le type est inconnu");
    }

    @Test
    public void testCreerProjectileTowerForgeron() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ProjectileFactory.creerProjectile("TowerForgeron"),
            "Une exception doit être levée pour TowerForgeron"
        );
        assertTrue(exception.getMessage().contains("Type de projectile inconnu"), 
            "Le message d'erreur doit indiquer que le type est inconnu");
    }

    @Test
    public void testCreerProjectileNull() {
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> ProjectileFactory.creerProjectile(null),
            "Une exception doit être levée pour null"
        );
        assertNotNull(exception, "L'exception ne doit pas être null");
    }

    @Test
    public void testCreerProjectileVide() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ProjectileFactory.creerProjectile(""),
            "Une exception doit être levée pour une chaîne vide"
        );
        assertTrue(exception.getMessage().contains("Type de projectile inconnu"), 
            "Le message d'erreur doit indiquer que le type est inconnu");
    }
}

