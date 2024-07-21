package com.github.creme332.view.patron;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

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
    private JTextField creditCardField;
    private JLabel errorLabel;
    private JButton registerButton;
    private JButton backButton;

    public JLabel createLegend(String name) {
        JLabel legend = new JLabel(name);
        legend.putClientProperty("FlatLaf.style", "font: $h2.font");
        return legend;
    }

    public JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());

        // add padding
        headerPanel.setBorder(new EmptyBorder(10, 10, 0, 0));

        // create backButton
        backButton = new JButton();
        FontIcon backIcon = FontIcon.of(BootstrapIcons.ARROW_LEFT, 40);
        backIcon.setIconColor(Color.white);
        backButton.setIcon(backIcon);
        
        headerPanel.add(backButton, BorderLayout.WEST);

        // create form title
        JLabel heading = new JLabel("Patron Registration Form", JLabel.CENTER);
        heading.putClientProperty("FlatLaf.style", "font: $h1.font");
        headerPanel.add(heading, BorderLayout.CENTER);

        return headerPanel;
    }

    public Registration() {
        this.setLayout(new BorderLayout());

        // add Header
        add(createHeaderPanel(), BorderLayout.NORTH);

        // add Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        add(formPanel, BorderLayout.CENTER);

        // make form scrollable
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane);

        // define layout constraints for form
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // add Personal details legend
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel personalLegend = createLegend("Personal details");
        formPanel.add(personalLegend, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel firstNameLabel = new JLabel("First Name");
        formPanel.add(firstNameLabel, gbc);
        gbc.gridy = 2;
        firstNameField = new JTextField(15);
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel lastNameLabel = new JLabel("Last Name");
        formPanel.add(lastNameLabel, gbc);
        gbc.gridy = 2;
        lastNameField = new JTextField(15);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel emailLabel = new JLabel("Email Address");
        formPanel.add(emailLabel, gbc);
        gbc.gridy = 4;
        emailField = new JTextField(15);
        formPanel.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JLabel phoneLabel = new JLabel("Phone Number");
        formPanel.add(phoneLabel, gbc);
        gbc.gridy = 4;
        phoneField = new JTextField(15);
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JLabel addressLabel = new JLabel("Home Address");
        formPanel.add(addressLabel, gbc);
        gbc.gridy = 6;
        addressField = new JTextField(15);
        formPanel.add(addressField, gbc);

        // Payment information
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JLabel paymentLegend = createLegend("Payment information");
        formPanel.add(paymentLegend, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 9;
        JLabel ccNumberLabel = new JLabel("Credit Card Number");
        formPanel.add(ccNumberLabel, gbc);

        gbc.gridx = 2;
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridheight = 3;
        IconLoader iconLoader = new IconLoader();
        try {
            // Load and scale the image
            ImageIcon ccIcon = iconLoader.loadIcon("/icons/credit_card.png", 100, 413);
            JLabel ccIconLabel = new JLabel(ccIcon);
            formPanel.add(ccIconLabel, gbc);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
            System.exit(0);
        }
        gbc.gridheight = 1;

        gbc.gridx = 0;
        gbc.gridy = 10;
        creditCardField = new JTextField(15);
        formPanel.add(creditCardField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        JLabel expiryDateLabel = new JLabel("Expiry Date");
        formPanel.add(expiryDateLabel, gbc);
        gbc.gridy = 12;
        expiryDateField = new JTextField(15);
        formPanel.add(expiryDateField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        JLabel securityCodeLabel = new JLabel("Security Code");
        formPanel.add(securityCodeLabel, gbc);
        gbc.gridy = 12;
        securityCodeField = new JTextField(15);
        formPanel.add(securityCodeField, gbc);

        // Account information
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        JLabel accountLegend = createLegend("Account information");
        formPanel.add(accountLegend, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 14;
        JLabel passwordLabel = new JLabel("Password");
        formPanel.add(passwordLabel, gbc);
        gbc.gridy = 15;
        passwordField = new JPasswordField(15);
        passwordField.putClientProperty("FlatLaf.style", "showRevealButton: true");
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 14;
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        formPanel.add(confirmPasswordLabel, gbc);
        gbc.gridy = 15;
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.putClientProperty("FlatLaf.style", "showRevealButton: true");
        formPanel.add(confirmPasswordField, gbc);

        // position error label
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.gridwidth = 2;
        errorLabel = new JLabel("");
        errorLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        formPanel.add(errorLabel, gbc);

        // position Register button
        gbc.gridy = 17;
        registerButton = new JButton("Register");
        formPanel.add(registerButton, gbc);
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
        return creditCardField.getText();
    }

    public String getExpiryDate() {
        return expiryDateField.getText();
    }

    public String getSecurityCode() {
        return securityCodeField.getText();
    }

    public void setErrorMessage(String text) {
        errorLabel.setText(text);
    }

    public void setSuccessMessage(String text) {
        errorLabel.setText(text);
    }

    public JTextField getAddressField() {
        return addressField;
    }

        public JTextField getPhoneField() {
        return phoneField;
    }

        public JTextField getLastNameField() {
        return lastNameField;
    }

        public JTextField getFirstNameField() {
        return firstNameField;
    }

        public JTextField getConfirmPasswordField() {
        return confirmPasswordField;
    }

        public JTextField getPasswordField() {
        return passwordField;
    }

        public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getCreditCardField(){
        return creditCardField;
    }
}
