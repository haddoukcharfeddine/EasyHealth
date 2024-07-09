package controller;

import entite.Plat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.PlatService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PlatsAccueil implements Initializable {
    @FXML
    private Button AccueilButton;
    @FXML
    private Button loginBtn;
    @FXML
    private Button faqBtn;
    @FXML
    private TextField SearchText;
    @FXML
    private GridPane gridPane;
    private PlatService platService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        platService = new PlatService();
        displayAllPlats();
    }

    private void displayAllPlats() {
        List<Plat> plats = platService.getAllPlats();
        updateGridPane(plats);
    }

    private void updateGridPane(List<Plat> plats) {
        gridPane.getChildren().clear(); // Clear the current content
        for (Plat plat : plats) {
            addPlate(plat);
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
        addToCartButton.setOnAction(event -> handleAddToCart(plat));

        VBox plateInfo = new VBox(imageView, nameLabel, descriptionLabel, proteinLabel, caloriesLabel, addToCartButton);
        plateInfo.getStyleClass().add("plate-info");
        plateInfo.setSpacing(10); // Set spacing between elements in the VBox

        gridPane.addRow(gridPane.getRowCount(), plateInfo);
    }

    private void handleAddToCart(Plat plat) {
        System.out.println("Added to cart: " + plat.getNomPlat());
        // Implement the logic to add the plate to the cart
    }

    @FXML
    private void navigateToLogin() {
        navigateTo("/LoginPage.fxml");
    }

    @FXML
    private void navigateToFAQ() {
        navigateTo("/faq.fxml");
    }

    @FXML
    private void navigateToAccueil() {
        navigateTo("/Accueil.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(page, 800, 600);
            Stage stage = (Stage) AccueilButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        String searchText = SearchText.getText();
        List<Plat> filteredPlats = platService.getAllPlats().stream()
                .filter(plat -> plat.getNomPlat().toLowerCase().contains(searchText.toLowerCase()) ||
                        plat.getDescription().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
        updateGridPane(filteredPlats);
        System.out.println("Search action performed with text: " + searchText);
    }
}



