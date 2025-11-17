package Epi.BarCassonne.game.Entities.Towers;

public class TowerForgeron extends Tower {



    protected int ApportLingots;

    public TowerForgeron() {
        super(0f, 0f, 1, 4, 0, 0f, 1000);
        this.ApportLingots = 200;
    }   


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
