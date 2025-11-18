package Epi.BarCassonne.game.Entities.Towers;

/**
 * Classe représentant une tour forgeron.
 * Tour économique qui génère des lingots au lieu d'attaquer.
 */
public class TowerForgeron extends Tower {

    protected static final int PRIX = 1000;
    protected static final float PORTEE = 0f;
    protected static final int APPORT_LINGOTS = 200;
    
    /** Nombre de lingots générés par cette tour. */
    protected int ApportLingots;

    /**
     * Constructeur par défaut.
     * Initialise la tour avec niveau 1
     */
    public TowerForgeron() {
        super(0f, 0f, 1, 4, 0, PORTEE, PRIX);
        this.ApportLingots = APPORT_LINGOTS;
    }   

    /**
     * Retourne le nombre de lingots générés par cette tour.
     * @return Le nombre de lingots générés
     */
    public int getApportLingots() {
        return ApportLingots;
    }
    
    /**
     * Améliore la tour en augmentant son niveau et son apport en lingots.
     * Ajoute 100 lingots supplémentaires par niveau.
     * L'amélioration n'est possible que si le niveau actuel est inférieur au niveau maximum.
     */
    public void upgrade() {
        if (this.level < this.maxLevel) {
            this.level++;
            this.ApportLingots += 100;
            System.out.println("La tour a été améliorée");
        }
        else {
            System.out.println("Argent Manquant");
        }
    }
}
