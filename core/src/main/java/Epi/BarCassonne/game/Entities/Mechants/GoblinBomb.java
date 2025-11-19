package Epi.BarCassonne.game.Entities.Mechants;
import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le GoblinBomb.
 */
public class GoblinBomb extends Mechant {
    
    /**
     * Constructeur par défaut.
     */
    public GoblinBomb() {
        super(130, 25f, AssetMana.getAnimation("GoblinBomb"));
    }


      /**
     * Initialise les résistances du GoblinBomb.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.MAGIE, 0.5f);   // 50% de résistance à la magie donc 50% de dégâts
    }
}
