package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Interfaces.Affichage;
import Epi.BarCassonne.game.Interfaces.Damageable;
import Epi.BarCassonne.game.Interfaces.Movable;
import Epi.BarCassonne.game.Managers.AssetMana;
import com.badlogic.gdx.graphics.Texture;

public abstract class Mechant implements Movable, Affichage , Damageable {

    protected int PV;
    protected float Vitesse;
    protected float positionX;
    protected float positionY;
    protected Texture sprite;


    public Mechant(int PV, float Vitesse, float PositionX, float PositionY, Texture sprite) {
        this.PV = PV;
        this.Vitesse = Vitesse;
        this.positionX = PositionX;
        this.positionY = PositionY;
        this.sprite = sprite;
    }

    public int getPV(){
        return this.PV;
    }

    public float getPositionX(){
        return this.positionX;
    }


    public float getPositionY(){
        return this.positionY;
    }

    public void setPositionX(float positionX){
        this.positionX = positionX;
    }

    public void setPositionY(float positionY){
        this.positionY = positionY;
    }

    public void recevoirDegats(int degats) {
        if (!isEnVie()) {
            return;
        }
        this.PV -= degats;
        if (this.PV <= 0) {
            this.PV = 0;

        }
    }

    public void move(){

    }

    public boolean isEnVie(){
        if (this.PV > 0){
            return true;
        }
        return false;
    }

    public void update(){
        //A FAIRE


    }
}
