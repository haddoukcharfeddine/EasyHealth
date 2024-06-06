package entite.Users;

import entite.Enum.UserType;
import entite.Menu;
import entite.Plat;
import service.PlatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendeur extends User {
    List<Plat> plats= new ArrayList<>();

    public Vendeur(int id, String nom, String email, String telephone, String adresse,String password) {
        super(id, nom, email, telephone, adresse,password, UserType.Vendeur);
    }





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


    public void ajouterMenu(String nomMenu, List<Plat> plats) {
        PlatService platService = new PlatService();
        Menu menu = new Menu(0, nomMenu, plats);
        platService.ajouterMenu(menu);
    }


    public void modifierMenu(int idM, String nomMenu, List<Plat> plats) {
        PlatService platService = new PlatService();
        Menu menu = new Menu(idM, nomMenu, plats);
        platService.modifierMenu(menu);
    }


    public void supprimerMenu(int idM) {
        PlatService platService = new PlatService();
        platService.supprimerMenu(idM);
    }

    public void gererCommandes() {}
}


