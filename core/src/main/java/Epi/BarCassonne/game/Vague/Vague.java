package Epi.BarCassonne.game.Vague;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * La classe Vague représente une vague d'ennemis dans le jeu.
 */
public class Vague {
    private Map<Class<? extends Mechant>, Integer> ennemisParType;
    private int numero;
    private float intervalleSpawn = 2f;
    private float tempsDepuisDernierSpawn = 0f;
    private Array<Mechant> ennemisActifs;

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

    public Vague(int numero) {
        this.numero = numero;
        this.ennemisParType = new HashMap<>();
        this.ennemisActifs = new Array<>();
    }

    public void ajouterEnnemi(Class<? extends Mechant> type, int quantite) {
        ennemisParType.put(type, quantite);
    }

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

    public boolean tousEnnemisSpawnes() {
        for (int nb : ennemisParType.values()) {
            if (nb > 0) return false;
        }
        return true;
    }

    public boolean estTerminee() {
        if (!tousEnnemisSpawnes()) return false;
        for (Mechant unMechant : ennemisActifs) {
            if (unMechant.isEnVie()) return false;
        }
        return true;
    }

    public Array<Mechant> getEnnemisActifs() {
        return ennemisActifs;
    }

}
