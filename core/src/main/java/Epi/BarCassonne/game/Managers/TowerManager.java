package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.Factory.TowerFactory;
import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Entities.Towers.Tower;
import Epi.BarCassonne.game.Entities.Towers.TowerForgeron;
import Epi.BarCassonne.game.UI.HUD;
import Epi.BarCassonne.game.Utils.CollisionValid;
import Epi.BarCassonne.game.Utils.MessageFlottant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestionnaire des tours placées sur le terrain.
 */
public class TowerManager {
    
    private static final float TAILLE_APERCU = 70f;
    private static final float TOWER_SIZE = 100f;
    private static final float INTERVALLE_GENERATION_LINGOTS = 11f;
    private static final float SEUIL_SAC_PLEIN = 10f;
    private static final float DECALAGE_SAC_X = 60f;
    private static final float TAILLE_SAC = 50f;
    private static final float DECALAGE_SAC_CENTRE = 25f;
    private static final float OPACITE_APERCU = 0.7f;
    private static final float DECALAGE_MESSAGE_Y = 80f;
    
    private final List<Tower> tours;
    private final TowerDataManager towerDataManager;
    private final MessageFlottant messageFlottant;
    private final Map<Tower, Float> tempsForgeron;
    private final CollisionValid collisionValid;
    private final VagueMana vagueManager;
    private final GameState gameState;
    
    private Texture textureSacPlein;
    private Texture textureSacVide;
    
    private boolean enModePlacement;
    private String towerTypeToPlace;
    private Texture tourPreviewTexture;
    private float tourPreviewX;
    private float tourPreviewY;
    private float tourPreviewPortee;
    private boolean positionValide;
    
    public TowerManager(CollisionValid collisionValid, VagueMana vagueManager, GameState gameState) {
        this.tours = new ArrayList<>();
        this.collisionValid = collisionValid;
        this.vagueManager = vagueManager;
        this.gameState = gameState;
        this.tempsForgeron = new HashMap<>();
        this.towerDataManager = new TowerDataManager();
        this.messageFlottant = new MessageFlottant();
        
        textureSacPlein = TextureManager.chargerTexture("sprites/SacPleins.png");
        textureSacVide = TextureManager.chargerTexture("sprites/SacVide.png");
    }
    
    public List<Tower> getTours() {
        return tours;
    }
    
    public boolean estEnModePlacement() {
        return enModePlacement;
    }
    
    public void activerModePlacement(int slot) {
        String towerType = convertirSlotEnTypeTour(slot);
        if (towerType == null) {
            return;
        }
        
        towerTypeToPlace = towerType;
        enModePlacement = true;
        tourPreviewTexture = towerDataManager.getTexture(towerType);
        tourPreviewPortee = towerDataManager.getPortee(towerType);
    }
    
    private String convertirSlotEnTypeTour(int slot) {
        switch (slot) {
            case 1: return "TowerArcher";
            case 2: return "TowerMagie";
            case 3: return "TowerForgeron";
            default: return null;
        }
    }
    
    public void mettreAJourApercu(float screenX, float screenY, float screenWidth, 
                                   float screenHeight, Viewport mapViewport) {
        if (!enModePlacement || towerTypeToPlace == null) {
            return;
        }

        Vector3 worldPos = convertirEcranVersMonde(screenX, screenY, screenWidth, screenHeight, mapViewport);
        if (worldPos == null) {
            positionValide = false;
            return;
        }

        tourPreviewX = worldPos.x;
        tourPreviewY = worldPos.y;
        positionValide = collisionValid.estPositionValide(worldPos.x, worldPos.y, TOWER_SIZE, tours);
    }
    
    public boolean placerTour(float screenX, float screenY, float screenWidth, 
                               float screenHeight, Viewport mapViewport) {
        if (!enModePlacement || towerTypeToPlace == null) {
            return false;
        }

        Vector3 worldPos = convertirEcranVersMonde(screenX, screenY, screenWidth, screenHeight, mapViewport);
        if (worldPos == null || !collisionValid.estPositionValide(worldPos.x, worldPos.y, TOWER_SIZE, tours)) {
            return false;
        }

        int prix = towerDataManager.getPrix(towerTypeToPlace);
        if (prix == -1 || !gameState.retirerLingots(prix)) {
            return false;
        }

        Tower nouvelleTour = creerTour(towerTypeToPlace, worldPos.x, worldPos.y);
        if (nouvelleTour == null) {
            return false;
        }
        
        tours.add(nouvelleTour);
        annulerModePlacement();
        return true;
    }
    
    public void annulerModePlacement() {
        enModePlacement = false;
        towerTypeToPlace = null;
        tourPreviewTexture = null;
        tourPreviewPortee = 0f;
    }
    
