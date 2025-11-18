package Epi.BarCassonne.game.Utils;

import com.badlogic.gdx.graphics.Color;

/**
 * Représente un message 
 */
public class Message {
    public float x;
    public float y;
    public String texte;
    public Color couleur;
    public int taillePolice;
    public float tempsRestant;
    public float decalageX;
    
    public Message(float x, float y, String texte, Color couleur, int taillePolice, float duree) {
        this(x, y, texte, couleur, taillePolice, duree, 0f);
    }
    
    public Message(float x, float y, String texte, Color couleur, int taillePolice, float duree, float decalageX) {
        this.x = x;
        this.y = y;
        this.texte = texte;
        this.couleur = couleur;
        this.taillePolice = taillePolice;
        this.tempsRestant = duree;
        this.decalageX = decalageX;
    }
}

