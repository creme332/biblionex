package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.github.creme332.model.Librarian;

import java.awt.*;

public class RegistrationForm extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JButton registerButton;
    private JButton backButton;

    public RegistrationForm() {
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());

        // create backButton
        backButton = new JButton("Back");

        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel heading = new JLabel("Librarian Registration Form", SwingConstants.CENTER);
        heading.putClientProperty("FlatLaf.style", "font: $h1.font");
        headerPanel.add(heading, BorderLayout.CENTER);

        return headerPanel;
    }

    private JScrollPane createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding form fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel firstNameLabel = new JLabel("First Name");
        formPanel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        firstNameField = new JTextField(15);
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Last Name"), gbc);
        gbc.gridx = 3;
        lastNameField = new JTextField(15);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Email Address"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(15);
        formPanel.add(emailField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Phone Number"), gbc);
        gbc.gridx = 3;
        phoneField = new JTextField(15);
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Home Address"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        addressField = new JTextField(15);
        formPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Password"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.putClientProperty("FlatLaf.style", "showRevealButton: true");
        formPanel.add(passwordField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Confirm Password"), gbc);
        gbc.gridx = 3;
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.putClientProperty("FlatLaf.style", "showRevealButton: true");
        formPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;

        gbc.gridy = 5;
        registerButton = new JButton("Create account");
        formPanel.add(registerButton, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        return scrollPane;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void resetForm() {
        emailField.setText(""); // Clears email field
        passwordField.setText(""); // Clears password field
        confirmPasswordField.setText(""); // Clears confirm password field
        firstNameField.setText(""); // Clears first name field
        lastNameField.setText(""); // Clears last name field
        phoneField.setText(""); // Clears phone field
        addressField.setText(""); // Clears address field
    }

    public Librarian getLibrarianData() {
        String email = emailField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        return new Librarian(email, new String(passwordField.getPassword()), address, firstName, lastName, phone,
                "Librarian");
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public char[] getConfirmPassword() {
        return confirmPasswordField.getPassword();
    }
}
