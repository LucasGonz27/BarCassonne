package Epi.BarCassonne.game.Interfaces;

/**
 * Interface définissant le comportement de réception de dégâts.
 * Permet à une entité de recevoir des dégâts.
 */
public interface Damageable {
    /**
     * Reçoit des dégâts.
     * @param degats Montant des dégâts à recevoir
     */
    void recevoirDegats(int degats);
}
