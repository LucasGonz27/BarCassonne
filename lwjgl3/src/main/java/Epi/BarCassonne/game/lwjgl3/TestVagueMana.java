package Epi.BarCassonne.game.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Managers.AssetMana;
import Epi.BarCassonne.game.Managers.BackgroundManager;
import Epi.BarCassonne.game.Managers.CheminMana;
import Epi.BarCassonne.game.Managers.VagueMana;
import Epi.BarCassonne.game.Vague.Vague;

/**
 * Teste le système de vagues avec affichage graphique complet.
 */
public class TestVagueMana {
    
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Test Vagues - BarCassonne");
        config.setWindowedMode(800, 600);
        config.useVsync(true);
        
        new Lwjgl3Application(new ApplicationAdapter() {
            private SpriteBatch batch;
            private BackgroundManager background;
            private VagueMana vagueMana;
            private OrthographicCamera camera;
            
            @Override
            public void create() {
                batch = new SpriteBatch();
                background = new BackgroundManager("backgrounds/map.png");
                
                camera = new OrthographicCamera();
                camera.setToOrtho(false, 800, 600);
                
                CheminMana chemin = new CheminMana();
                vagueMana = new VagueMana(chemin);
                
                AssetMana.load();
                System.out.println("Application démarrée - Vague " + vagueMana.getVagueActuelle().getNumero());
            }
            
            @Override
            public void render() {
                float delta = Gdx.graphics.getDeltaTime();
                
                // Mise à jour
                vagueMana.update(delta);
                
                // Rendu
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                camera.update();
                batch.setProjectionMatrix(camera.combined);
                
                batch.begin();
                background.render(batch);
                
                // Afficher les ennemis
                for (Mechant m : vagueMana.getEnnemisActifs()) {
                    if (m.isEnVie() && m.getSprite() != null) {
                        batch.draw(m.getSprite(), m.getPositionX(), m.getPositionY());
                    }
                }
                
                batch.end();
                
                // Afficher les infos dans la console toutes les secondes
                if (Gdx.graphics.getFrameId() % 60 == 0) {
                    Vague vague = vagueMana.getVagueActuelle();
                    if (vague != null) {
                        System.out.printf("Vague: %d | Ennemis actifs: %d | Spawnés: %s%n",
                                vague.getNumero(),
                                vagueMana.getEnnemisActifs().size,
                                vague.tousEnnemisSpawnes() ? "Oui" : "Non");
                    }
                }
                
                if (vagueMana.toutesVaguesTerminees()) {
                    System.out.println("Toutes les vagues sont terminées !");
                }
            }
            
            @Override
            public void dispose() {
                batch.dispose();
                background.dispose();
                AssetMana.dispose();
            }
        }, config);
    }
}


