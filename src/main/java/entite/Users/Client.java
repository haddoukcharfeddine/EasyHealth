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
    private Objectif objectif;
    private List<Commande> historiqueCommandes;

    // Constructors, getters, setters, and methods
    public Client(int id, String nom, String email, String telephone, String adresse,String password, Objectif objectif) {
        super(id, nom, email, telephone, adresse,password, UserType.Client);
        this.objectif = objectif;
    }

    public Objectif getObjectif() { return objectif; }
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
        Scanner scanner = new Scanner(System.in);
        List<Plat> plats = Menu.getPlats(); // Assuming Menu is a class with static method getPlats()
        List<Plat> platsCommandes = new ArrayList<>();
        String continuer;

        do {
            System.out.print("Entrez l'ID du plat que vous souhaitez commander: ");
            int idP = scanner.nextInt();
            scanner.nextLine(); // consume newline

            Plat platCommande = null;
            for (Plat plat : plats) {
                if (plat.getIdP() == idP) {
                    platCommande = plat;
                    break;
                }
            }

            if (platCommande != null) {
                platsCommandes.add(platCommande);
                System.out.println("Plat ajouté à la commande: " + platCommande.getNomPlat());
            } else {
                System.out.println("ID du plat non trouvé.");
            }

            System.out.print("Voulez-vous commander un autre plat? (oui/non): ");
            continuer = scanner.nextLine();
        } while (continuer.equalsIgnoreCase("oui"));

        if (!platsCommandes.isEmpty()) {
            Date dateCommande = new Date(); // Assuming current date as order date
            double prixTotal = platsCommandes.stream().mapToDouble(Plat::getPrix).sum();
            int idUVendeur = platsCommandes.get(0).getIdUVendeur(); // Assuming all plats are from the same vendor
            int idULivreur = 1; // Placeholder for delivery person ID

            Commande nouvelleCommande = new Commande(0, dateCommande, prixTotal, platsCommandes, getId(), idUVendeur, idULivreur);
            historiqueCommandes.add(nouvelleCommande);
            System.out.println("Commande passée avec succès.");
        } else {
            System.out.println("Aucune commande n'a été passée.");
        }
    }

    public void payerCommande() {
        // Implementation here
    }

    public void consulterHistoriqueCommandes() {
        // Implementation here
    }

}



