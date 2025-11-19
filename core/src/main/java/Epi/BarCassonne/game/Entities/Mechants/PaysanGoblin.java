package Epi.BarCassonne.game.Entities.Mechants;
import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le PaysanGoblin.
 */
public class PaysanGoblin extends Mechant {

    /**
     * Constructeur par défaut.
     */
    public PaysanGoblin() {
        super(50, 40f, AssetMana.getAnimation("PaysanGoblin"));
    }

    /**
     * Initialise les résistances du PaysanGoblin.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.MAGIE, -0.5f);   //donc 150% de dégâts
    }
}
