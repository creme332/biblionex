package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VendorForm extends JPanel {
    private JTextField emailField;
    private JTextField nameField;
    private JTextField contactPersonField;
    private JButton saveButton;
    private JButton backButton;

    public VendorForm() {
        this.setLayout(new BorderLayout());

        // Add Header
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Add Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        add(formPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add Form Fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Email"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JLabel("Name"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        emailField = new JTextField(15);
        formPanel.add(emailField, gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Contact Person"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        contactPersonField = new JTextField(32);
        formPanel.add(contactPersonField, gbc);
        gbc.gridwidth = 1; // Reset to default

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across two columns
        saveButton = new JButton("Submit");
        formPanel.add(saveButton, gbc);
        gbc.gridwidth = 1; // Reset to default
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 10, 0, 0));
        backButton = new JButton("< Back");
        headerPanel.add(backButton, BorderLayout.WEST);
        JLabel heading = new JLabel("Vendor Registration Form", JLabel.CENTER);
        heading.putClientProperty("FlatLaf.style", "font: $h1.font");
        headerPanel.add(heading, BorderLayout.CENTER);
        return headerPanel;
    }

    public JTextField getEmailField() { return emailField; }
    public JTextField getNameField() { return nameField; }
    public JTextField getContactPersonField() { return contactPersonField; }
    public JButton getSaveButton() { return saveButton; }
    public JButton getBackButton() { return backButton; }
}
