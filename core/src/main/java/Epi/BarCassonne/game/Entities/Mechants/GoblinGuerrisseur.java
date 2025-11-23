package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le GoblinGuerrisseur.
 */
public class GoblinGuerrisseur extends Mechant {


    /** Points de vie du GoblinGuerrisseur. */
    private static final int PV = 200;

    /** Vitesse de déplacement du GoblinGuerrisseur. */
    private static final float VITESSE = 35f;
    /**
     * Constructeur par défaut.
     */
    public GoblinGuerrisseur() {
        super(PV, VITESSE, AssetMana.getAnimation("GoblinGuerrisseur"));
    }
}
