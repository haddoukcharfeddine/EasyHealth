package com.EasyHealth.entite.Users;

import com.EasyHealth.entite.Enum.UserType;
import com.EasyHealth.entite.Menu;
import com.EasyHealth.entite.Plat;
import com.EasyHealth.entite.Users.User;
import com.EasyHealth.service.PlatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendeur extends User {
    List<Plat> plats= new ArrayList<>();
    // Constructors, getters, setters, and methods
    public Vendeur(int id, String nom, String email, String telephone, String adresse) {
        super(id, nom, email, telephone, adresse, UserType.Vendeur);
    }

    public void ajouterPlat() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the Vendeur to enter the name of the Plat
        System.out.println("Entrer le nom du Plat:");
        String nomPlat = scanner.nextLine();

        // Prompt the Vendeur to enter other details of the Plat (description, prix, protein, calories, categorie)
        System.out.println("Entrer la description:");
        String description = scanner.nextLine();

        System.out.println("Entrer le Prix:");
        double prix = Double.parseDouble(scanner.nextLine());

        System.out.println("Entrer le nombre de protein:");
        int protein = Integer.parseInt(scanner.nextLine());

        System.out.println("Entrer le nombre des calories:");
        int calories = Integer.parseInt(scanner.nextLine());

        System.out.println("Entrer la Categorie:");
        String categorie = scanner.nextLine();

        // Create a new Plat object with the entered details
        Plat plat = new Plat(0, nomPlat, description, prix, protein, calories, this.getId(), categorie);

        // Add the Plat using the UserService
        PlatService platService = new PlatService();
        platService.ajouterPlat(plat);
    }

    // Method to delete an existing plat

    public void supprimerPlat() {
        PlatService platService = new PlatService();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez-vous supprimer un plat par ID ou par nom ? (ID / Nom)");
        String choix = scanner.nextLine().trim().toLowerCase();

        if (choix.equals("id")) {
            System.out.println("Entrez l'ID du plat à supprimer :");
            int idPlat = Integer.parseInt(scanner.nextLine().trim());
            platService.supprimerPlatById(idPlat);
        } else if (choix.equals("nom")) {
            System.out.println("Entrez le nom du plat à supprimer :");
            String nomPlat = scanner.nextLine().trim();
            platService.supprimerPlatByNom(nomPlat);
        } else {
            System.out.println("Choix invalide. Veuillez entrer 'ID' ou 'Nom'.");
        }
    }

    // Method to add a new menu
    public void ajouterMenu(String nomMenu, List<Plat> plats) {
        PlatService platService = new PlatService();
        Menu menu = new Menu(0, nomMenu, plats);
        platService.ajouterMenu(menu);
    }

    // Method to modify an existing menu
    public void modifierMenu(int idM, String nomMenu, List<Plat> plats) {
        PlatService platService = new PlatService();
        Menu menu = new Menu(idM, nomMenu, plats);
        platService.modifierMenu(menu);
    }

    // Method to delete an existing menu
    public void supprimerMenu(int idM) {
        PlatService platService = new PlatService();
        platService.supprimerMenu(idM);
    }

    public void gererCommandes() {}
}


