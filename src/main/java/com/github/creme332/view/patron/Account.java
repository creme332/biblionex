package com.github.creme332.view.patron;

import javax.swing.*;
import java.awt.*;

public class Account extends JPanel {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton submitButton;

    public Account() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("Update Profile Details", SwingConstants.CENTER);
        titleLabel.putClientProperty("FlatLaf.style", "font: bold $h2.font");
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        JLabel firstNameLabel = new JLabel("First Name");
        add(firstNameLabel, gbc);
        gbc.gridx++;
        firstNameField = new JTextField(15);
        add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lastNameLabel = new JLabel("Last Name");
        add(lastNameLabel, gbc);
        gbc.gridx++;
        lastNameField = new JTextField(15);
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel emailLabel = new JLabel("Email");
        add(emailLabel, gbc);
        gbc.gridx++;
        emailField = new JTextField(15);
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel addressLabel = new JLabel("Address");
        add(addressLabel, gbc);
        gbc.gridx++;
        addressField = new JTextField(15);
        add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel phoneLabel = new JLabel("Phone Number");
        add(phoneLabel, gbc);
        gbc.gridx++;
        phoneField = new JTextField(15);
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JSeparator separator = new JSeparator();
        add(separator, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password");
        add(passwordLabel, gbc);
        gbc.gridx++;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        add(confirmPasswordLabel, gbc);
        gbc.gridx++;
        confirmPasswordField = new JPasswordField(15);
        add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        submitButton = new JButton("Submit Changes");
        add(submitButton, gbc);
    }

    /**
     * Clears all field on form
     */
    public void resetForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        addressField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    public JTextField getFirstNameField() {
        return firstNameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getAddressField() {
        return addressField;
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }
}
