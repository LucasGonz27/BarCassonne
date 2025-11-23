package Epi.BarCassonne.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import Epi.BarCassonne.game.Entities.Towers.TypeTour;
/**
 * Gestionnaire centralisé pour tous les sons du jeu.
 * Charge et gère les sons des tours et autres effets sonores.
 */
public class SoundManager {

    // ========================================================================
    // CHAMPS - SONS DES TOURS
    // ========================================================================

    /** Son de tir de flèche (pour les tours Archer) */
    private static Sound sonFleche = null;

    /** Son de tir de canon (pour les tours Canon) */
    private static Sound sonCanon = null;

    /** Son de sort magique (pour les tours Magie) */
    private static Sound sonMagie = null;

    /** Son de mort d'ennemi */
    private static Sound sonMortEnnemi = null;

    /*Son du menu */
    private static Sound musiqueMenu = null;

    /** Musique de fond du jeu */
    private static Sound musiqueJeu = null;

    /** ID de la musique en cours de lecture (pour pouvoir l'arrêter) */
    private static long musiqueId = -1;

    /** Indique si les sons ont été initialisés */
    private static boolean initialise = false;

    // ========================================================================
    // INITIALISATION
    // ========================================================================

    /**
     * Initialise tous les sons du jeu.
     * Doit être appelé une fois au démarrage du jeu.
     */
    public static void initialiser() {
        if (initialise) {
            return; 
        }

        sonFleche = chargerSon("sounds/fleche");
        sonCanon = chargerSon("sounds/canon");
        sonMagie = chargerSon("sounds/magie");
        sonMortEnnemi = chargerSon("sounds/mort");
        musiqueMenu = chargerSon("sounds/MusiqueAccueil");
        musiqueJeu = chargerSon("sounds/musiqueJeux");

        initialise = true;
    }

    /**
     * Méthode helper pour charger un son en essayant différents formats.
     * @param cheminBase Le chemin de base du fichier (sans extension)
     * @return Le son chargé, ou null si aucun fichier n'a été trouvé
     */
    private static Sound chargerSon(String cheminBase) {
        try {
            String[] extensions = {".mp3", ".wav", ".ogg"};

            for (String ext : extensions) {
                String chemin = cheminBase + ext;
                try {
                    if (Gdx.files.internal(chemin).exists()) {
                        Sound son = Gdx.audio.newSound(Gdx.files.internal(chemin));
                        System.out.println("Son chargé avec succès: " + chemin);
                        return son;
                    }
                } catch (Exception e) {
                    System.out.println("Erreur lors du chargement de " + chemin + ": " + e.getMessage());
                }
            }

            System.out.println("Fichier son non trouvé pour: " + cheminBase + " (essayé .mp3, .wav, .ogg)");
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du son: " + cheminBase + " - " + e.getMessage());
            return null;
        }
    }

    // ========================================================================
    // MÉTHODES PUBLIQUES - JOUER LES SONS
    // ========================================================================

    /**
     * Joue le son de flèche.
     * @param volume Volume du son (0.0 à 1.0)
     */
    public static void jouerSonFleche(float volume) {
        if (sonFleche != null) {
            sonFleche.play(volume);
        }
    }

    /**
     * Joue le son de canon.
     * @param volume Volume du son (0.0 à 1.0)
     */
    public static void jouerSonCanon(float volume) {
        if (sonCanon != null) {
            sonCanon.play(volume);
        }
    }

    /**
     * Joue le son de magie.
     * @param volume Volume du son (0.0 à 1.0)
     */
    public static void jouerSonMagie(float volume) {
        if (sonMagie != null) {
            sonMagie.play(volume);
        }
    }

    /**
     * Joue le son correspondant au type de tour.
     * @param typeTour Le type de tour (ARCHER, CANON, MAGIE)
     * @param volume Volume du son (0.0 à 1.0)
     */
    public static void jouerSonTour(TypeTour typeTour, float volume) {
        switch (typeTour) {
            case ARCHER:
                jouerSonFleche(volume);
                break;
            case CANON:
                jouerSonCanon(volume);
                break;
            case MAGIE:
                jouerSonMagie(volume);
                break;
            case FORGERON:
                // Les forgerons n'attaquent pas, donc pas de son
                break;
        }
    }

    /**
     * Joue le son de mort d'ennemi.
     * @param volume Volume du son (0.0 à 1.0)
     */
    public static void jouerSonMortEnnemi(float volume) {
        if (sonMortEnnemi != null) {
            sonMortEnnemi.play(volume);
        }
    }

    /** Arrête toute musique en cours */
    private static void arreterMusique() {
        if (musiqueId != -1) {
            if (musiqueMenu != null) musiqueMenu.stop(musiqueId);
            if (musiqueJeu != null) musiqueJeu.stop(musiqueId);
            musiqueId = -1;
        }
    }

    /** Démarre la musique du menu */
    public static void demarrerMusiqueMenu(float volume) {
        arreterMusique();
        if (musiqueMenu != null) {
            musiqueId = musiqueMenu.loop(volume);
            System.out.println("Musique du menu démarrée avec volume: " + volume);
        } else {
            System.out.println("ERREUR: musiqueMenu est null !");
        }
    }

    /** Arrête la musique du menu */
    public static void arreterMusiqueMenu() {
        arreterMusique();
    }

    /** Démarre la musique du jeu */
    public static void demarrerMusiqueJeu(float volume) {
        arreterMusique();
        if (musiqueJeu != null) musiqueId = musiqueJeu.loop(volume);
    }

    /** Arrête la musique du jeu */
    public static void arreterMusiqueJeu() {
        arreterMusique();
    }

    // ========================================================================
    // NETTOYAGE
    // ========================================================================

    /**
     * Libère toutes les ressources audio.
     * À appeler lors de la fermeture du jeu.
     */
    public static void dispose() {
        if (sonFleche != null) {
            sonFleche.dispose();
            sonFleche = null;
        }
        if (sonCanon != null) {
            sonCanon.dispose();
            sonCanon = null;
        }
        if (sonMagie != null) {
            sonMagie.dispose();
            sonMagie = null;
        }
        if (sonMortEnnemi != null) {
            sonMortEnnemi.dispose();
            sonMortEnnemi = null;
        }
        arreterMusique();
        if (musiqueMenu != null) {
            musiqueMenu.dispose();
            musiqueMenu = null;
        }
        if (musiqueJeu != null) {
            musiqueJeu.dispose();
            musiqueJeu = null;
        }
        initialise = false;
    }
}

