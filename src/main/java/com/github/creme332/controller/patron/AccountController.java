package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.model.User;
import com.github.creme332.view.patron.Account;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AccountController {
    private AppState app;
    private Account accountPage;

    public AccountController(AppState app, Account accountPage) {
        this.app = app;
        this.accountPage = accountPage;

        accountPage.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmitButton();
            }
        });
    }

    private void handleSubmitButton() {
        String firstName = accountPage.getFirstNameField().getText().trim();
        String lastName = accountPage.getLastNameField().getText().trim();
        String email = accountPage.getEmailField().getText().trim();
        String address = accountPage.getAddressField().getText().trim();
        String phone = accountPage.getPhoneField().getText().trim();
        char[] password = accountPage.getPasswordField().getPassword();
        char[] confirmPassword = accountPage.getConfirmPasswordField().getPassword();

        if (!String.valueOf(password).equals(String.valueOf(confirmPassword))) {
            JOptionPane.showMessageDialog(accountPage, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // fetch patron account
        Patron patron = (Patron) app.getLoggedInUser();

        int fieldsModified = 0;

        // Update patron details if set
        if (firstName.length() > 0) {
            patron.setFirstName(firstName);
            fieldsModified++;
        }

        if (lastName.length() > 0) {
            patron.setLastName(lastName);
            fieldsModified++;
        }

        if (email.length() > 0) {
            patron.setEmail(email);
            fieldsModified++;
        }

        if (address.length() > 0) {
            patron.setAddress(address);
            fieldsModified++;
        }

        if (phone.length() > 0) {
            patron.setPhoneNo(phone);
            fieldsModified++;
        }

        if (password.length > 0) {
            fieldsModified++;
        }

        if (confirmPassword.length > 0) {
            fieldsModified++;
        }

        try {
            Patron.update(patron);

            if (password.length > 0) {
                User.changePassword(patron, password);
            }

            if (fieldsModified > 0) {
                JOptionPane.showMessageDialog(accountPage, "Details updated successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                accountPage.resetForm();

                // update patron globally
                app.setLoggedInUser(Patron.findById(patron.getUserId()));
            } else {
                JOptionPane.showMessageDialog(accountPage, "Form cannot be left empty!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(accountPage, "Failed to update details: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
