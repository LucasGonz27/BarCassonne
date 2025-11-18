package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le BossChevalier.
 * C'est un ennemi boss avec 300 PV et une vitesse de 0.9.
 * Il utilise une animation provenant d'AssetMana.
 */
public class BossChevalier extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 300, la vitesse à 0.9f et l'animation.
     */
    public BossChevalier() {
        super(300, 0.9f, AssetMana.getAnimation("BossChevalier"));
    }
}
