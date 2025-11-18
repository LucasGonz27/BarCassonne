package Epi.BarCassonne.game.Utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire générique pour les messages flottants.
 * Peut être utilisé pour afficher des messages de lingots, dégâts, etc.
 */
public class MessageFlottant {
    private static final float VITESSE_MESSAGE = 30f;
    private static final float DUREE_MESSAGE = 2f;
    
    private final List<Message> messages;
    
    public MessageFlottant() {
        this.messages = new ArrayList<>();
    }
    
    /**
     * Crée un nouveau message flottant à la position spécifiée.
     * @param x Position X en coordonnées monde
     * @param y Position Y en coordonnées monde
     * @param texte Le texte à afficher
     * @param couleur La couleur du texte
     * @param taillePolice La taille de la police
     */
    public void creerMessage(float x, float y, String texte, Color couleur, int taillePolice) {
        messages.add(new Message(x, y, texte, couleur, taillePolice));
    }
    
    /**
     * Crée un message de lingots (format "+X" en jaune).
     * @param x Position X en coordonnées monde
     * @param y Position Y en coordonnées monde
     * @param lingots Le nombre de lingots
     */
    public void creerMessageLingots(float x, float y, int lingots) {
        final int TAILLE_POLICE = 30;
        creerMessage(x, y, "+" + lingots, Color.YELLOW, TAILLE_POLICE);
    }
    
    /**
     * Met à jour tous les messages flottants.
     * @param delta Temps écoulé depuis la dernière frame
     */
    public void update(float delta) {
        messages.removeIf(msg -> {
            msg.update(delta);
            return msg.estExpire();
        });
    }
    
    /**
     * Dessine tous les messages flottants.
     * @param batch Le SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {
        final float DECALAGE_TEXTE_X = 20f;
        
        for (Message msg : messages) {
            float x = msg.x - DECALAGE_TEXTE_X;
            Texte.drawText(batch, msg.texte, x, msg.y, msg.couleur, msg.taillePolice);
        }
    }
    
    /**
     * Représente un message flottant individuel.
     */
    private static class Message {
        float x;
        float y;
        final String texte;
        final Color couleur;
        final int taillePolice;
        float tempsRestant;
        
        Message(float x, float y, String texte, Color couleur, int taillePolice) {
            this.x = x;
            this.y = y;
            this.texte = texte;
            this.couleur = couleur;
            this.taillePolice = taillePolice;
            this.tempsRestant = DUREE_MESSAGE;
        }
        
        void update(float delta) {
            y += VITESSE_MESSAGE * delta;
            tempsRestant -= delta;
        }
        
        boolean estExpire() {
            return tempsRestant <= 0;
        }
    }
}
