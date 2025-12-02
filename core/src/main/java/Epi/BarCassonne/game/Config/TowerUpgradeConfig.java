package Epi.BarCassonne.game.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration des améliorations de tours.
 * Centralise les coûts d'amélioration et les multiplicateurs de dégâts par niveau.
 * Facilement extensible pour ajouter de nouveaux niveaux.
 *
 * @author Epi
 */
public class TowerUpgradeConfig {

    // ========================================================================
    // CONSTANTES
    // ========================================================================

    /** Pourcentage de remboursement lors de la suppression d'une tour (50%) */
    public static final float POURCENTAGE_REMBOURSEMENT = 0.5f;

    /** Multiplicateur de dégâts pour le niveau 2 */
    public static final float MULTIPLICATEUR_DEGATS_LEVEL_2 = 2.0f;

    /** Multiplicateur de dégâts pour le niveau 3*/
    public static final float MULTIPLICATEUR_DEGATS_LEVEL_3 = 3.5f;

    /** Multiplicateur de dégâts pour le niveau 4*/
    public static final float MULTIPLICATEUR_DEGATS_LEVEL_4 = 5.0f;

    /** Multiplicateur d'apport de lingots pour le niveau 2 */
    public static final float MULTIPLICATEUR_LINGOTS_LEVEL_2 = 1.5f;

    /** Multiplicateur d'apport de lingots pour le niveau 3 */
    public static final float MULTIPLICATEUR_LINGOTS_LEVEL_3 = 2.0f;

    /** Multiplicateur d'apport de lingots pour le niveau 4 */
    public static final float MULTIPLICATEUR_LINGOTS_LEVEL_4 = 3.5f;

    // ========================================================================
    // CONFIGURATION DES COÛTS D'AMÉLIORATION
    // ========================================================================

    /** Map contenant les coûts d'amélioration par type de tour et par niveau */
    private final Map<String, Map<Integer, Integer>> coutsAmelioration;

    /** Instance singleton */
    private static final TowerUpgradeConfig instance = new TowerUpgradeConfig();

    /**
     * Constructeur privé pour le pattern singleton.
     * Initialise les coûts d'amélioration.
     */
    private TowerUpgradeConfig() {
        this.coutsAmelioration = new HashMap<>();
        initialiserCoutsAmelioration();
    }


    /**
     * Initialise les coûts d'amélioration pour chaque type de tour.
     */
    private void initialiserCoutsAmelioration() {
        Map<Integer, Integer> coutsArcher = new HashMap<>();
        coutsArcher.put(2, 250);
        coutsArcher.put(3, 500);
        coutsArcher.put(4, 800);
        coutsAmelioration.put("TowerArcher", coutsArcher);

        Map<Integer, Integer> coutsMagie = new HashMap<>();
        coutsMagie.put(2, 500);
        coutsMagie.put(3, 1500);
        coutsMagie.put(4, 3000);
        coutsAmelioration.put("TowerMagie", coutsMagie);

        Map<Integer, Integer> coutsCanon = new HashMap<>();
        coutsCanon.put(2, 300);
        coutsCanon.put(3, 700);
        coutsCanon.put(4, 1800);
        coutsAmelioration.put("TowerCanon", coutsCanon);

        Map<Integer, Integer> coutsForgeron = new HashMap<>();
        coutsForgeron.put(2, 1500);
        coutsForgeron.put(3, 2500);
        coutsForgeron.put(4, 3500);
        coutsAmelioration.put("TowerForgeron", coutsForgeron);
    }



    // ========================================================================
    // MÉTHODES PUBLIQUES
    // ========================================================================

    /**
     * Récupère le coût d'amélioration pour une tour donnée vers un niveau cible.
     *
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @param niveauCible Le niveau cible (ex: 2 pour passer de 1 à 2)
     * @return Le coût d'amélioration, ou 0 si le type ou le niveau est inconnu
     */
    public static int getCoutAmelioration(String towerType, int niveauCible) {
        Map<Integer, Integer> coutsTour = instance.coutsAmelioration.get(towerType);
        if (coutsTour == null) {
            return 0;
        }
        return coutsTour.getOrDefault(niveauCible, 0);
    }

    /**
     * Calcule le montant du remboursement lors de la suppression d'une tour.
     * Rembourse 50% du coût total investi (prix initial + améliorations).
     *
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @param prixInitial Le prix d'achat initial de la tour
     * @param niveauActuel Le niveau actuel de la tour
     * @return Le montant du remboursement
     */
    public static int calculerRemboursement(String towerType, int prixInitial, int niveauActuel) {
        int coutTotal = prixInitial;

        // Ajouter le coût de toutes les améliorations effectuées
        for (int niveau = 2; niveau <= niveauActuel; niveau++) {
            coutTotal += getCoutAmelioration(towerType, niveau);
        }

        return (int) (coutTotal * POURCENTAGE_REMBOURSEMENT);
    }

    /**
     * Récupère le multiplicateur de dégâts pour un niveau donné.
     *
     * @param niveau Le niveau de la tour (1, 2, 3 ou 4)
     * @return Le multiplicateur de dégâts pour ce niveau
     */
    public static float getMultiplicateurDegats(int niveau) {
        switch (niveau) {
            case 1: return 1.0f;
            case 2: return MULTIPLICATEUR_DEGATS_LEVEL_2;
            case 3: return MULTIPLICATEUR_DEGATS_LEVEL_3;
            case 4: return MULTIPLICATEUR_DEGATS_LEVEL_4;
            default: return 1.0f;
        }
    }

    /**
     * Récupère le multiplicateur d'apport de lingots pour un niveau donné.
     *
     * @param niveau Le niveau de la tour (1, 2, 3 ou 4)
     * @return Le multiplicateur d'apport de lingots pour ce niveau
     */
    public static float getMultiplicateurLingots(int niveau) {
        switch (niveau) {
            case 1: return 1.0f;
            case 2: return MULTIPLICATEUR_LINGOTS_LEVEL_2;
            case 3: return MULTIPLICATEUR_LINGOTS_LEVEL_3;
            case 4: return MULTIPLICATEUR_LINGOTS_LEVEL_4;
            default: return 1.0f;
        }
    }

    /**
     * Vérifie si une tour peut être améliorée.
     *
     * @param niveauActuel Le niveau actuel de la tour
     * @param niveauMax Le niveau maximum de la tour
     * @return true si la tour peut être améliorée, false sinon
     */
    public static boolean peutEtreAmelioree(int niveauActuel, int niveauMax) {
        return niveauActuel < niveauMax;
    }
}
