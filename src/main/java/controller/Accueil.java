package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Accueil {
    @FXML
    private Button PlatsBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Button faqBtn;

    @FXML
    private Button workWithUsBtn;

    @FXML
    private TextField SearchText;


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
    private void navigateToPlats() {
        try {
            Parent PlatsPage = FXMLLoader.load(getClass().getResource("/PlatsAccueil.fxml"));
            Scene PlatsScene = new Scene(PlatsPage, 800, 600);
            Stage stage = (Stage) PlatsBtn.getScene().getWindow();
            stage.setScene(PlatsScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleWorkWithUsClick() {
        try {
            Parent menuPage = FXMLLoader.load(getClass().getResource("/Travailler.fxml"));
            Scene menuScene = new Scene(menuPage, 800, 600);
            Stage stage = (Stage) workWithUsBtn.getScene().getWindow();
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
    @FXML
    private void handleSearchAction(ActionEvent event) {

        String searchText = SearchText.getText();
        System.out.println("Search action performed with text: " + searchText);

    }
}

