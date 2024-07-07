package controller;

import entite.Users.User;
import entite.Plat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.PlatService;
import service.UserService;
import session.UserSession;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class ProfileController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label telephoneLabel;

    @FXML
    private VBox plateVBox; // Reference to the VBox for displaying plates
    @FXML
    private MenuButton profileMenuBtn;
    @FXML
    private MenuItem addDishItem;


    @FXML
    private MenuItem profileviewItem;

    @FXML
    private MenuItem logoutItem;
    @FXML
    private MenuItem profileItem;

    @FXML
    private Button AccueilButton;
    private UserService userService;
    private PlatService platService;
    private UserSession userSession;
    private User currentUser;

    @FXML
    public void initialize() {

        AccueilButton.setOnAction(event -> navigateToAccueilDeux());
        addDishItem.setOnAction((event -> handleAjouterPlatEditAction()));
        userService = new UserService();
        platService = new PlatService();
        userSession = UserSession.getInstance();

        // Fetch the current user from the database using their telephone number
        String currentUserTelephone = userSession.getTelephone();
        currentUser = userService.getUserByTelephone(currentUserTelephone);

        // Update the UI with the current user's information
        nameLabel.setText(currentUser.getNom());
        addressLabel.setText(currentUser.getAdresse());
        telephoneLabel.setText(currentUser.getTelephone());

        // Fetch plats of the current user
        List<Plat> plats = platService.getPlatsByVendeurTelephone(currentUserTelephone);

        // Display the fetched plates
        displayPlats(plats);

        if (currentUser != null) {
            String userType = String.valueOf(userService.getUserType(currentUser));

            if (userType != null) {
                if (userType.equals("Vendeur")) {
                    addDishItem.setVisible(true);
                } else {
                    addDishItem.setVisible(false);
                }
            }
        }
    }

    // Method to display the fetched plates in the VBox
    private void displayPlats(List<Plat> plats) {
        for (Plat plat : plats) {
            VBox platInfoBox = new VBox();
            platInfoBox.setSpacing(5);

            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(plat.getImageData())));
            imageView.setFitWidth(100); // Adjust the width of the image as needed
            imageView.setPreserveRatio(true); // Maintain the aspect ratio of the image
            platInfoBox.getChildren().add(imageView);
            // Create labels for plat information
            Label nomLabel = new Label("Name: " + plat.getNomPlat());
            Label descriptionLabel = new Label("Description: " + plat.getDescription());
            Label prixLabel = new Label("Price: " + plat.getPrix());
            Label proteinLabel = new Label("Protein: " + plat.getProtein());
            Label caloriesLabel = new Label("Calories: " + plat.getCalories());

            // Add labels to the platInfoBox
            platInfoBox.getChildren().addAll(nomLabel, descriptionLabel, prixLabel, proteinLabel, caloriesLabel);

            // Create and add an ImageView for the plat image


            // Optionally, you can add more UI components or customize the layout further

            // Add platInfoBox to the plateVBox
            plateVBox.getChildren().add(platInfoBox);
        }
    }
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
    @FXML
    private void handleEditProfile(){try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editProfile.fxml"));
        Parent root = loader.load();


        Stage stage = (Stage) profileMenuBtn.getScene().getWindow();


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }}
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
}