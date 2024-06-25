package com.github.creme332.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Librarian;
import com.github.creme332.model.Patron;
import com.github.creme332.model.User;
import com.github.creme332.view.ForgotPassword;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Controller for the forgot password screen.
 */
public class ForgotPasswordController {
    private ForgotPassword forgotPasswordPage;
    private AppState app;
    private String verificationCode;

    public ForgotPasswordController(AppState app, ForgotPassword forgotPasswordPage) {
        this.forgotPasswordPage = forgotPasswordPage;
        this.app = app;

        // Load environment variables
        Dotenv dotenv = Dotenv.load();

        // Add action listener for the back button
        forgotPasswordPage.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LOGIN_SCREEN));

        // Add action listener for the submit button
        forgotPasswordPage.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = forgotPasswordPage.getEmail();
                    char[] newPassword = forgotPasswordPage.getNewPassword();
                    char[] confirmNewPassword = forgotPasswordPage.getConfirmNewPassword();

                    if (newPassword.length == 0 || confirmNewPassword.length == 0) {
                        JOptionPane.showMessageDialog(forgotPasswordPage, "Password fields cannot be empty.");
                        return;
                    }

                    if (!String.valueOf(newPassword).equals(String.valueOf(confirmNewPassword))) {
                        JOptionPane.showMessageDialog(forgotPasswordPage, "Passwords do not match.");
                        return;
                    }

                    // Find user by email
                    User user = null;
                    try {
                        user = Patron.findByEmail(email);
                        if (user == null) {
                            user = Librarian.findByEmail(email);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(forgotPasswordPage, "Database error: " + ex.getMessage());
                        return;
                    }

                    if (user == null) {
                        JOptionPane.showMessageDialog(forgotPasswordPage, "No account found with the given email.");
                        return;
                    }

                    // Generate and send verification code
                    verificationCode = generateVerificationCode();
                    sendVerificationEmail(dotenv.get("EMAIL_USERNAME"), dotenv.get("EMAIL_PASSWORD"), email, verificationCode);

                    // Show verification dialog
                    String enteredCode = forgotPasswordPage.showVerificationDialog();
                    if (verificationCode.equals(enteredCode)) {
                        // Update user's password
                        User.changePassword(user, newPassword);
                        JOptionPane.showMessageDialog(forgotPasswordPage, "Password has been reset successfully.");
                        forgotPasswordPage.clearForm();
                        app.setCurrentScreen(Screen.LOGIN_SCREEN);
                    } else {
                        JOptionPane.showMessageDialog(forgotPasswordPage, "Failed to change password. Verification code did not match.");
                        forgotPasswordPage.clearForm();
                        app.setCurrentScreen(Screen.LOGIN_SCREEN);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(forgotPasswordPage, "Error: " + ex.getMessage());
                }
            }
        });
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private void sendVerificationEmail(String username, String password, String toEmail, String code) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "Biblionex"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Verification Code");

            String htmlContent = "<html>"
                    + "<body>"
                    + "<h1>Password Reset Verification</h1>"
                    + "<p>Dear user,</p>"
                    + "<p>Your verification code is: <strong>" + code + "</strong></p>"
                    + "<p>Please enter this code in the application to reset your password.</p>"
                    + "<br>"
                    + "<p>Best regards,</p>"
                    + "<p>Biblionex Team</p>"
                    + "<hr>"
                    + "<p style='font-size:0.8em;'>© 2024 Biblionex. All rights reserved.</p>"
                    + "</body>"
                    + "</html>";

            message.setContent(htmlContent, "text/html");

            Transport.send(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Handle the exception as needed, such as logging it or showing an error message to the user
        }
    }
}
