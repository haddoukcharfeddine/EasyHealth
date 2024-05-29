package com.EasyHealth.service;

import com.EasyHealth.entite.Enum.Activer;
import com.EasyHealth.entite.Enum.Objectif;
import com.EasyHealth.entite.Enum.Sexe;
import com.EasyHealth.entite.Enum.UserType;
import com.EasyHealth.entite.Users.*;
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

                if (client.getClass() == ClientSport.class && (client.getObjectif() == Objectif.Perdre_du_poids || client.getObjectif() == Objectif.Prendre_du_poids)) {
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
            System.out.println("Executing SQL query: " + sql); // Print SQL query
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String adresse = rs.getString("adresse");
                UserType userType = UserType.valueOf(rs.getString("userType"));
                Objectif objectif = rs.getString("objectif") != null ? Objectif.valueOf(rs.getString("objectif")) : null;

                System.out.println("Retrieved user details:"); // Print retrieved user details
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

                    System.out.println("Creating ClientSport object:"); // Print creation of ClientSport object
                    // Print other relevant information as needed

                    return new ClientSport(id, nom, email, telephone, adresse, objectif, poids, taille, age, sexe, activer);
                } else if (userType == UserType.Client) {
                    System.out.println("Creating Client object:"); // Print creation of Client object
                    // Print other relevant information as needed
                    return new Client(id, nom, email, telephone, adresse, objectif);
                } else if (userType == UserType.Vendeur) {
                    System.out.println("Creating Vendeur object:"); // Print creation of Vendeur object
                    // Print other relevant information as needed
                    return new Vendeur(id, nom, email, telephone, adresse);
                } else if (userType == UserType.Livreur) {
                    boolean disponible = rs.getBoolean("disponible");

                    System.out.println("Creating Livreur object:"); // Print creation of Livreur object
                    // Print other relevant information as needed

                    return new Livreur(id, nom, email, telephone, adresse, disponible);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User not found."); // Print if user is not found
        return null;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE User SET ";
        List<String> updateFields = new ArrayList<>();
        List<Object> updateValues = new ArrayList<>();

        // Check and add fields to be updated based on the provided User object
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
                    int oldId = client.getId();
                    float poids = Float.NaN;
                    float taille = Float.NaN;
                    int age = -1;
                    Sexe sexe = null;
                    Activer activer = null;

                    if (!(client.getClass() == ClientSport.class)) {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Please provide additional information for the selected goal (Poids, Taille, Age, Sexe, Activer):");

                        System.out.print("Poids: ");
                        poids = scanner.nextFloat();

                        System.out.print("Taille: ");
                        taille = scanner.nextFloat();

                        System.out.print("Age: ");
                        age = scanner.nextInt();

                        System.out.print("Sexe (HOMME/FEMME): ");
                        String sexeInput = scanner.next().toUpperCase();
                        try {
                            sexe = Sexe.valueOf(sexeInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid Sexe. Please enter HOMME or FEMME.");
                            return; // Exit method
                        }

                        System.out.print("Activer (SEDENTAIRE, LEGERE, MODEREE, INTENSE): ");
                        String activerInput = scanner.next().toUpperCase();
                        try {
                            activer = Activer.valueOf(activerInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid Activer. Please enter SEDENTAIRE, LEGERE, MODEREE, or INTENSE.");
                            return; // Exit method
                        }

                        client = new ClientSport(client.getId(), client.getNom(), client.getEmail(), client.getTelephone(), client.getAdresse(), newObjectif, poids, taille, age, sexe, activer);
                        deleteUser(oldId);
                        addUser(client);
                    } else {
                        ClientSport clientSport = (ClientSport) client;
                        if (Float.isNaN(clientSport.getPoids())) {
                            Scanner scanner = new Scanner(System.in);
                            System.out.print("Poids: ");
                            poids = scanner.nextFloat();
                            clientSport.setPoids(poids);
                        }
                        if (Float.isNaN(clientSport.getTaille())) {
                            Scanner scanner = new Scanner(System.in);
                            System.out.print("Taille: ");
                            taille = scanner.nextFloat();
                            clientSport.setTaille(taille);
                        }
                        if (clientSport.getAge() == -1) {
                            Scanner scanner = new Scanner(System.in);
                            System.out.print("Age: ");
                            age = scanner.nextInt();
                            clientSport.setAge(age);
                        }
                        if (clientSport.getSexe() == null) {
                            Scanner scanner = new Scanner(System.in);
                            System.out.print("Sexe (HOMME/FEMME): ");
                            String sexeInput = scanner.next().toUpperCase();
                            try {
                                sexe = Sexe.valueOf(sexeInput);
                                clientSport.setSexe(sexe);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid Sexe. Please enter HOMME or FEMME.");
                                return; // Exit method
                            }
                        }
                        if (clientSport.getActiver() == null) {
                            Scanner scanner = new Scanner(System.in);
                            System.out.print("Activer (SEDENTAIRE, LEGERE, MODEREE, INTENSE): ");
                            String activerInput = scanner.next().toUpperCase();
                            try {
                                activer = Activer.valueOf(activerInput);
                                clientSport.setActiver(activer);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid Activer. Please enter SEDENTAIRE, LEGERE, MODEREE, or INTENSE.");
                                return; // Exit method
                            }
                        }
                    }

                    if (!Float.isNaN(((ClientSport) client).getPoids())) {
                        updateFields.add("poids = ?");
                        updateValues.add(((ClientSport) client).getPoids());
                    }
                    if (!Float.isNaN(((ClientSport) client).getTaille())) {
                        updateFields.add("taille = ?");
                        updateValues.add(((ClientSport) client).getTaille());
                    }
                    if (((ClientSport) client).getAge() != -1) {
                        updateFields.add("age = ?");
                        updateValues.add(((ClientSport) client).getAge());
                    }
                    if (((ClientSport) client).getSexe() != null) {
                        updateFields.add("sexe = ?");
                        updateValues.add(((ClientSport) client).getSexe().name());
                    }
                    if (((ClientSport) client).getActiver() != null) {
                        updateFields.add("activer = ?");
                        updateValues.add(((ClientSport) client).getActiver().name());
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

        // Append the fields to be updated to the SQL query
        sql += String.join(", ", updateFields);
        sql += " WHERE id = ?";

        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            int parameterIndex = 1;

            // Set values for fields to be updated
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

            // Set ID for WHERE clause
            stmt.setInt(parameterIndex, user.getId());

            // Execute the update statement
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
    @Override
    public User getUserByTelephone(String telephone) {
        String sql = "SELECT * FROM User WHERE telephone = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            System.out.println("Executing SQL query: " + sql); // Print SQL query
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String adresse = rs.getString("adresse");
                UserType userType = UserType.valueOf(rs.getString("userType"));
                Objectif objectif = rs.getString("objectif") != null ? Objectif.valueOf(rs.getString("objectif")) : null;

                System.out.println("Retrieved user details:"); // Print retrieved user details
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

                    System.out.println("Creating ClientSport object:"); // Print creation of ClientSport object
                    // Print other relevant information as needed

                    return new ClientSport(id, nom, email, telephone, adresse, objectif, poids, taille, age, sexe, activer);
                } else if (userType == UserType.Client) {
                    System.out.println("Creating Client object:"); // Print creation of Client object
                    // Print other relevant information as needed
                    return new Client(id, nom, email, telephone, adresse, objectif);
                } else if (userType == UserType.Vendeur) {
                    System.out.println("Creating Vendeur object:"); // Print creation of Vendeur object
                    // Print other relevant information as needed
                    return new Vendeur(id, nom, email, telephone, adresse);
                } else if (userType == UserType.Livreur) {
                    boolean disponible = rs.getBoolean("disponible");

                    System.out.println("Creating Livreur object:"); // Print creation of Livreur object
                    // Print other relevant information as needed

                    return new Livreur(id, nom, email, telephone, adresse, disponible);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User not found."); // Print if user is not found
        return null;
    }

    @Override
    public void deleteUserByTelephone(String telephone) {
        String sql = "DELETE FROM User WHERE telephone = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            stmt.executeUpdate();
            System.out.println("Deleted user with telephone: " + telephone); // Print confirmation message
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete user with telephone: " + telephone); // Print error message
        }
    }
//
    //plat

 //



}





