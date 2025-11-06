package Epi.BarCassonne.game.Vague;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * La classe Vague représente une vague d'ennemis dans le jeu.
 * Chaque vague peut contenir plusieurs types d'ennemis avec une quantité définie.
 * Elle gère le spawn des ennemis à intervalles réguliers et le suivi de leur état.
 */
public class Vague {

    // ------------------------------------------------------------------------
    // ATTRIBUTS
    // ------------------------------------------------------------------------

    /** Map qui stocke chaque type d'ennemi et la quantité restante à spawn */
    protected Map<Class<? extends Mechant>, Integer> ennemisParType;

    /** Numéro de la vague */
    protected int numero;

    /** Intervalle en secondes entre chaque spawn d'un ennemi */
    protected float intervalleSpawn = 2f;

    /** Temps écoulé depuis le dernier spawn */
    protected float tempsDepuisDernierSpawn = 0f;

    /** Liste des ennemis actifs dans cette vague */
    protected Array<Mechant> ennemisActifs;

    // ------------------------------------------------------------------------
    // CONSTRUCTEUR
    // ------------------------------------------------------------------------

    /**
     * Crée une nouvelle vague.
     * @param numero Le numéro de la vague
     * @param ennemisParType les ennemis par type
     * @param ennemisActifs
     */
    public Vague(int numero) {
        this.numero = numero;
        this.ennemisParType = new HashMap<>();
        this.ennemisActifs = new Array<>();
    }

    // ------------------------------------------------------------------------
    // MÉTHODES DE GESTION DES ENNEMIS
    // ------------------------------------------------------------------------

    /**
     * Ajoute un type d'ennemi avec une quantité à spawn pour cette vague.
     * @param type Le type de l'ennemi (classe)
     * @param quantite Nombre d'ennemis de ce type
     */
    public void ajouterEnnemi(Class<? extends Mechant> type, int quantite) {
        ennemisParType.put(type, quantite);
    }

    /**
     * Retourne la map des ennemis par type et leur quantité restante.
     */
    public Map<Class<? extends Mechant>, Integer> getEnnemisParType() {
        return ennemisParType;
    }

    /**
     * Met à jour la vague à chaque frame.
     * - Gère le spawn des ennemis selon l'intervalle défini.
     * - Met à jour tous les ennemis actifs (déplacement, animations, etc.).
     * @param deltaTime Temps écoulé depuis la dernière frame (en secondes)
     */
    public void update(float deltaTime) {
        // On incrémente le temps écoulé depuis le dernier spawn
        tempsDepuisDernierSpawn += deltaTime;

        // Si c'est le moment de spawn un ennemi
        if (tempsDepuisDernierSpawn >= intervalleSpawn) {
            Mechant unMechant = spawnEnnemi();
            if (unMechant != null) {
                ennemisActifs.add(unMechant);
            }
            tempsDepuisDernierSpawn = 0f;
        }

        // Mise à jour de tous les ennemis actifs
        for (Mechant unMechant : ennemisActifs) {
            unMechant.update(deltaTime);
        }
    }

    /**
     * Crée et retourne le prochain ennemi à spawn si disponible.
     * @return Un ennemi prêt à être ajouté au jeu, ou null si tous ont été spawnés
     */
    public Mechant spawnEnnemi() {
        for (Map.Entry<Class<? extends Mechant>, Integer> entry : ennemisParType.entrySet()) {
            Class<? extends Mechant> type = entry.getKey();
            int restant = entry.getValue();

            if (restant > 0) {
                try {
                    // Création de l'ennemi via le constructeur par défaut
                    Mechant unMechant = type.getDeclaredConstructor().newInstance();
                    // On décrémente le compteur de ce type d'ennemi
                    ennemisParType.put(type, restant - 1);
                    return unMechant;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // ------------------------------------------------------------------------
    // MÉTHODES DE VÉRIFICATION
    // ------------------------------------------------------------------------

    /**
     * Vérifie si la vague est terminée.
     * Une vague est terminée si :
     * 1. Tous les ennemis ont été spawnés.
     * 2. Tous les ennemis actifs sont morts.
     * @return true si la vague est terminée, false sinon
     */
    public boolean estTerminee() {
        // Vérifie si tous les ennemis ont été spawnés
        boolean tousSpawnes = true;
        for (int nb : ennemisParType.values()) {
            if (nb > 0) {
                tousSpawnes = false;
                break;
            }
        }

        // Vérifie si tous les ennemis actifs sont morts
        boolean tousMorts = true;
        for (Mechant unMechant : ennemisActifs) {
            if (unMechant.isEnVie()) {
                tousMorts = false;
                break;
            }
        }

        return tousSpawnes && tousMorts;
    }

    /**
     * Retourne la liste des ennemis actuellement actifs dans la vague.
     */
    public Array<Mechant> getEnnemisActifs() {
        return ennemisActifs;
    }
}
