package entite.Users;

import entite.Enum.UserType;

public class User {
    private int id;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;
    private String password;
    private UserType userType;

    public User(int id, String nom, String email, String telephone, String adresse, String password, UserType userType) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.password = password;
        this.userType = userType;
    }

    public User(String idU, String nom, String email) {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    // Méthodes spécifiques pour votre application, par exemple :

    public void laisserReview() {
        // Implémentation pour laisser un avis
    }

    public void consulterReview() {
        // Implémentation pour consulter les avis
    }
}


