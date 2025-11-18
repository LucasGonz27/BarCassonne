package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le RoiGoblin.
 * C'est un ennemi puissant avec 500 PV et une vitesse de 50.
 * Il utilise une animation provenant d'AssetMana.
 * Le roi goblin a des résistances variées grâce à ses pouvoirs magiques.
 */
public class RoiGoblin extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 500, la vitesse à 50f et l'animation.
     */
    public RoiGoblin() {
        super(500, 50f, AssetMana.getAnimation("RoiGoblin"));
    }
    
    /**
     * Initialise les résistances du RoiGoblin.
     * Résistant à la magie grâce à ses pouvoirs, mais vulnérable aux attaques physiques.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.MAGIE, 0.4f);   // 40% de résistance à la magie (pouvoirs magiques)
        setResistance(TypeTour.ARCHER, -0.15f); // -15% de résistance = vulnérable aux flèches
        setResistance(TypeTour.CANON, -0.1f);  // -10% de résistance = légèrement vulnérable aux canons
    }
}
