package Epi.BarCassonne.game.Entities.Towers;

public class TowerForgeron extends Tower {



    protected int ApportLingots;

    public TowerForgeron(float positionX, float positionY, int ApportLingots) {
        super(positionX, positionY, 1, 4, ApportLingots, 0f, 1000);
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
