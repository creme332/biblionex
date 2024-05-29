package com.github.creme332.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JPanel {
    private JTextField userText;
    private JPasswordField passwordText;
    private JLabel success;

    public Login() {
        // Set the layout
        setLayout(null);

        // Create JLabel for username
        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        add(userLabel);

        // Create text field for username
        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        add(userText);

        // Create JLabel for password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        // Create text field for password
        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        add(passwordText);

        // Create login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        add(loginButton);

        // Create registration button
        JButton registerButton = new JButton("Create a new account");
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setBackground(null);
        registerButton.setBounds(10, 110, 190, 25);
        add(registerButton);

        // Create success message label
        success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        add(success);

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String password = new String(passwordText.getPassword());

                // Hardcoded username and password for validation
                if (user.equals("admin") && password.equals("password")) {
                    success.setText("Login successful!");
                } else {
                    success.setText("Login failed!");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch to registration page
            }
        });
    }
}
