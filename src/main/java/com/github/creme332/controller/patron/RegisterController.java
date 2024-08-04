package com.github.creme332.controller.patron;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.utils.exception.UserVisibleException;
import com.github.creme332.view.patron.Registration;

public class RegisterController {
    Registration registrationPage;
    AppState app;

    public RegisterController(AppState app, Registration registrationPage) {
        this.registrationPage = registrationPage;
        this.app = app;

        // Add action listener to register button
        registrationPage.getRegisterButton().addActionListener(e -> registerPatron());

        // Add action listener to back button
        registrationPage.getBackButton().addActionListener(e -> app.setCurrentScreen(app.getPreviousScreen()));

        registrationPage.initEnterKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used, but required by KeyListener interface
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    registerPatron();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used, but required by KeyListener interface
            }
        });
    }

    private void registerPatron() {
        char[] password = registrationPage.getPassword();
        char[] confirmPassword = registrationPage.getConfirmPassword();

        if (!new String(password).equals(new String(confirmPassword))) {
            JOptionPane.showMessageDialog(registrationPage, "Passwords do not match!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Patron patron = registrationPage.getPatronDetails();
        try {
            Patron.save(patron);
            JOptionPane.showMessageDialog(registrationPage, "Registration successful. Please log in.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            registrationPage.clearForm();
            app.setCurrentScreen(Screen.LOGIN_SCREEN);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(registrationPage, "Unknown error occurred.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (UserVisibleException e) {
            JOptionPane.showMessageDialog(registrationPage, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
