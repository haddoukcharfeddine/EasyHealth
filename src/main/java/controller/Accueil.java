package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Accueil {
    @FXML
    private Button menusBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Button faqBtn;

    @FXML
    public void initialize() {
        menusBtn.setOnAction(event -> navigateToMenu());
        // Set action for login button
        loginBtn.setOnAction(event -> navigateToLogin());
        faqBtn.setOnAction(event -> navigateToFAQ());

    }
    @FXML
    private void navigateToLogin() {
        try {
            Parent loginPage = FXMLLoader.load(getClass().getResource("/LoginPage.fxml"));
            Scene loginScene = new Scene(loginPage,800, 600);
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void navigateToMenu() {
        try {
            Parent menuPage = FXMLLoader.load(getClass().getResource("/menu.fxml"));
            Scene menuScene = new Scene(menuPage, 800, 600);
            Stage stage = (Stage) menusBtn.getScene().getWindow();
            stage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void navigateToFAQ() {
        try {
            Parent faqPage = FXMLLoader.load(getClass().getResource("/faq.fxml"));
            Scene faqScene = new Scene(faqPage, 800, 600);
            Stage stage = (Stage) faqBtn.getScene().getWindow();
            stage.setScene(faqScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
