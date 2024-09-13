package com.github.creme332.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

/**
 * Login form for both patron and admin.
 */
public class Login extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;
    private JButton forgotPasswordButton;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    public Login() {
        this.setLayout(new GridBagLayout());

        JPanel centerPanel = new JPanel(new BorderLayout()); // a container which will appear in middle of screen
        centerPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(centerPanel);

        JPanel imgContainer = createImageContainer(); // child of bodyPanel
        imgContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(imgContainer, BorderLayout.NORTH);

        JPanel formContainer = new JPanel(); // child of bodyPanel
        formContainer.setBorder(new EmptyBorder(new Insets(20, 0, 0, 0)));
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(formContainer, BorderLayout.CENTER);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formContainer.add(emailLabel);

        // Create text field for email
        emailField = new JTextField(20);
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(emailField);

        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create JLabel for password
        JLabel passwordLabel = new JLabel("Password", SwingConstants.LEFT);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(passwordLabel);

        // Create text field for password
        passwordField = new JPasswordField(20);
        passwordField.putClientProperty("FlatLaf.style", "showRevealButton: true");
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(passwordField);

        // add gap
        formContainer.add(Box.createRigidArea(new Dimension(0, 60)));

        // Create login button
        JPanel buttonContainer = getLoginButtonContainer();
        buttonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(buttonContainer);

        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create registration and forgot password buttons
        JPanel footerPanel = new JPanel(new BorderLayout());

        registerButton = new JButton("Create a new account");
        registerButton.setHorizontalTextPosition(SwingConstants.LEFT);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setBackground(null);

        forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setFocusPainted(false);
        forgotPasswordButton.setBackground(null);

        footerPanel.add(registerButton, BorderLayout.WEST);
        footerPanel.add(forgotPasswordButton, BorderLayout.EAST);
        centerPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public void showError() {
        emailField.putClientProperty("JComponent.outline", Color.red);
        passwordField.putClientProperty("JComponent.outline", Color.red);
    }

    public JTextField getEmailField() {
        return emailField;
    }

    /**
     * Resets form to its original state, erasing all data and removing any error
     * outlines.
     */
    public void clearForm() {
        emailField.setText("");
        passwordField.setText("");

        emailField.putClientProperty("JComponent.outline", "");
        passwordField.putClientProperty("JComponent.outline", "");
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    private JPanel getLoginButtonContainer() {
        JPanel buttonContainer = new JPanel(new GridBagLayout());
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(WIDTH, 50));
        buttonContainer.add(loginButton);
        return buttonContainer;
    }

    private JPanel createImageContainer() {
        JPanel imgContainer = new JPanel(new GridBagLayout());

        JLabel image = new JLabel();
        FontIcon icon = FontIcon.of(BootstrapIcons.PERSON_CIRCLE, 200);
        icon.setIconColor(Color.white);
        image.setIcon(icon);
        imgContainer.add(image);
        return imgContainer;
    }
}
