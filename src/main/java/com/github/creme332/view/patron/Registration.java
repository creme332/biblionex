package com.github.creme332.view.patron;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyListener;
import java.util.Date;

import com.github.creme332.model.Patron;
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
        backButton = new JButton("Back");

        headerPanel.add(backButton, BorderLayout.WEST);

        // create form title
        JLabel heading = new JLabel("Patron Registration Form", SwingConstants.CENTER);
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

        // position Register button
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 17;
        registerButton = new JButton("Register");
        formPanel.add(registerButton, gbc);
    }

    public Patron getPatronDetails() {
        String email = emailField.getText().trim();
        char[] password = passwordField.getPassword();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String creditCardNo = creditCardField.getText().trim();

        // hard code birthday
        final Date birthday = new Date();

        return new Patron(email, new String(password), address, firstName, lastName, phone,
                creditCardNo, birthday);
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public void initEnterKeyListener(KeyListener listener) {
        JTextField[] textFields = {
                emailField,
                passwordField,
                confirmPasswordField,
                firstNameField,
                lastNameField,
                phoneField,
                addressField,
                creditCardField
        };

        for (JTextField textField : textFields) {
            textField.addKeyListener(listener);
        }
    }

    public void clearForm() {
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        phoneField.setText("");
        addressField.setText("");
        expiryDateField.setText("");
        securityCodeField.setText("");
        creditCardField.setText("");
    }

    public JButton getBackButton() {
        return backButton;
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public char[] getConfirmPassword() {
        return confirmPasswordField.getPassword();
    }
}
