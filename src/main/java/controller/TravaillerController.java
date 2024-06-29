package controller;

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
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;

public class TravaillerController {

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
    private ChoiceBox<String> UserTypeChoiceBox;

    @FXML
    private CheckBox disponibleCheckBox;

    @FXML
    private Button Confirmer;

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
        UserTypeChoiceBox.getItems().addAll("Vendeur", "Livreur");
        UserTypeChoiceBox.setValue("Type d'Utilisateur");

        // Attach selection handler
        UserTypeChoiceBox.setOnAction(event -> handleUserTypeSelection());

        Confirmer.setOnAction(this::handleCreateAccount);

        disponibleCheckBox.setVisible(false);
    }

    @FXML
    private void handleUserTypeSelection() {
        String userType = UserTypeChoiceBox.getValue();
        if ("Livreur".equals(userType)) {
            disponibleCheckBox.setVisible(true);
        } else {
            disponibleCheckBox.setVisible(false);
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
            String userType = UserTypeChoiceBox.getValue();

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
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            User newUser = null;
            if ("Vendeur".equals(userType)) {
                newUser = new Vendeur(id, username, email, telephone, address, hashedPassword);
            } else if ("Livreur".equals(userType)) {
                boolean disponible = disponibleCheckBox.isSelected();
                newUser = new Livreur(id, username, email, telephone, address, hashedPassword, disponible);
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
            System.out.println("Invalid input!");
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
            Stage stage = (Stage) accueilButton.getScene().getWindow();
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
