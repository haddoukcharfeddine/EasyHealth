package com.EasyHealth;
import com.EasyHealth.entite.Users.User;
import com.EasyHealth.entite.Users.Vendeur;
import com.EasyHealth.service.UserService;


import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserService();
// creat Users

//        Client client = new Client(0, "John Dhoa", "john@example.com", "123456789", "123 Street", Objectif.Aucun);
//     userService.addUser(client);

//        Vendeur vendeur = new Vendeur(0, "Vendor One", "vendor1@example.com", "234567890", "789 Boulevard");
//        userService.addUser(vendeur);

//        Livreur livreur = new Livreur(0, "Delivery Guy", "delivery@example.com", "345678901", "101 Main Street", true);
//        userService.addUser(livreur);

//       ClientSport clientSport = new ClientSport(1, "michel Doe", "john@example.com", "12345567890", "123 Main St", Objectif.Prendre_du_poids, 70.0F, 175.0F, 25, Sexe.HOMME, Activer.INTENSE);
//            userService.addUser(clientSport);
//        ClientSport clientSport = new ClientSport(1, "John Doe", "john@example.com", "1234567890", "123 Main St", Objectif.Prendre_du_poids, 70.0F, 175.0F, 25, Sexe.HOMME, Activer.INTENSE);
//        userService.addUser(clientSport);


//getUserById
//        User user = userService.getUserById(48);

        //userToUpdate
//        if (user != null) {
//       User userToUpdate =new Client(user.getId(), "John Dhoa", "john@example.com", "123456789", "123 Street", Objectif.Aucun);
//       userService.updateUser(userToUpdate);
//       System.out.println("User updated successfully.");
//        }else {
//        System.out.println("User not found.");
//    }

        //deleteUser
//        userService.deleteUser(7);

        //getUserByTelephone
//        User user = userService.getUserByTelephone("123456789");

        //deleteUserByTelephone
//        userService.deleteUserByTelephone("123456789");


//   User.creerUnCompte();

        //creer plat
//        Vendeur vendeur = new Vendeur(43, "Vendor One", "vendor1@example.com", "234567890", "789 Boulevard");
//        vendeur.ajouterPlat();


//        vendeur.supprimerPlat();



    }
}


