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
        // Initialise les actions des éléments du menu déroulant profil
        profileMenuBtn.getItems().forEach(this::setMenuItemAction);
    }

    // Associe une action à chaque élément du menu déroulant profil
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

    // Gère le chargement d'une image depuis le système de fichiers
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

    // Gère l'ajout d'un plat
    @FXML
    private void handleAddPlat(ActionEvent event) {
        // Récupère les informations entrées par l'utilisateur depuis l'interface
        String nomPlat = nomPlatField.getText();
        String description = descriptionField.getText();
        double prix = Double.parseDouble(prixField.getText());
        int proteine = Integer.parseInt(proteineField.getText());
        int calories = Integer.parseInt(caloriesField.getText());
        String categorie = categorieField.getText();

        // Récupère le numéro de téléphone de l'utilisateur courant depuis la session utilisateur
        String currentUserTelephone = UserSession.getInstance().getTelephone();

        // Vérifie si le numéro de téléphone de l'utilisateur courant est disponible
        if (currentUserTelephone == null) {
            showErrorAlert("Error", "Failed to retrieve current user's telephone number");
            return;
        }

        // Prépare les données de l'image sous forme de tableau de bytes
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

        // Crée une nouvelle instance de PlatService pour gérer les opérations sur les plats
        PlatService platService = new PlatService();

        // Ajoute le nouveau plat en utilisant PlatService
        platService.ajouterPlat(new Plat(0, nomPlat, description, prix, proteine, calories, currentUserTelephone, categorie, imageData));

        // Affiche éventuellement un message de succès à l'utilisateur
        showInformationAlert("Success", "Plat added successfully");
    }

    // Navigue vers la page d'accueil après avoir ajouté un plat
    @FXML
    private void navigateToAccueilDeux() {
        switchScene("/AccueilDeux.fxml");
    }

    // Gère la navigation vers la page d'édition du profil utilisateur
    @FXML
    private void handleEditProfile(ActionEvent event) {
        switchScene("/editprofile.fxml");
    }

    // Gère la déconnexion de l'utilisateur
    @FXML
    private void handleLogout(ActionEvent event) {
        switchScene("/Accueil.fxml");
    }

    // Change la scène vers le fichier FXML spécifié
    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Objects.requireNonNull(profileMenuBtn.getScene()).setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Affiche une alerte d'erreur avec un titre et un message spécifiques
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Affiche une alerte d'information avec un titre et un message spécifiques
    private void showInformationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
