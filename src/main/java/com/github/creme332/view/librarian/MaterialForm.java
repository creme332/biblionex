package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MaterialForm extends JPanel {
    private JPanel formPanel;
    private JComboBox<String> materialTypeDropdown;
    private JButton submitButton;
    private JButton backButton;

    public MaterialForm() {
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);

        formPanel = new JPanel(new CardLayout());
        formPanel.add(createBookPanel(), "Book");
        formPanel.add(createJournalPanel(), "Journal");
        formPanel.add(createVideoPanel(), "Video");

        JScrollPane scrollPane = new JScrollPane(formPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        // Create a top panel for the back button, title, and dropdown
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));

        backButton = new JButton("Back");
        headerPanel.add(backButton, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        JLabel formTitle = new JLabel("New Material Form: ");
        centerPanel.add(formTitle, BorderLayout.CENTER);

        materialTypeDropdown = new JComboBox<>(new String[] { "Book", "Journal", "Video" });
        centerPanel.add(materialTypeDropdown);

        headerPanel.add(centerPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createCommonPanel() {
        JPanel commonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addCommonComponents(commonPanel, gbc);

        return commonPanel;
    }

    private void addCommonComponents(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Choose publisher:"), gbc);
        gbc.gridx = 1;
        panel.add(new JComboBox<>(new String[] { "ABC" }), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        panel.add(new JTextField(15), gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 3;
        panel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        panel.add(new JTextField(15), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 3;
        panel.add(new JSpinner(new SpinnerNumberModel(100, 0, 200, 1)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Image URL:"), gbc);
        gbc.gridx = 1;
        panel.add(new JTextField(15), gbc);

        // Add a separator
        JSeparator separator = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        panel.add(separator, gbc);
        gbc.gridwidth = 1;
    }

    private JPanel createBookPanel() {
        JPanel bookPanel = createCommonPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 5;
        bookPanel.add(new JLabel("Page count:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JSpinner(new SpinnerNumberModel(100, 0, 2000, 1)), gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        bookPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 3;
        bookPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        bookPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JComboBox<>(new String[] { "James" }), gbc);

        // Add the submit button
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = new JButton("Submit");
        bookPanel.add(submitButton, gbc);

        return bookPanel;
    }

    private JPanel createJournalPanel() {
        JPanel journalPanel = createCommonPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 5;
        journalPanel.add(new JLabel("ISSN:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JTextField(15), gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        journalPanel.add(new JLabel("Start Date:"), gbc);
        gbc.gridx = 3;
        journalPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        journalPanel.add(new JLabel("Website:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JTextField(15), gbc);
        gbc.gridx = 2;
        gbc.gridy = 6;
        journalPanel.add(new JLabel("Frequency:"), gbc);
        gbc.gridx = 3;
        journalPanel.add(new JComboBox<>(new String[] { "Weekly", "Monthly" }), gbc);

        // Add the submit button
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = new JButton("Submit");
        journalPanel.add(submitButton, gbc);

        return journalPanel;
    }

    private JPanel createVideoPanel() {
        JPanel videoPanel = createCommonPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 5;
        videoPanel.add(new JLabel("Language:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JTextField(15), gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        videoPanel.add(new JLabel("Rating:"), gbc);
        gbc.gridx = 3;
        videoPanel.add(new JSpinner(new SpinnerNumberModel(100, 0, 100, 1)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        videoPanel.add(new JLabel("Duration:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JTextField(15), gbc);
        gbc.gridx = 2;
        gbc.gridy = 6;
        videoPanel.add(new JLabel("Format:"), gbc);
        gbc.gridx = 3;
        videoPanel.add(new JTextField(15), gbc);

        // Add the submit button
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = new JButton("Submit");
        videoPanel.add(submitButton, gbc);

        return videoPanel;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JComboBox<String> getMaterialTypeDropdown() {
        return materialTypeDropdown;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JPanel getFormPanel() {
        return formPanel;
    }
}
