package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le Chevalier.
 * C'est un ennemi avec 250 PV et une vitesse de 1.6.
 * Il utilise une animation provenant d'AssetMana.
 */
public class Chevalier extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 250, la vitesse à 1.6f et l'animation.
     */
    public Chevalier() {
        super(250, 1.6f, AssetMana.getAnimation("Chevalier"));
    }
}
