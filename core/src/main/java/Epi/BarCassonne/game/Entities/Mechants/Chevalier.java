package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le Chevalier.
 */
public class Chevalier extends Mechant {


    /** Points de vie du Chevalier. */
    private static final int PV = 450;

    /** Vitesse de déplacement du Chevalier. */
    private static final float VITESSE = 170f;
    /**
     * Constructeur par défaut.
     */
    public Chevalier() {
        super(PV, VITESSE, AssetMana.getAnimation("Chevalier"));
    }

    /**
     * Initialise les résistances du Chevalier.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.ARCHER, 1.0f);  // donc les flèches ne font pas de dégâts
        setResistance(TypeTour.MAGIE, -0.2f);   // -20% de résistance à la magie donc 120% de dégâts
    }
}
