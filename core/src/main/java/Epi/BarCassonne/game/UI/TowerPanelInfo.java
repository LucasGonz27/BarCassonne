package Epi.BarCassonne.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import Epi.BarCassonne.game.Config.TowerUpgradeConfig;
import Epi.BarCassonne.game.Entities.Towers.Tower;
import Epi.BarCassonne.game.Managers.TextureManager;

/**
 * Interface d'amélioration des tours.
 * Affiche un panneau avec les options d'amélioration et de suppression.
 *
 * @author Epi
 */
public class TowerPanelInfo {

    // ========================================================================
    // CONSTANTES
    // ========================================================================

    /** Largeur du bouton */
    private static final float LARGEUR_BOUTON = 200f;

    /** Hauteur du bouton */
    private static final float HAUTEUR_BOUTON = 60f;

    /** Espacement entre les boutons */
    private static final float ESPACEMENT_BOUTON = 20f;

    /** Décalage vertical du panneau par rapport à la tour */
    private static final float DECALAGE_Y = 150f;

    /** Largeur du panneau de fond */
    private static final float LARGEUR_PANNEAU = 250f;

    /** Hauteur du panneau de fond */
    private static final float HAUTEUR_PANNEAU = 200f;

    /** Taille de la police pour les boutons */
    private static final int TAILLE_POLICE = 24;

    /** Taille de la police pour les informations */
    private static final int TAILLE_POLICE_INFO = 20;

    // ========================================================================
    // CHAMPS
    // ========================================================================

    /** Tour actuellement sélectionnée */
    private Tower tourSelectionnee;

    /** Texture de fond du panneau */
    private Texture textureAmelioration;

    /** Police pour le texte */
    private BitmapFont font;

    /** Police pour les informations */
    private BitmapFont fontInfo;

    /** Layout pour calculer les dimensions du texte */
    private GlyphLayout layout;

    /** Position X du panneau */
    private float panneauX;

    /** Position Y du panneau */
    private float panneauY;

    /** Indique si le panneau est visible */
    private boolean visible;

    // ========================================================================
    // CONSTRUCTEUR
    // ========================================================================

    /**
     * Crée une nouvelle interface d'amélioration de tours.
     */
    public TowerPanelInfo() {
        this.visible = false;
        this.layout = new GlyphLayout();
        chargerRessources();
    }

    /**
     * Charge les ressources nécessaires (textures, polices).
     */
    private void chargerRessources() {
        // Charger la texture de fond
        textureAmelioration = TextureManager.chargerTexture("sprites/Amelioration.png");

        // Créer les polices
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = TAILLE_POLICE;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);

        parameter.size = TAILLE_POLICE_INFO;
        fontInfo = generator.generateFont(parameter);

