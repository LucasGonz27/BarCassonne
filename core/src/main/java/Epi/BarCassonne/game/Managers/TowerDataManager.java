package Epi.BarCassonne.game.Managers;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.graphics.Texture;
import Epi.BarCassonne.game.Entities.Towers.TowerArcher;
import Epi.BarCassonne.game.Entities.Towers.TowerMagie;
import Epi.BarCassonne.game.Entities.Towers.TowerForgeron;
import Epi.BarCassonne.game.Entities.Towers.TowerCanon;
import Epi.BarCassonne.game.Entities.Projectiles.Fleche;
import Epi.BarCassonne.game.Entities.Projectiles.Bullet;
import Epi.BarCassonne.game.Entities.Projectiles.Sort;

/**
 * Gestionnaire des données statiques des tours (textures, prix, portée).
 * Centralise l'accès aux informations des différents types de tours.
 */
public class TowerDataManager {

    private final Map<String, Texture> textures;
    private final Map<String, Texture> texturesProjectiles;
    private final Map<String, Integer> prix;
    private final Map<String, Float> portee;
    private final Map<String, Integer> degats;
    private final Map<String, Float> vitesse;

    // Instances pour récupérer les valeurs depuis les classes
    private final TowerArcher towerArcher;
    private final TowerMagie towerMagie;
    private final TowerForgeron towerForgeron;
    private final TowerCanon towerCanon;
    private final Fleche fleche;
    private final Bullet bullet;
    private final Sort sort;

    /**
     * Crée un nouveau gestionnaire de données des tours.
     * Initialise toutes les maps et charge les données depuis les classes.
     */
    public TowerDataManager() {
        this.textures = new HashMap<>();
        this.texturesProjectiles = new HashMap<>();
        this.prix = new HashMap<>();
        this.portee = new HashMap<>();
        this.degats = new HashMap<>();
        this.vitesse = new HashMap<>();

        // Créer les instances pour récupérer les valeurs
        this.towerArcher = new TowerArcher();
        this.towerMagie = new TowerMagie();
        this.towerForgeron = new TowerForgeron();
        this.towerCanon = new TowerCanon();
        this.fleche = new Fleche();
        this.bullet = new Bullet();
        this.sort = new Sort();
        initialiserDonnees();
    }

    /**
     * Initialise toutes les données des tours.
     * Appelle toutes les méthodes d'initialisation.
     */
    private void initialiserDonnees() {
        initialiserTextures();
        initialiserTexturesProjectiles();
        initialiserPrix();
        initialiserPortee();
        initialiserDegats();
        initialiserVitesse();
    }

    /**
     * Initialise les textures des tours.
     * Charge les textures depuis les fichiers d'assets.
     */
    private void initialiserTextures() {

        // Textures Level 1
        textures.put("TowerArcher_1", TextureManager.chargerTexture("sprites/TourArcherLevel1.png"));
        textures.put("TowerMagie_1", TextureManager.chargerTexture("sprites/TourMagieLevel1.png"));
        textures.put("TowerCanon_1", TextureManager.chargerTexture("sprites/CanonLevel1.png"));
        textures.put("TowerForgeron_1", TextureManager.chargerTexture("sprites/ForgeronLevel1.png"));

        // Textures Level 2 
        textures.put("TowerArcher_2", TextureManager.chargerTexture("sprites/TourArcherLevel2.png"));
        textures.put("TowerMagie_2", TextureManager.chargerTexture("sprites/TourMagieLevel2.png"));
        textures.put("TowerCanon_2", TextureManager.chargerTexture("sprites/CanonLevel2.png"));
        textures.put("TowerForgeron_2", TextureManager.chargerTexture("sprites/ForgeronLevel2.png"));

        // Textures Level 3
        textures.put("TowerArcher_3", TextureManager.chargerTexture("sprites/TourArcherLevel3.png"));
        textures.put("TowerMagie_3", TextureManager.chargerTexture("sprites/TourMagieLevel3.png"));
        textures.put("TowerCanon_3", TextureManager.chargerTexture("sprites/CanonLevel3.png"));
        textures.put("TowerForgeron_3", TextureManager.chargerTexture("sprites/ForgeronLevel3.png"));

        // Textures Level 4
        textures.put("TowerArcher_4", TextureManager.chargerTexture("sprites/TourArcherLevel4.png"));
        textures.put("TowerMagie_4", TextureManager.chargerTexture("sprites/TourMagieLevel4.png"));
        textures.put("TowerCanon_4", TextureManager.chargerTexture("sprites/CanonLevel4.png"));
        textures.put("TowerForgeron_4", TextureManager.chargerTexture("sprites/ForgeronLevel4.png"));

        //on garder les clés sans niveau pour le mode placement
        textures.put("TowerArcher", TextureManager.chargerTexture("sprites/TourArcherLevel1.png"));
        textures.put("TowerMagie", TextureManager.chargerTexture("sprites/TourMagieLevel1.png"));
        textures.put("TowerCanon", TextureManager.chargerTexture("sprites/CanonLevel1.png"));
        textures.put("TowerForgeron", TextureManager.chargerTexture("sprites/ForgeronLevel1.png"));
    }

