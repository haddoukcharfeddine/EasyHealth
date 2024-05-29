package com.EasyHealth.entite.Users;

import com.EasyHealth.entite.Enum.Activer;
import com.EasyHealth.entite.Enum.Objectif;
import com.EasyHealth.entite.Enum.Sexe;
import com.EasyHealth.entite.Enum.UserType;
import com.EasyHealth.service.UserService;

import java.util.Scanner;

public  class User {
    private static int id;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;
    private UserType userType;

    // Constructors, getters, setters, and methods
    public User(int id, String nom, String email, String telephone, String adresse, UserType userType) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.userType = userType;
    }

    // Getters and setters
    public static int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }
    public static void creerUnCompte() {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);
        int id = 0;
        System.out.println("Entrez votre nom:");
        String nom = scanner.nextLine();

        System.out.println("Entrez votre adresse e-mail:");
        String email = scanner.nextLine();

        System.out.println("Entrez votre numéro de téléphone:");
        String telephone = scanner.nextLine();

        System.out.println("Entrez votre adresse:");
        String adresse = scanner.nextLine();

        System.out.println("Choisissez votre type d'utilisateur :");
        System.out.println("1. CLIENT");
        System.out.println("2. LIVREUR");
        System.out.println("3. VENDEUR");
        int userTypeChoice = scanner.nextInt();
        UserType userType;

        switch (userTypeChoice) {
            case 1:
                userType = UserType.Client;
                break;
            case 2:
                userType = UserType.Livreur;
                break;
            case 3:
                userType = UserType.Vendeur;
                break;
            default:
                System.out.println("Choix invalide, réessayez.");
                return;
        }

        switch (userType) {
            case Client:

                System.out.println("Choisissez votre objectif:");
                System.out.println("1. PERDRE_DU_POIDS");
                System.out.println("2. PRENDRE_DU_POIDS");
                System.out.println("3. AUCUN");
                int objectifChoice = scanner.nextInt();
                Objectif objectif;
                switch (objectifChoice) {
                    case 1:
                        objectif = Objectif.Perdre_du_poids;
                        break;
                    case 2:
                        objectif = Objectif.Prendre_du_poids;
                        break;
                    case 3:
                        objectif = Objectif.Aucun;
                        break;
                    default:
                        System.out.println("Choix invalide, réessayez.");
                        return;
                }

                if (objectif == Objectif.Perdre_du_poids || objectif == Objectif.Prendre_du_poids) {
                    System.out.println("Entrez votre poids:");
                    float poids = scanner.nextFloat();
                    System.out.println("Entrez votre taille:");
                    float taille = scanner.nextFloat();
                    System.out.println("Entrez votre âge:");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Choisissez votre sexe:");
                    System.out.println("1. HOMME");
                    System.out.println("2. FEMME");
                    System.out.println("3. AUTRE");

                    int sexeChoice = scanner.nextInt();
                    Sexe sexe;
                    switch (sexeChoice) {
                        case 1:
                            sexe = Sexe.HOMME;
                            break;
                        case 2:
                            sexe = Sexe.FEMME;
                            break;

                        default:
                            System.out.println("Choix invalide, réessayez.");
                            return; // Quitter la méthode en cas de choix invalide
                    }

                    System.out.println("Choisissez votre niveau d'activité:");
                    System.out.println("1. SEDENTAIRE");
                    System.out.println("2. INTENSE");
                    System.out.println("3. LEGERE");
                    System.out.println("4. MODEREE");
                    int activerChoice = scanner.nextInt();
                    Activer activer;
                    switch (activerChoice) {
                        case 1:
                            activer = Activer.SEDENTAIRE;
                            break;
                        case 2:
                            activer = Activer.INTENSE;
                            break;

                        case 3:
                            activer = Activer.LEGERE;
                            break;
                        case 4:
                            activer = Activer.MODEREE;
                            break;
                        default:
                            System.out.println("Choix invalide, réessayez.");
                            return;
                    }

                    ClientSport clientSport = new ClientSport(id, nom, email, telephone, adresse, objectif, poids, taille, age, sexe, activer);
                    clientSport.setObjectif(objectif);
                    clientSport.setPoids(poids);
                    clientSport.setTaille(taille);
                    clientSport.setAge(age);
                    clientSport.setSexe(sexe);
                    clientSport.setActiver(activer);
                    userService.addUser(clientSport);
                } else {
                    Client client = new Client(id, nom, email, telephone, adresse, objectif);
                    client.setObjectif(objectif);
                    userService.addUser(client);
                }
                break;
            case Livreur:
                System.out.println("Choisissez votre disponibilité:");
                System.out.println("1. Disponible");
                System.out.println("2. Non disponible");
                int disponibiliteChoice = scanner.nextInt();
                boolean disponible;
                switch (disponibiliteChoice) {
                    case 1:
                        disponible = true;
                        break;
                    case 2:
                        disponible = false;
                        break;
                    default:
                        System.out.println("Choix invalide, réessayez.");
                        return;
                }

                Livreur livreur = new Livreur(id, nom, email, telephone, adresse, disponible);

                livreur.setDisponible(disponible);
                userService.addUser(livreur);
                break;
            case Vendeur:
                Vendeur vendeur = new Vendeur(id, nom, email, telephone, adresse);
                userService.addUser(vendeur);
        }
    }

    public void laisserReview() {
        // implementation
    }

    public void consulterReview() {
        // implementation
    }
}




