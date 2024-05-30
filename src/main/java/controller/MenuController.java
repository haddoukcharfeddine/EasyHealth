package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button backBtn;

    @FXML
    public void initialize() {
        // Set action for back button
        backBtn.setOnAction(event -> handleBackButtonAction());
    }

    @FXML
    private void handleBackButtonAction() {
        try {
            Parent accueilPage = FXMLLoader.load(getClass().getResource("/accueil.fxml"));
            Scene accueilScene = new Scene(accueilPage, 800, 600);
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(accueilScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

