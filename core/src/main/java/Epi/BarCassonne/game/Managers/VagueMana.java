package Epi.BarCassonne.game.Managers;

import Epi.BarCassonne.game.Entities.Mechants.*;
import Epi.BarCassonne.game.Vague.Vague;
import com.badlogic.gdx.utils.Array;

public class VagueMana {

    // Liste de toutes les vagues
    protected Array<Vague> vagues;

    // Numéro de la vague actuelle
    protected int indexVagueActuelle;

    // Référence vers la vague en cours
    protected Vague vagueActuelle;

    // Liste des ennemis présents dans le jeu
    protected Array<Mechant> ennemisActifs;

    // Pour faire une pause entre deux vagues
    protected boolean enPauseEntreVagues = false;
    protected float tempsDepuisFinVague = 0;
    protected float delaiEntreVagues = 5f; // 5 secondes

    // Pour gérer le délai entre chaque spawn d’ennemi
    protected float tempsDepuisDernierSpawn = 0;
    protected float intervalleSpawn = 2f; // 1 ennemi toutes les 2 secondes

    // Pour donner le chemin aux ennemis
    protected CheminMana cheminMana;

    // Constructeur
    public VagueMana(CheminMana cheminMana) {
        this.cheminMana = cheminMana;
        this.vagues = new Array<>();
        this.ennemisActifs = new Array<>();

        // On crée nos vagues ici
        creerVagues();

        // On démarre avec la première vague
        indexVagueActuelle = 0;
        vagueActuelle = vagues.get(indexVagueActuelle);
    }

    // ------------------------------------------------------------------------
    // CRÉATION DES VAGUES
    // ------------------------------------------------------------------------
    private void creerVagues() {

        //vague 1
        Vague v1 = new Vague(1);
        v1.ajouterEnnemi(PaysanGoblin.class, 8);
        vagues.add(v1);

        //Vague 2
        Vague v2 = new Vague(2);
        v2.ajouterEnnemi(PaysanGoblin.class, 15);
        vagues.add(v2);

        //vague 3
        Vague v3 = new Vague(3);
        v3.ajouterEnnemi(PaysanGoblin.class, 3);
        v3.ajouterEnnemi(GuerrierGoblin.class, 8);
        vagues.add(v3);

        //vague 4
        Vague v4 = new Vague(4);
        v4.ajouterEnnemi(GuerrierGoblin.class, 10);
        v4.ajouterEnnemi(GoblinGuerrisseur.class, 2);
        vagues.add(v4);

        //vague 5
        Vague v5 = new Vague(5);
        v5.ajouterEnnemi(GoblinBomb.class, 4);
        v5.ajouterEnnemi(GuerrierGoblin.class, 2);
        vagues.add(v5);

        //vague 6
        Vague v6 = new Vague(6);
        v6.ajouterEnnemi(Cochon.class, 10);
        v6.ajouterEnnemi(GuerrierGoblin.class, 5);
        vagues.add(v6);

        //vague 7
        Vague v7 = new Vague(7);
        v7.ajouterEnnemi(GuerrierGoblin.class, 15);
        v7.ajouterEnnemi(GoblinBomb.class, 5);
        vagues.add(v7);

        //vague 8
        Vague v8 = new Vague(8);
        v8.ajouterEnnemi(Cochon.class, 10);
        v8.ajouterEnnemi(GuerrierGoblin.class, 5);
        v8.ajouterEnnemi(PaysanGoblin.class, 10);
        vagues.add(v8);

        //vague 9
        Vague v9 = new Vague(9);
        v9.ajouterEnnemi(Chevalier.class, 2);
        vagues.add(v9);

        //vague 10
        Vague v10 = new Vague(10);
        v10.ajouterEnnemi(Chevalier.class, 2);
        v10.ajouterEnnemi(GoblinBomb.class, 10);
        vagues.add(v10);

        //vague 11
        Vague v11 = new Vague(11);
        v11.ajouterEnnemi(Chevalier.class, 5);
        v11.ajouterEnnemi(GuerrierGoblin.class, 10);
        v11.ajouterEnnemi(PaysanGoblin.class, 20);
        vagues.add(v11);

        //vague 12
        Vague v12 = new Vague(12);
        v12.ajouterEnnemi(BossChevalier.class, 5);
        v12.ajouterEnnemi(GuerrierGoblin.class, 10);
        v12.ajouterEnnemi(Cochon.class, 10);
        v12.ajouterEnnemi(GoblinGuerrisseur.class, 2);
        vagues.add(v12);

        //vague 13
        Vague v13 = new Vague(13);
        v13.ajouterEnnemi(Golem.class, 5);
        v13.ajouterEnnemi(PaysanGoblin.class, 20);
        v13.ajouterEnnemi(GuerrierGoblin.class, 10);
        v13.ajouterEnnemi(Chevalier.class, 10);
        vagues.add(v13);

        //vague 14
        Vague v14 = new Vague(14);
        v14.ajouterEnnemi(GuerrierGoblin.class, 10);
        v14.ajouterEnnemi(Chevalier.class, 20);
        v14.ajouterEnnemi(PaysanGoblin.class, 20);
        v14.ajouterEnnemi(Golem.class, 5);
        v14.ajouterEnnemi(BossChevalier.class, 10);
        vagues.add(v14);

        //vague 15
        Vague v15 = new Vague(15);
        v15.ajouterEnnemi(GuerrierGoblin.class, 10);
        v15.ajouterEnnemi(PaysanGoblin.class, 20);
        v15.ajouterEnnemi(Golem.class, 10);
        v15.ajouterEnnemi(BossChevalier.class, 10);
        v15.ajouterEnnemi(GoblinGuerrisseur.class, 10);
        v15.ajouterEnnemi(Chevalier.class, 10);
        v15.ajouterEnnemi(RoiGoblin.class, 1);
    }





}




