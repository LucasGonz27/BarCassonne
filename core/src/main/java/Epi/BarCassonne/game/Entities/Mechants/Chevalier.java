package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le Chevalier.
 * C'est un ennemi avec 250 PV et une vitesse de 1.6.
 * Il utilise une animation provenant d'AssetMana.
 * Résistant aux flèches grâce à son armure, mais vulnérable aux explosions de canon.
 */
public class Chevalier extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 250, la vitesse à 1.6f et l'animation.
     */
    public Chevalier() {
        super(250, 1.6f, AssetMana.getAnimation("Chevalier"));
    }
    
    /**
     * Initialise les résistances du Chevalier.
     * Résistant à 30% aux flèches grâce à son armure, mais vulnérable aux canons.
     */
    @Override
    protected void initialiserResistances() {
        setResistance(TypeTour.ARCHER, 0.3f);   // 30% de résistance aux flèches (armure)
        setResistance(TypeTour.CANON, -0.2f);  // -20% de résistance = vulnérable aux explosions
    }
}