        generator.dispose();
    }

    // ========================================================================
    // AFFICHAGE
    // ========================================================================

    /**
     * Affiche le panneau d'amélioration pour une tour.
     *
     * @param tour La tour sélectionnée
     */
    public void afficher(Tower tour) {
        this.tourSelectionnee = tour;
        this.visible = true;
        calculerPositionPanneau();
    }

    /**
     * Masque le panneau d'amélioration.
     */
    public void masquer() {
        this.visible = false;
        this.tourSelectionnee = null;
    }

    /**
     * Calcule la position du panneau en fonction de la tour sélectionnée.
     */
    private void calculerPositionPanneau() {
        if (tourSelectionnee != null) {
            panneauX = tourSelectionnee.getPositionX() - LARGEUR_PANNEAU / 2;
            panneauY = tourSelectionnee.getPositionY() + DECALAGE_Y;
        }
    }

    // ========================================================================
    // RENDU
    // ========================================================================

    /**
     * Dessine le panneau d'amélioration.
     *
     * @param batch SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {
        if (!visible || tourSelectionnee == null) {
            return;
        }

        // Dessiner le fond
        if (textureAmelioration != null) {
            batch.draw(textureAmelioration, panneauX, panneauY, LARGEUR_PANNEAU, HAUTEUR_PANNEAU);
        }

        // Dessiner les informations de la tour
        dessinerInformations(batch);

        // Dessiner les boutons
        dessinerBoutons(batch);
    }

    /**
     * Dessine les informations de la tour (niveau, type, coût).
     *
     * @param batch SpriteBatch pour le rendu
     */
    private void dessinerInformations(SpriteBatch batch) {
        String towerType = tourSelectionnee.getClass().getSimpleName();
        int niveau = tourSelectionnee.getLevel();

        // Afficher le type et le niveau
        String info = towerType + " - Niveau " + niveau;
        layout.setText(fontInfo, info);
        float infoX = panneauX + (LARGEUR_PANNEAU - layout.width) / 2;
        float infoY = panneauY + HAUTEUR_PANNEAU - 20f;
        fontInfo.draw(batch, info, infoX, infoY);

        // Afficher le coût d'amélioration si possible
        if (TowerUpgradeConfig.peutEtreAmelioree(niveau, tourSelectionnee.getMaxLevel())) {
            int coutAmelioration = TowerUpgradeConfig.getCoutAmelioration(towerType, niveau + 1);
            String coutTexte = "Coût: " + coutAmelioration + " lingots";
            layout.setText(fontInfo, coutTexte);
            float coutX = panneauX + (LARGEUR_PANNEAU - layout.width) / 2;
            float coutY = infoY - 30f;
            fontInfo.draw(batch, coutTexte, coutX, coutY);
        } else {
            String maxTexte = "Niveau MAX";
            layout.setText(fontInfo, maxTexte);
            float maxX = panneauX + (LARGEUR_PANNEAU - layout.width) / 2;
            float maxY = infoY - 30f;
            fontInfo.setColor(Color.GOLD);
            fontInfo.draw(batch, maxTexte, maxX, maxY);
            fontInfo.setColor(Color.WHITE);
        }
    }

    /**
     * Dessine les boutons Améliorer et Supprimer.
     *
     * @param batch SpriteBatch pour le rendu
     */
    private void dessinerBoutons(SpriteBatch batch) {
        float boutonY = panneauY + 40f;

        // Bouton Améliorer (si possible)
        if (TowerUpgradeConfig.peutEtreAmelioree(tourSelectionnee.getLevel(), tourSelectionnee.getMaxLevel())) {
            dessinerBouton(batch, "Améliorer", panneauX + 25f, boutonY, Color.GREEN);
        }

        // Bouton Supprimer
        dessinerBouton(batch, "Supprimer", panneauX + 25f, boutonY - HAUTEUR_BOUTON - ESPACEMENT_BOUTON, Color.RED);
    }

    /**
     * Dessine un bouton avec du texte.
     *
     * @param batch SpriteBatch pour le rendu
     * @param texte Texte du bouton
     * @param x Position X du bouton
     * @param y Position Y du bouton
     * @param couleur Couleur du texte
     */
    private void dessinerBouton(SpriteBatch batch, String texte, float x, float y, Color couleur) {
        font.setColor(couleur);
        layout.setText(font, texte);
        float textX = x + (LARGEUR_BOUTON - layout.width) / 2;
        float textY = y + HAUTEUR_BOUTON / 2 + layout.height / 2;
        font.draw(batch, texte, textX, textY);
        font.setColor(Color.WHITE);
    }

    // ========================================================================
    // DÉTECTION DES CLICS
    // ========================================================================

    /**
     * Vérifie si un clic a eu lieu sur le bouton Améliorer.
     *
     * @param screenX Position X du clic à l'écran
     * @param screenY Position Y du clic à l'écran
     * @return true si le bouton Améliorer a été cliqué
     */
    public boolean clicSurBoutonAmeliorer(float screenX, float screenY) {
        if (!visible || tourSelectionnee == null) {
            return false;
        }

        if (!TowerUpgradeConfig.peutEtreAmelioree(tourSelectionnee.getLevel(), tourSelectionnee.getMaxLevel())) {
            return false;
        }

        float boutonY = panneauY + 40f;
        return estDansZone(screenX, screenY, panneauX + 25f, boutonY, LARGEUR_BOUTON, HAUTEUR_BOUTON);
    }

    /**
     * Vérifie si un clic a eu lieu sur le bouton Supprimer.
     *
     * @param screenX Position X du clic à l'écran
     * @param screenY Position Y du clic à l'écran
     * @return true si le bouton Supprimer a été cliqué
     */
    public boolean clicSurBoutonSupprimer(float screenX, float screenY) {
        if (!visible || tourSelectionnee == null) {
            return false;
        }

        float boutonY = panneauY + 40f - HAUTEUR_BOUTON - ESPACEMENT_BOUTON;
        return estDansZone(screenX, screenY, panneauX + 25f, boutonY, LARGEUR_BOUTON, HAUTEUR_BOUTON);
    }

    /**
     * Vérifie si un clic a eu lieu sur le panneau.
     *
     * @param screenX Position X du clic à l'écran
     * @param screenY Position Y du clic à l'écran
     * @return true si le clic est sur le panneau
     */
    public boolean clicSurPanneau(float screenX, float screenY) {
        if (!visible) {
            return false;
        }
        return estDansZone(screenX, screenY, panneauX, panneauY, LARGEUR_PANNEAU, HAUTEUR_PANNEAU);
    }

    /**
     * Vérifie si un point est dans une zone rectangulaire.
     *
     * @param x Position X du point
     * @param y Position Y du point
     * @param zoneX Position X de la zone
     * @param zoneY Position Y de la zone
     * @param zoneLargeur Largeur de la zone
     * @param zoneHauteur Hauteur de la zone
     * @return true si le point est dans la zone
     */
    private boolean estDansZone(float x, float y, float zoneX, float zoneY, float zoneLargeur, float zoneHauteur) {
        return x >= zoneX && x <= zoneX + zoneLargeur && y >= zoneY && y <= zoneY + zoneHauteur;
    }

    // ========================================================================
    // GETTERS
    // ========================================================================

    /**
     * @return true si le panneau est visible
     */
    public boolean estVisible() {
        return visible;
    }

    /**
     * @return La tour actuellement sélectionnée
     */
    public Tower getTourSelectionnee() {
        return tourSelectionnee;
    }

    // ========================================================================
    // NETTOYAGE
    // ========================================================================

    /**
     * Libère les ressources utilisées.
     */
    public void dispose() {
        if (textureAmelioration != null) {
            TextureManager.libererTexture(textureAmelioration);
        }
        if (font != null) {
            font.dispose();
        }
        if (fontInfo != null) {
            fontInfo.dispose();
        }
    }
}
