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
        List<Plat> plats = platService.getAllPlats();
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

        VBox plateInfo = new VBox(imageView, nameLabel, descriptionLabel, proteinLabel, caloriesLabel, addToCartButton);
        plateInfo.getStyleClass().add("plate-info");
        plateInfo.setSpacing(10); // Set spacing between elements in the VBox

        gridPane.addRow(gridPane.getRowCount(), plateInfo);
    }
    @FXML
    private void navigateToLogin() {
        try {
            Parent loginPage = FXMLLoader.load(getClass().getResource("/LoginPage.fxml"));
            Scene loginScene = new Scene(loginPage, 800, 600);
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToFAQ() {
        try {
            Parent faqPage = FXMLLoader.load(getClass().getResource("/faq.fxml"));
            Scene faqScene = new Scene(faqPage, 800, 600);
            Stage stage = (Stage) faqBtn.getScene().getWindow();
            stage.setScene(faqScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void navigateToAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AccueilButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleSearchAction(ActionEvent event) {

        String searchText = SearchText.getText();
        System.out.println("Search action performed with text: " + searchText);

    }
}



