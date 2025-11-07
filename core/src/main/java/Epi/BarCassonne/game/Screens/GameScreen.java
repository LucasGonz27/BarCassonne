package Epi.BarCassonne.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Epi.BarCassonne.game.Entities.Mechants.Mechant;
import Epi.BarCassonne.game.Managers.AssetMana;
import Epi.BarCassonne.game.Managers.BackgroundManager;
import Epi.BarCassonne.game.Managers.CheminMana;
import Epi.BarCassonne.game.Managers.VagueMana;

/**
 * Écran principal du jeu.
 * Gère l'affichage et la mise à jour du jeu.
 */
public class GameScreen implements Screen {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    private SpriteBatch batch;
    private BackgroundManager background;
    private OrthographicCamera camera;
    private Viewport viewport;
    private VagueMana vagueMana;
    private CheminMana chemin;
    private int numeroVaguePrecedente = 0;

    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 600;

    // ------------------------------------------------------------------------
    // REGION : INITIALISATION
    // ------------------------------------------------------------------------
    @Override
    public void show() {
        initialiserRendu();
        chargerAssets();
        initialiserJeu();
    }

    /**
     * Initialise les composants de rendu.
     */
    private void initialiserRendu() {
        batch = new SpriteBatch();
        background = new BackgroundManager("backgrounds/map.png");

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
    }

    /**
     * Charge tous les assets nécessaires.
     */
    private void chargerAssets() {
        AssetMana.loadAnimation("PaysanGoblin");
        // Décommenter les autres animations si nécessaire
        // AssetMana.loadAnimation("GuerrierGoblin");
        // AssetMana.loadAnimation("GoblinGuerrisseur");
        // AssetMana.loadAnimation("GoblinBomb");
        // AssetMana.loadAnimation("Cochon");
        // AssetMana.loadAnimation("Chevalier");
        // AssetMana.loadAnimation("BossChevalier");
        // AssetMana.loadAnimation("Golem");
        // AssetMana.loadAnimation("RoiGoblin");
    }

    /**
     * Initialise les composants du jeu.
     */
    private void initialiserJeu() {
        chemin = new CheminMana();
        vagueMana = new VagueMana(chemin);

        // Initialiser le numéro de vague précédente
        if (vagueMana.getVagueActuelle() != null) {
            numeroVaguePrecedente = vagueMana.getVagueActuelle().getNumero();
            System.out.println("GameScreen initialisé - Vague " + numeroVaguePrecedente);
        }
    }

    // ------------------------------------------------------------------------
    // REGION : RENDU
    // ------------------------------------------------------------------------
    @Override
    public void render(float delta) {
        mettreAJourJeu(delta);
        dessiner();
    }

    /**
     * Met à jour la logique du jeu.
     */
    private void mettreAJourJeu(float delta) {

        vagueMana.update(delta);
            int numeroVagueActuelle = vagueMana.getVagueActuelle().getNumero();
            if (numeroVagueActuelle != numeroVaguePrecedente) {
                System.out.println("Vague " + numeroVagueActuelle + " commencée !");
                numeroVaguePrecedente = numeroVagueActuelle;
            }

        if (vagueMana.toutesVaguesTerminees()) {
            System.out.println("Toutes les vagues sont terminées !");
        }
    }

    /**
     * Dessine tous les éléments à l'écran.
     */
    private void dessiner() {
        // Effacer l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Mettre à jour la caméra
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Dessiner
        batch.begin();
        background.render(batch);
        dessinerEnnemis();
        batch.end();
    }

    /**
     * Dessine tous les ennemis actifs.
     */
    private void dessinerEnnemis() {
        for (Mechant m : vagueMana.getEnnemisActifs()) {

            if (m.isEnVie() && m.getFrame() != null) {
                batch.draw(m.getFrame(), m.getPositionX(), m.getPositionY());
            }
        }
    }

    // ------------------------------------------------------------------------
    // REGION : GESTION D'ÉVÉNEMENTS
    // ------------------------------------------------------------------------
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    // ------------------------------------------------------------------------
    // REGION : NETTOYAGE
    // ------------------------------------------------------------------------
    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        AssetMana.dispose();
    }
}
