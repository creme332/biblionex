package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.view.patron.Account;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AccountController {
    private AppState app;
    private Account account;
    private Patron patron;

    public AccountController(AppState app, Account account) {
        this.app = app;
        this.account = account;

        account.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmitButton();
            }
        });
    }

    private void handleSubmitButton() {
        String firstName = account.getFirstNameField().getText();
        String lastName = account.getLastNameField().getText();
        String email = account.getEmailField().getText();
        String address = account.getAddressField().getText();
        String phone = account.getPhoneField().getText();
        char[] password = account.getPasswordField().getPassword();
        char[] confirmPassword = account.getConfirmPasswordField().getPassword();

        if (!String.valueOf(password).equals(String.valueOf(confirmPassword))) {
            JOptionPane.showMessageDialog(account, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update patron details
        patron.setFirstName(firstName);
        patron.setLastName(lastName);
        patron.setEmail(email);
        patron.setAddress(address);
        patron.setPhoneNo(phone);

        try {
            Patron.update(patron);

            if (password.length > 0) {
                Patron.changePassword(patron, password);
            }

            JOptionPane.showMessageDialog(account, "Details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(account, "Failed to update details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}

