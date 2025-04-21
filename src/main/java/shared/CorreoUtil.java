/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

/**
 *
 * @author Usuario
 */
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class CorreoUtil {

    private static final String REMITENTE = "sistemabiblioteca6@gmail.com";
    private static final String CONTRASENIA = "eppn lsya enhp jkvs"; 

    public static void enviar(String destinatario, String asunto, String mensaje) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true"); 

        System.setProperty("https.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, CONTRASENIA); 
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMITENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);
            System.out.println("Correo enviado correctamente a " + destinatario);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
