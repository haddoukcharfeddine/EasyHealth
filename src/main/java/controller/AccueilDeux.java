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

    private String userType;

    public void setUserType(String userType) {
        this.userType = userType;
        updateMenuItemsVisibility();
    }

    @FXML
    private void initialize() {

        menusBtn.setOnAction(event -> handleMenus());
        profileItem.setOnAction(event -> handleProfileEditAction());
        logoutItem.setOnAction(event -> handleLogout());


        updateMenuItemsVisibility();
    }

    private void handleMenus() {

        System.out.println("Menus button clicked");
    }



    private void handleStoreList() {

        System.out.println("Store List button clicked");
    }

    private void handleAddDish() {

        System.out.println("Add Dish action clicked");
    }
    private void handleAddMenu() {

        System.out.println("Add Dish action clicked");
    }

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

        String searchText = SearchText.getText();
        System.out.println("Search action performed with text: " + searchText);

    }
}


