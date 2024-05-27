package com.EasyHealth;
import com.EasyHealth.entite.*;
import com.EasyHealth.service.UserService;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserService();

//        Client client = new Client(0, "John Dhoa", "john@example.com", "123456789", "123 Street", Objectif.Aucun);
////     userService.addUser(client);
//
//        Vendeur vendeur = new Vendeur(0, "Vendor One", "vendor1@example.com", "234567890", "789 Boulevard");
//        userService.addUser(vendeur);
//
//        Livreur livreur = new Livreur(0, "Delivery Guy", "delivery@example.com", "345678901", "101 Main Street", true);
//        userService.addUser(livreur);
//
//       ClientSport clientSport = new ClientSport(1, "John Doe", "john@example.com", "1234567890", "123 Main St", Objectif.Perdre_du_poids, 70.0F, 175.0F, 25, Sexe.Homme, Activer.Intense);
//            userService.addUser(clientSport);
//
//        User retrievedUser = userService.getUserById(9);
//        System.out.println("Retrieved User: " + retrievedUser.getNom());
        User userToUpdate = new ClientSport(1, "John Doe", "john@example.com", "123456789", "123 Main St", Objectif.Perdre_du_poids, 70.5f, 170f, 30, Sexe.Homme,Activer.Intense);
        userService.updateUser(userToUpdate);
//
//        userService.deleteUser(7);

//        User retrievedUser = userService.getUserById(8);
    }
    }

