package service;

import entite.Menu;
import entite.Plat;
import util.DataSource;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public  class PlatService implements PService<Plat> {
    private Connection cnx;

    public PlatService() {
        cnx= DataSource.getInstance().getConnexion();
    }

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
            stmt.setBytes(8, plat.getImageData()); // Set image as a byte array
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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
                plats.add(plat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plats;
    }
    public List<Plat> getPlatsByVendeurTelephone(String telephone) {
        List<Plat> plats = new ArrayList<>();
        String query = "SELECT * FROM Plat WHERE idUVendeur = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, telephone);
            ResultSet rs = stmt.executeQuery();
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
                plats.add(plat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plats;
    }
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
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerPlatById(int idP) {
        String query = "DELETE FROM Plat WHERE idP = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, idP);
            stmt.executeUpdate();
            System.out.println("Plat supprimé avec succès (ID: " + idP + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void supprimerPlatByNom(String nomPlat) {
        String query = "DELETE FROM Plat WHERE nomPlat = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, nomPlat);
            stmt.executeUpdate();
            System.out.println("Plat supprimé avec succès (Nom: " + nomPlat + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String platIdsToString(List<Plat> plats) {
        return plats.stream()
                .map(plat -> String.valueOf(plat.getIdP()))
                .collect(Collectors.joining(","));
    }

    // menu
    @Override
    public void ajouterMenu(Menu menu) {
        String query = "INSERT INTO Menu (nomMenu, platIds) VALUES (?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, menu.getNomMenu());
            stmt.setString(2, platIdsToString(menu.getPlats()));
            stmt.executeUpdate();

            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idM = rs.getInt(1);
                    menu.setIdM(idM);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void supprimerMenu(int idM) {
        String query = "DELETE FROM Menu WHERE idM = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, idM);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void modifierMenu(Menu menu) {
        String query = "UPDATE Menu SET nomMenu = ?, platIds = ? WHERE idM = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, menu.getNomMenu());
            stmt.setString(2, platIdsToString(menu.getPlats()));
            stmt.setInt(3, menu.getIdM());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
