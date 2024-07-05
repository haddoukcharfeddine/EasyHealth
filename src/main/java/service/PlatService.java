package service;

import entite.Plat;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Service pour la gestion des plats dans la base de données
public class PlatService implements PService<Plat> {
    private Connection cnx; // Connexion à la base de données

    // Constructeur qui initialise la connexion à la base de données
    public PlatService() {
        cnx = DataSource.getInstance().getConnexion(); // Obtient la connexion depuis DataSource
    }

    // Méthode pour ajouter un plat dans la base de données
    @Override
    public void ajouterPlat(Plat plat) {
        String query = "INSERT INTO Plat (nomPlat, description, prix, protein, calories, idUVendeur, Categorie, imageData) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, plat.getNomPlat());
            stmt.setString(2, plat.getDescription());
            stmt.setDouble(3, plat.getPrix());
            stmt.setInt(4, plat.getProtein());
            stmt.setInt(5, plat.getCalories());
            stmt.setString(6, plat.getIdUVendeur());
            stmt.setString(7, plat.getCategorie());
            stmt.setBytes(8, plat.getImageData()); // Insère les données d'image sous forme de tableau d'octets
            stmt.executeUpdate(); // Exécute la requête d'insertion
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion des exceptions SQL
        }
    }

    // Méthode pour modifier un plat existant dans la base de données
    @Override
    public void modifierPlat(Plat plat) {
        String query = "UPDATE Plat SET nomPlat = ?, description = ?, prix = ?, protein = ?, calories = ? WHERE idP = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, plat.getNomPlat());
            stmt.setString(2, plat.getDescription());
            stmt.setDouble(3, plat.getPrix());
            stmt.setInt(4, plat.getProtein());
            stmt.setInt(5, plat.getCalories());
            stmt.setInt(6, plat.getIdP());
            stmt.executeUpdate(); // Exécute la requête de mise à jour
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion des exceptions SQL
        }
    }

    // Méthode pour récupérer tous les plats d'un vendeur spécifique par son numéro de téléphone
    public List<Plat> getPlatsByVendeurTelephone(String telephone) {
        List<Plat> plats = new ArrayList<>();
        String query = "SELECT * FROM Plat WHERE idUVendeur = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, telephone);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idP = rs.getInt("idP");
                    String nomPlat = rs.getString("nomPlat");
                    String description = rs.getString("description");
                    double prix = rs.getDouble("prix");
                    int protein = rs.getInt("protein");
                    int calories = rs.getInt("calories");
                    String idUVendeur = rs.getString("idUVendeur");
                    String categorie = rs.getString("Categorie");
                    byte[] imageData = rs.getBytes("imageData");

                    Plat plat = new Plat(idP, nomPlat, description, prix, protein, calories, idUVendeur, categorie, imageData);
                    plats.add(plat); // Ajoute le plat à la liste des plats récupérés
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion des exceptions SQL
        }
        return plats; // Retourne la liste des plats du vendeur
    }

    // Méthode pour supprimer un plat de la base de données par son identifiant
    @Override
    public void supprimerPlatById(int idP) {
        String query = "DELETE FROM Plat WHERE idP = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, idP);
            stmt.executeUpdate(); // Exécute la requête de suppression
            System.out.println("Plat supprimé avec succès (ID: " + idP + ")");
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion des exceptions SQL
        }
    }

    // Méthode pour supprimer un plat de la base de données par son nom
    @Override
    public void supprimerPlatByNom(String nomPlat) {
        String query = "DELETE FROM Plat WHERE nomPlat = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, nomPlat);
            stmt.executeUpdate(); // Exécute la requête de suppression
            System.out.println("Plat supprimé avec succès (Nom: " + nomPlat + ")");
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion des exceptions SQL
        }
    }

    // Méthode pour récupérer tous les plats de la base de données
    @Override
    public List<Plat> getAllPlats() {
        List<Plat> plats = new ArrayList<>();
        String query = "SELECT * FROM Plat";
        try (Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int idP = rs.getInt("idP");
                String nomPlat = rs.getString("nomPlat");
                String description = rs.getString("description");
                double prix = rs.getDouble("prix");
                int protein = rs.getInt("protein");
                int calories = rs.getInt("calories");
                String idUVendeur = rs.getString("idUVendeur");
                String categorie = rs.getString("Categorie");
                byte[] imageData = rs.getBytes("imageData");

                Plat plat = new Plat(idP, nomPlat, description, prix, protein, calories, idUVendeur, categorie, imageData);
                plats.add(plat); // Ajoute le plat à la liste des plats récupérés
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion des exceptions SQL
        }
        return plats; // Retourne la liste de tous les plats
    }
}
