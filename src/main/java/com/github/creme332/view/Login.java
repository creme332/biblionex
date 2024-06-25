package com.github.creme332.view;

import java.awt.BorderLayout;
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

        JPanel bodyPanel = new JPanel(); // a container which will appear in middle of screen
        bodyPanel.setBackground(Color.red);
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        add(bodyPanel);

        JPanel imgContainer = createImageContainer(); // child of bodyPanel
        imgContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgContainer.setBackground(Color.CYAN);
        bodyPanel.add(imgContainer);

        // add gap between imgContainer and formContainer
        bodyPanel.add(Box.createRigidArea(new Dimension(0, 60)));

        JPanel formContainer = new JPanel(); // child of bodyPanel
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBackground(Color.blue);
        formContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyPanel.add(formContainer);

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
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(passwordField);

        // add gap
        formContainer.add(Box.createRigidArea(new Dimension(0, 60)));

        // Create login button
        JPanel buttonContainer = getLoginButtonContainer();
        formContainer.add(buttonContainer);

        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create registration and forgot password buttons
        JPanel linkContainer = new JPanel(new BorderLayout());
        linkContainer.setBackground(Color.green);
        linkContainer.setPreferredSize(new Dimension(400, 50));

        registerButton = new JButton("Create a new account");
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setBackground(null);

        forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setFocusPainted(false);
        forgotPasswordButton.setBackground(null);

        linkContainer.add(registerButton, BorderLayout.WEST);
        linkContainer.add(forgotPasswordButton, BorderLayout.EAST);
        bodyPanel.add(linkContainer);

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

    public void resetFields() {
        emailField.setText("");
        passwordField.setText("");
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    private JPanel getLoginButtonContainer() {
        JPanel buttonContainer = new JPanel(new GridBagLayout());
        buttonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        loginButton = new JButton("Login");

        loginButton.setPreferredSize(new Dimension(600, 50));
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
