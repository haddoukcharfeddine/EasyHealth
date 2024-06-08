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
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.UserService;
import session.UserSession;

import java.io.IOException;

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
    private Label usernameErrorLabel;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private Label telephoneErrorLabel;

    @FXML
    private Label addressErrorLabel;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    public void initialize() {

        userTypeChoiceBox.getItems().addAll("Client", "Vendeur", "Livreur");
        userTypeChoiceBox.setValue("Type d'Utilisateur");

        objectifChoiceBox.getItems().addAll("Perdre_du_poids", "Prendre_du_poids", "Aucun");
        objectifChoiceBox.setValue("Objectif");

        sexeChoiceBox.getItems().addAll("HOMME", "FEMME");
        sexeChoiceBox.setValue("Sexe");
        ActiverChoiceBox.getItems().addAll("SEDENTAIRE", "LEGERE", "MODEREE", "INTENSE");
        ActiverChoiceBox.setValue("Activiter");



        createAccountButton.setOnAction(event -> handleCreateAccount(event));

        objectifChoiceBox.setVisible(false);
        disponibleCheckBox.setVisible(false);
        poidsField.setVisible(false);
        tailleField.setVisible(false);
        ageField.setVisible(false);
        sexeChoiceBox.setVisible(false);
        ActiverChoiceBox.setVisible(false);
    }

    @FXML
    private void handleUserTypeSelection() {
        String userType = userTypeChoiceBox.getValue();
        String objectifType = objectifChoiceBox.getValue();

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
            } else {
                poidsField.setVisible(false);
                tailleField.setVisible(false);
                ageField.setVisible(false);
                sexeChoiceBox.setVisible(false);
                disponibleCheckBox.setVisible(false);
                ActiverChoiceBox.setVisible(false);
            }
        } else if (userType.equals("Vendeur")) {
            objectifChoiceBox.setVisible(false);
            poidsField.setVisible(false);
            tailleField.setVisible(false);
            ageField.setVisible(false);
            sexeChoiceBox.setVisible(false);
            disponibleCheckBox.setVisible(false);
            ActiverChoiceBox.setVisible(false);
        } else if (userType.equals("Livreur")) {
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
            int id = 0;
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

            boolean valid = true;

            // Validate mandatory fields
            if (username.isEmpty()) {
                setFieldError(newUsernameField, usernameErrorLabel, "Le nom d'utilisateur est requis.");
                valid = false;
            } else {
                clearFieldError(newUsernameField, usernameErrorLabel);
            }

            if (email.isEmpty() || !email.contains("@")) {
                setFieldError(newEmailField, emailErrorLabel, "L'email est invalide.");
                valid = false;
            } else {
                clearFieldError(newEmailField, emailErrorLabel);
            }

            if (telephone.isEmpty() || !telephone.matches("\\d{8}")) {
                setFieldError(newTelephoneField, telephoneErrorLabel, "Le numéro de téléphone doit contenir exactement 8 chiffres.");
                valid = false;
            } else {
                clearFieldError(newTelephoneField, telephoneErrorLabel);
            }

            if (address.isEmpty()) {
                setFieldError(newAddresseField, addressErrorLabel, "L'adresse est requise.");
                valid = false;
            } else {
                clearFieldError(newAddresseField, addressErrorLabel);
            }

            if (password.isEmpty() || !isValidPassword(password)) {
                setFieldError(newPasswordField, passwordErrorLabel, "Au moins une majuscule et un caractère spécial");
                valid = false;
            } else {
                clearFieldError(newPasswordField, passwordErrorLabel);
            }

            if (!valid) {
                throw new IllegalArgumentException("Some fields are not filled or invalid!");
            }

            if (poidsField.isVisible()) {
                poids = Float.parseFloat(poidsField.getText().trim());
            }
            if (tailleField.isVisible()) {
                taille = Float.parseFloat(tailleField.getText().trim());
            }
            if (ageField.isVisible()) {
                age = Integer.parseInt(ageField.getText().trim());
            }

            if (userType.equals("Client") && (objectifType.equals("Perdre_du_poids") || objectifType.equals("Prendre_du_poids"))) {
                if (poids <= 0 || taille <= 0 || age <= 0) {
                    throw new NumberFormatException("Invalid input for poids, taille, or age!");
                }
            }

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

            if (newUser != null) {
                UserService userService = new UserService();
                userService.addUser(newUser);
                User user = userService.getUserByTelephone(telephone);
                UserSession session = UserSession.getInstance();
                session.setTelephone(user.getTelephone());
                session.setUserId(user.getId());

                if (newUser instanceof Livreur) {
                    navigateToAccueilTrois(event);
                } else {
                    navigateToAccueilDeux(event);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for poids, taille, or age!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFieldError(TextField field, Label errorLabel, String errorMessage) {
        field.setStyle("-fx-border-color: red;");
        errorLabel.setText(errorMessage);
        errorLabel.setVisible(true);
    }

    private void clearFieldError(TextField field, Label errorLabel) {
        field.setStyle(null);
        errorLabel.setVisible(false);
    }

    private boolean isValidPassword(String password) {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasSpecial = password.matches(".*[!@#$%^&*()].*");
        return hasUppercase && hasSpecial;
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

    private void navigateToAccueilDeux(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilDeux.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToAccueilTrois(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilTrois.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



