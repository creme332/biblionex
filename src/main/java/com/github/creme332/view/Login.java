package com.github.creme332.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

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

    private final Border redBorder = new LineBorder(Color.red);

    public Login() {
        this.setLayout(new GridBagLayout());

        JPanel main = new JPanel(); // a container which will appear in middle of screen
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        add(main);

        JPanel imgContainer = getImgContainer();
        main.add(imgContainer);

        // add gap between imgContainer and formContainer
        main.add(Box.createRigidArea(new Dimension(0, 60)));

        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        main.add(formContainer);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        formContainer.add(emailLabel);

        // Create text field for email
        emailField = new JTextField(20);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);

        formContainer.add(emailField);

        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create JLabel for password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        formContainer.add(passwordLabel);

        // Create text field for password
        passwordField = new JPasswordField(20);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        formContainer.add(passwordField);

        formContainer.add(Box.createRigidArea(new Dimension(0, 60)));

        // Create login button
        JPanel buttonContainer = getLoginButtonContainer();
        formContainer.add(buttonContainer);

        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create registration and forgot password buttons
        JPanel linkContainer = new JPanel();
        linkContainer.setLayout(new BoxLayout(linkContainer, BoxLayout.X_AXIS));
        linkContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton = new JButton("Create a new account");
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setBackground(null);

        forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setFocusPainted(false);
        forgotPasswordButton.setBackground(null);

        linkContainer.add(registerButton);
        linkContainer.add(Box.createRigidArea(new Dimension(20, 0))); // Space between buttons
        linkContainer.add(forgotPasswordButton);

        formContainer.add(linkContainer);
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
        emailField.setBorder(redBorder);
        passwordField.setBorder(redBorder);
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    private JPanel getLoginButtonContainer() {
        JPanel buttonContainer = new JPanel(new GridBagLayout());
        buttonContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton = new JButton("Login");

        loginButton.setPreferredSize(new Dimension(600, 50));
        buttonContainer.add(loginButton);
        return buttonContainer;
    }

    private JPanel getImgContainer() {
        JPanel imgContainer = new JPanel(new GridBagLayout());
        imgContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel image = new JLabel();
        FontIcon icon = FontIcon.of(BootstrapIcons.PERSON_CIRCLE, 200);
        icon.setIconColor(Color.white);
        image.setIcon(icon);
        imgContainer.add(image);
        return imgContainer;
    }
}
