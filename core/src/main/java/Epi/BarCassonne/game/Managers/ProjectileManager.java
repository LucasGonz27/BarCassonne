package Epi.BarCassonne.game.Managers;

import Epi.BarCassonne.game.Entities.Projectiles.Projectile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire de tous les projectiles du jeu.
 * Responsable de la mise à jour, du rendu et de la suppression des projectiles.
 * 
 * @author Epi
 */
public class ProjectileManager {

    /** Liste de tous les projectiles actifs dans le jeu */
    private final List<Projectile> projectiles;

    /** Gestionnaire des données des tours*/
    private final TowerDataManager towerDataManager;

    /**
     * Crée un nouveau gestionnaire de projectiles.
     * Initialise la liste des projectiles et le gestionnaire de données.
     */
    public ProjectileManager() {
        this.projectiles = new ArrayList<>();
        this.towerDataManager = new TowerDataManager();
    }

    /**
     * Ajoute un projectile à la liste des projectiles actifs.
     * 
     * @param projectile Le projectile à ajouter (ignoré si null)
     */
    public void ajouterProjectile(Projectile projectile) {
        if (projectile != null) {
            projectiles.add(projectile);
        }
    }

    /**
     * Met à jour tous les projectiles actifs.
     * Supprime automatiquement les projectiles qui doivent être supprimés.
     * 
     * @param delta Temps écoulé depuis la dernière frame (en secondes)
     */
    public void update(float delta) {
        // Mettre à jour tous les projectiles
        for (Projectile projectile : projectiles) {
            projectile.update(delta);
        }
        
        // Filtrer les projectiles actifs (ceux qui ne doivent pas être supprimés)
        List<Projectile> projectilesActifs = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            if (!projectile.doitEtreSupprime()) {
                projectilesActifs.add(projectile);
            }
        }
        
        // Remplacer l'ancienne liste par la nouvelle liste filtrée
        projectiles.clear();
        projectiles.addAll(projectilesActifs);
    }

    /**
     * Dessine tous les projectiles actifs à l'écran.
     * Récupère la texture appropriée pour chaque projectile selon son niveau depuis TowerDataManager.
     * 
     * @param batch SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {
        for (Projectile projectile : projectiles) {
            String towerTypeName = projectile.getTowerTypeName();
            if (towerTypeName != null) {
                // Récupérer la texture selon le niveau de la tour qui a tiré le projectile
                int niveau = projectile.getNiveauTour();
                Texture texture = towerDataManager.getTextureProjectileWithLevel(towerTypeName, niveau);
                if (texture != null) {
                    projectile.render(batch, texture);
                }
            }
        }
    }

    /**
     * Supprime tous les projectiles de la liste.
     * Utile pour réinitialiser le gestionnaire (ex: nouvelle partie).
     */
    public void clear() {
        projectiles.clear();
    }


}
