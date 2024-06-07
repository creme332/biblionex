package com.github.creme332.view.patron;

import javax.swing.*;

public class Registration extends JPanel {
    private JTextField userText;
    private JPasswordField passwordText;
    private JLabel success;
    private JButton registerButton;
    private JButton loginButton;

    public Registration() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Patron Registration Form");
        heading.putClientProperty("FlatLaf.style", "font: $h1.font");
        add(heading);

        JPanel container = new JPanel();
        add(container);

        // Create JLabel for username
        JLabel userLabel = new JLabel("Email");
        container.add(userLabel);

        // Create text field for username
        userText = new JTextField(20);
        container.add(userText);

        // Create JLabel for password
        JLabel passwordLabel = new JLabel("Password");
        container.add(passwordLabel);

        // Create text field for password
        passwordText = new JPasswordField(20);
        container.add(passwordText);

        // Create Register button
        registerButton = new JButton("Register");
        container.add(registerButton);

        // Create Login button
        loginButton = new JButton("Already have an account?");
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setBackground(null);
        container.add(loginButton);

        // Create success message label
        success = new JLabel("");
        container.add(success);
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public String getEnteredText() {
        return userText.getText();
    }

    public char[] getEnteredUser() {
        return passwordText.getPassword();
    }

    public void setErrorMessage(String text) {
        success.setText(text);
    }
}
