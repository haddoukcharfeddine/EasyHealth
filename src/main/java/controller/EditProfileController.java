package controller;

import entite.Enum.Activer;
import entite.Enum.Objectif;
import entite.Enum.Sexe;
import entite.Users.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.UserService;
import session.UserSession;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {
    private UserService userService;

    @FXML
    private TextField NomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField TelephoneField;

    @FXML
    private TextField PoidsField;

    @FXML
    private TextField TailleField;

    @FXML
    private TextField ageField;

    @FXML
    private ChoiceBox<String> objectiveChoiceBox;

    @FXML
    private ChoiceBox<String> ActiverChoiceBox;

    @FXML
    private ChoiceBox<String> SexeChoiceBox;

    @FXML
    private Button saveButton;

    private User currentUser;

    @FXML
    private MenuItem profileItem;

    @FXML
    private MenuItem addDishItem;

    @FXML
    private MenuItem addMenuItem;
    @FXML
    private MenuItem profileviewItem;

    @FXML
    private MenuItem logoutItem;
   @FXML
   private  MenuButton profileMenuBtn;
    @FXML
    private Button AccueilButton;
    @FXML
    private Button platsBtn;
    @FXML
    private Button menusBtn;
    @FXML
    private Label poidsLabel;
    @FXML
    private Label tailleLabel;
    @FXML
    private Label activiterLabel;
    @FXML
    private Label sexeLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label objecitiveLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        objectiveChoiceBox.getItems().addAll("Perdre_du_poids", "Prendre_du_poids", "Aucun");
        objectiveChoiceBox.setValue("Objectif");
        SexeChoiceBox.getItems().addAll("HOMME", "FEMME");
        SexeChoiceBox.setValue("Sexe");


        ActiverChoiceBox.getItems().addAll("SEDENTAIRE", "LEGERE", "MODEREE", "INTENSE");
        ActiverChoiceBox.setValue("Activiter");

        AccueilButton.setOnAction(event -> {
            if(currentUser instanceof Livreur) {
                navigateToAccueilTrois();
            } else {
                navigateToAccueilDeux();
            }
        });

        platsBtn.setOnAction(event -> handlePlatsButtonClick());
        userService = new UserService();
        UserSession session = UserSession.getInstance();
        String currentUserTelephone = session.getTelephone();


        currentUser = userService.getUserByTelephone(currentUserTelephone);
        if (currentUser != null) {
            String userType = String.valueOf(userService.getUserType(currentUser));

            if (userType != null) {
                if (userType.equals("Vendeur")) {
                    addDishItem.setVisible(true);
                    addMenuItem.setVisible(true);
                    profileviewItem.setVisible(true);
                    platsBtn.setVisible(false);
                    menusBtn.setVisible(false);

                    addDishItem.setOnAction(event -> handleAjouterPlatEditAction());
                    addMenuItem.setOnAction(event -> handleAddMenu());
                } else {
                    addDishItem.setVisible(false);
                    addMenuItem.setVisible(false);
                    profileviewItem.setVisible(false);
                    platsBtn.setVisible(false);
                    menusBtn.setVisible(false);

                }
            }

            if (userType.equals("Client")) {

                setupClientUI();


                objectiveChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if ("Perdre_du_poids".equals(newValue) || "Prendre_du_poids".equals(newValue)) {

                        PoidsField.setVisible(true);
                        TailleField.setVisible(true);
                        ActiverChoiceBox.setVisible(true);
                        SexeChoiceBox.setVisible(true);
                        ageField.setVisible(true);

                    } else {
                        PoidsField.setVisible(false);
                        TailleField.setVisible(false);
                        ActiverChoiceBox.setVisible(false);
                        SexeChoiceBox.setVisible(false);
                        ageField.setVisible(false);
                    }
                });
            } else {

                setupNonClientUI();
            }
        }
        loadUserData();
    }

    private void setupClientUI() {
        NomField.setVisible(true);
        emailField.setVisible(true);
        addressField.setVisible(true);
        TelephoneField.setVisible(true);
        objectiveChoiceBox.setVisible(true);
        PoidsField.setVisible(false);
        TailleField.setVisible(false);
        ActiverChoiceBox.setVisible(false);
        SexeChoiceBox.setVisible(false);
        ageField.setVisible(false);
    }

    private void setupNonClientUI() {
        PoidsField.setVisible(false);
        TailleField.setVisible(false);
        objectiveChoiceBox.setVisible(false);
        ActiverChoiceBox.setVisible(false);
        SexeChoiceBox.setVisible(false);
        ageField.setVisible(false);
        poidsLabel.setVisible(false);
        tailleLabel.setVisible(false);
        activiterLabel.setVisible(false);
        sexeLabel.setVisible(false);
        ageLabel.setVisible(false);
        objecitiveLabel.setVisible(false);

        NomField.setVisible(true);
        emailField.setVisible(true);
        addressField.setVisible(true);
        TelephoneField.setVisible(true);
    }


    private void loadUserData() {
        if (currentUser != null) {

            NomField.setText(currentUser.getNom());
            emailField.setText(currentUser.getEmail());
            addressField.setText(currentUser.getAdresse());
            TelephoneField.setText(currentUser.getTelephone());


            if (currentUser instanceof Client) {
                Client client = (Client) currentUser;
                String objective = String.valueOf(client.getObjectif());


                objectiveChoiceBox.setValue(objective);

                if (!objective.equals("Perdre_du_poids") && !objective.equals("Prendre_du_poids")) {
                    PoidsField.clear();
                    TailleField.clear();
                    ageField.clear();
                }

                if (client instanceof ClientSport) {
                    ClientSport clientSport = (ClientSport) client;
                    PoidsField.setText(String.valueOf(clientSport.getPoids()));
                    TailleField.setText(String.valueOf(clientSport.getTaille()));
                    ageField.setText(String.valueOf(clientSport.getAge()));
                    ActiverChoiceBox.setValue(clientSport.getActiver().toString());
                    SexeChoiceBox.setValue(clientSport.getSexe().toString());
                } else {

                    PoidsField.clear();
                    TailleField.clear();
                    ageField.clear();
                    ActiverChoiceBox.setValue(null);
                    SexeChoiceBox.setValue(null);
                }
            }
        }
    }

    @FXML
    private void handleSaveAction() {
        String name = NomField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String telephone = TelephoneField.getText();


        float poids = !PoidsField.getText().isEmpty() ? Float.parseFloat(PoidsField.getText()) : Float.NaN;
        float taille = !TailleField.getText().isEmpty() ? Float.parseFloat(TailleField.getText()) : Float.NaN;
        int age = !ageField.getText().isEmpty() ? Integer.parseInt(ageField.getText()) : -1;
        Objectif objectif = objectiveChoiceBox.getValue() != null ? Objectif.valueOf(objectiveChoiceBox.getValue()) : null;
        Activer activer = ActiverChoiceBox.getValue() != null ? Activer.valueOf(ActiverChoiceBox.getValue()) : null;
        Sexe sexe = SexeChoiceBox.getValue() != null ? Sexe.valueOf(SexeChoiceBox.getValue()) : null;


        currentUser.setNom(name);
        currentUser.setEmail(email);
        currentUser.setAdresse(address);
        currentUser.setTelephone(telephone);

        if (currentUser instanceof Client) {
            Client client = (Client) currentUser;
            client.setObjectif(objectif);

            if (objectif == Objectif.Perdre_du_poids || objectif == Objectif.Prendre_du_poids) {
                if (!(client instanceof ClientSport)) {
                    ClientSport clientSport = new ClientSport(client.getId(), name, email, telephone, address, client.getPassword(), objectif, poids, taille, age, sexe, activer);
                    userService.deleteUser(currentUser.getId());
                    userService.addUser(clientSport);
                    currentUser = clientSport;
                } else {
                    ClientSport clientSport = (ClientSport) client;
                    if (!Float.isNaN(poids)) clientSport.setPoids(poids);
                    if (!Float.isNaN(taille)) clientSport.setTaille(taille);
                    if (age != -1) clientSport.setAge(age);
                    if (sexe != null) clientSport.setSexe(sexe);
                    if (activer != null) clientSport.setActiver(activer);
                }
            } else if (objectif == Objectif.Aucun) {
                if (client instanceof ClientSport) {
                    Client newClient = new Client(client.getId(), name, email, telephone, address, client.getPassword(), Objectif.Aucun);
                    userService.deleteUser(currentUser.getId());
                    userService.addUser(newClient);
                    currentUser = newClient;
                }
            }
        } else if (currentUser instanceof Livreur) {
            Livreur livreur = (Livreur) currentUser;
            livreur.setDisponible(true);
        }

        try {
            userService.updateUser(currentUser);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Profile updated successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Update Failed", "An error occurred while updating the profile");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    private void handleAddMenu() {
        System.out.println("Add Menu action clicked");
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
    private void navigateToAccueilTrois() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilTrois.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AccueilButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erreur de navigation", "Échec du chargement de la page AccueilTrois.");
            e.printStackTrace();
        }
    }
}





