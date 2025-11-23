package Epi.BarCassonne.game.Entities.Mechants;
import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le GuerrierGoblin.
 */
public class GuerrierGoblin extends Mechant {


    /** Points de vie du GuerrierGoblin. */
    private static final int PV = 110;

    /** Vitesse de déplacement du GuerrierGoblin. */
    private static final float VITESSE = 70f;
    /**
     * Constructeur par défaut.
     */
    public GuerrierGoblin() {
        super(PV, VITESSE, AssetMana.getAnimation("GuerrierGoblin"));
    }

    /**
     * Initialise les résistances du GuerrierGoblin.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.CANON, -0.2f);    // -20% de résistance aux canons donc 120% de dégâts
    }
}