    public void update(float delta) {
        for (Tower tour : tours) {
            tour.update(delta);
            attaquerEnnemis(tour);
            mettreAJourForgeron(tour, delta);
        }
        messageFlottant.update(delta);
    }
    
    private void attaquerEnnemis(Tower tour) {
        if (vagueManager == null || vagueManager.getEnnemisActifs() == null) {
            return;
        }
        
        for (Mechant ennemi : vagueManager.getEnnemisActifs()) {
            if (ennemi != null && ennemi.isEnVie()) {
                tour.attacker(ennemi);
            }
        }
    }
    
    private void mettreAJourForgeron(Tower tour, float delta) {
        if (!(tour instanceof TowerForgeron)) {
            return;
        }
        
        float temps = tempsForgeron.getOrDefault(tour, 0f) + delta;
        if (temps >= INTERVALLE_GENERATION_LINGOTS) {
            TowerForgeron forgeron = (TowerForgeron) tour;
            int lingots = forgeron.getApportLingots();
            gameState.ajouterLingots(lingots);
            messageFlottant.creerMessageLingots(tour.getPositionX(), tour.getPositionY() + DECALAGE_MESSAGE_Y, lingots);
            tempsForgeron.put(tour, 0f);
        } else {
            tempsForgeron.put(tour, temps);
        }
    }
    
    public void render(SpriteBatch batch) {
        dessinerTours(batch);
        dessinerApercu(batch);
        dessinerSacs(batch);
        messageFlottant.render(batch);
    }
    
    private void dessinerTours(SpriteBatch batch) {
        for (Tower tour : tours) {
            Texture texture = towerDataManager.getTexture(tour.getClass().getSimpleName());
            if (texture != null) {
                float x = tour.getPositionX() - TOWER_SIZE / 2;
                float y = tour.getPositionY() - TOWER_SIZE / 2;
                batch.draw(texture, x, y, TOWER_SIZE, TOWER_SIZE);
            }
        }
    }
    
    private void dessinerApercu(SpriteBatch batch) {
        if (!enModePlacement || tourPreviewTexture == null) {
            return;
        }
        
        Color couleur = positionValide ? Color.WHITE : Color.RED;
        batch.setColor(couleur.r, couleur.g, couleur.b, OPACITE_APERCU);
        batch.draw(tourPreviewTexture, tourPreviewX - TAILLE_APERCU / 2, tourPreviewY - TAILLE_APERCU / 2, 
                   TAILLE_APERCU, TAILLE_APERCU);
        batch.setColor(Color.WHITE);
    }
    
    private void dessinerSacs(SpriteBatch batch) {
        for (Tower tour : tours) {
            if (tour instanceof TowerForgeron) {
                float temps = tempsForgeron.getOrDefault(tour, 0f);
                float x = tour.getPositionX() + DECALAGE_SAC_X - DECALAGE_SAC_CENTRE;
                float y = tour.getPositionY() - DECALAGE_SAC_CENTRE;
                Texture textureSac = (temps >= SEUIL_SAC_PLEIN) ? textureSacPlein : textureSacVide;
                if (textureSac != null) {
                    batch.draw(textureSac, x, y, TAILLE_SAC, TAILLE_SAC);
                }
            }
        }
    }
    
    public void renderPortee(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        if (!enModePlacement || tourPreviewPortee <= 0) {
            return;
        }
        
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Color couleur = positionValide ? Color.WHITE : Color.RED;
        shapeRenderer.setColor(couleur.r, couleur.g, couleur.b, OPACITE_APERCU);
        shapeRenderer.circle(tourPreviewX, tourPreviewY, tourPreviewPortee);
        shapeRenderer.end();
    }
    
    private Tower creerTour(String towerType, float x, float y) {
        try {
            Tower tour = TowerFactory.creerTower(towerType);
            tour.setPositionX(x);
            tour.setPositionY(y);
            return tour;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    private Vector3 convertirEcranVersMonde(float screenX, float screenY, float screenWidth, float screenHeight, Viewport mapViewport) {
        float largeurHUD = HUD.getLargeurHUD(screenWidth);
        if (screenX >= screenWidth - largeurHUD) {
            return null;
        }

        mapViewport.apply();
        Vector3 worldPos = mapViewport.unproject(new Vector3(screenX, screenY, 0));

        if (worldPos.x < 0 || worldPos.x > mapViewport.getWorldWidth() || 
            worldPos.y < 0 || worldPos.y > mapViewport.getWorldHeight()) {
            return null;
        }

        return worldPos;
    }
    
    public void dispose() {
        towerDataManager.dispose();
        if (textureSacPlein != null) {
            TextureManager.libererTexture(textureSacPlein);
        }
        if (textureSacVide != null) {
            TextureManager.libererTexture(textureSacVide);
        }
    }
}
