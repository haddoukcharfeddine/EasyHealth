package controller;

import entite.Plat;
import entite.Users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.PlatService;
import service.UserService;
import session.UserSession;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlatsController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField SearchText;
    private PlatService platService;
    @FXML
    private Button AccueilButton;

    @FXML
    private MenuItem profileItem;

    @FXML
    private MenuItem settingsItem;

    @FXML
    private MenuItem addDishItem;
    private UserService userService;
    private User currentUser;
    @FXML
    private MenuItem profileviewItem;
    @FXML
    private Button platsBtn;
    @FXML
    private MenuItem logoutItem;
    @FXML
    private MenuButton profileMenuBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AccueilButton.setOnAction(event -> navigateToAccueilDeux());
        profileItem.setOnAction(event -> handleProfileEditAction());
        logoutItem.setOnAction(event -> handleLogout());
        profileviewItem.setOnAction((event -> handleProfileViewItemAction()));
        addDishItem.setOnAction((event -> handleAjouterPlatEditAction()));
        platService = new PlatService();
        List<Plat> plats = platService.getAllPlats();
        for (Plat plat : plats) {
            addPlate(plat);
        }
        userService = new UserService();
        UserSession session = UserSession.getInstance();
        String currentUserTelephone = session.getTelephone();


        currentUser = userService.getUserByTelephone(currentUserTelephone);
        if (currentUser != null) {
            String userType = String.valueOf(userService.getUserType(currentUser));

            if (userType != null) {
                if (userType.equals("Vendeur")) {
                    addDishItem.setVisible(true);
                    profileviewItem.setVisible(true);

                } else {
                    addDishItem.setVisible(false);
                    profileviewItem.setVisible(false);

                }
            }
        }
    }

    private void addPlate(Plat plat) {
        ByteArrayInputStream bis = new ByteArrayInputStream(plat.getImageData());
        Image image = new Image(bis);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.getStyleClass().add("plate-image");
        Label nameLabel = new Label(plat.getNomPlat());
        Label descriptionLabel = new Label(plat.getDescription());
        Label proteinLabel = new Label("Protein: " + plat.getProtein());
        Label caloriesLabel = new Label("Calories: " + plat.getCalories());
        Button addToCartButton = new Button("Ajouter au Panier");

        VBox plateInfo = new VBox(imageView, nameLabel, descriptionLabel, proteinLabel, caloriesLabel, addToCartButton);
        plateInfo.getStyleClass().add("plate-info");
        plateInfo.setSpacing(10); // Set spacing between elements in the VBox

        gridPane.addRow(gridPane.getRowCount(), plateInfo);
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        String searchText = SearchText.getText();
        System.out.println("Search action performed with text: " + searchText);
    }
    @FXML
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void navigateToAccueilDeux() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilDeux.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AccueilButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erreur de navigation", "Échec du chargement de la page AccueilDeux.");
            e.printStackTrace();
        }
    }
    @FXML
    private void handleAjouterPlatEditAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPlat.fxml"));
            VBox root = loader.load();
            Stage stage = (Stage) profileMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleProfileViewItemAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            VBox root = loader.load();
            Stage stage = (Stage) profileMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleLogout() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) profileMenuBtn.getScene().getWindow();


            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleProfileEditAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editprofile.fxml"));
            VBox root = loader.load();
            Stage stage = (Stage) profileMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

