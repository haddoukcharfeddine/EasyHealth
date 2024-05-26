package com.EasyHealth.entite;

import util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vendeur extends User {
    // Constructors, getters, setters, and methods
    public Vendeur(int id, String nom, String email, String telephone, String adresse) {
        super(id, nom, email, telephone, adresse, UserType.Vendeur);
    }

    public void ajouterPlat() {}
    public void modifierPlat() {}
    public void supprimerPlat() {}
    public void ajouterMenu() {}
    public void supprimerMenu() {}
    public void gererCommandes() {}
}


