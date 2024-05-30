package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AccueilDeux {

    @FXML
    private Button menusBtn;

    @FXML
    private Button faqBtn;

    @FXML
    private MenuButton profileMenuBtn;

    @FXML
    private MenuItem addDishItem;

    @FXML
    private MenuItem addMenuItem;

    @FXML
    private MenuItem logoutItem;

    @FXML
    private TextField emailField;

    @FXML
    private Button storeListBtn;

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
        faqBtn.setOnAction(event -> handleFAQ());
        storeListBtn.setOnAction(event -> handleStoreList());
        logoutItem.setOnAction(event -> handleLogout());

        // Set visibility of vendor-specific menu items
        updateMenuItemsVisibility();
    }

    private void handleMenus() {
        // Handle menus button action
        System.out.println("Menus button clicked");
    }

    private void handleFAQ() {
        // Handle FAQ button action
        System.out.println("FAQ button clicked");
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
            Parent root = loader.load();

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
}


