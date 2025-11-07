package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le PaysanGoblin.
 * C'est un ennemi de base avec PV et vitesse spécifiques.
 * Il utilise une animation provenant d'AssetMana.
 */
public class PaysanGoblin extends Mechant {

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Constructeur par défaut.
     * Initialise les PV, la vitesse et l'animation.
     */
    public PaysanGoblin() {
        super(50, 100f, AssetMana.getAnimation("PaysanGoblin"));
    }
}
