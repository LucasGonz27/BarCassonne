package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Managers.AssetMana;

/**
 * Classe représentant le GuerrierGoblin.
 * C'est un ennemi avec 65 PV et une vitesse de 60.
 * Il utilise une animation provenant d'AssetMana.
 */
public class GuerrierGoblin extends Mechant {
    
    /**
     * Constructeur par défaut.
     * Initialise les PV à 65, la vitesse à 60f et l'animation.
     */
    public GuerrierGoblin() {
        super(65, 60f, AssetMana.getAnimation("GuerrierGoblin"));
    }
}
