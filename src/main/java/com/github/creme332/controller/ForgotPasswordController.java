package com.github.creme332.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Librarian;
import com.github.creme332.model.Patron;
import com.github.creme332.model.User;
import com.github.creme332.view.ForgotPassword;

/**
 * Controller for the forgot password screen.
 */
public class ForgotPasswordController {
    private ForgotPassword forgotPasswordPage;
    private AppState app;

    public ForgotPasswordController(AppState app, ForgotPassword forgotPasswordPage) {
        this.forgotPasswordPage = forgotPasswordPage;
        this.app = app;

        // Add action listener for the back button
        forgotPasswordPage.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LOGIN_SCREEN));

        // Add action listener for the submit button
        forgotPasswordPage.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                User user = Patron.findByEmail(email);
                if (user == null) {
                    user = Librarian.findByEmail(email);
                }

                if (user == null) {
                    JOptionPane.showMessageDialog(forgotPasswordPage, "No account found with the given email.");
                    return;
                }

                // Update user's password
                user.setPassword(String.valueOf(newPassword));
                JOptionPane.showMessageDialog(forgotPasswordPage, "Password has been reset successfully.");
                forgotPasswordPage.clearForm();
                app.setCurrentScreen(Screen.LOGIN_SCREEN);
            }
        });
    }
}
