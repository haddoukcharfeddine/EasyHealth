package service;

import entite.Enum.Activer;
import entite.Enum.Objectif;
import entite.Enum.Sexe;
import entite.Enum.UserType;
import entite.Users.*;
import util.DataSource;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService implements IService<User> {

    private Connection cnx;

    public UserService() {
        cnx= DataSource.getInstance().getConnexion();
    }
    //user
    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO User (nom, email, telephone, adresse, password, userType, objectif, poids, taille, age, sexe, activer, disponible) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {

            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getTelephone());
            stmt.setString(4, user.getAdresse());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getUserType().name());


            if (user instanceof Client) {
                Client client = (Client) user;
                stmt.setString(7, client.getObjectif().name());

                if (client instanceof ClientSport && (client.getObjectif() == Objectif.Perdre_du_poids || client.getObjectif() == Objectif.Prendre_du_poids)) {
                    ClientSport clientSport = (ClientSport) client;
                    stmt.setFloat(8, clientSport.getPoids());
                    stmt.setFloat(9, clientSport.getTaille());
                    stmt.setInt(10, clientSport.getAge());
                    stmt.setString(11, clientSport.getSexe().name());
                    stmt.setString(12, clientSport.getActiver().name());
                } else {
                    stmt.setNull(8, Types.FLOAT);
                    stmt.setNull(9, Types.FLOAT);
                    stmt.setNull(10, Types.INTEGER);
                    stmt.setNull(11, Types.VARCHAR);
                    stmt.setNull(12, Types.VARCHAR);
                }
                stmt.setNull(13, Types.BOOLEAN);
            } else if (user instanceof Livreur) {
                Livreur livreur = (Livreur) user;
                stmt.setNull(7, Types.VARCHAR);
                stmt.setNull(8, Types.FLOAT);
                stmt.setNull(9, Types.FLOAT);
                stmt.setNull(10, Types.INTEGER);
                stmt.setNull(11, Types.VARCHAR);
                stmt.setNull(12, Types.VARCHAR);
                stmt.setNull(13, Types.BOOLEAN);
                stmt.setBoolean(13, livreur.isDisponible());
            } else {

                stmt.setNull(7, Types.VARCHAR);
                stmt.setNull(8, Types.FLOAT);
                stmt.setNull(9, Types.FLOAT);
                stmt.setNull(10, Types.INTEGER);
                stmt.setNull(11, Types.VARCHAR);
                stmt.setNull(12, Types.VARCHAR);
                stmt.setNull(13, Types.BOOLEAN);
            }

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("User added successfully!");
            } else {
                System.out.println("Failed to add user!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setInt(1, id);
            System.out.println("Executing SQL query: " + sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String adresse = rs.getString("adresse");
                String password = rs.getString("password");
                UserType userType = UserType.valueOf(rs.getString("userType"));
                Objectif objectif = rs.getString("objectif") != null ? Objectif.valueOf(rs.getString("objectif")) : null;

                System.out.println("Retrieved user details:");
                System.out.println("Name: " + nom);
                System.out.println("Email: " + email);
                System.out.println("User type: " + userType);
                // Print other relevant information as needed

                if (userType == UserType.Client && (objectif == Objectif.Perdre_du_poids || objectif == Objectif.Prendre_du_poids)) {
                    float poids = rs.getFloat("poids");
                    float taille = rs.getFloat("taille");
                    int age = rs.getInt("age");
                    Sexe sexe = Sexe.valueOf(rs.getString("sexe"));
                    Activer activer = Activer.valueOf(rs.getString("activer"));

                    System.out.println("Creating ClientSport object:");


                    return new ClientSport(id, nom, email, telephone, adresse,password, objectif, poids, taille, age, sexe, activer);
                } else if (userType == UserType.Client) {
                    System.out.println("Creating Client object:");

                    return new Client(id, nom, email, telephone, adresse,password, objectif);
                } else if (userType == UserType.Vendeur) {
                    System.out.println("Creating Vendeur object:");

                    return new Vendeur(id, nom, email, telephone, adresse,password);
                } else if (userType == UserType.Livreur) {
                    boolean disponible = rs.getBoolean("disponible");

                    System.out.println("Creating Livreur object:");


                    return new Livreur(id, nom, email, telephone, adresse,password, disponible);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User not found.");
        return null;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE User SET ";
        List<String> updateFields = new ArrayList<>();
        List<Object> updateValues = new ArrayList<>();


        if (user.getNom() != null) {
            updateFields.add("nom = ?");
            updateValues.add(user.getNom());
        }
        if (user.getEmail() != null) {
            updateFields.add("email = ?");
            updateValues.add(user.getEmail());
        }
        if (user.getTelephone() != null) {
            updateFields.add("telephone = ?");
            updateValues.add(user.getTelephone());
        }
        if (user.getAdresse() != null) {
            updateFields.add("adresse = ?");
            updateValues.add(user.getAdresse());
        }
        if (user.getPassword() != null) {
            updateFields.add("password = ?");
            updateValues.add(user.getPassword());
        }
        if (user.getUserType() != null) {
            updateFields.add("userType = ?");
            updateValues.add(user.getUserType().name());
        }

        if (user instanceof Client) {
            Client client = (Client) user;
            Objectif newObjectif = client.getObjectif();
            if (newObjectif != null) {
                updateFields.add("objectif = ?");
                updateValues.add(newObjectif.name());

                if (newObjectif == Objectif.Perdre_du_poids || newObjectif == Objectif.Prendre_du_poids) {
                    ClientSport clientSport;
                    if (!(client instanceof ClientSport)) {
                        clientSport = new ClientSport(client.getId(), client.getNom(), client.getEmail(), client.getTelephone(), client.getAdresse(), client.getPassword(), newObjectif, 0, 0, 0, Sexe.HOMME, Activer.SEDENTAIRE);
                        deleteUser(client.getId());
                        addUser(clientSport);
                    } else {
                        clientSport = (ClientSport) client;
                    }

                    if (!Float.isNaN(clientSport.getPoids())) {
                        updateFields.add("poids = ?");
                        updateValues.add(clientSport.getPoids());
                    }
                    if (!Float.isNaN(clientSport.getTaille())) {
                        updateFields.add("taille = ?");
                        updateValues.add(clientSport.getTaille());
                    }
                    if (clientSport.getAge() != -1) {
                        updateFields.add("age = ?");
                        updateValues.add(clientSport.getAge());
                    }
                    if (clientSport.getSexe() != null) {
                        updateFields.add("sexe = ?");
                        updateValues.add(clientSport.getSexe().name());
                    }
                    if (clientSport.getActiver() != null) {
                        updateFields.add("activer = ?");
                        updateValues.add(clientSport.getActiver().name());
                    }
                } else if (newObjectif == Objectif.Aucun) {
                    updateFields.add("poids = NULL");
                    updateFields.add("taille = NULL");
                    updateFields.add("age = NULL");
                    updateFields.add("sexe = NULL");
                    updateFields.add("activer = NULL");
                }
            }
        }

        if (user instanceof Livreur) {
            Livreur livreur = (Livreur) user;
            updateFields.add("disponible = ?");
            updateValues.add(livreur.isDisponible());
        }


        sql += String.join(", ", updateFields);
        sql += " WHERE id = ?";

        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            int parameterIndex = 1;


            for (Object value : updateValues) {
                if (value instanceof String) {
                    stmt.setString(parameterIndex++, (String) value);
                } else if (value instanceof Float) {
                    stmt.setFloat(parameterIndex++, (Float) value);
                } else if (value instanceof Integer) {
                    stmt.setInt(parameterIndex++, (Integer) value);
                } else if (value instanceof Boolean) {
                    stmt.setBoolean(parameterIndex++, (Boolean) value);
                } else if (value instanceof Enum) {
                    stmt.setString(parameterIndex++, ((Enum<?>) value).name());
                }
            }


            stmt.setInt(parameterIndex, user.getId());


            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void updateUserPassword(String telephone, String hashedPassword) throws SQLException {
        String query = "UPDATE user SET password = ? WHERE telephone = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, telephone);
            preparedStatement.executeUpdate();
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
    @Override
    public User getUserByTelephone(String telephone) {
        String sql = "SELECT * FROM User WHERE telephone = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            System.out.println("Executing SQL query: " + sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String adresse = rs.getString("adresse");
                String password = rs.getString("password");
                UserType userType = UserType.valueOf(rs.getString("userType"));
                Objectif objectif = rs.getString("objectif") != null ? Objectif.valueOf(rs.getString("objectif")) : null;

                System.out.println("Retrieved user details:");
                System.out.println("Name: " + nom);
                System.out.println("Email: " + email);
                System.out.println("User type: " + userType);


                if (userType == UserType.Client && (objectif == Objectif.Perdre_du_poids || objectif == Objectif.Prendre_du_poids)) {
                    float poids = rs.getFloat("poids");
                    float taille = rs.getFloat("taille");
                    int age = rs.getInt("age");
                    Sexe sexe = Sexe.valueOf(rs.getString("sexe"));
                    Activer activer = Activer.valueOf(rs.getString("activer"));

                    System.out.println("Creating ClientSport object:");


                    return new ClientSport(id, nom, email, telephone, adresse,password,objectif, poids, taille, age, sexe, activer);
                } else if (userType == UserType.Client) {
                    System.out.println("Creating Client object:");
                    // Print other relevant information as needed
                    return new Client(id, nom, email, telephone, adresse,password, objectif);
                } else if (userType == UserType.Vendeur) {
                    System.out.println("Creating Vendeur object:");
                    // Print other relevant information as needed
                    return new Vendeur(id, nom, email, telephone, adresse,password);
                } else if (userType == UserType.Livreur) {
                    boolean disponible = rs.getBoolean("disponible");

                    System.out.println("Creating Livreur object:");


                    return new Livreur(id, nom, email, telephone, adresse,password, disponible);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User not found.");
        return null;
    }

    @Override
    public void deleteUserByTelephone(String telephone) {
        String sql = "DELETE FROM User WHERE telephone = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            stmt.executeUpdate();
            System.out.println("Deleted user with telephone: " + telephone);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete user with telephone: " + telephone);
        }
    }
    public boolean isLivreurAvailableByTelephone(String telephone) {
        String sql = "SELECT disponible FROM User WHERE telephone = ? AND userType = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            stmt.setString(2, UserType.Livreur.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("disponible");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void updateLivreurAvailabilityByTelephone(String telephone, boolean isAvailable) {
        String sql = "UPDATE User SET disponible = ? WHERE telephone = ? AND userType = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setBoolean(1, isAvailable);
            stmt.setString(2, telephone);
            stmt.setString(3, UserType.Livreur.name());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Livreur availability updated successfully!");
            } else {
                System.out.println("Failed to update livreur availability!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean checkTelephoneExists(String telephone) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM User WHERE telephone = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }
    @Override
    public boolean validateLogin(String telephone, String password) throws SQLException {
        String sql = "SELECT * FROM User WHERE telephone = ? AND password = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    public static UserType getUserType(User user) {
        return user.getUserType();
    }



}





