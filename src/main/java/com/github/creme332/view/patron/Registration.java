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

        JLabel heading = new JLabel("Registration");
        add(heading);

        JPanel container = new JPanel();
        container.setLayout(null);
        add(container);

        // Create JLabel for username
        JLabel userLabel = new JLabel("Email");
        userLabel.setBounds(10, 20, 80, 25);
        container.add(userLabel);

        // Create text field for username
        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        container.add(userText);

        // Create JLabel for password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        container.add(passwordLabel);

        // Create text field for password
        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        container.add(passwordText);

        // Create Register button
        registerButton = new JButton("Register");
        registerButton.setBounds(10, 80, 100, 25);
        container.add(registerButton);

        // Create Login button
        loginButton = new JButton("Already have an account?");
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setBackground(null);
        loginButton.setBounds(10, 110, 220, 25);
        container.add(loginButton);

        // Create success message label
        success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
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
