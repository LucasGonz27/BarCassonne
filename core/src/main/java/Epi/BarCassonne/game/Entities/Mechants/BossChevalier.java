package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le BossChevalier.
 * C'est un ennemi boss avec 300 PV et une vitesse de 0.9.
 * Il utilise une animation provenant d'AssetMana.
 * Très résistant aux flèches et partiellement à la magie grâce à son armure de boss.
 */
public class BossChevalier extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 300, la vitesse à 0.9f et l'animation.
     */
    public BossChevalier() {
        super(300, 0.9f, AssetMana.getAnimation("BossChevalier"));
    }
    
    /**
     * Initialise les résistances du BossChevalier.
     * Très résistant aux flèches et partiellement à la magie grâce à son armure de boss.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.ARCHER, 0.6f);  // 60% de résistance aux flèches (armure de boss)
        setResistance(TypeTour.MAGIE, 0.2f);   // 20% de résistance à la magie
    }
}
