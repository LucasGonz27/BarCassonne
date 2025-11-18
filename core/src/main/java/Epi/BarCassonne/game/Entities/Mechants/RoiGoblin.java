package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le RoiGoblin.
 * C'est un ennemi puissant avec 500 PV et une vitesse de 50.
 * Il utilise une animation provenant d'AssetMana.
 */
public class RoiGoblin extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 500, la vitesse à 50f et l'animation.
     */
    public RoiGoblin() {
        super(500, 50f, AssetMana.getAnimation("RoiGoblin"));
    }
}
