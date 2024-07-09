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
import util.EmailUtil; // Assurez-vous que EmailUtil est bien importé

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
    private MenuItem logoutItem;
    @FXML
    private MenuButton profileMenuBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialisation des actions des boutons et des éléments de menu
        AccueilButton.setOnAction(event -> navigateToAccueilDeux());
        profileItem.setOnAction(event -> handleProfileEditAction());
        logoutItem.setOnAction(event -> handleLogout());
        profileviewItem.setOnAction(event -> handleProfileViewItemAction());
        addDishItem.setOnAction(event -> handleAjouterPlatEditAction());
        platService = new PlatService();

        // Récupération de tous les plats depuis le service
        List<Plat> plats = platService.getAllPlats();

        // Ajout de chaque plat à la grille d'affichage
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

    // Méthode privée pour ajouter un plat à la grille d'affichage
    private void addPlate(Plat plat) {
        // Conversion des données de l'image en Image JavaFX
        ByteArrayInputStream bis = new ByteArrayInputStream(plat.getImageData());
        Image image = new Image(bis);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.getStyleClass().add("plate-image");

        // Création des étiquettes pour afficher les détails du plat
        Label nameLabel = new Label(plat.getNomPlat());
        Label descriptionLabel = new Label(plat.getDescription());
        Label proteinLabel = new Label("Protein: " + plat.getProtein());
        Label caloriesLabel = new Label("Calories: " + plat.getCalories());

        // Bouton pour ajouter au panier (exemple)
        Button addToCartButton = new Button("Ajouter au Panier");

        // Conteneur VBox pour regrouper les éléments du plat
        VBox plateInfo = new VBox(imageView, nameLabel, descriptionLabel, proteinLabel, caloriesLabel, addToCartButton);
        plateInfo.getStyleClass().add("plate-info");
        plateInfo.setSpacing(10); // Définit l'espacement entre les éléments dans le VBox

        // Ajout du conteneur VBox à la grille d'affichage
        gridPane.addRow(gridPane.getRowCount(), plateInfo);
    }

    // Méthode pour ajouter un plat et envoyer un email
    public void ajouterPlat(Plat plat) {
        platService.ajouterPlat(plat); // Utilise la méthode ajoutée du service

        // Le service gère maintenant l'envoi de l'email
    }

    // Gère l'action de recherche
    @FXML
    private void handleSearchAction(ActionEvent event) {
        String searchText = SearchText.getText();
        System.out.println("Search action performed with text: " + searchText);
        // Ici, vous pourriez implémenter la logique de recherche en fonction du texte saisi
        // par exemple, filtrer et afficher uniquement les plats correspondant à la recherche
    }

    // Affiche une alerte d'erreur avec un titre et un message spécifiques
    @FXML
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigation vers la page d'accueil deux
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

    // Gère l'action d'ajout de plat
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

    // Affiche une alerte avec un type spécifique, un titre et un message
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Gère l'action de visualisation du profil
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

    // Gère la déconnexion de l'utilisateur
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

    // Gère l'action d'édition du profil utilisateur
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

