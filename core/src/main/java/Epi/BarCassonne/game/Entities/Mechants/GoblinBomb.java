package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le GoblinBomb.
 * C'est un ennemi avec 130 PV et une vitesse de 25.
 * Il utilise une animation provenant d'AssetMana.
 */
public class GoblinBomb extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 130, la vitesse à 25f et l'animation.
     */
    public GoblinBomb() {
        super(130, 25f, AssetMana.getAnimation("GoblinBomb"));
    }
}