    /**
     * Initialise les textures des projectiles.
     * Charge les textures des projectiles depuis les fichiers d'assets.
     */
    private void initialiserTexturesProjectiles() {
        // Textures Level 1
        texturesProjectiles.put("TowerArcher_1", TextureManager.chargerTexture("sprites/flecheLevel1.png"));
        texturesProjectiles.put("TowerCanon_1", TextureManager.chargerTexture("sprites/bulletLevel1.png"));
        texturesProjectiles.put("TowerMagie_1", TextureManager.chargerTexture("sprites/SortLevel1.png"));

        // Textures Level 2 (utiliser Level1 pour l'instant, à remplacer quand les sprites seront disponibles)
        texturesProjectiles.put("TowerArcher_2", TextureManager.chargerTexture("sprites/flecheLevel1.png"));
        texturesProjectiles.put("TowerCanon_2", TextureManager.chargerTexture("sprites/bulletLevel2.png"));
        texturesProjectiles.put("TowerMagie_2", TextureManager.chargerTexture("sprites/SortLevel1.png"));

        // Textures Level 3 (utiliser Level1 pour l'instant, à remplacer quand les sprites seront disponibles)
        texturesProjectiles.put("TowerArcher_3", TextureManager.chargerTexture("sprites/flecheLevel1.png"));
        texturesProjectiles.put("TowerCanon_3", TextureManager.chargerTexture("sprites/bulletLevel3.png"));
        texturesProjectiles.put("TowerMagie_3", TextureManager.chargerTexture("sprites/SortLevel3.png"));

        // Textures Level 4 (utiliser Level1 pour l'instant, à remplacer quand les sprites seront disponibles)
        texturesProjectiles.put("TowerArcher_4", TextureManager.chargerTexture("sprites/flecheLevel1.png"));
        texturesProjectiles.put("TowerCanon_4", TextureManager.chargerTexture("sprites/bulletLevel4.png"));
        texturesProjectiles.put("TowerMagie_4", TextureManager.chargerTexture("sprites/SortLevel4.png"));

        // On garde les clés sans niveau pour compatibilité (utiliser Level1 par défaut)
        texturesProjectiles.put("TowerArcher", TextureManager.chargerTexture("sprites/flecheLevel1.png"));
        texturesProjectiles.put("TowerCanon", TextureManager.chargerTexture("sprites/bulletLevel1.png"));
        texturesProjectiles.put("TowerMagie", TextureManager.chargerTexture("sprites/SortLevel1.png"));
    }

    /**
     * Initialise les prix des tours.
     * Récupère les prix depuis les instances des tours.
     */
    private void initialiserPrix() {
        prix.put("TowerArcher", towerArcher.getPrix());
        prix.put("TowerMagie", towerMagie.getPrix());
        prix.put("TowerCanon", towerCanon.getPrix());
        prix.put("TowerForgeron", towerForgeron.getPrix());
    }

    /**
     * Initialise les portées des tours.
     * Récupère les portées depuis les instances des tours.
     */
    private void initialiserPortee() {
        portee.put("TowerArcher", towerArcher.getPortee());
        portee.put("TowerMagie", towerMagie.getPortee());
        portee.put("TowerCanon", towerCanon.getPortee());
        portee.put("TowerForgeron", towerForgeron.getPortee());
    }

