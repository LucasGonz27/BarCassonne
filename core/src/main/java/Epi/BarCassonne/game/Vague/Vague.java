package Epi.BarCassonne.game.Vague;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import java.util.Map;
import Epi.BarCassonne.Factory.MechantFactory;

/**
 * Représente une vague d'ennemis.
 * Contient les types d'ennemis et leurs quantités à spawner.
 */
public class Vague {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    /** Map associant chaque type d'ennemi à sa quantité restante à spawner. */
    private Map<Class<? extends Mechant>, Integer> ennemisParType;
    
    /** Numéro de la vague. */
    private int numero;
    
    /** Intervalle en secondes entre chaque spawn d'ennemi. */
    private float intervalleSpawn = 3f;
    
    /** Temps écoulé depuis le dernier spawn. */
    private float tempsDepuisDernierSpawn = 0f;
    
    /** Liste des ennemis actuellement actifs de cette vague. */
    private Array<Mechant> ennemisActifs;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée une nouvelle vague.
     * @param numero Le numéro de la vague
     */
    public Vague(int numero) {
        this.numero = numero;
        this.ennemisParType = new HashMap<>();
        this.ennemisActifs = new Array<>();
    }

    // ------------------------------------------------------------------------
    // REGION : CONFIGURATION
    // ------------------------------------------------------------------------
    /**
     * Ajoute un type d'ennemi à cette vague.
     * @param type Le type d'ennemi
     * @param quantite Le nombre d'ennemis de ce type
     */
    public void ajouterEnnemi(Class<? extends Mechant> type, int quantite) {
        ennemisParType.put(type, quantite);
    }

    // ------------------------------------------------------------------------
    // REGION : SPAWN
    // ------------------------------------------------------------------------
    /**
     * Spawne le prochain ennemi de la vague.
     * Utilise la MechantFactory pour créer l'ennemi selon son type.
     * @return L'ennemi créé, ou null si tous sont spawnés
     */
    public Mechant CreerEnnemi() {
        for (Map.Entry<Class<? extends Mechant>, Integer> entry : ennemisParType.entrySet()) {
            if (entry.getValue() > 0) {
                try {
                    Mechant ennemi = MechantFactory.creerMechant(entry.getKey().getSimpleName());
                    ennemisParType.put(entry.getKey(), entry.getValue() - 1);
                    return ennemi;
                } catch (IllegalArgumentException e) {
                    System.err.println("Erreur lors de la création de l'ennemi: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // ------------------------------------------------------------------------
    // REGION : VÉRIFICATIONS
    // ------------------------------------------------------------------------
    /**
     * @return true si tous les ennemis ont été spawnés
     */
    public boolean tousEnnemisSpawnes() {
        for (int nb : ennemisParType.values()) {
            if (nb > 0) return false;
        }
        return true;
    }

    /**
     * @return true si la vague est terminée (tous spawnés et tous morts)
     */
    public boolean estTerminee() {
        if (!tousEnnemisSpawnes()) return false;

        for (Mechant m : ennemisActifs) {
            if (m.isEnVie()) return false;
        }
        return true;
    }

    // ------------------------------------------------------------------------
    // REGION : GETTERS & SETTERS
    // ------------------------------------------------------------------------
    /**
     * Retourne le temps écoulé depuis le dernier spawn.
     * @return Le temps écoulé en secondes
     */
    public float getTempsDepuisDernierSpawn() {
        return tempsDepuisDernierSpawn;
    }

    /**
     * Définit le temps écoulé depuis le dernier spawn.
     * @param temps Le nouveau temps en secondes
     */
    public void setTempsDepuisDernierSpawn(float temps) {
        this.tempsDepuisDernierSpawn = temps;
    }

    /**
     * Retourne l'intervalle entre chaque spawn d'ennemi.
     * @return L'intervalle en secondes
     */
    public float getIntervalleSpawn() {
        return intervalleSpawn;
    }

    /**
     * Retourne le numéro de la vague.
     * @return Le numéro de la vague
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Retourne la liste des ennemis actifs de cette vague.
     * @return La liste des ennemis actifs
     */
    public Array<Mechant> getEnnemisActifs() {
        return ennemisActifs;
    }
}
