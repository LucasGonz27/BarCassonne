package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le Cochon.
 */
public class Cochon extends Mechant {


    /** Points de vie du Cochon. */
    private static final int PV = 250;

    /** Vitesse de déplacement du Cochon. */
    private static final float VITESSE = 60f;
    /**
     * Constructeur par défaut.
     */
    public Cochon() {
        super(PV, VITESSE, AssetMana.getAnimation("Cochon"));
    }
}
