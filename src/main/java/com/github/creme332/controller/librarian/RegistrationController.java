package com.github.creme332.controller.librarian;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import com.github.creme332.model.AppState;
import com.github.creme332.controller.Screen;
import com.github.creme332.model.Librarian;
import com.github.creme332.view.librarian.RegistrationForm;

public class RegistrationController {
    RegistrationForm registrationForm;
    AppState app;

    public RegistrationController(AppState app, RegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
        this.app = app;

        // Add action listener to register button
        registrationForm.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerLibrarian();
            }
        });

        // Add action listener to back button
        registrationForm.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LOGIN_SCREEN));

        // Add key listener for Enter key press in form fields
        addEnterKeyListener(registrationForm.getEmailField());
        addEnterKeyListener(registrationForm.getPasswordField());
        addEnterKeyListener(registrationForm.getConfirmPasswordField());
        addEnterKeyListener(registrationForm.getFirstNameField());
        addEnterKeyListener(registrationForm.getLastNameField());
        addEnterKeyListener(registrationForm.getPhoneField());
        addEnterKeyListener(registrationForm.getAddressField());
    }

    private void addEnterKeyListener(javax.swing.JTextField textField) {
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used, but required by KeyListener interface
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    registerLibrarian();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used, but required by KeyListener interface
            }
        });
    }

    private void registerLibrarian() {
        String email = registrationForm.getEmail();
        char[] password = registrationForm.getPassword();
        char[] confirmPassword = registrationForm.getConfirmPassword();
        String firstName = registrationForm.getFirstName();
        String lastName = registrationForm.getLastName();
        String phone = registrationForm.getPhone();
        String address = registrationForm.getAddress();

        if (!new String(password).equals(new String(confirmPassword))) {
            registrationForm.setErrorMessage("Passwords do not match!");
            return;
        }

        if (email.isEmpty() || password.length == 0 || firstName.isEmpty() || lastName.isEmpty()
                || phone.isEmpty() || address.isEmpty()) {
            registrationForm.setErrorMessage("All fields must be filled out!");
            return;
        }

        Librarian librarian = new Librarian(email, new String(password), address, firstName, lastName, phone,
                "Librarian");
        try {
            Librarian.save(librarian);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        registrationForm.setSuccessMessage("Registration successful. Please log in.");
        app.setCurrentScreen(Screen.LOGIN_SCREEN);
    }
}
