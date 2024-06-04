package controller;

import entite.Enum.UserType;
import entite.Users.Livreur;
import entite.Users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    public void initialize() {

        creat.setOnAction(event -> navigateToCreateAccount());

        AccueilButton.setOnAction(event -> navigateToAccueil());

        loginButton.setOnAction(event -> {
            try {
                handleLogin(event);
            } catch (IOException | SQLException e) {
                showErrorAlert("Error", "An error occurred while logging in.");
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException, SQLException {
        String telephone = telephoneField.getText().trim();
        String password = passwordField.getText().trim();

        if (telephone.isEmpty() || password.isEmpty()) {
            showErrorAlert("Incomplete Information", "Please enter both telephone number and password.");
            return;
        }

        UserService userService = new UserService();
        boolean telephoneExists = userService.checkTelephoneExists(telephone);

        if (!telephoneExists) {
            showErrorAlert("Telephone Not Registered", "The telephone number entered is not registered.");
            return;
        }

        User user = userService.getUserByTelephone(telephone);

        if (user != null && userService.validateLogin(telephone, password)) {
            UserSession session = UserSession.getInstance();
            session.setTelephone(user.getTelephone());
            session.setUserId(user.getId());

            if (user.getUserType() == UserType.Livreur) {
                navigateToAccueilTrois();
            } else {
                navigateToAccueilDeux();
            }
        } else {
            showErrorAlert("Invalid Credentials", "Telephone number or password is incorrect.");
        }
    }

    @FXML
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AccueilButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Navigation Error", "Failed to load the Accueil page.");
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
            showErrorAlert("Navigation Error", "Failed to load the Home page.");
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
            showErrorAlert("Navigation Error", "Failed to load the AccueilTrois page.");
            e.printStackTrace();
        }
    }

    private void navigateToCreateAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/create_account.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) creat.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Navigation Error", "Failed to load the Create Account page.");
            e.printStackTrace();
        }
    }
}







