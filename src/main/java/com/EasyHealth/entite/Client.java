package com.EasyHealth.entite;

public class Client extends User {
    private Objectif objectif;

    // Constructors, getters, setters, and methods
    public Client(int id, String nom, String email, String telephone, String adresse, Objectif objectif) {
        super(id, nom, email, telephone, adresse, UserType.Client);
        this.objectif = objectif;
    }

    public Objectif getObjectif() { return objectif; }
    public void setObjectif(Objectif objectif) { this.objectif = objectif; }

}



