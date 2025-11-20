package Epi.BarCassonne.game.Entities.Mechants;
import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le PaysanGoblin.
 */
public class PaysanGoblin extends Mechant {

    /** Points de vie du PaysanGoblin. */
    private static final int PV = 50;

    /** Vitesse de déplacement du PaysanGoblin. */
    private static final float VITESSE = 40f;

    /**
     * Constructeur par défaut.
     */
    public PaysanGoblin() {
        super(PV, VITESSE, AssetMana.getAnimation("PaysanGoblin"));
    }

    /**
     * Initialise les résistances du PaysanGoblin.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.MAGIE, -0.5f);   //donc 150% de dégâts
    }
}
