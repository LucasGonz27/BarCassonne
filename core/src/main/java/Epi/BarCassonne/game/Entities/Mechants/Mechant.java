package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Interfaces.Affichage;
import Epi.BarCassonne.game.Interfaces.Damageable;
import Epi.BarCassonne.game.Interfaces.Movable;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

/**
 * Classe abstraite représentant un ennemi.
 * Gère les PV, le mouvement, le chemin et l'animation.
 */
public abstract class Mechant implements Movable, Affichage, Damageable {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    protected int PV;                                    // Points de vie
    protected float Vitesse;                             // Vitesse de déplacement
    protected float positionX;                           // Position X
    protected float positionY;                           // Position Y
    protected List<Vector2> chemin;                      // Chemin à suivre
    protected int indexActuel = 0;                       // Point actuel du chemin
    protected Animation<TextureRegion> animation;        // Animation
    protected float stateTime = 0f;                      // Temps pour l'animation  

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouvel ennemi.
     * La position sera définie lors du spawn par VagueMana.
     * @param PV Points de vie
     * @param Vitesse Vitesse de déplacement
     * @param animation Animation de l'ennemi
     */
    public Mechant(int PV, float Vitesse, Animation<TextureRegion> animation) {
        this.PV = PV;
        this.Vitesse = Vitesse;
        this.positionX = 0;
        this.positionY = 0;
        this.animation = animation;
    }

    // ------------------------------------------------------------------------
    // REGION : GETTERS & SETTERS
    // ------------------------------------------------------------------------
    public int getPV() {
        return PV;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    /**
     * Définit le chemin que l'ennemi doit suivre.
     * @param chemin Liste des points du chemin
     */
    public void setChemin(List<Vector2> chemin) {
        this.chemin = chemin;
        this.indexActuel = 0;
    }

    /**
     * Définit l'index du point actuel du chemin.
     * @param index Index du point
     */
    public void setIndexActuel(int index) {
        this.indexActuel = index;
    }

    /**
     * Retourne les dégâts infligés au joueur quand cet ennemi atteint la fin du chemin.
     * @return Les dégâts infligés
     */
    public int getDegatsFinChemin() {
        switch (this.getClass().getSimpleName()) {
            case "PaysanGoblin":
                return 1;
            case "GuerrierGoblin":
                return 2;
            case "GoblinGuerrisseur":
                return 3;
            case "GoblinBomb":
                return 4;
            case "Cochon":
                return 5;
            case "Chevalier":
                return 8;
            case "BossChevalier":
                return 10;
            case "Golem":
                return 12;
            case "RoiGoblin":
                return 20;
            default:
                return 1;
        }
    }
    
    /**
     * Vérifie si l'ennemi a atteint la fin du chemin.
     * @return true si l'ennemi a atteint la fin du chemin
     */
    public boolean aAtteintFinChemin() {
        return chemin != null && !chemin.isEmpty() && indexActuel >= chemin.size();
    }

    // ------------------------------------------------------------------------
    // REGION : DOMMAGES
    // ------------------------------------------------------------------------
    /**
     * Applique des dégâts à l'ennemi.
     * @param degats Montant des dégâts
     */
    @Override
    public void recevoirDegats(int degats) {
        if (!isEnVie()) return;
        this.PV -= degats;
        if (this.PV <= 0) this.PV = 0;
    }

    /**
     * Vérifie si l'ennemi est en vie.
     * @return true si PV > 0
     */
    public boolean isEnVie() {
        return this.PV > 0;
    }

    // ------------------------------------------------------------------------
    // REGION : MOUVEMENT
    // ------------------------------------------------------------------------
    /**
     * Déplace l'ennemi vers le prochain point du chemin.
     * @param deltaTime Temps écoulé depuis la dernière frame
     */
    @Override
    public void move(float deltaTime) {
        if (chemin == null || chemin.isEmpty() || indexActuel >= chemin.size()) {
            return;
        }

        Vector2 target = chemin.get(indexActuel);
        float dx = target.x - positionX;
        float dy = target.y - positionY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Si on est déjà très proche du point, passer au suivant
        if (distance < 1f) {
            indexActuel++;
            return;
        }

        // Calculer le déplacement
        float moveX = (dx / distance) * Vitesse * deltaTime;
        float moveY = (dy / distance) * Vitesse * deltaTime;

        // Éviter de dépasser la cible
        if (Math.abs(moveX) > Math.abs(dx)) moveX = dx;
        if (Math.abs(moveY) > Math.abs(dy)) moveY = dy;

        // Appliquer le déplacement
        positionX += moveX;
        positionY += moveY;
    }

    // ------------------------------------------------------------------------
    // REGION : MISE À JOUR
    // ------------------------------------------------------------------------
    /**
     * Met à jour l'ennemi : mouvement et animation.
     * @param deltaTime Temps écoulé depuis la dernière frame
     */
    public void update(float deltaTime) {
        if (!isEnVie()) return;
        
        if (chemin == null || chemin.isEmpty()) return;

        move(deltaTime);

        stateTime += deltaTime; // Mise à jour de l'animation
    }

    /**
     * Marque l'ennemi comme mort (appelé quand il atteint la fin du chemin).
     */
    public void mourir() {
        this.PV = 0;
    }

    // ------------------------------------------------------------------------
    // REGION : ANIMATION
    // ------------------------------------------------------------------------
    /**
     * Retourne la frame actuelle de l'animation.
     * @return La TextureRegion de la frame actuelle
     */
    public TextureRegion getFrame() {
        if (animation == null) return null;
        return animation.getKeyFrame(stateTime, true);
    }
}
