package Epi.BarCassonne.game.Vague;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Représente une vague d'ennemis.
 * Contient les types d'ennemis et leurs quantités à spawner.
 */
public class Vague {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    private Map<Class<? extends Mechant>, Integer> ennemisParType;
    private int numero;
    private float intervalleSpawn = 2f;
    private float tempsDepuisDernierSpawn = 0f;
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
     * @return L'ennemi créé, ou null si tous sont spawnés
     */
    public Mechant spawnEnnemi() {
        for (Map.Entry<Class<? extends Mechant>, Integer> entry : ennemisParType.entrySet()) {
            if (entry.getValue() > 0) {
                try {
                    Mechant ennemi = entry.getKey().getDeclaredConstructor().newInstance();
                    ennemisParType.put(entry.getKey(), entry.getValue() - 1);
                    return ennemi;
                } catch (Exception e) {
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
    public float getTempsDepuisDernierSpawn() {
        return tempsDepuisDernierSpawn;
    }

    public void setTempsDepuisDernierSpawn(float temps) {
        this.tempsDepuisDernierSpawn = temps;
    }

    public float getIntervalleSpawn() {
        return intervalleSpawn;
    }

    public int getNumero() {
        return numero;
    }

    public Array<Mechant> getEnnemisActifs() {
        return ennemisActifs;
    }
}
