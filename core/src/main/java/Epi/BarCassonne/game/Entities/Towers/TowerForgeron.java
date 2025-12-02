package Epi.BarCassonne.game.Entities.Towers;

import Epi.BarCassonne.game.Config.TowerUpgradeConfig;

/**
 * Classe représentant une tour forgeron.
 */
public class TowerForgeron extends Tower {

    protected static final int PRIX = 1200;
    protected static final float PORTEE = 0f;
    protected static final int APPORT_LINGOTS_BASE = 100;

    public TowerForgeron() {
        super(0f, 0f, 1, 4, PORTEE, PRIX, TypeTour.FORGERON);
    }

    /**
     * Retourne le nombre de lingots générés par cette tour selon son niveau.
     * @return Le nombre de lingots générés (base * multiplicateur du niveau)
     */
    public int getApportLingots() {
        float multiplicateur = TowerUpgradeConfig.getMultiplicateurLingots(getLevel());
        return (int) (APPORT_LINGOTS_BASE * multiplicateur);
    }
}
