package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AccueilDeux {

    @FXML
    private Button menusBtn;


    @FXML
    private MenuButton profileMenuBtn;
    @FXML
    private MenuItem addDishItem;
    @FXML
    private MenuItem profileItem;

    @FXML
    private MenuItem addMenuItem;

    @FXML
    private MenuItem logoutItem;

    @FXML
    private TextField SearchText;

    @FXML
    private HBox footer;

    private String userType; // Store the user type

    public void setUserType(String userType) {
        this.userType = userType;
        updateMenuItemsVisibility();
    }

    @FXML
    private void initialize() {
        // Initialize your buttons and other components if needed
        menusBtn.setOnAction(event -> handleMenus());
        profileItem.setOnAction(event -> handleProfileEditAction());
        logoutItem.setOnAction(event -> handleLogout());

        // Set visibility of vendor-specific menu items
        updateMenuItemsVisibility();
    }

    private void handleMenus() {
        // Handle menus button action
        System.out.println("Menus button clicked");
    }



    private void handleStoreList() {
        // Handle store list button action
        System.out.println("Store List button clicked");
    }

    private void handleAddDish() {
        // Handle add dish action
        System.out.println("Add Dish action clicked");
    }
    private void handleAddMenu() {
        // Handle add dish action
        System.out.println("Add Dish action clicked");
    }

    private void handleLogout() {
        try {
            // Load the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            VBox root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) profileMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleProfileEditAction() {
        try {
            // Load the EditProfile FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editprofile.fxml"));
            VBox root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) profileMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void updateMenuItemsVisibility() {
        if (userType != null) {
            if (userType.equals("Vendeur")) {
                addDishItem.setVisible(true);
                addMenuItem.setVisible(true);
                addDishItem.setOnAction(event -> handleAddDish());
                addMenuItem.setOnAction(event -> handleAddMenu());
            } else {
                addDishItem.setVisible(false);
                addMenuItem.setVisible(false);
            }
        }
    }
    @FXML
    private void handleSearchAction(ActionEvent event) {
        // Perform the action you want when Enter is pressed in the search text field
        String searchText = SearchText.getText();
        System.out.println("Search action performed with text: " + searchText);
        // Add your search logic here
    }
}


