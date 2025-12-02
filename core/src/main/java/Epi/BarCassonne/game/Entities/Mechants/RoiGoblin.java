package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le RoiGoblin.
 */
public class RoiGoblin extends Mechant {

    /** Points de vie du RoiGoblin. */
    private static final int PV = 1500;

    /** Vitesse de déplacement du RoiGoblin. */
    private static final float VITESSE = 60f;

    /**
     * Constructeur par défaut.
     */
    public RoiGoblin() {
        super(PV, VITESSE, AssetMana.getAnimation("RoiGoblin"));
    }

    /**
     * Initialise les résistances du RoiGoblin.
   
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.MAGIE, 0.4f);   // 40% de résistance à la magie (pouvoirs magiques)
        setResistance(TypeTour.ARCHER, 1.0f); // donc les flèches ne font pas de dégâts
        setResistance(TypeTour.CANON, -0.1f);  // -10% de résistance = légèrement vulnérable aux canons
    }
}
