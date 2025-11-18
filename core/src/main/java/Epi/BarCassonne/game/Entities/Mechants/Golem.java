package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le Golem.
 * C'est un ennemi puissant avec 400 PV et une vitesse de 0.5.
 * Il utilise une animation provenant d'AssetMana.
 */
public class Golem extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 400, la vitesse à 0.5f et l'animation.
     */
    public Golem() {
        super(400, 0.5f, AssetMana.getAnimation("Golem"));
    }
}
