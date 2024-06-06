package entite.Users;

import entite.Commande;
import entite.Enum.Objectif;
import entite.Enum.UserType;
import entite.Menu;
import entite.Plat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Client extends User {
    private static Objectif objectif;
    private List<Commande> historiqueCommandes;


    public Client(int id, String nom, String email, String telephone, String adresse,String password, Objectif objectif) {
        super(id, nom, email, telephone, adresse,password, UserType.Client);
        this.objectif = objectif;
    }

    public static Objectif getObjectif() { return objectif; }
    public void setObjectif(Objectif objectif) { this.objectif = objectif; }
    public void consulterMenu() {
        List<Plat> plats = Menu.getPlats();
        System.out.println("===== Menu =====");
        for (Plat plat : plats) {
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

    public void passerCommande() {
    }

    public void payerCommande() {

    }

    public void consulterHistoriqueCommandes() {

    }

}



