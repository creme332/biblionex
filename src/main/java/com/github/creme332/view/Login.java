package com.github.creme332.view;

import javax.swing.*;

public class Login extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    private JButton loginButton;
    private JButton registerButton;

    public Login() {
        // Set the layout
        setLayout(null);

        // Create JLabel for username
        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        add(userLabel);

        // Create text field for username
        emailField = new JTextField(20);
        emailField.setBounds(100, 20, 165, 25);
        add(emailField);

        // Create JLabel for password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        // Create text field for password
        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        add(passwordField);

        // Create login button
        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        add(loginButton);

        // Create registration button
        registerButton = new JButton("Create a new account");
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setBackground(null);
        registerButton.setBounds(10, 110, 190, 25);
        add(registerButton);

        // Create success message label
        errorLabel = new JLabel("Invalid credentials");
        errorLabel.setBounds(10, 140, 300, 25);
        errorLabel.setVisible(false);
        add(errorLabel);

    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public void showError() {
        errorLabel.setVisible(true);
    }

    public void hideError() {
        errorLabel.setVisible(false);
    }
}
