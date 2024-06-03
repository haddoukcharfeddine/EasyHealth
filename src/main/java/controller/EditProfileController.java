package controller;

import entite.Enum.Activer;
import entite.Enum.Objectif;
import entite.Enum.Sexe;
import entite.Users.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.UserService;
import session.UserSession;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {
    private UserService userService;

    @FXML
    private TextField NomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField TelephoneField;

    @FXML
    private TextField PoidsField;

    @FXML
    private TextField TailleField;

    @FXML
    private TextField ageField;

    @FXML
    private ChoiceBox<String> objectiveChoiceBox;

    @FXML
    private ChoiceBox<String> ActiverChoiceBox;

    @FXML
    private ChoiceBox<String> SexeChoiceBox;

    @FXML
    private Button saveButton;

    private User currentUser;

    @FXML
    private MenuItem profileItem;

    @FXML
    private MenuItem settingsItem;

    @FXML
    private MenuItem addDishItem;

    @FXML
    private MenuItem addMenuItem;

    @FXML
    private MenuItem logoutItem;
   @FXML
   private  MenuButton profileMenuBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add items to the objectiveChoiceBox
        objectiveChoiceBox.getItems().addAll("Perdre_du_poids", "Prendre_du_poids", "Aucun");
        objectiveChoiceBox.setValue("Objectif"); // Set default value
        SexeChoiceBox.getItems().addAll("HOMME", "FEMME");
        SexeChoiceBox.setValue("Sexe");

        // Add items to the ActiverChoiceBox
        ActiverChoiceBox.getItems().addAll("SEDENTAIRE", "LEGERE", "MODEREE", "INTENSE");
        ActiverChoiceBox.setValue("Activiter"); // Set default value

        userService = new UserService(); // Initialize userService
        UserSession session = UserSession.getInstance();
        String currentUserTelephone = session.getTelephone();

        // Use the telephone number to fetch additional user data from the database
        currentUser = userService.getUserByTelephone(currentUserTelephone);
        if (currentUser != null) {
            String userType = String.valueOf(userService.getUserType(currentUser));

            // Now you have the user's type and objective, you can show/hide UI elements accordingly
            if (userType.equals("Client")) {
                // Show fields for basic user information
                setupClientUI();

                // Add a listener to the objectiveChoiceBox
                objectiveChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if ("Perdre_du_poids".equals(newValue) || "Prendre_du_poids".equals(newValue)) {
                        // Show fields related to weight management
                        PoidsField.setVisible(true);
                        TailleField.setVisible(true);
                        ActiverChoiceBox.setVisible(true);
                        SexeChoiceBox.setVisible(true);
                        ageField.setVisible(true);
                    } else {
                        // Hide weight management fields
                        PoidsField.setVisible(false);
                        TailleField.setVisible(false);
                        ActiverChoiceBox.setVisible(false);
                        SexeChoiceBox.setVisible(false);
                        ageField.setVisible(false);
                    }
                });
            } else {
                // For non-client users (vendeur, livreur, etc.), hide weight management fields
                setupNonClientUI();
            }
        }
        loadUserData();
    }

    private void setupClientUI() {
        NomField.setVisible(true);
        emailField.setVisible(true);
        addressField.setVisible(true);
        TelephoneField.setVisible(true);
        objectiveChoiceBox.setVisible(true);
        PoidsField.setVisible(false);
        TailleField.setVisible(false);
        ActiverChoiceBox.setVisible(false);
        SexeChoiceBox.setVisible(false);
        ageField.setVisible(false);
    }

    private void setupNonClientUI() {
        PoidsField.setVisible(false);
        TailleField.setVisible(false);
        objectiveChoiceBox.setVisible(false);
        ActiverChoiceBox.setVisible(false);
        SexeChoiceBox.setVisible(false);
        ageField.setVisible(false);

        NomField.setVisible(true);
        emailField.setVisible(true);
        addressField.setVisible(true);
        TelephoneField.setVisible(true);
    }

    // Load current user data
    private void loadUserData() {
        if (currentUser != null) {
            // Populate basic user information
            NomField.setText(currentUser.getNom());
            emailField.setText(currentUser.getEmail());
            addressField.setText(currentUser.getAdresse());
            TelephoneField.setText(currentUser.getTelephone());

            // Check if the user is a Client
            if (currentUser instanceof Client) {
                Client client = (Client) currentUser;
                String objective = String.valueOf(client.getObjectif());

                // Set objective choice box
                objectiveChoiceBox.setValue(objective);

                // Clear fields related to weight management if the objective is not to lose or gain weight
                if (!objective.equals("Perdre_du_poids") && !objective.equals("Prendre_du_poids")) {
                    PoidsField.clear();
                    TailleField.clear();
                    ageField.clear();
                }

                // Handle ClientSport specific information
                if (client instanceof ClientSport) {
                    ClientSport clientSport = (ClientSport) client;
                    PoidsField.setText(String.valueOf(clientSport.getPoids()));
                    TailleField.setText(String.valueOf(clientSport.getTaille()));
                    ageField.setText(String.valueOf(clientSport.getAge()));
                    ActiverChoiceBox.setValue(clientSport.getActiver().toString());
                    SexeChoiceBox.setValue(clientSport.getSexe().toString());
                } else {
                    // Clear fields related to ClientSport specific information
                    PoidsField.clear();
                    TailleField.clear();
                    ageField.clear();
                    ActiverChoiceBox.setValue(null);
                    SexeChoiceBox.setValue(null);
                }
            }
        }
    }

    @FXML
    private void handleSaveAction() {
        String name = NomField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String telephone = TelephoneField.getText();

        // Get and validate additional fields if the user is a ClientSport
        float poids = !PoidsField.getText().isEmpty() ? Float.parseFloat(PoidsField.getText()) : Float.NaN;
        float taille = !TailleField.getText().isEmpty() ? Float.parseFloat(TailleField.getText()) : Float.NaN;
        int age = !ageField.getText().isEmpty() ? Integer.parseInt(ageField.getText()) : -1;
        Objectif objectif = objectiveChoiceBox.getValue() != null ? Objectif.valueOf(objectiveChoiceBox.getValue()) : null;
        Activer activer = ActiverChoiceBox.getValue() != null ? Activer.valueOf(ActiverChoiceBox.getValue()) : null;
        Sexe sexe = SexeChoiceBox.getValue() != null ? Sexe.valueOf(SexeChoiceBox.getValue()) : null;

        // Update fields in the currentUser object
        currentUser.setNom(name);
        currentUser.setEmail(email);
        currentUser.setAdresse(address);
        currentUser.setTelephone(telephone);

        if (currentUser instanceof Client) {
            Client client = (Client) currentUser;
            client.setObjectif(objectif);

            if (objectif == Objectif.Perdre_du_poids || objectif == Objectif.Prendre_du_poids) {
                if (!(client instanceof ClientSport)) {
                    ClientSport clientSport = new ClientSport(client.getId(), name, email, telephone, address, client.getPassword(), objectif, poids, taille, age, sexe, activer);
                    userService.deleteUser(currentUser.getId());
                    userService.addUser(clientSport);
                    currentUser = clientSport;
                } else {
                    ClientSport clientSport = (ClientSport) client;
                    if (!Float.isNaN(poids)) clientSport.setPoids(poids);
                    if (!Float.isNaN(taille)) clientSport.setTaille(taille);
                    if (age != -1) clientSport.setAge(age);
                    if (sexe != null) clientSport.setSexe(sexe);
                    if (activer != null) clientSport.setActiver(activer);
                }
            } else if (objectif == Objectif.Aucun) {
                if (client instanceof ClientSport) {
                    Client newClient = new Client(client.getId(), name, email, telephone, address, client.getPassword(), Objectif.Aucun);
                    userService.deleteUser(currentUser.getId());
                    userService.addUser(newClient);
                    currentUser = newClient;
                }
            }
        } else if (currentUser instanceof Livreur) {
            Livreur livreur = (Livreur) currentUser;
            livreur.setDisponible(true); // Add logic for updating "disponible" if necessary
        }

        try {
            userService.updateUser(currentUser);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Profile updated successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Update Failed", "An error occurred while updating the profile");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleLogout() {
        try {
            // Load the FXML file of the Accueil page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) profileMenuBtn.getScene().getWindow();

            // Set the new scene with the content of the Accueil page
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleEditProfile(){try {
        // Load the FXML file of the Accueil page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editProfile.fxml"));
        Parent root = loader.load();

        // Get the current stage
        Stage stage = (Stage) profileMenuBtn.getScene().getWindow();

        // Set the new scene with the content of the Accueil page
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }}
}





