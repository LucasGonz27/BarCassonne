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

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouvel état de jeu avec les valeurs initiales.
     * @param lingotsInitiaux Nombre de lingots au début du jeu
     * @param vieInitiale Vie initiale du joueur
     */
    public GameState(int lingotsInitiaux, int vieInitiale) {
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

    public void ajouterLingots(int montant) {
        this.lingots += montant;
    }

    public boolean retirerLingots(int montant) {
        if (lingots >= montant) {
            lingots -= montant;
            return true;
        }
        return false;
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = Math.max(0, Math.min(vie, vieMax));
    }

    public void recevoirDegats(int degats) {
        this.vie = Math.max(0, vie - degats);
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
}
