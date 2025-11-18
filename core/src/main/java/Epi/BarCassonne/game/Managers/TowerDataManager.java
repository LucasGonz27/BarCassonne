package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.graphics.Texture;
import Epi.BarCassonne.game.Entities.Towers.TowerArcher;
import Epi.BarCassonne.game.Entities.Towers.TowerForgeron;
import Epi.BarCassonne.game.Entities.Towers.TowerMagie;

import java.util.HashMap;
import java.util.Map;

/**
 * Gère les données statiques des tours (prix, portée, textures).
 * Utilise les constantes statiques des classes de tours (meilleure pratique).
 */
public class TowerDataManager {
    private final Map<String, TowerData> towerData;
    private final Map<String, Texture> textures;
    
    public TowerDataManager() {
        this.towerData = new HashMap<>();
        this.textures = new HashMap<>();
        initialiserDonnees();
    }
    
    private void initialiserDonnees() {
        chargerTexture("TowerArcher", "sprites/TourArcherLevel1.png");
        chargerTexture("TowerMagie", "sprites/TourMagieLevel1.png");
        chargerTexture("TowerForgeron", "sprites/ForgeronLevel1.png");
        

        TowerArcher tempArcher = new TowerArcher();
        towerData.put("TowerArcher", new TowerData(
            tempArcher.getPrix(), 
            tempArcher.getPortee(), 
            textures.get("TowerArcher")
        ));
        
        TowerMagie tempMagie = new TowerMagie();
        towerData.put("TowerMagie", new TowerData(
            tempMagie.getPrix(), 
            tempMagie.getPortee(), 
            textures.get("TowerMagie")
        ));
        
        TowerForgeron tempForgeron = new TowerForgeron();
        towerData.put("TowerForgeron", new TowerData(
            tempForgeron.getPrix(), 
            tempForgeron.getPortee(), 
            textures.get("TowerForgeron")
        ));
    }
    
    private void chargerTexture(String typeTour, String chemin) {
        Texture texture = TextureManager.chargerTexture(chemin);
        textures.put(typeTour, texture);
    }
    
    /**
     * Retourne les données d'un type de tour.
     * @param typeTour Le type de tour (ex: "TowerArcher")
     * @return Les données de la tour, ou null si invalide
     */
    public TowerData getTowerData(String typeTour) {
        return towerData.get(typeTour);
    }
    
    /**
     * Retourne la texture d'un type de tour.
     * @param typeTour Le type de tour (ex: "TowerArcher")
     * @return La texture, ou null si invalide
     */
    public Texture getTexture(String typeTour) {
        return textures.get(typeTour);
    }
    
    /**
     * Retourne le prix d'un type de tour.
     * @param typeTour Le type de tour
     * @return Le prix, ou -1 si invalide
     */
    public int getPrix(String typeTour) {
        TowerData data = towerData.get(typeTour);
        return data != null ? data.getPrix() : -1;
    }
    
    /**
     * Retourne la portée d'un type de tour.
     * @param typeTour Le type de tour
     * @return La portée, ou 0f si invalide
     */
    public float getPortee(String typeTour) {
        TowerData data = towerData.get(typeTour);
        return data != null ? data.getPortee() : 0f;
    }
    
    /**
     * Libère les ressources utilisées.
     */
    public void dispose() {
        for (Texture texture : textures.values()) {
            if (texture != null) {
                TextureManager.libererTexture(texture);
            }
        }
        textures.clear();
        towerData.clear();
    }
    
    /**
     * Classe interne contenant les données d'un type de tour.
     */
    public static class TowerData {
        private final int prix;
        private final float portee;
        private final Texture texture;
        
        public TowerData(int prix, float portee, Texture texture) {
            this.prix = prix;
            this.portee = portee;
            this.texture = texture;
        }
        
        public int getPrix() {
            return prix;
        }
        
        public float getPortee() {
            return portee;
        }
        
        public Texture getTexture() {
            return texture;
        }
    }
}
