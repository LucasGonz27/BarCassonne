package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Interfaces.Affichage;
import Epi.BarCassonne.game.Interfaces.Damageable;
import Epi.BarCassonne.game.Interfaces.Movable;
import Epi.BarCassonne.game.Managers.AssetMana;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

public abstract class Mechant implements Movable, Affichage , Damageable {

    protected int PV;
    protected float Vitesse;
    protected float positionX;
    protected float positionY;
    protected Texture sprite;
    protected List<Vector2> chemin;
    protected int indexActuel = 0;
    protected float widht;
    protected float height;


    public Mechant(int PV, float Vitesse, float PositionX, float PositionY, Texture sprite, float widht, float height) {
        this.PV = PV;
        this.Vitesse = Vitesse;
        this.positionX = PositionX;
        this.positionY = PositionY;
        this.sprite = sprite;
        this.widht = widht;
        this.height = height;
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

    public float getWidht(){
        return this.widht;
    }

    public float getHeight(){
        return this.height;
    }

    public void setPositionX(float positionX){
        this.positionX = positionX;
    }

    public void setPositionY(float positionY){
        this.positionY = positionY;
    }

    public void setChemin(List<Vector2> chemin){
        this.chemin = chemin;
        this.indexActuel = 0;
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

    public void move(float deltaTime) {

        // on verifie si un chmin existe deja et on verifie si il reste des points
        if (chemin == null || indexActuel >= chemin.size()) {
            return;
        }

        // on récupere le point cible actuel
        Vector2 target = chemin.get(indexActuel);
        float targetX = target.x;
        float targetY = target.y;

        //on calcule la direction vers le point cible
        float dx = targetX - positionX;
        float dy = targetY - positionY;
        float distance = (float) Math.sqrt(dx*dx + dy*dy);

        // on avance vers le point cible si pas encore arrivé
        if (distance > 0) {
            float moveX = (dx / distance) * Vitesse * deltaTime;
            float moveY = (dy / distance) * Vitesse * deltaTime;

            // pour éviter de dépasser le point cible
            if (Math.abs(moveX) > Math.abs(dx)) moveX = dx;
            if (Math.abs(moveY) > Math.abs(dy)) moveY = dy;

            positionX += moveX;
            positionY += moveY;
        }

        // on passe au point suivant si le point actuel est atteint
        if (distance < 1f) { // Tolérance pour éviter de “sauter” le point merci karim
            indexActuel++;
        }
    }


    public boolean isEnVie(){
        if (this.PV > 0){
            return true;
        }
        return false;
    }

    public void update(float deltaTime) {
        if (!isEnVie()) return;
        move(deltaTime);
        if (indexActuel >= chemin.size()) {
            atteindreFinChemin();
        }
    }

    protected void atteindreFinChemin() {
        this.PV = 0;
    }


}
