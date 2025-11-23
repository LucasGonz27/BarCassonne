package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le BossChevalier.
 */
public class BossChevalier extends Mechant {

    /** Points de vie du BossChevalier. */
    private static final int PV = 750;

    /** Vitesse de déplacement du BossChevalier. */
    private static final float VITESSE = 50f;
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 300, la vitesse à 0.9f et l'animation.
     */
    public BossChevalier() {
        super(PV, VITESSE, AssetMana.getAnimation("BossChevalier"));
    }
    
    /**
     * Initialise les résistances du BossChevalier.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.ARCHER, 1.0f);  // donc les flèches ne font pas de dégâts
        setResistance(TypeTour.MAGIE, -0.2f);   // -20% de résistance à la magie donc 120% de dégâts
    }
}
