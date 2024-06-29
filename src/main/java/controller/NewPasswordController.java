package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.springframework.security.crypto.bcrypt.BCrypt;
import service.UserService;
import session.UserSession;

import java.io.IOException;

public class NewPasswordController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button accueilButton;

    @FXML
    private void handleSaveNewPassword(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs.");
            messageLabel.setVisible(true);
        } else if (!newPassword.equals(confirmPassword)) {
            messageLabel.setText("Les mots de passe ne correspondent pas.");
            messageLabel.setVisible(true);
        } else if (!isValidPassword(newPassword)) {
            messageLabel.setText("Au moins une majuscule et un caractère spécial.");
            messageLabel.setVisible(true);
        } else {
            try {
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                UserSession session = UserSession.getInstance();
                String telephone = session.getTelephone();

                UserService userService = new UserService();
                userService.updateUserPassword(telephone, hashedPassword);

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre mot de passe a été mis à jour avec succès.");
                alert.showAndWait();

                // Navigate to Accueil or other relevant page
                navigateToAccueil(event);
            } catch (Exception e) {
                e.printStackTrace();
                messageLabel.setText("Une erreur est survenue. Veuillez réessayer.");
                messageLabel.setVisible(true);
            }
        }
    }

    @FXML
    private void navigateToAccueil(ActionEvent event) {
        try {
            Parent accueilPage = FXMLLoader.load(getClass().getResource("/Accueil.fxml"));
            Scene accueilScene = new Scene(accueilPage, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(accueilScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidPassword(String password) {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasSpecial = password.matches(".*[!@#$%^&*()].*");
        return hasUppercase && hasSpecial;
    }
}

