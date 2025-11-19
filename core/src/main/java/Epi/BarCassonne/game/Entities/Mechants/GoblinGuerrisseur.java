package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le GoblinGuerrisseur.
 */
public class GoblinGuerrisseur extends Mechant {
    
    /**
     * Constructeur par défaut.
     */
    public GoblinGuerrisseur() {
        super(80, 30f, AssetMana.getAnimation("GoblinGuerrisseur"));
    }
}
