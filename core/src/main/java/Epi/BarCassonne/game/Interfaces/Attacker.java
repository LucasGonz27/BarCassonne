package Epi.BarCassonne.game.Interfaces;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Managers.ProjectileManager;

/**
 * Interface définissant le comportement d'attaque.
 * Permet à une entité d'attaquer un ennemi.
 */
public interface Attacker {
    /**
     * Attaque un ennemi.
     * @param mechant L'ennemi à attaquer
     */
    void attacker(Mechant mechant, ProjectileManager projectileManager);
}
