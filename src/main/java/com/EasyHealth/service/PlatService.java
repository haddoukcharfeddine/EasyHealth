package com.EasyHealth.service;

import com.EasyHealth.entite.*;
import util.DataSource;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public  class PlatService implements PService<Plat> {
    private Connection cnx;

    public PlatService() {
        cnx= DataSource.getInstance().getConnexion();
    }
    @Override
    public void ajouterPlat(Plat plat) {
        String query = "INSERT INTO Plat (nomPlat, description, prix, protein, calories, idUVendeur, Categorie) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, plat.getNomPlat());
            stmt.setString(2, plat.getDescription());
            stmt.setDouble(3, plat.getPrix());
            stmt.setInt(4, plat.getProtein());
            stmt.setInt(5, plat.getCalories());
            stmt.setInt(6, plat.getIdUVendeur());
            stmt.setString(7, plat.getCategorie());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
