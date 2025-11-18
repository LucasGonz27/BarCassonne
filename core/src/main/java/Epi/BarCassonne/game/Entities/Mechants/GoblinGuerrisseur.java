package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le GoblinGuerrisseur.
 * C'est un ennemi avec 80 PV et une vitesse de 30.
 * Il utilise une animation provenant d'AssetMana.
 */
public class GoblinGuerrisseur extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 80, la vitesse à 30f et l'animation.
     */
    public GoblinGuerrisseur() {
        super(80, 30f, AssetMana.getAnimation("GoblinGuerrisseur"));
    }
}
