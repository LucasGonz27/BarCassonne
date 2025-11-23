package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le Golem
 */
public class Golem extends Mechant {

    /** Points de vie du Golem. */
    private static final int PV = 1000;

    /** Vitesse de déplacement du Golem. */

    private static final float VITESSE = 40f;
    
    /**
     * Constructeur par défaut.
     */
    public Golem() {
        super(PV, VITESSE, AssetMana.getAnimation("Golem"));
    }
    
    /**
     * Initialise les résistances du Golem.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.ARCHER, 1.0f);  // donc les flèches ne font pas de dégâts
        setResistance(TypeTour.CANON, 0.5f);   // 50% de résistance aux canons donc 50% de dégâts
    }
}
