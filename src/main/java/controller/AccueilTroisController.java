package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import entite.Commande;
import service.UserService;
import session.UserSession;

import java.io.IOException;

public class AccueilTroisController {



    @FXML
    private MenuButton profileMenuBtn;

    @FXML
    private TableView<Commande> commandesTable;

    @FXML
    private TableColumn<Commande, Integer> idcColumn;

    @FXML
    private TableColumn<Commande, String> dateCommandeColumn;

    @FXML
    private TableColumn<Commande, Double> prixTotalColumn;

    @FXML
    private TableColumn<Commande, String> telephoneClientColumn;

    @FXML
    private TableColumn<Commande, String> telephoneVendeurColumn;

    @FXML
    private TableColumn<Commande, String> actionsColumn;

    @FXML
    private CheckBox availabilityCheckBox;

    @FXML
    private Button confirmAvailabilityButton;

    private ObservableList<Commande> commandesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize the table columns
        idcColumn.setCellValueFactory(new PropertyValueFactory<>("idc"));
        dateCommandeColumn.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
        prixTotalColumn.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        telephoneClientColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneClient"));
        telephoneVendeurColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneVendeur"));


        commandesTable.setItems(commandesList);

        actionsColumn.setCellFactory(createActionsCellFactory());

        loadCurrentAvailability();

        handleCheckAvailability();
    }

    private void loadCurrentAvailability() {
        UserSession session = UserSession.getInstance();
        UserService userService = new UserService();
        boolean isAvailable = userService.isLivreurAvailableByTelephone(session.getTelephone());
        availabilityCheckBox.setSelected(isAvailable);
    }

    private void updateAvailability() {
        boolean isAvailable = availabilityCheckBox.isSelected();
        UserSession session = UserSession.getInstance();
        UserService userService = new UserService();
        userService.updateLivreurAvailabilityByTelephone(session.getTelephone(), isAvailable);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Availability Update");
        alert.setHeaderText(null);
        alert.setContentText("Your availability status has been updated.");
        alert.showAndWait();
    }

    @FXML
    private void handleCheckAvailability() {

    }
    @FXML
    private void handleConfirmAvailability() {
        boolean isAvailable = availabilityCheckBox.isSelected();
        commandesTable.setVisible(isAvailable);
        if (isAvailable) {

        }

        updateAvailability();
    }

    private Callback<TableColumn<Commande, String>, TableCell<Commande, String>> createActionsCellFactory() {
        return column -> new TableCell<>() {
            private final Button acceptButton = new Button("Accept");
            private final Button refuseButton = new Button("Refuse");

            {
                acceptButton.setOnAction(event -> handleAcceptCommand(getTableView().getItems().get(getIndex())));
                refuseButton.setOnAction(event -> handleRefuseCommand(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(acceptButton, refuseButton);
                    hbox.setSpacing(10);
                    setGraphic(hbox);
                }
            }
        };
    }

    private void handleAcceptCommand(Commande commande) {

        System.out.println("Accepted: " + commande);
    }

    private void handleRefuseCommand(Commande commande) {

        System.out.println("Refused: " + commande);
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
}
