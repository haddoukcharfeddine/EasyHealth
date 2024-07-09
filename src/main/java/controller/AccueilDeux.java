package controller;

import entite.Enum.Objectif;
import entite.Users.Client;
import entite.Users.ClientSport;
import entite.Users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.UserService;
import session.UserSession;
import javafx.scene.control.Label;

import java.io.IOException;

public class AccueilDeux {

    @FXML
    private MenuButton profileMenuBtn;
    @FXML
    private MenuItem addDishItem;
    @FXML
    private MenuItem profileItem;
    @FXML
    private MenuItem profileviewItem;

    @FXML
    private MenuItem gcommande;

    @FXML
    private MenuItem logoutItem;
    @FXML
    private TextField SearchText;
    @FXML
    private HBox CaloriesItem;
    @FXML
    private HBox ProteineItem;
    private User currentUser;
    private String userType;
    @FXML
    private Button platsBtn;
    @FXML
    private Button PanierBtn;

    public void setUserType(String userType) {
        this.userType = userType;
        updateMenuItemsVisibility();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        updateMenuItemsVisibility();
        updateCaloriesAndProteineLabels(); // Add this method call
    }

    @FXML
    private void initialize() {
        UserService userService = new UserService();
        UserSession session = UserSession.getInstance();
        String currentUserTelephone = session.getTelephone();
        currentUser = userService.getUserByTelephone(currentUserTelephone);
        if (currentUser != null) {
            userType = userService.getUserType(currentUser).toString();
            setCurrentUser(currentUser); // Ensure that the current user and userType are set, and visibility is updated
        }
    }

    private void handleStoreList() {
        System.out.println("Store List button clicked");
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            VBox root = loader.load();
            Stage stage = (Stage) profileMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void handlegestioncommande() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
            AnchorPane root = loader.load();
            Stage stage = (Stage) profileMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handlePlatsButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Plats.fxml"));
            VBox root = loader.load();
            Stage stage = (Stage) platsBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception here, such as displaying an error message to the user
        }
    }

    private void updateMenuItemsVisibility() {
        if (userType != null) {
            if (userType.equals("Vendeur")) {
                addDishItem.setVisible(true);
                profileviewItem.setVisible(true);
                platsBtn.setVisible(true);

                gcommande.setVisible(false);
                SearchText.setVisible(true);
                PanierBtn.setVisible(false);
                addDishItem.setOnAction(event -> handleAjouterPlatEditAction());
            } else {
                addDishItem.setVisible(false);
                profileviewItem.setVisible(false);
                platsBtn.setVisible(true);

                gcommande.setVisible(true);
                SearchText.setVisible(true);
                PanierBtn.setVisible(true);
            }
        }
    }

    private void updateCaloriesAndProteineLabels() {
        if (currentUser instanceof Client) {
            Client client = (Client) currentUser;
            Objectif objectif = client.getObjectif();
            if (objectif == Objectif.Perdre_du_poids || objectif == Objectif.Prendre_du_poids) {
                int[] besoins = ClientSport.calculerBesoinsNutritionnels();
                double tee = besoins[0];
                double proteinNeeds = besoins[1];

                // Update CaloriesItem label with the calculated value
                Label caloriesLabel = new Label("Calories: " + (int) Math.round(tee));
                CaloriesItem.getChildren().setAll(caloriesLabel);

                // Update ProteineItem label with the calculated value
                Label proteineLabel = new Label("Proteine: " + (int) Math.round(proteinNeeds));
                ProteineItem.getChildren().setAll(proteineLabel);

                // Make CaloriesItem and ProteineItem visible
                CaloriesItem.setVisible(true);
                ProteineItem.setVisible(true);
            } else {
                CaloriesItem.setVisible(false);
                ProteineItem.setVisible(false);
            }
        }
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        String searchText = SearchText.getText();
        System.out.println("Search action performed with text: " + searchText);
    }
}
