package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class CheminMana {

    protected List<Vector2> cheminPrincipal;

    public CheminMana() {

        cheminPrincipal = new ArrayList<>();
        cheminPrincipal.add(new Vector2(100, 500));
        cheminPrincipal.add(new Vector2(300, 500));
        cheminPrincipal.add(new Vector2(300, 300));
        cheminPrincipal.add(new Vector2(600, 300));
        cheminPrincipal.add(new Vector2(800, 100));
    }

    public List<Vector2> getCheminPrincipal() {
        return cheminPrincipal;
    }
}
