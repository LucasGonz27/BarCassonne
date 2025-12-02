package Epi.BarCassonne.game.Managers;

import Epi.BarCassonne.game.Entities.Mechants.*;
import Epi.BarCassonne.game.Vague.Vague;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Gestionnaire des vagues d'ennemis.
 * Gère le spawn, la mise à jour et le passage entre les vagues.
 */
public class VagueMana {

    // ------------------------------------------------------------------------
    // REGION : CHAMPS
    // ------------------------------------------------------------------------
    /** Liste de toutes les vagues du jeu. */
    private Array<Vague> vagues;

    /** Index de la vague actuellement en cours. */
    private int indexVagueActuelle;

    /** La vague actuellement en cours. */
    private Vague vagueActuelle;

    /** Liste de tous les ennemis actuellement actifs sur le terrain. */
    private Array<Mechant> ennemisActifs;

    /** Délai en secondes entre la fin d'une vague et le début de la suivante. */
    private float delaiEntreVagues = 5f;

    /** Temps écoulé depuis la fin de la vague précédente. */
    private float tempsDepuisFinVague = 0f;

    /** Gestionnaire du chemin que suivent les ennemis. */
    private CheminMana cheminMana;

    /** État du jeu (ressources, vie, etc.). */
    private GameState gameState;

    // ------------------------------------------------------------------------
    // REGION : CONSTRUCTEUR
    // ------------------------------------------------------------------------
    /**
     * Crée un nouveau gestionnaire de vagues.
     * @param cheminMana Le gestionnaire de chemin pour les ennemis
     * @param gameState L'état du jeu
     */
    public VagueMana(CheminMana cheminMana, GameState gameState) {
        this.cheminMana = cheminMana;
        this.gameState = gameState;
        this.vagues = new Array<>();
        this.ennemisActifs = new Array<>();
        creerVagues();
        indexVagueActuelle = 0;
        vagueActuelle = vagues.get(0);
    }

