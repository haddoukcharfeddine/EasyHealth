package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {
    // Méthode statique pour envoyer un email
    public static void envoyerEmail(String destinataire, String sujet, String contenu) {
        // Paramètres du serveur SMTP
        String host = "smtp.gmail.com"; // Adresse du serveur SMTP de Gmail
        final String user = "easyhealth241@gmail.com"; //  adresse email Gmail
        final String password = "passwordhere"; //  mot de passe Gmail (ou mot de passe d'application)

        // Propriétés du serveur SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host); // Définition de l'hôte SMTP
        properties.put("mail.smtp.auth", "true"); // Activation de l'authentification SMTP
        properties.put("mail.smtp.starttls.enable", "true"); // Activation de STARTTLS pour la sécurité
        properties.put("mail.smtp.port", "587"); // Port SMTP pour TLS/STARTTLS

        // Création de la session SMTP avec l'authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password); // Retourne l'authentification avec l'email et le mot de passe
            }
        });

        try {
            // Création du message MIME
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user)); // Définition de l'expéditeur du message
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinataire)); // Ajout du destinataire du message
            message.setSubject(sujet); // Définition du sujet du message
            message.setText(contenu); // Définition du contenu du message

            // Envoi du message
            Transport.send(message); // Envoi effectif du message

            System.out.println("Email envoyé avec succès");

        } catch (MessagingException e) {
            e.printStackTrace(); // En cas d'erreur lors de l'envoi de l'email, affiche l'erreur
        }
    }
}

