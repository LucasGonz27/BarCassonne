package Epi.BarCassonne.game.Interfaces;

/**
 * Interface définissant le comportement de mouvement.
 * Permet à une entité de se déplacer.
 */
public interface Movable {
    /**
     * Déplace l'entité.
     * @param deltaTime Temps écoulé depuis la dernière frame
     */
    void move(float deltaTime);
}