    // ------------------------------------------------------------------------
    // REGION : INITIALISATION
    // ------------------------------------------------------------------------
    /**
     * Crée toutes les vagues du jeu.
     */
    private void creerVagues() {
        // Vague 1
        Vague v1 = new Vague(1);
        v1.ajouterEnnemi(PaysanGoblin.class, 8);
        vagues.add(v1);

        // Vague 2
        Vague v2 = new Vague(2);
        v2.ajouterEnnemi(PaysanGoblin.class, 5);
        v2.ajouterEnnemi(GuerrierGoblin.class, 5);
        v2.ajouterEnnemi(PaysanGoblin.class, 5);
        vagues.add(v2);

        // Vague 3
        Vague v3 = new Vague(3);
        v3.ajouterEnnemi(GuerrierGoblin.class, 3);
        v3.ajouterEnnemi(GoblinGuerrisseur.class, 5);
        v3.ajouterEnnemi(GuerrierGoblin.class, 5);
        vagues.add(v3);

        //vague 4
        Vague v4 = new Vague(4);
        v4.ajouterEnnemi(GoblinGuerrisseur.class, 2);
        v4.ajouterEnnemi(GuerrierGoblin.class, 10);
        v4.ajouterEnnemi(GoblinGuerrisseur.class, 2);
        vagues.add(v4);

        // Vague 5
        Vague v5 = new Vague(5);
        v5.ajouterEnnemi(GoblinBomb.class, 4);
        v5.ajouterEnnemi(Cochon.class, 2);
        v5.ajouterEnnemi(GuerrierGoblin.class, 2);
        vagues.add(v5);

        // Vague 6
        Vague v6 = new Vague(6);
        v6.ajouterEnnemi(Cochon.class, 10);
        v6.ajouterEnnemi(GuerrierGoblin.class, 5);
        v6.ajouterEnnemi(PaysanGoblin.class, 5);
        vagues.add(v6);

        // Vague 7
        Vague v7 = new Vague(7);
        v7.ajouterEnnemi(GuerrierGoblin.class, 15);
        v7.ajouterEnnemi(GoblinBomb.class, 5);
        v7.ajouterEnnemi(GoblinGuerrisseur.class, 5);
        vagues.add(v7);

        // Vague 8
        Vague v8 = new Vague(8);
        v8.ajouterEnnemi(Cochon.class, 10);
        v8.ajouterEnnemi(GuerrierGoblin.class, 5);
        v8.ajouterEnnemi(PaysanGoblin.class, 10);
        vagues.add(v8);

        // Vague 9
        Vague v9 = new Vague(9);
        v9.ajouterEnnemi(Chevalier.class, 5);
        vagues.add(v9);

        // Vague 10
        Vague v10 = new Vague(10);
        v10.ajouterEnnemi(Chevalier.class, 2);
        v10.ajouterEnnemi(GoblinBomb.class, 5);
        v10.ajouterEnnemi(Chevalier.class, 2);
        v10.ajouterEnnemi(GoblinBomb.class, 5);
        vagues.add(v10);

        // Vague 11
        Vague v11 = new Vague(11);
        v11.ajouterEnnemi(Chevalier.class, 5);
        v11.ajouterEnnemi(GuerrierGoblin.class, 10);
        v11.ajouterEnnemi(PaysanGoblin.class, 20);
        vagues.add(v11);

        // Vague 12
        Vague v12 = new Vague(12);
        v12.ajouterEnnemi(BossChevalier.class, 5);
        v12.ajouterEnnemi(GuerrierGoblin.class, 10);
        v12.ajouterEnnemi(Cochon.class, 10);
        v12.ajouterEnnemi(GoblinGuerrisseur.class, 2);
        vagues.add(v12);

        // Vague 13
        Vague v13 = new Vague(13);
        v13.ajouterEnnemi(Golem.class, 5);
        v13.ajouterEnnemi(PaysanGoblin.class, 20);
        v13.ajouterEnnemi(GuerrierGoblin.class, 10);
        v13.ajouterEnnemi(Chevalier.class, 10);
        vagues.add(v13);

        // Vague 14
        Vague v14 = new Vague(14);
        v14.ajouterEnnemi(Golem.class, 5);
        v14.ajouterEnnemi(Chevalier.class, 20);
        v14.ajouterEnnemi(GuerrierGoblin.class, 10);
        v14.ajouterEnnemi(PaysanGoblin.class, 20);
        v14.ajouterEnnemi(BossChevalier.class, 10);
        vagues.add(v14);

        // Vague 15
        Vague v15 = new Vague(15);
        v15.ajouterEnnemi(GuerrierGoblin.class, 10);
        v15.ajouterEnnemi(Golem.class, 10);
        v15.ajouterEnnemi(PaysanGoblin.class, 20);
        v15.ajouterEnnemi(BossChevalier.class, 10);
        v15.ajouterEnnemi(GoblinGuerrisseur.class, 10);
        v15.ajouterEnnemi(Chevalier.class, 10);
        v15.ajouterEnnemi(RoiGoblin.class, 1);
        vagues.add(v15);
    }

    // ------------------------------------------------------------------------
    // REGION : MISE À JOUR
    // ------------------------------------------------------------------------
    /**
     * Met à jour le système de vagues.
     * @param deltaTime Temps écoulé depuis la dernière frame
     */
    public void update(float deltaTime) {
        // Mettre à jour les ennemis même si toutes les vagues sont terminées
        mettreAJourEnnemis(deltaTime);
        
        if (vagueActuelle == null) return;

        if (vagueActuelle.estTerminee()) {
            passerVagueSuivante(deltaTime);
            return;
        }

        spawnEnnemisSuivant(deltaTime);
    }

    /**
     * Gère le spawn des ennemis selon l'intervalle défini.
     * @param deltaTime Temps écoulé depuis la dernière frame
     */
    private void spawnEnnemisSuivant(float deltaTime) {
        float temps = vagueActuelle.getTempsDepuisDernierSpawn() + deltaTime;
        vagueActuelle.setTempsDepuisDernierSpawn(temps);

        if (temps >= vagueActuelle.getIntervalleSpawn()) {
            Mechant ennemi = vagueActuelle.CreerEnnemi();
            if (ennemi != null) {
                initialiserEnnemi(ennemi);
                vagueActuelle.getEnnemisActifs().add(ennemi);
                ennemisActifs.add(ennemi);
            }
            vagueActuelle.setTempsDepuisDernierSpawn(0f);
        }
    }

