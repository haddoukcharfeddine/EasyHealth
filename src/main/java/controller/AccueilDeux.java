package controller;

import entite.Users.Client;
import entite.Users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.UserService;
import session.UserSession;

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
        updateCaloriesAndProteineLabels();
    }

    @FXML
    private void initialize() {
        UserService userService = new UserService();
        UserSession session = UserSession.getInstance();
        String currentUserTelephone = session.getTelephone();
        currentUser = userService.getUserByTelephone(currentUserTelephone);
        if (currentUser != null) {
            userType = userService.getUserType(currentUser).toString();
            setCurrentUser(currentUser);
        }
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
    void handlePlatsButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Plats.fxml"));
            VBox root = loader.load();
            Stage stage = (Stage) platsBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateMenuItemsVisibility() {
        if (userType != null) {
            if (userType.equals("Vendeur")) {
                addDishItem.setVisible(true);
                profileviewItem.setVisible(true);
                platsBtn.setVisible(false);
                SearchText.setVisible(false);
                PanierBtn.setVisible(false);
                addDishItem.setOnAction(event -> handleAjouterPlatEditAction());
            } else {
                addDishItem.setVisible(false);
                profileviewItem.setVisible(false);
                platsBtn.setVisible(true);
                SearchText.setVisible(true);
                PanierBtn.setVisible(true);
            }
        }
    }

    private void updateCaloriesAndProteineLabels() {
        if (currentUser instanceof Client) {
            Client client = (Client) currentUser;
            // Here you would have the logic for setting the labels based on client's details
            // For example:
            Label caloriesLabel = new Label("Calories: ...");
            CaloriesItem.getChildren().setAll(caloriesLabel);

            Label proteineLabel = new Label("Proteine: ...");
            ProteineItem.getChildren().setAll(proteineLabel);

            CaloriesItem.setVisible(true);
            ProteineItem.setVisible(true);
        } else {
            CaloriesItem.setVisible(false);
            ProteineItem.setVisible(false);
        }
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        String searchText = SearchText.getText();
        System.out.println("Search action performed with text: " + searchText);
    }
}
