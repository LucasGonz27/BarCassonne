package Epi.BarCassonne.game.Entities.Mechants;
import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le GuerrierGoblin.
 */
public class GuerrierGoblin extends Mechant {
    
    /**
     * Constructeur par défaut.
     */
    public GuerrierGoblin() {
        super(65, 60f, AssetMana.getAnimation("GuerrierGoblin"));
    }

    /**
     * Initialise les résistances du GuerrierGoblin.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.CANON, -0.2f);    // -20% de résistance aux canons donc 120% de dégâts
    }
}
