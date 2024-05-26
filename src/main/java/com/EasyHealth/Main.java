package com.EasyHealth;
import com.EasyHealth.entite.*;
import com.EasyHealth.service.UserService;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserService();
        Client client = new Client(0, "John Doe", "john@example.com", "123456789", "123 Street", Objectif.Aucun);
//      userService.addUser(client);
//
//        Vendeur vendeur = new Vendeur(0, "Vendor One", "vendor1@example.com", "234567890", "789 Boulevard");
//        userService.addUser(vendeur);
//
//        Livreur livreur = new Livreur(0, "Delivery Guy", "delivery@example.com", "345678901", "101 Main Street", true);
//        userService.addUser(livreur);
//
//        ClientSport clientSport = new ClientSport(0, "Jane Doe", "jane@example.com", "987654321", "456 Avenue", Objectif.Prendre_du_poids, 65.0f, 170.0f, 25, Sexe.Femme, Activer.Modérée);
//        userService.addUser(clientSport);
//
//        User retrievedUser = userService.getUserById(1);
//        System.out.println("Retrieved User: " + retrievedUser.getNom());

        client.setNom("John whooo");
        userService.updateUser(client);
        
        userService.deleteUser(7);
    }

}