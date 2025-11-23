package Epi.BarCassonne.game.Managers;

/**
 * Gère l'état du jeu (ressources, vie, etc.).
 * Sépare la logique de jeu de l'affichage.
 */
public class GameState {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    /** Nombre de lingots (monnaie) du joueur. */
    private int lingots;
    
    /** Points de vie actuels du joueur. */
    private int vie;
    
    /** Points de vie maximum du joueur. */
    private int vieMax;
    
    /** Numéro de la vague actuelle. */
    private int numeroVague;

    private static GameState instance = null;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouvel état de jeu avec les valeurs initiales.
     * @param lingotsInitiaux Nombre de lingots au début du jeu
     * @param vieInitiale Vie initiale du joueur
     */
    private GameState(int lingotsInitiaux, int vieInitiale) {
        this.lingots = lingotsInitiaux;
        this.vie = vieInitiale;
        this.vieMax = vieInitiale;
        this.numeroVague = 0;
    }

    // ------------------------------------------------------------------------
    // REGION : GETTERS & SETTERS
    // ------------------------------------------------------------------------
    public int getLingots() {
        return lingots;
    }

    public void setLingots(int lingots) {
        this.lingots = Math.max(0, lingots);
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = Math.max(0, Math.min(vie, vieMax));
    }

    public int getVieMax() {
        return vieMax;
    }

    public int getNumeroVague() {
        return numeroVague;
    }

    public void setNumeroVague(int numeroVague) {
        this.numeroVague = numeroVague;
    }

    public boolean estEnVie() {
        return vie > 0;
    }

    /**
     * Retourne l'instance de GameState.
     * @return L'instance de GameState
     */
    public static GameState getInstance() {
        if (instance == null) {
            // Mode difficile : moins de lingots et moins de vie
            instance = new GameState(10000, 100);
        }
        return instance;
    }

    /**
     * Réinitialise l'instance de GameState.
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Ajoute un montant de lingots à l'état du jeu.
     * @param montant Le montant de lingots à ajouter
     */
    public void ajouterLingots(int montant) {
        this.lingots += montant;
    }

    /**
     * Retire un montant de lingots à l'état du jeu.
     * @param montant Le montant de lingots à retirer
     * @return true si le montant a été retiré, false sinon
     */
    public boolean retirerLingots(int montant) {
        if (lingots >= montant) {
            lingots -= montant;
            return true;
        }
        return false;
    }

    /**
     * Retire un montant de vie à l'état du jeu.
     * @param degats Le montant de vie à retirer
     */
    public void recevoirDegats(int degats) {
        this.vie = Math.max(0, vie - degats);
    }

}
