package com.github.creme332.view.librarian;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.github.creme332.model.Publisher;

import java.awt.*;
import java.awt.event.ActionListener;
public class PublisherForm {
    private JTextField NameField;
    private JTextField EmailField;
    private JTextField CountryField;
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
        NameField = new JTextField(15);
        formPanel.add(NameField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email"), gbc)


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        EmailField = new JTextField(32);
        formPanel.add(EmailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Country"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Span across two columns
        EmailField = new JTextField(32);
        formPanel.add(CountryField, gbc);

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
        JLabel heading = new JLabel("Create new author", javax.swing.SwingConstants.CENTER);
        heading.putClientProperty("FlatLaf.style", "font: $h1.font");
        headerPanel.add(heading, BorderLayout.CENTER);
        return headerPanel;
    }

    public void clearForm() {
       NameField.setText("");
       EmailField.setText("");
       CountryField.setText("");

        // reset outlines
       NameField.putClientProperty("JComponent.outline", "");
       EmailField.putClientProperty("JComponent.outline", "");
       CountryField.putClientProperty("JComponent.outline", "");
    }

    /**
     * Shows colored outline around field if it is valid or not.
     * 
     * @param isValid Whether field value is valid
     */
    public void highlightNameField(boolean isValid) {
        NameField.putClientProperty("JComponent.outline", isValid ? Color.green : Color.red);
    }

    /**
     * Shows colored outline around field if it is valid or not.
     * 
     * @param isValid Whether field value is valid
     */
    public void highlightEmailField(boolean isValid) {
        EmailField.putClientProperty("JComponent.outline", isValid ? Color.green : Color.red);
    }

    /**
     * Shows colored outline around field if it is valid or not.
     * 
     * @param isValid Whether field value is valid
     */
    public void highlightCountryField(boolean isValid) {
        CountryField.putClientProperty("JComponent.outline", isValid ? Color.green : Color.red);
    }

    public void handleFormSubmission(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    /**
     * 
     * @return Author data input in form
     */
    public Publisher getPublisher() {
        return new Publisher(NameField.getText().trim(), EmailField.getText().trim(), CountryField.getText().trim());
    }

    public JButton getBackButton() {
        return backButton;
    }
}

    
