package com.github.creme332.view.patron;

import javax.swing.*;

import com.github.creme332.utils.IconLoader;

import java.awt.*;

public class Registration extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField expiryDateField;
    private JTextField securityCodeField;
    private JTextField CCNField;
    private JLabel success;
    private JButton registerButton;
    private JButton backButton;

    public Registration() {
        this.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        backButton = new JButton("Back");
        headerPanel.add(backButton, BorderLayout.WEST);
        JLabel heading = new JLabel("Patron Registration Form", JLabel.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 25));
        headerPanel.add(heading, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(2000, 1000));
        GridBagConstraints gbc = new GridBagConstraints();
        // gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Serif", Font.PLAIN, 15);
        Font inputFont = new Font("Serif", Font.PLAIN, 15);

        // Personal details
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel personalDetailsLabel = new JLabel("Personal details");
        personalDetailsLabel.setFont(new Font("Serif", Font.BOLD, 20));
        formPanel.add(personalDetailsLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(labelFont);
        formPanel.add(firstNameLabel, gbc);
        gbc.gridy = 2;
        firstNameField = new JTextField(15);
        firstNameField.setFont(inputFont);
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(labelFont);
        formPanel.add(lastNameLabel, gbc);
        gbc.gridy = 2;
        lastNameField = new JTextField(15);
        lastNameField.setFont(inputFont);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel, gbc);
        gbc.gridy = 4;
        emailField = new JTextField(15);
        emailField.setFont(inputFont);
        formPanel.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(labelFont);
        formPanel.add(phoneLabel, gbc);
        gbc.gridy = 4;
        phoneField = new JTextField(15);
        phoneField.setFont(inputFont);
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JLabel addressLabel = new JLabel("Home Address:");
        addressLabel.setFont(labelFont);
        formPanel.add(addressLabel, gbc);
        gbc.gridy = 6;
        addressField = new JTextField(15);
        addressField.setFont(inputFont);
        formPanel.add(addressField, gbc);

        // Payment information
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JLabel paymentInfoLabel = new JLabel("Payment information");
        paymentInfoLabel.setFont(new Font("Serif", Font.BOLD, 20));
        formPanel.add(paymentInfoLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 9;
        JLabel ccNumberLabel = new JLabel("Credit Card Number:");
        ccNumberLabel.setFont(labelFont);
        formPanel.add(ccNumberLabel, gbc);

        gbc.gridx = 2;
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridheight = 3;
        IconLoader iconLoader = new IconLoader();
        try {
            // Load and scale the image
            ImageIcon ccIcon = iconLoader.loadIcon("/icons/credit_card.png", 120);
            JLabel ccIconLabel = new JLabel(ccIcon);
            formPanel.add(ccIconLabel, gbc);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
        }
        gbc.gridheight = 1;

        gbc.gridx = 0;
        gbc.gridy = 10;
        CCNField = new JTextField(15);
        CCNField.setFont(inputFont);
        formPanel.add(CCNField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        JLabel expiryDateLabel = new JLabel("Expiry date:");
        expiryDateLabel.setFont(labelFont);
        formPanel.add(expiryDateLabel, gbc);
        gbc.gridy = 12;
        expiryDateField = new JTextField(15);
        expiryDateField.setFont(inputFont);
        formPanel.add(expiryDateField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        JLabel securityCodeLabel = new JLabel("Security code:");
        securityCodeLabel.setFont(labelFont);
        formPanel.add(securityCodeLabel, gbc);
        gbc.gridy = 12;
        securityCodeField = new JTextField(15);
        securityCodeField.setFont(inputFont);
        formPanel.add(securityCodeField, gbc);

        // Account information
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        JLabel accountInfoLabel = new JLabel("Account information");
        accountInfoLabel.setFont(new Font("Serif", Font.BOLD, 20));
        formPanel.add(accountInfoLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 14;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        formPanel.add(passwordLabel, gbc);
        gbc.gridy = 15;
        passwordField = new JPasswordField(15);
        passwordField.setFont(inputFont);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 14;
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(labelFont);
        formPanel.add(confirmPasswordLabel, gbc);
        gbc.gridy = 15;
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setFont(inputFont);
        formPanel.add(confirmPasswordField, gbc);

        // Register button
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Register");
        formPanel.add(registerButton, gbc);

        // Success message
        gbc.gridy = 17;
        success = new JLabel("");
        formPanel.add(success, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public String getEmail() {
        return emailField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public char[] getConfirmPassword() {
        return confirmPasswordField.getPassword();
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getCreditCardNo() {
        return CCNField.getText();
    }

    public String getExpiryDate() {
        return expiryDateField.getText();
    }

    public String getSecurityCode() {
        return securityCodeField.getText();
    }

    public void setErrorMessage(String text) {
        success.setText(text);
    }

    public void setSuccessMessage(String text) {
        success.setText(text);
    }
}
