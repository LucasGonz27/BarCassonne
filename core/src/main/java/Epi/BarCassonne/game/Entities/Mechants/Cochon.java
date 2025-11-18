package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le Cochon.
 * C'est un ennemi avec 100 PV et une vitesse de 50.
 * Il utilise une animation provenant d'AssetMana.
 */
public class Cochon extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 100, la vitesse à 50f et l'animation.
     */
    public Cochon() {
        super(100, 50f, AssetMana.getAnimation("Cochon"));
    }
}
