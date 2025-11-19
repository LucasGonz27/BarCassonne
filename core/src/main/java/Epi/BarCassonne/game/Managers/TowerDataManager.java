package Epi.BarCassonne.game.Managers;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.graphics.Texture;
import Epi.BarCassonne.game.Entities.Towers.TowerMagie;
import Epi.BarCassonne.game.Entities.Towers.TowerArcher;
import Epi.BarCassonne.game.Entities.Towers.TowerForgeron;

/**
 * Gestionnaire des données statiques des tours (textures, prix, portée).
 * Centralise l'accès aux informations des différents types de tours.
 */
public class TowerDataManager {

    private final Map<String, Texture> textures;
    private final TowerMagie towerMagie;
    private final TowerArcher towerArcher;
    private final TowerForgeron towerForgeron;
    
    public TowerDataManager() {
        this.textures = new HashMap<>();
        this.towerMagie = new TowerMagie();
        this.towerArcher = new TowerArcher();
        this.towerForgeron = new TowerForgeron();
        
        initialiserTextures();
    }
    
    /**
     * Initialise les textures pour tous les types de tours.
     */
    private void initialiserTextures() {
        textures.put("TowerArcher", TextureManager.chargerTexture("sprites/TourArcherLevel1.png"));
        textures.put("TowerMagie", TextureManager.chargerTexture("sprites/TourMagieLevel1.png"));
        textures.put("TowerForgeron", TextureManager.chargerTexture("sprites/ForgeronLevel1.png"));
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
     * Récupère le prix d'un type de tour.
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @return Le prix de la tour, ou -1 si le type est inconnu
     */
    public int getPrix(String towerType) {
        switch (towerType) {
            case "TowerMagie":
                return towerMagie.getPrix();
            case "TowerArcher":
                return towerArcher.getPrix();
            case "TowerForgeron":
                return towerForgeron.getPrix();
            default:
                return -1;
        }
    }

    /**
     * Récupère la portée d'un type de tour.
     * @param towerType Le type de tour (ex: "TowerArcher")
     * @return La portée de la tour, ou -1 si le type est inconnu
     */
    public float getPortee(String towerType) {
        switch (towerType) {
            case "TowerMagie":
                return towerMagie.getPortee();
            case "TowerArcher":
                return towerArcher.getPortee();
            case "TowerForgeron":
                return towerForgeron.getPortee();
            default:
                return -1f;
        }
    }
    
    /**
     * Libère toutes les textures chargées.
     */
    public void dispose() {
        for (Texture texture : textures.values()) {
            if (texture != null) {
                texture.dispose();
            }
        }
        textures.clear();
    }
}
