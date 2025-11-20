package Epi.BarCassonne.Factory;
import Epi.BarCassonne.game.Entities.Projectiles.*;

/**
 * Factory pour créer des instances de Mechant.
 * Utilise le pattern Factory pour centraliser la création des ennemis.
 */
public class ProjectileFactory {
    
    /**
     * Crée un ennemi selon son type.
     * @param TowerType Le nom du type de tour (ex: "TowerArcher")
     * @return Le projectile créé
     * @throws IllegalArgumentException Si le type est inconnu
     */
    public static Projectile creerProjectile(String TowerType) {
        switch (TowerType) {
            case "TowerArcher":
                return new Fleche();
            case "TowerCanon":
                return new Bullet();
            case "TowerMagie":
                return new Sort();
            default:
                throw new IllegalArgumentException("Type de projectile inconnu pour la tour : " + TowerType);
        }
    }
}