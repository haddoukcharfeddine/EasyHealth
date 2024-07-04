package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import entite.Plat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.PlatService;
import session.UserSession;

public class AjouterPlatController {

    @FXML
    private TextField nomPlatField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField prixField;
    @FXML
    private TextField proteineField;
    @FXML
    private TextField caloriesField;
    @FXML
    private TextField categorieField;
    @FXML
    private ImageView imageView;
    @FXML
    private MenuButton profileMenuBtn;
    @FXML
    private Button AccueilButton;
    private File selectedImageFile;

    @FXML
    private void initialize() {
        profileMenuBtn.getItems().forEach(this::setMenuItemAction);
    }

    private void setMenuItemAction(MenuItem menuItem) {
        switch (menuItem.getId()) {
            case "profileItem":
                menuItem.setOnAction(this::handleEditProfile);
                break;
            case "logoutItem":
                menuItem.setOnAction(this::handleLogout);
                break;
        }
    }

    @FXML
    private void handleSelectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedImageFile = fileChooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    private void handleAddPlat(ActionEvent event) {
        String nomPlat = nomPlatField.getText();
        String description = descriptionField.getText();
        double prix = Double.parseDouble(prixField.getText());
        int proteine = Integer.parseInt(proteineField.getText());
        int calories = Integer.parseInt(caloriesField.getText());
        String categorie = categorieField.getText();

        // Get the current user's telephone number from the UserSession
        String currentUserTelephone = UserSession.getInstance().getTelephone();

        if (currentUserTelephone == null) {
            // Handle case where current user's telephone is not available
            showErrorAlert("Error", "Failed to retrieve current user's telephone number");
            return;
        }

        // Prepare image data
        byte[] imageData = null;
        try {
            if (selectedImageFile != null) {
                FileInputStream inputStream = new FileInputStream(selectedImageFile);
                imageData = inputStream.readAllBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "Failed to read image file");
            return;
        }

        // Create a new instance of PlatService
        PlatService platService = new PlatService();

        // Add the new plat using PlatService
        platService.ajouterPlat(new Plat(0, nomPlat, description, prix, proteine, calories, currentUserTelephone, categorie, imageData));

        // Optionally, display a success message
        showInformationAlert("Success", "Plat added successfully");
    }

    @FXML
    private void navigateToAccueilDeux() {
        switchScene("/AccueilDeux.fxml");
    }

    @FXML
    private void handleEditProfile(ActionEvent event) {
        switchScene("/editprofile.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        switchScene("/Accueil.fxml");
    }

    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Objects.requireNonNull(profileMenuBtn.getScene()).setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInformationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
