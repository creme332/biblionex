package com.github.creme332.view.librarian;

import com.github.creme332.model.Publisher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PublisherForm extends JPanel {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField countryField;
    private JButton saveButton;
    private JButton backButton;

    public PublisherForm() {
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
        formPanel.add(new JLabel("Name"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        emailField = new JTextField(32);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Country"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        countryField = new JTextField(32);
        formPanel.add(countryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Span across two columns
        saveButton = new JButton("Submit");
        formPanel.add(saveButton, gbc);
        gbc.gridwidth = 1; // Reset to default
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 10, 0, 0));
        backButton = new JButton("Back");
        headerPanel.add(backButton, BorderLayout.WEST);
        JLabel heading = new JLabel("Create new publisher", javax.swing.SwingConstants.CENTER);
        heading.putClientProperty("FlatLaf.style", "font: $h1.font");
        headerPanel.add(heading, BorderLayout.CENTER);
        return headerPanel;
    }

    public void clearForm() {
        nameField.setText("");
        emailField.setText("");
        countryField.setText("");

        // reset outlines
        nameField.putClientProperty("JComponent.outline", "");
        emailField.putClientProperty("JComponent.outline", "");
        countryField.putClientProperty("JComponent.outline", "");
    }

    public void highlightNameField(boolean isValid) {
        nameField.putClientProperty("JComponent.outline", isValid ? Color.green : Color.red);
    }

    public void highlightEmailField(boolean isValid) {
        emailField.putClientProperty("JComponent.outline", isValid ? Color.green : Color.red);
    }

    public void highlightCountryField(boolean isValid) {
        countryField.putClientProperty("JComponent.outline", isValid ? Color.green : Color.red);
    }

    public void handleFormSubmission(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public Publisher getPublisher() {
        return new Publisher(nameField.getText().trim(), emailField.getText().trim(), countryField.getText().trim());
    }

    public JButton getBackButton() {
        return backButton;
    }
}
