package entite.Users;

import entite.Enum.Activer;
import entite.Enum.Objectif;
import entite.Enum.Sexe;
import entite.Enum.UserType;
import service.UserService;

import java.util.Scanner;

public  class User {
    private static int id;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;
    private String password;
    private UserType userType;

    // Constructors, getters, setters, and methods
    public User(int id, String nom, String email, String telephone,String adresse,String password ,UserType userType) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.userType = userType;
        this.password=password;
    }

    // Getters and setters

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public void laisserReview() {
        // implementation
    }

    public void consulterReview() {
        // implementation
    }
}