    /**
     * Initialise les dégâts des projectiles.
     * Récupère les dégâts depuis les instances des projectiles.
     */
    private void initialiserDegats() {
        degats.put("TowerArcher", fleche.getDegats());
        degats.put("TowerCanon", bullet.getDegats());
        degats.put("TowerMagie", sort.getDegats());
        degats.put("TowerForgeron", 0);
    }

    /**
     * Initialise les vitesses des projectiles.
     * Récupère les vitesses depuis les instances des projectiles.
     */
    private void initialiserVitesse() {
        vitesse.put("TowerArcher", fleche.getVitesse());
        vitesse.put("TowerCanon", bullet.getVitesse());
        vitesse.put("TowerMagie", sort.getVitesse());
    }

    /**
     * Récupère la texture d'un type de tour.
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @return La texture de la tour, ou null si le type est inconnu
     */
    public Texture getTexture(String towerType) {
        return textures.get(towerType);
    }

    /**
     * Récupère la texture d'une tour selon son type et son niveau.
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @param level Le niveau de la tour (1, 2, 3 ou 4)
     * @return La texture de la tour pour ce niveau, ou la texture niveau 1 par défaut
     */
    public Texture getTextureWithLevel(String towerType, int level) {
        String key = towerType + "_" + level;
        Texture texture = textures.get(key);

        // Fallback sur la texture level 1 si le niveau demandé n'existe pas
        if (texture == null) {
            texture = textures.get(towerType + "_1");
        }

        return texture;
    }

    /**
     * Récupère la texture du projectile d'un type de tour.
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @return La texture du projectile, ou null si le type est inconnu
     */
    public Texture getTextureProjectile(String towerType) {
        return texturesProjectiles.get(towerType);
    }

    /**
     * Récupère la texture d'un projectile selon le type de tour et le niveau.
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @param level Le niveau de la tour (1, 2, 3 ou 4)
     * @return La texture du projectile pour ce niveau, ou la texture niveau 1 par défaut
     */
    public Texture getTextureProjectileWithLevel(String towerType, int level) {
        String key = towerType + "_" + level;
        Texture texture = texturesProjectiles.get(key);

        // Fallback sur la texture level 1 si le niveau demandé n'existe pas
        if (texture == null) {
            texture = texturesProjectiles.get(towerType + "_1");
        }

        // Fallback final sur la texture sans niveau si toujours null
        if (texture == null) {
            texture = texturesProjectiles.get(towerType);
        }

        return texture;
    }

    /**
     * Récupère le prix d'un type de tour.
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @return Le prix de la tour, ou -1 si le type est inconnu
     */
    public int getPrix(String towerType) {
        if (towerType == null) {
            return -1;
        }
        Integer prixValue = prix.get(towerType);
        if (prixValue == null) {
            return -1;
        }
        return prixValue.intValue();
    }

    /**
     * Récupère la portée d'un type de tour.
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @return La portée de la tour, ou -1f si le type est inconnu
     */
    public float getPortee(String towerType) {
        if (towerType == null) {
            return -1f;
        }
        Float porteeValue = portee.get(towerType);
        if (porteeValue == null) {
            return -1f;
        }
        return porteeValue.floatValue();
    }

    /**
     * Récupère les dégâts d'un type de tour via son projectile.
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @return Les dégâts du projectile, ou null si le type est inconnu
     */
    public int getDegats(String towerType) {
        Integer degatsValue = degats.get(towerType);
        if (degatsValue == null) {
            return 0;
        }
        return degatsValue.intValue();
    }

    /**
     * Récupère la vitesse d'un type de tour via son projectile.
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @return La vitesse du projectile, ou null si le type est inconnu
     */
    public float getVitesse(String towerType) {
        Float vitesseValue = vitesse.get(towerType);
        if (vitesseValue == null) {
            return 0f;
        }
        return vitesseValue.floatValue();
    }

    /**
     * Libère toutes les textures chargées.
     * Appelle dispose() sur toutes les textures et vide les maps.
     */
    public void dispose() {
        libererTextures(textures);
        libererTextures(texturesProjectiles);
    }

    /**
     * Libère les textures d'une map donnée.
     * @param texturesMap La map contenant les textures à libérer
     */
    private void libererTextures(Map<String, Texture> texturesMap) {
        for (Texture texture : texturesMap.values()) {
            if (texture != null) {
                texture.dispose();
            }
        }
        texturesMap.clear();
    }
}
