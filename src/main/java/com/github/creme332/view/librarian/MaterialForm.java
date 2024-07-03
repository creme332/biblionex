package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MaterialForm extends JPanel {
    private JPanel formPanel;
    private JComboBox<String> materialTypeDropdown;
    private JButton submitButton;
    private JButton backButton;

    // Common form components
    private JComboBox<String> publisherComboBox;
    private JTextField titleField;
    private JTextField genreField;
    private JTextField descriptionField;
    private JSpinner ageSpinner;
    private JTextField imageUrlField;

    // Book specific components
    private JSpinner pageCountSpinner;
    private JTextField isbnField;
    private JComboBox<String> authorComboBox;

    // Journal specific components
    private JTextField issnField;
    private JTextField startDateField;
    private JTextField websiteField;
    private JComboBox<String> frequencyComboBox;

    // Video specific components
    private JTextField languageField;
    private JSpinner ratingSpinner;
    private JTextField durationField;
    private JTextField formatField;

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
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));

        backButton = new JButton("Back");
        headerPanel.add(backButton, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        JLabel formTitle = new JLabel("New Material Form: ");
        centerPanel.add(formTitle, BorderLayout.CENTER);

        materialTypeDropdown = new JComboBox<>();
        centerPanel.add(materialTypeDropdown);

        headerPanel.add(centerPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    private void addCommonComponents(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Choose publisher:"), gbc);
        gbc.gridx = 1;
        publisherComboBox = new JComboBox<>();
        panel.add(publisherComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(15);
        panel.add(titleField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 3;
        genreField = new JTextField(15);
        panel.add(genreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(15);
        panel.add(descriptionField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 3;
        ageSpinner = new JSpinner(new SpinnerNumberModel(100, 0, 200, 1));
        panel.add(ageSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Image URL:"), gbc);
        gbc.gridx = 1;
        imageUrlField = new JTextField(15);
        panel.add(imageUrlField, gbc);

        JSeparator separator = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        panel.add(separator, gbc);
        gbc.gridwidth = 1;
    }

    private JPanel createCommonPanel() {
        JPanel commonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addCommonComponents(commonPanel, gbc);

        return commonPanel;
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
        pageCountSpinner = new JSpinner(new SpinnerNumberModel(100, 0, 2000, 1));
        bookPanel.add(pageCountSpinner, gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        bookPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 3;
        isbnField = new JTextField(15);
        bookPanel.add(isbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        bookPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        authorComboBox = new JComboBox<>();
        bookPanel.add(authorComboBox, gbc);

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
        issnField = new JTextField(15);
        journalPanel.add(issnField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        journalPanel.add(new JLabel("Start Date:"), gbc);
        gbc.gridx = 3;
        startDateField = new JTextField(15);
        journalPanel.add(startDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        journalPanel.add(new JLabel("Website:"), gbc);
        gbc.gridx = 1;
        websiteField = new JTextField(15);
        journalPanel.add(websiteField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 6;
        journalPanel.add(new JLabel("Frequency:"), gbc);
        gbc.gridx = 3;
        frequencyComboBox = new JComboBox<>();
        journalPanel.add(frequencyComboBox, gbc);

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
        languageField = new JTextField(15);
        videoPanel.add(languageField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        videoPanel.add(new JLabel("Rating:"), gbc);
        gbc.gridx = 3;
        ratingSpinner = new JSpinner(new SpinnerNumberModel(100, 0, 100, 1));
        videoPanel.add(ratingSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        videoPanel.add(new JLabel("Duration:"), gbc);
        gbc.gridx = 1;
        durationField = new JTextField(15);
        videoPanel.add(durationField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 6;
        videoPanel.add(new JLabel("Format:"), gbc);
        gbc.gridx = 3;
        formatField = new JTextField(15);
        videoPanel.add(formatField, gbc);

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

    public JComboBox<String> getPublisherComboBox() {
        return publisherComboBox;
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JTextField getGenreField() {
        return genreField;
    }

    public JTextField getDescriptionField() {
        return descriptionField;
    }

    public JSpinner getAgeSpinner() {
        return ageSpinner;
    }

    public JTextField getImageUrlField() {
        return imageUrlField;
    }

    public JSpinner getPageCountSpinner() {
        return pageCountSpinner;
    }

    public JTextField getIsbnField() {
        return isbnField;
    }

    public JComboBox<String> getAuthorComboBox() {
        return authorComboBox;
    }

    public JTextField getIssnField() {
        return issnField;
    }

    public JTextField getStartDateField() {
        return startDateField;
    }

    public JTextField getWebsiteField() {
        return websiteField;
    }

    public JComboBox<String> getFrequencyComboBox() {
        return frequencyComboBox;
    }

    public JTextField getLanguageField() {
        return languageField;
    }

    public JSpinner getRatingSpinner() {
        return ratingSpinner;
    }

    public JTextField getDurationField() {
        return durationField;
    }

    public JTextField getFormatField() {
        return formatField;
    }
}
