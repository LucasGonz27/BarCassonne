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
    private static final float DECALAGE_TEXTE_X = 20f;

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
     * @param duree La durée d'affichage du message
     */
    public void creerMessage(float x, float y, String texte, Color couleur, int taillePolice, float duree) {
        creerMessage(x, y, texte, couleur, taillePolice, duree, DECALAGE_TEXTE_X);
    }
    
    /**
     * Crée un nouveau message flottant à la position spécifiée avec un décalage personnalisé.
     * @param x Position X en coordonnées monde
     * @param y Position Y en coordonnées monde
     * @param texte Le texte à afficher
     * @param couleur La couleur du texte
     * @param taillePolice La taille de la police
     * @param duree La durée d'affichage du message
     * @param decalageX Le décalage horizontal (0 pour aucun décalage)
     */
    public void creerMessage(float x, float y, String texte, Color couleur, int taillePolice, float duree, float decalageX) {
        messages.add(new Message(x, y, texte, couleur, taillePolice, duree, decalageX));
    }
    
    /**
     * Met à jour tous les messages flottants.
     * @param delta Temps écoulé depuis la dernière frame
     */
    public void update(float delta) {
        for (Message msg : messages) {
            msg.y += VITESSE_MESSAGE * delta;
            msg.tempsRestant -= delta;
        }
        messages.removeIf(msg -> msg.tempsRestant <= 0);
    }
    
    /**
     * Dessine tous les messages flottants.
     * @param batch Le SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {
        for (Message msg : messages) {
            float x = msg.x - msg.decalageX;
            Texte.drawText(batch, msg.texte, x, msg.y, msg.couleur, msg.taillePolice);
            
        }
    }
}
