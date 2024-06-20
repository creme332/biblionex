package com.github.creme332.view.librarian;

import javax.swing.*;
import java.awt.*;

public class MaterialForm extends JPanel {
    private CardLayout cardLayout;
    private JPanel formPanel;
    private JComboBox<String> materialTypeDropdown;
    private JButton submitButton;

    public MaterialForm() {
        setLayout(new BorderLayout());

        // Create a top panel for the dropdown and submit button
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        materialTypeDropdown = new JComboBox<>(new String[]{"Book", "Journal", "Video"});
        materialTypeDropdown.addActionListener(e -> {
            CardLayout cl = (CardLayout) (formPanel.getLayout());
            cl.show(formPanel, (String) materialTypeDropdown.getSelectedItem());
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(materialTypeDropdown, gbc);

        submitButton = new JButton("Submit");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;  // Pushes the submit button to the right
        topPanel.add(submitButton, gbc);

        add(topPanel, BorderLayout.NORTH);

        formPanel = new JPanel(new CardLayout());
        formPanel.add(createBookPanel(), "Book");
        formPanel.add(createJournalPanel(), "Journal");
        formPanel.add(createVideoPanel(), "Video");

        add(formPanel, BorderLayout.CENTER);
    }

    private JPanel createBookPanel() {
        JPanel bookPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        bookPanel.add(new JLabel("Choose publisher:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JComboBox<>(new String[]{"ABC"}), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        bookPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        bookPanel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        bookPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        bookPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JSpinner(new SpinnerNumberModel(100, 0, 200, 1)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        bookPanel.add(new JLabel("Image URL:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        bookPanel.add(new JLabel("Page count:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JSpinner(new SpinnerNumberModel(100, 0, 2000, 1)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        bookPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        bookPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        bookPanel.add(new JComboBox<>(new String[]{"James"}), gbc);

        return bookPanel;
    }

    private JPanel createJournalPanel() {
        JPanel journalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        journalPanel.add(new JLabel("Choose publisher:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JComboBox<>(new String[]{"ABC"}), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        journalPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        journalPanel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        journalPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        journalPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JSpinner(new SpinnerNumberModel(100, 0, 200, 1)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        journalPanel.add(new JLabel("Image URL:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        journalPanel.add(new JLabel("ISSN:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        journalPanel.add(new JLabel("Start Date:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        journalPanel.add(new JLabel("Website:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        journalPanel.add(new JLabel("Frequency:"), gbc);
        gbc.gridx = 1;
        journalPanel.add(new JComboBox<>(new String[]{"Weekly", "Monthly"}), gbc);

        return journalPanel;
    }

    private JPanel createVideoPanel() {
        JPanel videoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        videoPanel.add(new JLabel("Choose publisher:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JComboBox<>(new String[]{"ABC"}), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        videoPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        videoPanel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        videoPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        videoPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JSpinner(new SpinnerNumberModel(100, 0, 200, 1)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        videoPanel.add(new JLabel("Image URL:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        videoPanel.add(new JLabel("Language:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        videoPanel.add(new JLabel("Rating:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JSpinner(new SpinnerNumberModel(100, 0, 100, 1)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        videoPanel.add(new JLabel("Duration:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        videoPanel.add(new JLabel("Format:"), gbc);
        gbc.gridx = 1;
        videoPanel.add(new JTextField(15), gbc);

        return videoPanel;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JComboBox<String> getMaterialTypeDropdown() {
        return materialTypeDropdown;
    }
}
