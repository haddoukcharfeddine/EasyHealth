package controller;

import entite.Enum.UserType;
import entite.Users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import service.UserService;
import session.UserSession;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField telephoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button creat;
    @FXML
    private Button loginButton;
    @FXML
    private Button AccueilButton;

    @FXML
    private Label telephoneErrorLabel;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> {
            try {
                handleLogin(event);
            } catch (IOException | SQLException e) {
                showErrorAlert("Erreur", "Une erreur s'est produite lors de la connexion.");
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException, SQLException {
        clearFieldErrors();

        String telephone = telephoneField.getText().trim();
        String password = passwordField.getText().trim();

        if (telephone.isEmpty() || password.isEmpty()) {
            if (telephone.isEmpty()) {
                setFieldError(telephoneField, telephoneErrorLabel, "Le numéro de téléphone est requis.");
            }
            if (password.isEmpty()) {
                setFieldError(passwordField, passwordErrorLabel, "Le mot de passe est requis.");
            }
            return;
        }

        UserService userService = new UserService();
        boolean telephoneExists = userService.checkTelephoneExists(telephone);

        if (!telephoneExists) {
            showErrorAlert("Numéro de téléphone non enregistré", "Le numéro de téléphone entré n'est pas enregistré.");
            return;
        }

        User user = userService.getUserByTelephone(telephone);

        if (user != null && validatePassword(password, user.getPassword())) {
            UserSession session = UserSession.getInstance();
            session.setTelephone(user.getTelephone());
            session.setUserId(user.getId());

            if (user.getUserType() == UserType.Livreur) {
                navigateToAccueilTrois();
            } else {
                navigateToAccueilDeux();
            }
        } else {
            showErrorAlert("Identifiants invalides", "Le numéro de téléphone ou le mot de passe est incorrect.");
        }
    }

    private void setFieldError(TextField field, Label label, String message) {
        field.setStyle("-fx-border-color: red;");
        label.setText(message);
        label.setVisible(true);
    }

    private void clearFieldErrors() {
        telephoneErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
    }

    @FXML
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void navigateToAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AccueilButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erreur de navigation", "Échec du chargement de la page d'accueil.");
            e.printStackTrace();
        }
    }

    private void navigateToAccueilDeux() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilDeux.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erreur de navigation", "Échec du chargement de la page AccueilDeux.");
            e.printStackTrace();
        }
    }

    private void navigateToAccueilTrois() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilTrois.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erreur de navigation", "Échec du chargement de la page AccueilTrois.");
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToCreateAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/create_account.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) creat.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erreur de navigation", "Échec du chargement de la page de création de compte.");
            e.printStackTrace();
        }
    }

    private boolean validatePassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}






