package controller;

import entite.Enum.Activer;
import entite.Enum.Objectif;
import entite.Enum.Sexe;
import entite.Users.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UserService;
import session.UserSession;

import java.io.IOException;
import java.sql.SQLException;

public class CreateAccountController {

    @FXML
    private Button accueilButton;

    @FXML
    private TextField newUsernameField;

    @FXML
    private TextField newEmailField;

    @FXML
    private TextField newTelephoneField;

    @FXML
    private TextField newAddresseField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private ChoiceBox<String> userTypeChoiceBox;

    @FXML
    private ChoiceBox<String> objectifChoiceBox;
    @FXML
    private ChoiceBox<String> ActiverChoiceBox;

    @FXML
    private CheckBox disponibleCheckBox;

    @FXML
    private TextField poidsField;

    @FXML
    private TextField tailleField;

    @FXML
    private TextField ageField;

    @FXML
    private ChoiceBox<String> sexeChoiceBox;

    @FXML
    private Button createAccountButton;

    @FXML
    public void initialize() {
        // Set action for Accueil button
        accueilButton.setOnAction(event -> navigateToAccueil());
        userTypeChoiceBox.getItems().addAll("Client", "Vendeur", "Livreur");
        userTypeChoiceBox.setValue("Type d'Utilisateur"); // Set default value

        objectifChoiceBox.getItems().addAll("Perdre_du_poids", "Prendre_du_poids", "Aucun");
        objectifChoiceBox.setValue("Objectif");

        sexeChoiceBox.getItems().addAll( "HOMME", "FEMME");
        sexeChoiceBox.setValue("Sexe");
        ActiverChoiceBox.getItems().addAll("SEDENTAIRE", "LEGERE", "MODEREE", "INTENSE");
        ActiverChoiceBox.setValue("Activiter");
        userTypeChoiceBox.setOnAction(event -> handleUserTypeSelection());
        objectifChoiceBox.setOnAction(event -> handleUserTypeSelection());

        createAccountButton.setOnAction(event -> handleCreateAccount(event));

        // Hide additional fields by default
        objectifChoiceBox.setVisible(false);
        disponibleCheckBox.setVisible(false);
        poidsField.setVisible(false);
        tailleField.setVisible(false);
        ageField.setVisible(false);
        sexeChoiceBox.setVisible(false);
        ActiverChoiceBox.setVisible(false);
    }

    private void handleUserTypeSelection() {
        String userType = userTypeChoiceBox.getValue();
        String objectifType = objectifChoiceBox.getValue();

        // Reset visibility of all fields
        objectifChoiceBox.setVisible(true);
        poidsField.setVisible(true);
        tailleField.setVisible(true);
        ageField.setVisible(true);
        sexeChoiceBox.setVisible(true);
        disponibleCheckBox.setVisible(true);
        ActiverChoiceBox.setVisible(true);

        if (userType.equals("Client")) {

            if (objectifType.equals("Perdre_du_poids") || objectifType.equals("Prendre_du_poids")) {
                disponibleCheckBox.setVisible(false);
                // No changes needed, fields already visible
            } else {
                // Hide additional fields if objectif is not related to weight
                poidsField.setVisible(false);
                tailleField.setVisible(false);
                ageField.setVisible(false);
                sexeChoiceBox.setVisible(false);
                disponibleCheckBox.setVisible(false);
                ActiverChoiceBox.setVisible(false);
            }
        } else if (userType.equals("Vendeur")) {
            // Hide all additional fields for Vendeur
            objectifChoiceBox.setVisible(false);
            poidsField.setVisible(false);
            tailleField.setVisible(false);
            ageField.setVisible(false);
            sexeChoiceBox.setVisible(false);
            disponibleCheckBox.setVisible(false);
            ActiverChoiceBox.setVisible(false);
        } else if (userType.equals("Livreur")) {
            // Hide additional fields for Livreur except for disponible
            objectifChoiceBox.setVisible(false);
            poidsField.setVisible(false);
            tailleField.setVisible(false);
            ageField.setVisible(false);
            sexeChoiceBox.setVisible(false);
            ActiverChoiceBox.setVisible(false);
        }
    }

    @FXML
    private void handleCreateAccount(ActionEvent event) {
        try {
            // Retrieve input values from the UI components
            int id = 0; // You may need to generate or retrieve a unique ID
            String username = newUsernameField.getText().trim();
            String email = newEmailField.getText().trim();
            String telephone = newTelephoneField.getText().trim();
            String address = newAddresseField.getText().trim();
            String password = newPasswordField.getText().trim();
            String userType = userTypeChoiceBox.getValue();
            String objectifType = objectifChoiceBox.getValue();
            String activerType = ActiverChoiceBox.getValue();
            String sexe = sexeChoiceBox.getValue();
            float poids = 0;
            float taille = 0;
            int age = 0;


            // Parse poids, taille, and age only if they are visible
            if (poidsField.isVisible()) {
                poids = Float.parseFloat(poidsField.getText().trim());
            }
            if (tailleField.isVisible()) {
                taille = Float.parseFloat(tailleField.getText().trim());
            }
            if (ageField.isVisible()) {
                age = Integer.parseInt(ageField.getText().trim());
            }

            // Validate input based on objectifType and userType
            if (userType.equals("Client") && (objectifType.equals("Perdre_du_poids") || objectifType.equals("Prendre_du_poids"))) {
                if (poids <= 0 || taille <= 0 || age <= 0) {
                    throw new NumberFormatException("Invalid input for poids, taille, or age!");
                }
            }

            // Create a new user object based on the input values
            User newUser = null;
            if (userType.equals("Client")) {
                if (objectifType.equals("Perdre_du_poids") || objectifType.equals("Prendre_du_poids")) {
                    newUser = new ClientSport(id, username, email, telephone, address, password, Objectif.valueOf(objectifType), poids, taille, age, Sexe.valueOf(sexe), Activer.valueOf(activerType));
                } else {
                    newUser = new Client(id, username, email, telephone, address, password, Objectif.valueOf(objectifType));
                }
            } else if (userType.equals("Vendeur")) {
                newUser = new Vendeur(id, username, email, telephone, address, password);
            } else if (userType.equals("Livreur")) {
                boolean disponible = disponibleCheckBox.isSelected();
                newUser = new Livreur(id, username, email, telephone, address, password, disponible);
            }

            // Add the new user to the database using UserService
            if (newUser != null) {
                UserService userService = new UserService();
                userService.addUser(newUser);
                User user = userService.getUserByTelephone(telephone);
                UserSession session = UserSession.getInstance();
                session.setTelephone(user.getTelephone());
                session.setUserId(user.getId());

                // Navigate to AccueilDeux after successful account creation
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilDeux.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for poids, taille, or age!");
        } catch (NullPointerException e) {
            System.out.println("Some fields are not filled!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToAccueil() {
        try {
            Parent accueilPage = FXMLLoader.load(getClass().getResource("/Accueil.fxml"));
            Scene accueilScene = new Scene(accueilPage, 800, 600);
            Stage stage = (Stage) createAccountButton.getScene().getWindow();
            stage.setScene(accueilScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


