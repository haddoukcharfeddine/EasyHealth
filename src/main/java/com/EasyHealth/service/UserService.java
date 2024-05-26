package com.EasyHealth.service;

import com.EasyHealth.entite.*;
import util.DataSource;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    private Connection cnx;

    public UserService() {
        cnx= DataSource.getInstance().getConnexion();
    }
    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO User (nom, email, telephone, adresse, userType, objectif, poids, taille, age, sexe, activer, disponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getTelephone());
            stmt.setString(4, user.getAdresse());
            stmt.setString(5, user.getUserType().name());

            if (user instanceof Client) {
                Client client = (Client) user;
                stmt.setString(6, client.getObjectif().name());

                if (client instanceof ClientSport) {
                    ClientSport clientSport = (ClientSport) client;
                    stmt.setFloat(7, clientSport.getPoids());
                    stmt.setFloat(8, clientSport.getTaille());
                    stmt.setInt(9, clientSport.getAge());
                    stmt.setString(10, clientSport.getSexe().name());
                    stmt.setString(11, clientSport.getActiver().name());
                } else {
                    stmt.setNull(7, Types.FLOAT);
                    stmt.setNull(8, Types.FLOAT);
                    stmt.setNull(9, Types.INTEGER);
                    stmt.setNull(10, Types.VARCHAR);
                    stmt.setNull(11, Types.VARCHAR);
                }
                stmt.setNull(12, Types.BOOLEAN);
            } else if (user instanceof Livreur) {
                Livreur livreur = (Livreur) user;
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.FLOAT);
                stmt.setNull(8, Types.FLOAT);
                stmt.setNull(9, Types.INTEGER);
                stmt.setNull(10, Types.VARCHAR);
                stmt.setNull(11, Types.VARCHAR);
                stmt.setBoolean(12, livreur.isDisponible());
            } else {
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.FLOAT);
                stmt.setNull(8, Types.FLOAT);
                stmt.setNull(9, Types.INTEGER);
                stmt.setNull(10, Types.VARCHAR);
                stmt.setNull(11, Types.VARCHAR);
                stmt.setNull(12, Types.BOOLEAN);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String adresse = rs.getString("adresse");
                UserType userType = UserType.valueOf(rs.getString("userType"));
                Objectif objectif = rs.getString("objectif") != null ? Objectif.valueOf(rs.getString("objectif")) : null;

                if (userType == UserType.Client && (objectif == Objectif.Perdre_du_poids || objectif == Objectif.Prendre_du_poids)) {
                    float poids = rs.getFloat("poids");
                    float taille = rs.getFloat("taille");
                    int age = rs.getInt("age");
                    Sexe sexe = Sexe.valueOf(rs.getString("sexe"));
                    Activer activer = Activer.valueOf(rs.getString("activer"));
                    return new ClientSport(id, nom, email, telephone, adresse, objectif, poids, taille, age, sexe, activer);
                } else if (userType == UserType.Client) {
                    return new Client(id, nom, email, telephone, adresse, objectif);
                } else if (userType == UserType.Vendeur) {
                    return new Vendeur(id, nom, email, telephone, adresse);
                } else if (userType == UserType.Livreur) {
                    boolean disponible = rs.getBoolean("disponible");
                    return new Livreur(id, nom, email, telephone, adresse, disponible);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE User SET nom = ?, email = ?, telephone = ?, adresse = ?, userType = ?, objectif = ?, poids = ?, taille = ?, age = ?, sexe = ?, activer = ?, disponible = ? WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getTelephone());
            stmt.setString(4, user.getAdresse());
            stmt.setString(5, user.getUserType().name());

            if (user instanceof Client) {
                Client client = (Client) user;
                stmt.setString(6, client.getObjectif().name());

                if (client instanceof ClientSport) {
                    ClientSport clientSport = (ClientSport) client;
                    stmt.setFloat(7, clientSport.getPoids());
                    stmt.setFloat(8, clientSport.getTaille());
                    stmt.setInt(9, clientSport.getAge());
                    stmt.setString(10, clientSport.getSexe().name());
                    stmt.setString(11, clientSport.getActiver().name());
                } else {
                    stmt.setNull(7, Types.FLOAT);
                    stmt.setNull(8, Types.FLOAT);
                    stmt.setNull(9, Types.INTEGER);
                    stmt.setNull(10, Types.VARCHAR);
                    stmt.setNull(11, Types.VARCHAR);
                }
                stmt.setNull(12, Types.BOOLEAN);
            } else if (user instanceof Livreur) {
                Livreur livreur = (Livreur) user;
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.FLOAT);
                stmt.setNull(8, Types.FLOAT);
                stmt.setNull(9, Types.INTEGER);
                stmt.setNull(10, Types.VARCHAR);
                stmt.setNull(11, Types.VARCHAR);
                stmt.setBoolean(12, livreur.isDisponible());
            } else {
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.FLOAT);
                stmt.setNull(8, Types.FLOAT);
                stmt.setNull(9, Types.INTEGER);
                stmt.setNull(10, Types.VARCHAR);
                stmt.setNull(11, Types.VARCHAR);
                stmt.setNull(12, Types.BOOLEAN);
            }

            stmt.setInt(13, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}





