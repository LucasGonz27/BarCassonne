package Epi.BarCassonne.game.Entities.Towers;

/**
 * Enum représentant les différents types de tours disponibles.
 * Utilisé pour le système de résistances des méchants.
 */
public enum TypeTour {
    /** Tour d'archer, attaque physique à distance */
    ARCHER,

    /** Tour de magie, attaque magique */
    MAGIE,

    /** Tour canon, attaque physique explosive */
    CANON,

    /** Tour forgeron, ne génère pas de dégâts, génère des ressources */
    FORGERON
}

