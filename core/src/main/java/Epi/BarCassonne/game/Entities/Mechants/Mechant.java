package Epi.BarCassonne.game.Entities.Mechants;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
import Epi.BarCassonne.game.Interfaces.Damageable;
import Epi.BarCassonne.game.Interfaces.Movable;
import Epi.BarCassonne.game.Managers.SoundManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Epi.BarCassonne.game.Utils.MessageFlottant;
import com.badlogic.gdx.graphics.Color;

/**
 * Classe abstraite représentant un ennemi.
 * Gère les PV, le mouvement, le chemin et l'animation.
 */
public abstract class Mechant implements Movable, Damageable {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
   
    /** Points de vie de l'ennemi. */
    protected int PV;
    
    /** Vitesse de déplacement de l'ennemi. */
    protected float Vitesse;
    
    /** Position X de l'ennemi sur la carte. */
    protected float positionX;
    
    /** Position Y de l'ennemi sur la carte. */
    protected float positionY;
    
    /** Chemin que l'ennemi doit suivre (liste de points). */
    protected List<Vector2> chemin;
    
    /** Index du point actuel dans le chemin. */
    protected int indexActuel = 0;
    
    /** Animation de l'ennemi. */
    protected Animation<TextureRegion> animation;
    
    /** Temps écoulé pour l'animation. */
    protected float stateTime = 0f;
    
    /** 
     * Map des résistances par type de tour.
     * La valeur représente le pourcentage de réduction des dégâts.
     * Donc par exemple 0.3 = 30% de résistance, donc 70% de dégâts.
     * Ou -0.2 = 20% de vulnérabilité, donc 120% de dégâts.
     */
    protected Map<TypeTour, Float> resistances;
    
    /** Message flottant pour afficher les dégâts */
    protected MessageFlottant messageFlottant;

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
        this.resistances = new HashMap<>();
        this.messageFlottant = new MessageFlottant();
        initialiserResistances();
    }
    
    /**
     * Initialise les résistances du méchant.
     * Par défaut, 0% de résistance pour chaque type de tour.
     */
    protected void initialiserResistances() {
        setResistance(TypeTour.ARCHER, 0.0f);
        setResistance(TypeTour.MAGIE, 0.0f);
        setResistance(TypeTour.CANON, 0.0f);
    }

    // ------------------------------------------------------------------------
    // REGION : GETTERS & SETTERS
    // ------------------------------------------------------------------------
    /**
     * Retourne les points de vie de l'ennemi.
     * @return Les points de vie actuels
     */
    public int getPV() {
        return PV;
    }

    /**
     * Retourne la position X de l'ennemi.
     * @return La position X en coordonnées monde
     */
    public float getPositionX() {
        return positionX;
    }

    /**
     * Retourne la position Y de l'ennemi.
     * @return La position Y en coordonnées monde
     */
    public float getPositionY() {
        return positionY;
    }

    /**
     * Définit la position X de l'ennemi.
     * @param positionX La nouvelle position X en coordonnées monde
     */
    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    /**
     * Définit la position Y de l'ennemi.
     * @param positionY La nouvelle position Y en coordonnées monde
     */
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
                return 2;
            case "GuerrierGoblin":
                return 4;
            case "GoblinGuerrisseur":
                return 6;
            case "GoblinBomb":
                return 8;
            case "Cochon":
                return 10;
            case "Chevalier":
                return 16;
            case "BossChevalier":
                return 20;
            case "Golem":
                return 24;
            case "RoiGoblin":
                return 40;
            default:
                return 2;
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
     * Applique des dégâts à l'ennemi en tenant compte des résistances.
     * @param degats Montant des dégâts bruts
     * @param typeTour Type de tour qui inflige les dégâts
     */
    public void recevoirDegats(int degats, TypeTour typeTour) {
        if (!isEnVie()) return;
        
        // Calculer les dégâts réels en tenant compte de la résistance
        float resistance = getResistance(typeTour);
        int degatsReels = (int) (degats * (1.0f - resistance));
        
        
        this.PV -= degatsReels;
        if (this.PV <= 0) {
            this.PV = 0;
        }
        
        // Si l'ennemi vient de mourir, jouer le son de mort
        if (!isEnVie()) {
            System.out.println("Ennemi mort ! Jouer son de mort. PV: " + this.PV);
            SoundManager.jouerSonMortEnnemi(0.4f);
        }
        
        // Afficher un message flottant avec les dégâts réels
        messageFlottant.creerMessage(positionX, positionY + 60f, String.valueOf(degatsReels), Color.RED, 25, 0.5f, -60f);
    }
    
    /**
     * Applique des dégâts à l'ennemi (méthode de l'interface Damageable).
     * Utilisée pour la compatibilité avec l'interface, applique les dégâts sans résistance.
     * @param degats Montant des dégâts
     */
    @Override
    public void recevoirDegats(int degats) {
        if (!isEnVie()) return;
        
        
        this.PV -= degats;
        if (this.PV <= 0) {
            this.PV = 0;
        }
        
        // Si l'ennemi vient de mourir, jouer le son de mort
        if (!isEnVie()) {
            System.out.println("Ennemi mort ! Jouer son de mort. PV: " + this.PV);
            SoundManager.jouerSonMortEnnemi(0.4f);
        }
        
        messageFlottant.creerMessage(positionX, positionY + 60f, String.valueOf(degats), Color.RED, 25, 0.5f, -60f);
    }
    
    /**
     * Retourne le pourcentage de résistance pour un type de tour donné.
     * @param typeTour Le type de tour
     * @return Le pourcentage de résistance 
     */
    public float getResistance(TypeTour typeTour) {
        return resistances.getOrDefault(typeTour, 0.0f);
    }
    
    /**
     * Définit la résistance pour un type de tour donné.
     * @param typeTour Le type de tour
     * @param resistance Le pourcentage de résistance 
     *                   (négatif = vulnérabilité, 0.0 = pas de résistance, 1.0 = immunité totale)
     *                   Exemple : -0.25 = vulnérable (+25% de dégâts), 0.5 = 50% de résistance
     */
    protected void setResistance(TypeTour typeTour, float resistance) {
        // Limiter la résistance entre -1.0 (double vulnérabilité) et 1.0 (immunité totale)
        if (resistance < -1.0f) resistance = -1.0f;
        if (resistance > 1.0f) resistance = 1.0f;
        resistances.put(typeTour, resistance);
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

        messageFlottant.update(deltaTime);
        
        if (!isEnVie()) return;
        
        if (chemin == null || chemin.isEmpty()) return;

        move(deltaTime);

        stateTime += deltaTime;
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
    
    /**
     * Retourne le gestionnaire de messages flottants de l'ennemi.
     * @return Le MessageFlottant de l'ennemi
     */
    public MessageFlottant getMessageFlottant() {
        return messageFlottant;
    }

    /**
     * Retourne la position X du centre de la hitbox du méchant.
     * @return La position X du centre
     */
    public float getCentreX() {
        if (animation == null || getFrame() == null) {
            return positionX;
        }
        return positionX + getFrame().getRegionWidth() / 2f;
    }

    /**
     * Retourne la position Y du centre de la hitbox du méchant.
     * @return La position Y du centre
     */
    public float getCentreY() {
        if (animation == null || getFrame() == null) {
            return positionY;
        }
        return positionY + getFrame().getRegionHeight() / 2f;
    }
}
