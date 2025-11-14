package Epi.BarCassonne.Factory;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Entities.Mechants.*;

/**
 * Factory pour créer des instances de Mechant.
 * Utilise le pattern Factory pour centraliser la création des ennemis.
 */
public class MechantFactory {
    
    /**
     * Crée un ennemi selon son type.
     * @param mechantType Le nom du type d'ennemi (ex: "PaysanGoblin")
     * @return L'ennemi créé
     * @throws IllegalArgumentException Si le type est inconnu
     */
    public static Mechant creerMechant(String mechantType) {
        switch (mechantType) {
            case "PaysanGoblin":
                return new PaysanGoblin();
            case "GuerrierGoblin":
                return new GuerrierGoblin();
            case "GoblinGuerrisseur":
                return new GoblinGuerrisseur();
            case "GoblinBomb":
                return new GoblinBomb();
            case "Cochon":
                return new Cochon();
            case "Chevalier":
                return new Chevalier();
            case "BossChevalier":
                return new BossChevalier();
            case "Golem":
                return new Golem();
            case "RoiGoblin":
                return new RoiGoblin();
            default:
                throw new IllegalArgumentException("Type de mechant inconnu: " + mechantType);
        }
    }
}