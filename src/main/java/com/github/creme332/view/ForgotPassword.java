package com.github.creme332.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Forgot Password form.
 */
public class ForgotPassword extends JFrame {
    private JTextField emailField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmNewPasswordField;
    private JButton submitButton;
    private JButton backButton;

    public ForgotPassword() {
        setLayout(new BorderLayout());

        // Top panel for back button and title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        backButton = new JButton("Back");
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Reset Password", javax.swing.SwingConstants.CENTER);
        titleLabel.putClientProperty("FlatLaf.style", "font: $h1.font");
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Main panel for form
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel emailLabel = new JLabel("Email");
        mainPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        emailField = new JTextField(20); // Adjust the field size
        mainPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        JSeparator separator = new JSeparator();
        mainPanel.add(separator, gbc);

        // New Password field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JLabel newPasswordLabel = new JLabel("New Password");
        mainPanel.add(newPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        newPasswordField = new JPasswordField(10); // Adjust the field size
        newPasswordField.putClientProperty("FlatLaf.style", "showRevealButton: true");
        mainPanel.add(newPasswordField, gbc);

        // Confirm New Password field
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        JLabel confirmNewPasswordLabel = new JLabel("Confirm New Password");
        mainPanel.add(confirmNewPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        confirmNewPasswordField = new JPasswordField(10); // Adjust the field size
        confirmNewPasswordField.putClientProperty("FlatLaf.style", "showRevealButton: true");
        mainPanel.add(confirmNewPasswordField, gbc);

        // Submit button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        submitButton = new JButton("Submit");
        mainPanel.add(submitButton, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public char[] getNewPassword() {
        return newPasswordField.getPassword();
    }

    public char[] getConfirmNewPassword() {
        return confirmNewPasswordField.getPassword();
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void clearForm() {
        emailField.setText("");
        newPasswordField.setText("");
        confirmNewPasswordField.setText("");
    }

    public String showVerificationDialog() {
        return JOptionPane.showInputDialog(this,
                "An email was sent to you. Please enter the verification code in the email:", "Email Verification",
                JOptionPane.PLAIN_MESSAGE);
    }
}
