package entite.Users;

import entite.Enum.Activer;
import entite.Enum.Objectif;
import entite.Enum.Sexe;

public class ClientSport extends Client {
    private static float poids;
    private static float taille;
    private static int age;
    private static Sexe sexe;
    private static Activer activer;


    public ClientSport(int id, String nom, String email, String telephone, String adresse, String password, Objectif objectif, float poids, float taille, int age, Sexe sexe, Activer activer) {
        super(id, nom, email, telephone, adresse, password, objectif);
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.activer = activer;
    }


    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public float getTaille() {
        return taille;
    }

    public void setTaille(float taille) {
        this.taille = taille;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Activer getActiver() {
        return activer;
    }

    public void setActiver(Activer activer) {
        this.activer = activer;
    }

    public static int[] calculerBesoinsNutritionnels() {
        double bmr;
        if (sexe == Sexe.HOMME) {
            bmr = 88.362 + (13.397 * poids) + (4.799 * taille) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * poids) + (3.098 * taille) - (4.330 * age);
        }

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

        if (getObjectif() == Objectif.Perdre_du_poids) {
            tee -= 500;
        } else if (getObjectif() == Objectif.Prendre_du_poids) {
            tee += 500;
        }

        // Calculate daily protein needs
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

        return new int[]{(int) Math.round(tee), (int) Math.round(proteinNeeds)};
    }
}
