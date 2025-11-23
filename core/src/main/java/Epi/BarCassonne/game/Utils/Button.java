package Epi.BarCassonne.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Epi.BarCassonne.game.Managers.TextureManager;

/**
 * Classe utilitaire simple pour créer et gérer des boutons interactifs.
 * Gère la détection de clics et l'affichage avec texte centré.
 */
public class Button {
    
    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    /** Position X du bouton. */
    private float x;
    
    /** Position Y du bouton. */
    private float y;
    
    /** Largeur du bouton. */
    private float width;
    
    /** Hauteur du bouton. */
    private float height;
    
    /** Texte affiché sur le bouton. */
    private String texte;
    
    /** Texture du bouton. */
    private Texture texture;
    
    /** Couleur du texte. */
    private Color couleurTexte;
    
    /** Taille de la police du texte. */
    private int taillePolice;
    
    /** Action à exécuter lors du clic sur le bouton. */
    private Runnable action;
    
    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEURS
    // ------------------------------------------------------------------------
    /**
     * Crée un bouton avec une texture (skin PNG).
     * @param x Position X du bouton
     * @param y Position Y du bouton
     * @param width Largeur du bouton
     * @param height Hauteur du bouton
     * @param texte Texte à afficher sur le bouton
     * @param cheminTexture Chemin vers la texture (ex: "skins/bouton.png")
     * @param couleurTexte Couleur du texte
     * @param taillePolice Taille de la police
     */
    public Button(float x, float y, float width, float height, String texte, String cheminTexture, Color couleurTexte, int taillePolice) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texte = texte;
        this.texture = TextureManager.chargerTexture(cheminTexture);
        this.couleurTexte = couleurTexte;
        this.taillePolice = taillePolice;
    }

    // ------------------------------------------------------------------------
    // REGION : MÉTHODES PUBLIQUES
    // ------------------------------------------------------------------------
    /**
     * Met à jour le bouton et détecte les clics.
     * Doit être appelé à chaque frame.
     */
    public void update() {
        // Ne traiter que si un clic vient d'être effectué
        if (!Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
            return;
        }
        
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        
        // Vérifier précisément si le clic est dans la zone du bouton
        boolean estDansBouton = mouseX >= x && mouseX < x + width && 
                                mouseY >= y && mouseY < y + height;
        
        if (estDansBouton && action != null) {
            action.run();
        }
    }
    
    /**
     * Dessine le bouton.
     * @param batch Le SpriteBatch (déjà ouvert avec batch.begin())
     */
    public void render(SpriteBatch batch) {
        // Dessiner la texture
        if (texture != null) {
            batch.draw(texture, x, y, width, height);
        }
        
        // Dessiner le texte centré
        if (texte != null && !texte.isEmpty()) {
            // Calculer la vraie largeur du texte pour un centrage parfait
            BitmapFont font = Texte.getFont(taillePolice);
            GlyphLayout layout = new GlyphLayout();
            layout.setText(font, texte);
            
            // Centrer horizontalement
            float texteX = x + (width / 2) - (layout.width / 2);
            // Centrer verticalement
            float texteY = y + (height / 2) + (layout.height / 2);
            
            font.setColor(couleurTexte);
            font.draw(batch, texte, texteX, texteY);
        }
    }
    
    /**
     * Définit l'action à exécuter lors du clic.
     * @param action L'action à exécuter
     */
    public void setAction(Runnable action) {
        this.action = action;
    }
    
    /**
     * Met à jour la position du bouton.
     * @param x Nouvelle position X
     * @param y Nouvelle position Y
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Met à jour la taille du bouton.
     * @param width Nouvelle largeur
     * @param height Nouvelle hauteur
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    // ------------------------------------------------------------------------
    // REGION : NETTOYAGE
    // ------------------------------------------------------------------------
    /**
     * Libère les ressources.
     */
    public void dispose() {
        if (texture != null) {
            TextureManager.libererTexture(texture);
            texture = null;
        }
    }
}