    /**
     * Initialise un ennemi avec son chemin et sa position de départ.
     * Place l'ennemi au premier point du chemin et configure son index pour viser le deuxième point.
     * @param ennemi L'ennemi à initialiser
     */
    private void initialiserEnnemi(Mechant ennemi) {
        if (cheminMana == null || cheminMana.getCheminPrincipal() == null ||
            cheminMana.getCheminPrincipal().isEmpty()) {
            System.err.println("Erreur: Le chemin n'est pas défini !");
            return;
        }

        ennemi.setChemin(cheminMana.getCheminPrincipal());

        // Placer l'ennemi au premier point du chemin
        Vector2 premierPoint = cheminMana.getCheminPrincipal().get(0);
        ennemi.setPositionX(premierPoint.x);
        ennemi.setPositionY(premierPoint.y);

        // Commencer à viser le deuxième point (index 1)
        if (cheminMana.getCheminPrincipal().size() > 1) {
            ennemi.setIndexActuel(1);
        } else {
            // Si un seul point, l'ennemi reste sur place
            ennemi.setIndexActuel(0);
        }
    }

    /**
     * Met à jour tous les ennemis actifs et nettoie les morts.
     * Vérifie si les ennemis ont atteint la fin du chemin et inflige les dégâts au joueur.
     * @param deltaTime Temps écoulé depuis la dernière frame
     */
    private void mettreAJourEnnemis(float deltaTime) {
        for (int i = ennemisActifs.size - 1; i >= 0; i--) {
            Mechant unMechant = ennemisActifs.get(i);
            if (!unMechant.isEnVie()) {
                ennemisActifs.removeIndex(i);
            } else {
                unMechant.update(deltaTime);

                // Vérifier si l'ennemi a atteint la fin du chemin
                if (unMechant.aAtteintFinChemin()) {
                    // Infliger les dégâts au joueur
                    int degats = unMechant.getDegatsFinChemin();
                    gameState.recevoirDegats(degats);
                    // Tuer l'ennemi
                    unMechant.mourir();
                }
            }
        }
    }

    /**
     * Passe à la vague suivante après un délai.
     * @param deltaTime Temps écoulé depuis la dernière frame
     */
    private void passerVagueSuivante(float deltaTime) {
        tempsDepuisFinVague += deltaTime;

        if (tempsDepuisFinVague >= delaiEntreVagues) {
            indexVagueActuelle++;
            if (indexVagueActuelle < vagues.size) {
                vagueActuelle = vagues.get(indexVagueActuelle);
                tempsDepuisFinVague = 0f;
            } else {
                vagueActuelle = null; // Toutes les vagues sont terminées
            }
        }
    }

    // ------------------------------------------------------------------------
    // REGION : GETTERS
    // ------------------------------------------------------------------------
    /**
     * Retourne la liste de tous les ennemis actifs.
     * @return La liste des ennemis actifs
     */
    public Array<Mechant> getEnnemisActifs() {
        return ennemisActifs;
    }

    /**
     * Retourne la vague actuellement en cours.
     * @return La vague actuelle, ou null si toutes les vagues sont terminées
     */
    public Vague getVagueActuelle() {
        return vagueActuelle;
    }

    /**
     * Vérifie si toutes les vagues sont terminées.
     * @return true si toutes les vagues sont terminées (index dépasse la taille ou vagueActuelle est null)
     */
    public boolean toutesVaguesTerminees() {
        return indexVagueActuelle >= vagues.size || vagueActuelle == null;
    }

    /**
     * Met à jour le chemin de tous les ennemis actifs.
     * À appeler quand le chemin est recalculé (par exemple lors d'un resize).
     * Préserve la progression relative de chaque ennemi sur le nouveau chemin.
     */
    public void mettreAJourCheminEnnemis() {
        if (cheminMana == null || cheminMana.getCheminPrincipal() == null ||
            cheminMana.getCheminPrincipal().isEmpty()) {
            return;
        }

        // Mettre à jour le chemin de tous les ennemis actifs
        for (Mechant ennemi : ennemisActifs) {
            if (ennemi.isEnVie()) {
                ennemi.setChemin(cheminMana.getCheminPrincipal());
            }
        }
    }

    // ------------------------------------------------------------------------
    // REGION : RENDU
    // ------------------------------------------------------------------------
    /**
     * Dessine tous les ennemis actifs.
     * @param batch Le SpriteBatch pour le rendu
     */
    public void render(SpriteBatch batch) {
        for (Mechant mechant : ennemisActifs) {
            if (mechant.isEnVie() && mechant.getFrame() != null) {
                batch.draw(mechant.getFrame(), mechant.getPositionX(), mechant.getPositionY());
            }
            // Rendre les messages flottants de l'ennemi (même s'il est mort, pour afficher les derniers dégâts)
            mechant.getMessageFlottant().render(batch);
        }
    }
}
