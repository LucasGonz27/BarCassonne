package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Tests unitaires pour la classe CheminMana.
 * Teste la création et la mise à jour du chemin des ennemis.
 */
public class CheminManaTest {

    @Test
    public void testCreationChemin() {
        float mapWidth = 1000f;
        float mapHeight = 800f;

        CheminMana cheminMana = new CheminMana(mapWidth, mapHeight);
        List<Vector2> chemin = cheminMana.getCheminPrincipal();

        assertNotNull(chemin, "Le chemin ne doit pas être null");
        assertEquals(4, chemin.size(), "Le chemin doit contenir 4 points");
    }

    @Test
    public void testPointsCheminCoordonnees() {
        float mapWidth = 1000f;
        float mapHeight = 800f;

        CheminMana cheminMana = new CheminMana(mapWidth, mapHeight);
        List<Vector2> chemin = cheminMana.getCheminPrincipal();

        Vector2 point0 = chemin.get(0);
        assertEquals(0.0f, point0.x, 0.01f, "Point 0 X doit être 0");
        assertEquals(0.376f * mapHeight, point0.y, 0.01f,
                "Point 0 Y doit être 37.6% de la hauteur");

        Vector2 point1 = chemin.get(1);
        assertEquals(0.494f * mapWidth, point1.x, 0.01f,
                "Point 1 X doit être 49.4% de la largeur");
        assertEquals(0.430f * mapHeight, point1.y, 0.01f,
                "Point 1 Y doit être 43.0% de la hauteur");

        Vector2 point3 = chemin.get(3);
        assertEquals(0.961f * mapWidth, point3.x, 0.01f,
                "Point 3 X doit être 96.1% de la largeur");
        assertEquals(0.634f * mapHeight, point3.y, 0.01f,
                "Point 3 Y doit être 63.4% de la hauteur");
    }

    @Test
    public void testMettreAJourChemin() {
        float mapWidth1 = 1000f;
        float mapHeight1 = 800f;
        CheminMana cheminMana = new CheminMana(mapWidth1, mapHeight1);

        Vector2 premierPointAvant = cheminMana.getCheminPrincipal().get(0);
        Vector2 dernierPointAvant = cheminMana.getCheminPrincipal().get(3);

        float mapWidth2 = 1920f;
        float mapHeight2 = 1080f;
        cheminMana.mettreAJourChemin(mapWidth2, mapHeight2);

        Vector2 premierPointApres = cheminMana.getCheminPrincipal().get(0);
        Vector2 dernierPointApres = cheminMana.getCheminPrincipal().get(3);

        assertNotEquals(premierPointAvant.y, premierPointApres.y, 0.01f,
                "Les coordonnées Y doivent changer après mise à jour");
        assertNotEquals(dernierPointAvant.x, dernierPointApres.x, 0.01f,
                "Les coordonnées X doivent changer après mise à jour");
        assertEquals(4, cheminMana.getCheminPrincipal().size(),
                "Le chemin doit toujours contenir 4 points");

        assertEquals(0.0f, premierPointApres.x, 0.01f,
                "Point 0 X doit toujours être 0");
        assertEquals(0.376f * mapHeight2, premierPointApres.y, 0.01f,
                "Point 0 Y doit être 37.6% de la nouvelle hauteur");
    }

    @Test
    public void testCheminAvecDimensionsDifferentes() {
        float mapWidth = 500f;
        float mapHeight = 400f;

        CheminMana cheminMana = new CheminMana(mapWidth, mapHeight);
        List<Vector2> chemin = cheminMana.getCheminPrincipal();

        for (Vector2 point : chemin) {
            assertTrue(point.x >= 0, "Le point X doit être >= 0");
            assertTrue(point.x <= mapWidth, "Le point X doit être <= mapWidth");
            assertTrue(point.y >= 0, "Le point Y doit être >= 0");
            assertTrue(point.y <= mapHeight, "Le point Y doit être <= mapHeight");
        }
    }

    @Test
    public void testPointsNonIdentiques() {
        float mapWidth = 1000f;
        float mapHeight = 800f;

        CheminMana cheminMana = new CheminMana(mapWidth, mapHeight);
        List<Vector2> chemin = cheminMana.getCheminPrincipal();

        Vector2 premierPoint = chemin.get(0);
        Vector2 dernierPoint = chemin.get(3);

        boolean pointsDifferents = Math.abs(premierPoint.x - dernierPoint.x) > 0.01f ||
                Math.abs(premierPoint.y - dernierPoint.y) > 0.01f;

        assertTrue(pointsDifferents,
                "Le premier et le dernier point doivent être différents");
    }

    @Test
    public void testPoint2IdentiquePoint1X() {
        float mapWidth = 1000f;
        float mapHeight = 800f;

        CheminMana cheminMana = new CheminMana(mapWidth, mapHeight);
        List<Vector2> chemin = cheminMana.getCheminPrincipal();

        Vector2 point1 = chemin.get(1);
        Vector2 point2 = chemin.get(2);

        assertEquals(point1.x, point2.x, 0.01f,
                "Point 1 et Point 2 doivent avoir la même coordonnée X");
        assertNotEquals(point1.y, point2.y, 0.01f,
                "Point 1 et Point 2 doivent avoir des coordonnées Y différentes");
    }

    @Test
    public void testCheminAvecDimensionsExtremes() {
        float mapWidth = 100f;
        float mapHeight = 50f;

        CheminMana cheminMana = new CheminMana(mapWidth, mapHeight);
        List<Vector2> chemin = cheminMana.getCheminPrincipal();

        assertEquals(4, chemin.size(),
                "Le chemin doit toujours contenir 4 points même avec petites dimensions");

        for (Vector2 point : chemin) {
            assertTrue(point.x >= 0 && point.x <= mapWidth,
                    "Point doit être dans les limites X");
            assertTrue(point.y >= 0 && point.y <= mapHeight,
                    "Point doit être dans les limites Y");
        }
    }
}
