package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class ForgotPasswordController {

    @FXML
    private TextField emailOrPhoneField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button sendButton;

    @FXML
    private TextField verificationCodeField;

    @FXML
    private Button verifyButton;

    @FXML
    private Button AccueilButton;

    private int verificationCode; // Variable to store the generated verification code

    // Function to generate a random 6-digit verification code
    private int generateRandomCode() {
        Random random = new Random();
        return random.nextInt(900000) + 100000; // Generates a random number between 100000 and 999999
    }

    @FXML
    private void handlePasswordRecovery() {
        String recipient = emailOrPhoneField.getText().trim();

        if (recipient.isEmpty()) {
            showErrorAlert("Erreur", "Veuillez entrer votre email");
            return;
        }

        // Generate and store the verification code
        verificationCode = generateRandomCode();

        // Send email with verification code
        sendEmail(recipient, verificationCode);

        showSuccessAlert("Succès", "Un email de récupération a été envoyé à " + recipient);

        // Navigate to verification code input section
        verificationCodeField.setVisible(true);
        verifyButton.setVisible(true);
    }

    private void sendEmail(String recipient, int verificationCode) {

        String host = "smtp.gmail.com";
        String port = "587";
        String username = "easyhealth241@gmail.com";
        String password = "yffy hnjt kebm prvs";


        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Get the Session object
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(username));

            // Set To: header field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            // Set Subject: header field
            message.setSubject("Password Recovery");

            // Set the actual message
            message.setText("Your recovery code is " + verificationCode);

            // Send the message
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleVerifyCode(ActionEvent event) {
        String enteredCode = verificationCodeField.getText().trim();

        if (enteredCode.isEmpty()) {
            showErrorAlert("Erreur", "Veuillez entrer le code de vérification.");
            return;
        }

        // Compare entered code with generated code
        if (enteredCode.equals(String.valueOf(verificationCode))) {
            showSuccessAlert("Succès", "Code de vérification correct. Vous pouvez réinitialiser votre mot de passe.");
            navigateToNewpass(event); // Pass the event to navigateToNewpass method
        } else {
            showErrorAlert("Erreur", "Code de vérification incorrect. Veuillez réessayer.");
        }
    }


    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void navigateToNewpass(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewPassword.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erreur de navigation", "Échec du chargement de la page de réinitialisation du mot de passe.");
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
            showErrorAlert("Erreur de navigation", "Échec du chargement de la page d'accueil.");
            e.printStackTrace();
        }
    }
}

