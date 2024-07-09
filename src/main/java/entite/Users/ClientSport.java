package entite.Users;

import entite.Enum.Activer;
import entite.Enum.Objectif;
import entite.Enum.Sexe;

// Classe ClientSport qui hérite de la classe Client
public class ClientSport extends Client {
    // Attributs spécifiques à ClientSport
    private static float poids;     // Poids du client sportif
    private static float taille;    // Taille du client sportif
    private static int age;         // Âge du client sportif
    private static Sexe sexe;       // Sexe du client sportif
    private static Activer activer; // Niveau d'activité physique du client sportif

    // Constructeur de la classe ClientSport
    public ClientSport(int id, String nom, String email, String telephone, String adresse, String password, Objectif objectif, float poids, float taille, int age, Sexe sexe, Activer activer) {
        super(id, nom, email, telephone, adresse, password, objectif); // Appel au constructeur de la classe parente Client
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.activer = activer;
    }

    // Getter pour le poids du client sportif
    public float getPoids() {
        return poids;
    }

    // Setter pour le poids du client sportif
    public void setPoids(float poids) {
        this.poids = poids;
    }

    // Getter pour la taille du client sportif
    public float getTaille() {
        return taille;
    }

    // Setter pour la taille du client sportif
    public void setTaille(float taille) {
        this.taille = taille;
    }

    // Getter pour l'âge du client sportif
    public int getAge() {
        return age;
    }

    // Setter pour l'âge du client sportif
    public void setAge(int age) {
        this.age = age;
    }

    // Getter pour le sexe du client sportif
    public Sexe getSexe() {
        return sexe;
    }

    // Setter pour le sexe du client sportif
    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    // Getter pour le niveau d'activité physique du client sportif
    public Activer getActiver() {
        return activer;
    }

    // Setter pour le niveau d'activité physique du client sportif
    public void setActiver(Activer activer) {
        this.activer = activer;
    }

    // Méthode pour calculer les besoins nutritionnels du client sportif
    public static int[] calculerBesoinsNutritionnels() {
        // Calcul du métabolisme de base (BMR)
        double bmr;
        if (sexe == Sexe.HOMME) {
            bmr = 88.362 + (13.397 * poids) + (4.799 * taille) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * poids) + (3.098 * taille) - (4.330 * age);
        }

        // Calcul du besoin énergétique total (TEE) en fonction du niveau d'activité physique
        double tee;
        switch (activer) {
            case SEDENTAIRE:
                tee = bmr * 1.2;
                break;
            case LEGERE:
                tee = bmr * 1.375;
                break;
            case MODEREE:
                tee = bmr * 1.55;
                break;
            case INTENSE:
                tee = bmr * 1.725;
                break;
            default:
                tee = bmr;
        }

        // Ajustement du TEE en fonction de l'objectif (perte de poids, gain de masse, etc.)
        if (getObjectif() == Objectif.PERTE_POIDS) {
            tee -= 500;
        } else if (getObjectif() == Objectif.GAIN_MASSE) {
            tee += 500;
        }

        // Calcul des besoins quotidiens en protéines en fonction du niveau d'activité physique
        double proteinNeeds;
        switch (activer) {
            case SEDENTAIRE:
                proteinNeeds = poids * 0.8;
                break;
            case LEGERE:
                proteinNeeds = poids * 1.0;
                break;
            case MODEREE:
                proteinNeeds = poids * 1.2;
                break;
            case INTENSE:
                proteinNeeds = poids * 1.4;
                break;
            default:
                proteinNeeds = poids * 0.8;
        }

        // Arrondir et retourner les besoins calculés sous forme de tableau d'entiers
        return new int[]{(int) Math.round(tee), (int) Math.round(proteinNeeds)};
    }
}
