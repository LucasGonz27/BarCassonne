package Epi.BarCassonne.game.Entities.Projectiles;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classe représentant un projectile tiré par une tour.
 * Le projectile se déplace en ligne droite.
 */
public class Projectile {

    // ------------------------------------------------------------------------
    // CONSTANTES
    // ------------------------------------------------------------------------

    /** Vitesse par défaut du projectile */
    private static final float VITESSE_DEFAUT = 400f;

    /** Distance seuil pour considérer que le projectile a touché la cible */
    private static final float RAYON_COLLISION = 25f;

    /** Taille d'affichage du projectile */
    private static final float TAILLE_RENDU = 32f;

    // ------------------------------------------------------------------------
    // CHAMPS
    // ------------------------------------------------------------------------

    /** Position actuelle du projectile */
    private float x;
    private float y;

    /** Vitesse du projectile */
    private final float vitesse;

    /** Direction normalisée du projectile */
    private final float directionX;
    private final float directionY;

    /** Angle de rotation pour l'affichage */
    private final float angle;

    /** Dégâts infligés */
    private final int degats;

    /** Type de tour (pour les résistances) */
    private final TypeTour typeTour;

    /** Cible du projectile */
    private final Mechant cible;

    /** Texture du projectile */
    private final Texture texture;

    /** Indique si le projectile a touché sa cible */
    private boolean aTouche;

    // ------------------------------------------------------------------------
    // CONSTRUCTEURS
    // ------------------------------------------------------------------------

    /**
     * Crée un nouveau projectile.
     * @param startX Position X de départ
     * @param startY Position Y de départ
     * @param cible Cible à atteindre
     * @param degats Dégâts à infliger
     * @param typeTour Type de tour qui a tiré
     * @param texture Texture du projectile
     * @param vitesse Vitesse du projectile
     */
    public Projectile(float startX, float startY, Mechant cible, int degats,
                      TypeTour typeTour, Texture texture, float vitesse) {
        this.x = startX;
        this.y = startY;
        this.cible = cible;
        this.degats = degats;
        this.typeTour = typeTour;
        this.texture = texture;
        this.vitesse = vitesse;
        this.aTouche = false;

        // Calculer la direction vers le CENTRE de la cible (une seule fois au départ)
        float dx = cible.getCentreX() - startX;
        float dy = cible.getCentreY() - startY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Normaliser la direction
        if (distance > 0) {
            this.directionX = dx / distance;
            this.directionY = dy / distance;
        } else {
            this.directionX = 0;
            this.directionY = 0;
        }

        // Calculer l'angle de rotation pour l'affichage
        this.angle = (float) Math.toDegrees(Math.atan2(dy, dx));
    }

    /**
     * Crée un nouveau projectile avec vitesse par défaut.
     */
    public Projectile(float startX, float startY, Mechant cible, int degats,
                      TypeTour typeTour, Texture texture) {
        this(startX, startY, cible, degats, typeTour, texture, VITESSE_DEFAUT);
    }

    // ------------------------------------------------------------------------
    // MISE À JOUR
    // ------------------------------------------------------------------------

    /**
     * Met à jour la position du projectile.
     * @param delta Temps écoulé depuis la dernière frame
     */
    public void update(float delta) {
        if (aTouche || cible == null || !cible.isEnVie()) {
            aTouche = true;
            return;
        }

        // Déplacer le projectile en ligne droite
        x += directionX * vitesse * delta;
        y += directionY * vitesse * delta;

        // Vérifier si le projectile est proche du CENTRE de la cible
        float dx = cible.getCentreX() - x;
        float dy = cible.getCentreY() - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance <= RAYON_COLLISION) {
            aTouche = true;
            cible.recevoirDegats(degats, typeTour);
        }
    }

    // ------------------------------------------------------------------------
    // RENDU
    // ------------------------------------------------------------------------

    /**
     * Dessine le projectile.
     * @param batch SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {
        if (texture != null && !aTouche) {
            batch.draw(texture,
                x - TAILLE_RENDU / 2,
                y - TAILLE_RENDU / 2,
                TAILLE_RENDU / 2,
                TAILLE_RENDU / 2,
                TAILLE_RENDU,
                TAILLE_RENDU,
                1,
                1,
                angle,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false);
        }
    }

    // ------------------------------------------------------------------------
    // GETTERS
    // ------------------------------------------------------------------------

    /**
     * @return true si le projectile doit être supprimé
     */
    public boolean doitEtreSupprime() {
        return aTouche || (cible != null && !cible.isEnVie());
    }
}
