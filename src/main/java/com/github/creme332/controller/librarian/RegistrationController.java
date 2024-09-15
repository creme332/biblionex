package com.github.creme332.controller.librarian;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Librarian;
import com.github.creme332.view.librarian.RegistrationForm;

public class RegistrationController {
    RegistrationForm registrationForm;
    AppState app;

    public RegistrationController(AppState app, RegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
        this.app = app;

        // Add action listener to register button
        registrationForm.getRegisterButton().addActionListener(e -> registerLibrarian());

        // Add action listener to back button
        registrationForm.getBackButton().addActionListener(e -> app.setCurrentScreen(app.getPreviousScreen()));
    }

    private void registerLibrarian() {
        char[] password = registrationForm.getPassword();
        char[] confirmPassword = registrationForm.getConfirmPassword();
        Librarian librarian = registrationForm.getLibrarianData();

        if (!(librarian.getPassword()).equals(new String(confirmPassword))) {
            JOptionPane.showMessageDialog(null, "Passwords do not match!", "Invalid passwords",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (librarian.getEmail().isEmpty() || password.length == 0 || librarian.getFirstName().isEmpty()
                || librarian.getLastName().isEmpty()
                || librarian.getPhoneNo().isEmpty() || librarian.getAddress().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled out!", "Invalid form data",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Librarian.save(librarian);
            registrationForm.resetForm();
            JOptionPane.showMessageDialog(null, "Librarian account was successfully created.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An unknown error occurred.", "Unknown error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
