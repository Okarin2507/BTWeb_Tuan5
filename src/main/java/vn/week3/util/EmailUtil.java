package vn.week3.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.Random;

public class EmailUtil {
    private static final String FROM_EMAIL = "lengonhuttan2005@gmail.com";
    private static final String APP_PASSWORD = "ealv umni bebe pcnm";

    public static String generateOTP() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    public static void sendOtpEmail(String recipientEmail, String otp) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("[Project Manager] Ma OTP xac thuc");
        String emailContent = "<h3>Chao ban,</h3><p>Ma OTP de xac thuc tai khoan cua ban la:</p>"
                            + "<h1 style='color: #4A90E2;'>" + otp + "</h1>";
        message.setContent(emailContent, "text/html; charset=utf-8");
        Transport.send(message);
    }
}