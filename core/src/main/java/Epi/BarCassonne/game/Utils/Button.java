package Epi.BarCassonne.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Classe utilitaire pour créer et gérer des boutons interactifs.
 * Gère la détection de clics et l'affichage avec texte centré.
 */
public class Button {
    
    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    private float x, y, width, height;
    private String texte;
    private Color couleurFond;
    private Color couleurTexte;
    private int taillePolice;
    private Rectangle rectangle;
    private Runnable action;
    
    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un bouton avec tous les paramètres.
     * @param x Position X du bouton
     * @param y Position Y du bouton
     * @param width Largeur du bouton
     * @param height Hauteur du bouton
     * @param texte Texte à afficher sur le bouton
     * @param couleurFond Couleur de fond du bouton
     * @param couleurTexte Couleur du texte
     * @param taillePolice Taille de la police du texte
     */
    public Button(float x, float y, float width, float height, String texte, Color couleurFond, Color couleurTexte, int taillePolice) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texte = texte;
        this.couleurFond = couleurFond;
        this.couleurTexte = couleurTexte;
        this.taillePolice = taillePolice;
        this.rectangle = new Rectangle(x, y, width, height);
    }
    
    // ------------------------------------------------------------------------
    // REGION : MÉTHODES PUBLIQUES
    // ------------------------------------------------------------------------
    /**
     * Met à jour le bouton et détecte les clics.
     * Doit être appelé à chaque frame.
     */
    public void update() {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        
        if (rectangle.contains(mouseX, mouseY) && Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.LEFT) && action != null) {
            action.run();
        }
    }
    
    /**
     * Dessine le bouton (rectangle et texte centré).
     * Note: Le SpriteBatch et ShapeRenderer doivent être configurés.
     * @param batch Le SpriteBatch pour le rendu (déjà ouvert avec batch.begin())
     * @param shapeRenderer Le ShapeRenderer pour dessiner le rectangle (déjà ouvert avec shapeRenderer.begin())
     */
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(couleurFond);
        shapeRenderer.rect(x, y, width, height);
        
        if (texte != null && !texte.isEmpty()) {
            float texteX = x + (width / 2) - (texte.length() * taillePolice * 0.3f);
            float texteY = y + (height / 2) + (taillePolice / 3);
            Texte.drawText(batch, texte, texteX, texteY, couleurTexte, taillePolice);
        }
    }
    
    /**
     * Définit l'action à exécuter lors du clic sur le bouton.
     * @param action L'action à exécuter (Runnable)
     */
    public void setAction(Runnable action) {
        this.action = action;
    }
}
