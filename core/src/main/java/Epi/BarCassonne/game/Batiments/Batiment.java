package Epi.BarCassonne.game.Batiments;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;

public abstract class Batiment {
    protected float positionX;
    protected float positionY;
    protected int level;
    protected int maxLevel;
    protected float damage;
    protected float portee;

    //region [Constructeur]
    public Batiment(float positionX, float positionY, int level, int maxLevel, float damage, float portee) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.level = level;
        this.maxLevel = maxLevel;
        this.damage = damage;
        this.portee = portee;
    }
    // endregion

    // region [Get, Set]
    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getPortee() {
        return portee;
    }

    public void setPortee(float portee) {
        this.portee = portee;
    }
    // endregion

    // region [Methods]
    public void attack(Mechant target) {
        if (target == null) return;

        float distance = (float) Math.sqrt(Math.pow(target.getPositionX() - positionX, 2) + Math.pow(target.getPositionY() - positionY, 2));

        if (distance <= portee) {
            target.recevoirDegats((int) this.damage);
            System.out.println(distance + " " + target.getPositionX() + " " + target.getPositionY());
        }
        else {
            System.out.println("Cible hors de portée");
        }
    }

    public void upgrade() {
        if (this.level < this.maxLevel) {
            this.level++;
            this.damage = 1.5f;
            this.portee = 1.5f;
            System.out.println("La tour a été améliorée");
        }
        else {
            System.out.println("Argent Manquant");
        }

    }

    public void display() {
    }

    public void update() {
    }
    // endregion
}
