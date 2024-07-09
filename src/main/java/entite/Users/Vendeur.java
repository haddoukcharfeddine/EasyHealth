package entite.Users;

import entite.Enum.UserType;
import entite.Plat;
import service.PlatService;
import util.EmailUtil; // Importation de la classe utilitaire pour l'envoi d'emails
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendeur extends User {
    List<Plat> plats = new ArrayList<>(); // Liste des plats gérés par le vendeur

    // Constructeur de la classe Vendeur
    public Vendeur(int id, String nom, String email, String telephone, String adresse, String password) {
        super(id, nom, email, telephone, adresse, password, UserType.Vendeur); // Appel du constructeur de la classe parente User
    }

    // Méthode pour supprimer un plat
    public void supprimerPlat() {
        PlatService platService = new PlatService(); // Création d'une instance de PlatService pour gérer les opérations sur les plats
        Scanner scanner = new Scanner(System.in); // Création d'un scanner pour lire l'entrée utilisateur

        System.out.println("Voulez-vous supprimer un plat par ID ou par nom ? (ID / Nom)");
        String choix = scanner.nextLine().trim().toLowerCase(); // Lecture du choix de l'utilisateur

        if (choix.equals("id")) {
            System.out.println("Entrez l'ID du plat à supprimer :");
            int idPlat = Integer.parseInt(scanner.nextLine().trim()); // Lecture de l'ID du plat à supprimer
            platService.supprimerPlatById(idPlat); // Appel de la méthode de PlatService pour supprimer le plat par ID
        } else if (choix.equals("nom")) {
            System.out.println("Entrez le nom du plat à supprimer :");
            String nomPlat = scanner.nextLine().trim(); // Lecture du nom du plat à supprimer
            platService.supprimerPlatByNom(nomPlat); // Appel de la méthode de PlatService pour supprimer le plat par nom
        } else {
            System.out.println("Choix invalide. Veuillez entrer 'ID' ou 'Nom'.");
        }
    }

    // Méthode pour ajouter un nouveau plat
    public void ajouterPlat(Plat plat) {
        PlatService platService = new PlatService(); // Création d'une instance de PlatService pour gérer les opérations sur les plats
        platService.ajouterPlat(plat); // Appel de la méthode de PlatService pour ajouter le plat

        // Envoyer un email de confirmation à l'adresse email du vendeur
        String destinataire = this.getEmail(); // Récupération de l'email du vendeur à partir de la classe parente User
        String sujet = "Confirmation d'ajout de plat";
        String contenu = "Bonjour " + this.getNom() + ",\n\nVotre plat \"" + plat.getNomPlat() + "\" a été ajouté avec succès.\n\nCordialement,\nVotre équipe.";
        EmailUtil.envoyerEmail(destinataire, sujet, contenu); // Appel de la méthode d'envoi d'email dans EmailUtil
    }

    // Méthode pour modifier un plat existant
    public void modifierPlat(Plat plat) {
        PlatService platService = new PlatService(); // Création d'une instance de PlatService pour gérer les opérations sur les plats
        platService.modifierPlat(plat); // Appel de la méthode de PlatService pour modifier le plat
    }

    // Méthode pour gérer les commandes (à implémenter selon les besoins spécifiques)
    public void gererCommandes() {
        // Cette méthode pourrait être utilisée pour gérer les commandes des plats, mais elle n'est pas encore implémentée ici
    }
}

