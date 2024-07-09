package entite.Users;

import entite.Commande;
import entite.Enum.Objectif;
import entite.Enum.UserType;
import entite.Plat;

import java.util.ArrayList;
import java.util.List;

// Classe Client qui hérite de la classe User
public class Client extends User {
    private static Objectif objectif; // Objectif nutritionnel du client (perte de poids, gain de masse, etc.)
    private List<Commande> historiqueCommandes; // Historique des commandes du client
    private int besoinsCaloriques; // Besoins caloriques actuels du client
    private List<Plat> plats; // Liste des plats disponibles

    // Constructeur de la classe Client
    public Client(int id, String nom, String email, String telephone, String adresse, String password, Objectif objectif) {
        super(id, nom, email, telephone, adresse, password, UserType.Client);
        this.objectif = objectif;
        this.historiqueCommandes = new ArrayList<>();
        this.plats = new ArrayList<>(); // Initialise la liste des plats à une liste vide
    }


    // Getter pour l'objectif du client
    public static Objectif getObjectif() {
        return objectif;
    }

    // Setter pour l'objectif du client
    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }

    // Getter pour les besoins caloriques du client
    public int getBesoinsCaloriques() {
        return besoinsCaloriques;
    }

    // Setter pour les besoins caloriques du client
    public void setBesoinsCaloriques(int besoinsCaloriques) {
        this.besoinsCaloriques = besoinsCaloriques;
    }

    // Getter pour la liste des plats
    public List<Plat> getPlats() {
        return plats;
    }

    // Setter pour la liste des plats
    public void setPlats(List<Plat> plats) {
        this.plats = plats;
    }

    // Méthode pour afficher la liste des plats
    public void afficherPlats() {
        System.out.println("===== Plats =====");
        for (Plat plat : plats) { // Parcourt chaque plat dans la liste des plats
            System.out.println("ID: " + plat.getIdP());
            System.out.println("Nom: " + plat.getNomPlat());
            System.out.println("Description: " + plat.getDescription());
            System.out.println("Prix: " + plat.getPrix() + " EUR");
            System.out.println("Protéines: " + plat.getProtein() + "g");
            System.out.println("Calories: " + plat.getCalories() + " kcal");
            System.out.println("Catégorie: " + plat.getCategorie());
            System.out.println("=================");
        }
    }

    // Méthode pour choisir un plat à partir de son ID
    public void choisirPlat(int platId) {
        Plat platChoisi = null;
        for (Plat plat : plats) { // Parcourt chaque plat dans la liste des plats
            if (plat.getIdP() == platId) { // Si l'ID correspond
                platChoisi = plat;
                break; // Sort de la boucle une fois le plat trouvé
            }
        }
        if (platChoisi != null) { // Si un plat a été trouvé
            ajusterCalories(platChoisi.getCalories()); // Ajuste les besoins caloriques du client
            System.out.println("Vous avez choisi : " + platChoisi.getNomPlat());
            System.out.println("Description : " + platChoisi.getDescription());
            System.out.println("Prix : " + platChoisi.getPrix() + " EUR");
            System.out.println("Protéines : " + platChoisi.getProtein() + "g");
            System.out.println("Calories : " + platChoisi.getCalories() + " kcal");
            System.out.println("Catégorie : " + platChoisi.getCategorie());
            System.out.println("Vos besoins caloriques actuels : " + besoinsCaloriques + " kcal");
        } else {
            System.out.println("Plat non trouvé.");
        }
    }

    // Méthode privée pour ajuster les besoins caloriques en fonction des calories du plat
    private void ajusterCalories(int caloriesPlat) {
        if (objectif == Objectif.PERTE_POIDS) { // Si l'objectif est de perdre du poids
            besoinsCaloriques -= caloriesPlat;
        } else if (objectif == Objectif.GAIN_MASSE) { // Si l'objectif est de gagner de la masse
            besoinsCaloriques += caloriesPlat;
        }
        // Vous pouvez ajuster les besoins caloriques différemment selon l'objectif
    }

    // Méthode pour passer une commande (à implémenter)
    public void passerCommande() {
    }

    // Méthode pour payer une commande (à implémenter)
    public void payerCommande() {
    }

    // Méthode pour consulter l'historique des commandes (à implémenter)
    public void consulterHistoriqueCommandes() {
    }
}

