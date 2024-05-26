package com.EasyHealth.entite;

public class ClientSport extends Client {
    private float poids;
    private float taille;
    private int age;
    private Sexe sexe;
    private Activer activer;

    // Constructors, getters, setters, and methods
    public ClientSport(int id, String nom, String email, String telephone, String adresse, Objectif objectif, float poids, float taille, int age, Sexe sexe, Activer activer) {
        super(id, nom, email, telephone, adresse, objectif);
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.activer = activer;
    }

    public float getPoids() { return poids; }
    public void setPoids(float poids) { this.poids = poids; }
    public float getTaille() { return taille; }
    public void setTaille(float taille) { this.taille = taille; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public Sexe getSexe() { return sexe; }
    public void setSexe(Sexe sexe) { this.sexe = sexe; }
    public Activer getActiver() { return activer; }
    public void setActiver(Activer activer) { this.activer = activer; }
}
