package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire du chemin que suivent les ennemis.
 * Définit les points de passage sur la carte.
 */
public class CheminMana {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    protected List<Vector2> cheminPrincipal;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée le chemin principal avec les points de passage.
     */
    public CheminMana() {
        cheminPrincipal = new ArrayList<>();

        // Définir les points du chemin (de gauche à droite, de haut en bas)
        cheminPrincipal.add(new Vector2(0, 350));
        cheminPrincipal.add(new Vector2(750, 400));
        cheminPrincipal.add(new Vector2(750, 590));
        cheminPrincipal.add(new Vector2(1460, 590));
       
    }

    // ------------------------------------------------------------------------
    // REGION : GETTERS
    // ------------------------------------------------------------------------
    /**
     * @return La liste des points du chemin principal
     */
    public List<Vector2> getCheminPrincipal() {
        return cheminPrincipal;
    }
}
