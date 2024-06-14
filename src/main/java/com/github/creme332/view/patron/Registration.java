package com.github.creme332.view.patron;

import javax.swing.*;
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
    private JButton loginButton;

    public Registration() {
        this.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        headerPanel.add(backButton);
        JLabel heading = new JLabel("Patron Registration Form");
        headerPanel.add(heading);
        add(headerPanel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Personal details
        formPanel.add(new JLabel("Personal details"));
        JPanel personalDetailsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        personalDetailsPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(20);
        personalDetailsPanel.add(firstNameField);
        personalDetailsPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(20);
        personalDetailsPanel.add(lastNameField);
        personalDetailsPanel.add(new JLabel("Email Address:"));
        emailField = new JTextField(20);
        personalDetailsPanel.add(emailField);
        personalDetailsPanel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField(20);
        personalDetailsPanel.add(phoneField);
        personalDetailsPanel.add(new JLabel("Home Address:"));
        addressField = new JTextField(20);
        personalDetailsPanel.add(addressField);
        formPanel.add(personalDetailsPanel);

        // Payment information
        formPanel.add(new JLabel("Payment information"));
        JPanel paymentInfoPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        paymentInfoPanel.add(new JLabel("Credit Card Number:"));
        CCNField = new JTextField(20);
        paymentInfoPanel.add(CCNField);
        paymentInfoPanel.add(new JLabel("")); // Placeholder for icons
        paymentInfoPanel.add(new JLabel("Expiry date:"));
        expiryDateField = new JTextField(20);
        paymentInfoPanel.add(expiryDateField);
        paymentInfoPanel.add(new JLabel("Security code:"));
        securityCodeField = new JTextField(20);
        paymentInfoPanel.add(securityCodeField);
        formPanel.add(paymentInfoPanel);

        // Account information
        formPanel.add(new JLabel("Account information"));
        JPanel accountInfoPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        accountInfoPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        accountInfoPanel.add(passwordField);
        accountInfoPanel.add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField(20);
        accountInfoPanel.add(confirmPasswordField);
        formPanel.add(accountInfoPanel);

        // Register button
        registerButton = new JButton("Register");
        formPanel.add(registerButton);

        // Success message
        success = new JLabel("");
        formPanel.add(success);

        add(formPanel, BorderLayout.CENTER);
    }

    public JButton getRegisterButton() {
        return registerButton;
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
