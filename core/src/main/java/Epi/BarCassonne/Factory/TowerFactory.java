package Epi.BarCassonne.Factory;

import Epi.BarCassonne.game.Entities.Towers.*;

/**
 * Factory pour créer des instances de Tower.
 * Utilise le pattern Factory pour centraliser la création des towers.
 */
public class TowerFactory {

    /**
     * Crée un tower selon son type.
     * @param TowerType Le nom du type de tower (ex: "TowerArcher")
     * @return La Tower créé
     * @throws IllegalArgumentException Si le type de tower est inconnu
     */
    public static Tower creerTower(String TowerType) {
        switch (TowerType) {
            case "TowerArcher":
                return new TowerArcher();
            case "TowerMagie":
                return new TowerMagie();
            case "TowerCanon":
                return new TowerCanon();
            case "TowerForgeron":
                return new TowerForgeron();

            default:
                throw new IllegalArgumentException("Type de tours inconnu: " + TowerType);
        }
    }
}