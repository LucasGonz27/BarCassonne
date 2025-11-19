package Epi.BarCassonne.game.Managers;

import Epi.BarCassonne.game.Entities.Projectiles.Projectile;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gestionnaire de tous les projectiles du jeu.*/
public class ProjectileManager {


    /** Liste de tous les projectiles actifs */
    private final List<Projectile> projectiles;

    /**
     * Crée un nouveau gestionnaire de projectiles.*/
    public ProjectileManager() {
        this.projectiles = new ArrayList<>();
    }

    /**
     * Ajoute un projectile à la liste.*/
    public void ajouterProjectile(Projectile projectile) {
        if (projectile != null) {
            projectiles.add(projectile);
        }
    }

    /**
     * Met à jour tous les projectiles.
     * Supprime les projectiles qui ont atteint leur cible.*/
    public void update(float delta) {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update(delta);

            // Supprimer le projectile s'il a atteint sa cible ou si la cible est morte
            if (projectile.doitEtreSupprime()) {
                iterator.remove();
            }
        }
    }

    /**
     * Dessine tous les projectiles.*/
    public void render(SpriteBatch batch) {
        for (Projectile projectile : projectiles) {
            projectile.render(batch);
        }
    }

    /**
     * Vide la liste de tous les projectiles.*/
    public void clear() {
        projectiles.clear();
    }

    /**
     * Retourne le nombre de projectiles actifs.*/
    public int getNombreProjectiles() {
        return projectiles.size();
    }
}
