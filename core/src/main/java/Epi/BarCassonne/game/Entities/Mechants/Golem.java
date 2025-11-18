package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le Golem.
 * C'est un ennemi puissant avec 400 PV et une vitesse de 0.5.
 * Il utilise une animation provenant d'AssetMana.
 * Résistant aux attaques physiques (ARCHER, CANON) mais vulnérable à la magie.
 */
public class Golem extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 400, la vitesse à 0.5f et l'animation.
     */
    public Golem() {
        super(400, 0.5f, AssetMana.getAnimation("Golem"));
    }
    
    /**
     * Initialise les résistances du Golem.
     * Résistant à 50% aux attaques d'archer et de canon, mais vulnérable à la magie (-25% de résistance = +25% de dégâts).
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.ARCHER, 0.5f);  // 50% de résistance aux flèches
        setResistance(TypeTour.CANON, 0.5f);   // 50% de résistance aux canons
        setResistance(TypeTour.MAGIE, -0.25f); // -25% de résistance = vulnérable à la magie (+25% de dégâts)
    }
}
