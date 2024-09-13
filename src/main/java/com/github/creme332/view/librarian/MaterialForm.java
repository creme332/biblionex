package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;
import com.github.creme332.model.Author;
import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.JournalFrequency;
import com.github.creme332.model.MaterialType;
import com.github.creme332.model.Publisher;
import com.github.creme332.model.Video;

import java.util.Date;
import java.util.List;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MaterialForm extends JFrame {
    /**
     * Bottom section of form which contains fields specific to a material type.
     */
    JPanel switchPanel;

    private JComboBox<MaterialTypeComboBox> materialTypeDropdown;
    private JButton submitButton = new JButton("Submit");
    private JButton backButton;
    private JButton expandButton;
    private JButton createPublisherButton;
    private JButton createAuthorButton;

    // Common form components
    private JComboBox<PublisherComboBoxItem> publisherComboBox;
    private JTextField titleField;
    private JTextField genreField;
    private JTextField descriptionField;
    private JSpinner ageSpinner;
    private JTextField imageUrlField;

    // Book specific components
    private JSpinner pageCountSpinner;
    private JTextField isbnField;
    private JList<Author> authorList;

    // Journal specific components
    private JTextField issnField;
    private JTextField startDateField;
    private JTextField websiteField;
    private JComboBox<JournalFrequencyComboBoxItem> journalFrequencyComboBox;

    // Video specific components
    private JTextField languageField;
    private JSpinner ratingSpinner;
    private JTextField durationField;
    private JTextField formatField;

    public MaterialForm() {
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JPanel commonPanel = createCommonPanel();
        gbc.gridy = 0;
        formPanel.add(commonPanel, gbc);

        switchPanel = new JPanel(new CardLayout());
        switchPanel.setBackground(Color.red);

        gbc.gridy = 1;
        formPanel.add(switchPanel, gbc);

        switchPanel.add(createBookPanel(), MaterialType.BOOK.name());
        switchPanel.add(createJournalPanel(), MaterialType.JOURNAL.name());
        switchPanel.add(createVideoPanel(), MaterialType.VIDEO.name());

        // add submit button
        gbc.gridy = 2;
        formPanel.add(submitButton, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        add(scrollPane, BorderLayout.CENTER);

        // show form that corresponds to the initial selected material
        showForm(((MaterialTypeComboBox) materialTypeDropdown.getSelectedItem()).getMaterialType());

        // implement form switching logic based on selected material type
        materialTypeDropdown.addActionListener(e -> {
            MaterialType selectedMaterialType = ((MaterialTypeComboBox) materialTypeDropdown.getSelectedItem())
                    .getMaterialType();
            showForm(selectedMaterialType);
        });
    }

    private class PublisherComboBoxItem {
        private Publisher publisher;

        public PublisherComboBoxItem(Publisher publisher) {
            this.publisher = publisher;
        }

        public Publisher getPublisher() {
            return publisher;
        }

        @Override
        public String toString() {
            return publisher.getName(); // This will be displayed in the JComboBox
        }
    }

    private class MaterialTypeComboBox {
        private MaterialType data;

        public MaterialTypeComboBox(MaterialType publisher) {
            this.data = publisher;
        }

        public MaterialType getMaterialType() {
            return data;
        }

        @Override
        public String toString() {
            String name = data.name();
            return name.substring(0, 1).toUpperCase() +
                    name.substring(1).toLowerCase();// This will be displayed in the
                                                    // JComboBox
        }
    }

    private class JournalFrequencyComboBoxItem {
        private JournalFrequency data;

        public JournalFrequencyComboBoxItem(JournalFrequency frequency) {
            this.data = frequency;
        }

        public JournalFrequency getJournalFrequency() {
            return data;
        }

        @Override
        public String toString() {
            String name = data.name();
            return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }
    }

    /**
     * 
     * @return Header panel with back button and material selection dropdown
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));

        backButton = new JButton("Back");
        headerPanel.add(backButton, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        JLabel formTitle = new JLabel("Material Type ");
        centerPanel.add(formTitle, BorderLayout.CENTER);

        materialTypeDropdown = new JComboBox<>();
        for (MaterialType type : MaterialType.values()) {
            materialTypeDropdown.addItem(new MaterialTypeComboBox(type));
        }
        centerPanel.add(materialTypeDropdown);

        headerPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        createPublisherButton = new JButton("Create Publisher");
        createAuthorButton = new JButton("Create Author");
        rightPanel.add(createPublisherButton);
        rightPanel.add(createAuthorButton);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    /**
     * 
     * @return Section of formPanel that is common to all forms irrespective of
     *         material
     *         type.
     */
    private JPanel createCommonPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Publisher"), gbc);
        gbc.gridx = 1;
        publisherComboBox = new JComboBox<>();
        panel.add(publisherComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Title"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(15);
        panel.add(titleField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(new JLabel("Genre"), gbc);
        gbc.gridx = 3;
        genreField = new JTextField(15);
        panel.add(genreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Description"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(15);
        panel.add(descriptionField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(new JLabel("Age"), gbc);
        gbc.gridx = 3;
        ageSpinner = new JSpinner(new SpinnerNumberModel(18, 0, 200, 1));
        panel.add(ageSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Image URL"), gbc);
        gbc.gridx = 1;
        imageUrlField = new JTextField(15);
        panel.add(imageUrlField, gbc);

        JSeparator separator = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        panel.add(separator, gbc);
        gbc.gridwidth = 1;

        return panel;
    }

    private JPanel createBookPanel() {
        JPanel bookPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 5;
        bookPanel.add(new JLabel("Page count"), gbc);
        gbc.gridx = 1;
        pageCountSpinner = new JSpinner(new SpinnerNumberModel(100, 0, 2000, 10));
        bookPanel.add(pageCountSpinner, gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        bookPanel.add(new JLabel("ISBN"), gbc);
        gbc.gridx = 3;
        isbnField = new JTextField(15);
        bookPanel.add(isbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        bookPanel.add(new JLabel("Authors"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;

        DefaultListModel<Author> authorListModel = new DefaultListModel<>();
        authorList = new JList<>(authorListModel);
        authorList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        authorList.setCellRenderer(new AuthorListCellRenderer());
        JScrollPane authorScrollPane = new JScrollPane(authorList);
        authorScrollPane.setPreferredSize(new Dimension(150, 100));

        JPanel authorsPanel = new JPanel(new BorderLayout());
        authorsPanel.add(authorScrollPane, BorderLayout.CENTER);

        expandButton = new JButton("Expand");
        authorsPanel.add(expandButton, BorderLayout.SOUTH);

        bookPanel.add(authorsPanel, gbc);

        return bookPanel;
    }

    private static class AuthorListCellRenderer extends JPanel implements ListCellRenderer<Author> {
        private JTextArea textArea;

        public AuthorListCellRenderer() {
            setLayout(new BorderLayout());
            textArea = new JTextArea();
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setOpaque(true);
            textArea.setBorder(new EmptyBorder(5, 5, 5, 5));
            add(textArea, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Author> list, Author value, int index,
                boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                textArea.setText(value.getFirstName() + " " + value.getLastName());
                textArea.setToolTipText(value.getFirstName() + " " + value.getLastName());
            } else {
                textArea.setText("");
                textArea.setToolTipText(null);
            }

            if (isSelected) {
                textArea.setBackground(list.getSelectionBackground());
                textArea.setForeground(list.getSelectionForeground());
            } else {
                textArea.setBackground(list.getBackground());
                textArea.setForeground(list.getForeground());
            }

            return this;
        }
    }

    /**
     * 
     * @return Section of formPanel that is specific to journals.
     */
    private JPanel createJournalPanel() {
        JPanel journalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 5;
        journalPanel.add(new JLabel("ISSN"), gbc);
        gbc.gridx = 1;
        issnField = new JTextField(15);
        journalPanel.add(issnField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        journalPanel.add(new JLabel("Start Date"), gbc);
        gbc.gridx = 3;
        startDateField = new JTextField(15);
        startDateField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "DD-MM-YYYY");

        journalPanel.add(startDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        journalPanel.add(new JLabel("Website"), gbc);
        gbc.gridx = 1;
        websiteField = new JTextField(15);
        journalPanel.add(websiteField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 6;
        journalPanel.add(new JLabel("Frequency"), gbc);
        gbc.gridx = 3;
        journalFrequencyComboBox = new JComboBox<>();

        for (JournalFrequency frequency : JournalFrequency.values()) {
            journalFrequencyComboBox.addItem(new JournalFrequencyComboBoxItem(frequency));
        }
        journalPanel.add(journalFrequencyComboBox, gbc);

        return journalPanel;
    }

    /**
     * 
     * @return Section of formPanel that is specific to videos.
     */
    private JPanel createVideoPanel() {
        JPanel videoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 5;
        videoPanel.add(new JLabel("Language"), gbc);

        gbc.gridx = 1;
        languageField = new JTextField(15);
        videoPanel.add(languageField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        videoPanel.add(new JLabel("Rating"), gbc);

        gbc.gridx = 3;
        ratingSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
        videoPanel.add(ratingSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        videoPanel.add(new JLabel("Duration"), gbc);

        gbc.gridx = 1;
        durationField = new JTextField(15);
        videoPanel.add(durationField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        videoPanel.add(new JLabel("Format"), gbc);

        gbc.gridx = 3;
        formatField = new JTextField(15);
        formatField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "DD-MM-YYYY");
        videoPanel.add(formatField, gbc);

        return videoPanel;
    }

    public void clearForm() {
        // Common form components
        publisherComboBox.setSelectedIndex(-1); // Deselect any selected item
        titleField.setText("");
        genreField.setText("");
        descriptionField.setText("");
        ageSpinner.setValue(0); // Assuming ageSpinner is for age and starts from 0
        imageUrlField.setText("");

        // Book specific components
        pageCountSpinner.setValue(0); // Assuming pageCountSpinner starts from 0
        isbnField.setText("");
        authorList.clearSelection(); // Clear selection in JList

        // Journal specific components
        issnField.setText("");
        startDateField.setText("");
        websiteField.setText("");
        journalFrequencyComboBox.setSelectedIndex(-1); // Deselect any selected item

        // Video specific components
        languageField.setText("");
        ratingSpinner.setValue(0); // Assuming ratingSpinner starts from 0
        durationField.setText("");
        formatField.setText("");
    }

    public void showForm(MaterialType type) {
        CardLayout cl = (CardLayout) (switchPanel.getLayout());
        cl.show(switchPanel, type.name());
    }

    /**
     * 
     * @return Data from journal form
     */
    public Journal getJournalData() {
        Publisher publisher = ((PublisherComboBoxItem) publisherComboBox.getSelectedItem()).getPublisher();
        String title = titleField.getText();
        String description = descriptionField.getText();
        int age = (int) ageSpinner.getValue();
        String imageUrl = imageUrlField.getText();
        String issn = issnField.getText();
        String website = websiteField.getText();
        JournalFrequency frequency = ((JournalFrequencyComboBoxItem) journalFrequencyComboBox.getSelectedItem())
                .getJournalFrequency();

        // convert entered date to date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = null;
        try {
            startDate = dateFormat.parse(startDateField.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Journal(
                publisher.getPublisherId(),
                description,
                imageUrl,
                age,
                title,
                issn,
                website,
                frequency,
                startDate);

    }

    /**
     * 
     * @return Data from video form
     */
    public Video getVideoData() {
        Publisher publisher = ((PublisherComboBoxItem) publisherComboBox.getSelectedItem()).getPublisher();
        String title = titleField.getText();
        String description = descriptionField.getText();
        int age = (int) ageSpinner.getValue();
        String imageUrl = imageUrlField.getText();

        String language = languageField.getText().trim();
        int duration = Integer.parseInt(durationField.getText());
        int rating = (int) ratingSpinner.getValue();
        String format = formatField.getText().trim();

        return new Video(
                publisher.getPublisherId(),
                description,
                imageUrl,
                age,
                title,
                language,
                duration,
                rating,
                format);
    }

    /**
     * 
     * @return Data from book form
     */
    public Book getBookData() {
        Publisher publisher = ((PublisherComboBoxItem) publisherComboBox.getSelectedItem()).getPublisher();
        String title = titleField.getText();
        String description = descriptionField.getText();
        int age = (int) ageSpinner.getValue();
        String imageUrl = imageUrlField.getText();

        int pageCount = (int) pageCountSpinner.getValue();
        String isbn = isbnField.getText().trim();

        Book newBook = new Book(
                publisher.getPublisherId(),
                description,
                imageUrl,
                age,
                title,
                pageCount,
                isbn);

        // Add all selected authors from the JList
        Author[] selectedAuthors = authorList.getSelectedValuesList().toArray(new Author[0]);
        for (Author author : selectedAuthors) {
            newBook.addAuthor(author);
        }

        return newBook;
    }

    public void loadAuthors(List<Author> authors) {
        authorList.setListData(authors.toArray(new Author[0]));
    }

    /**
     * 
     * @return Material type of active form.
     */
    public MaterialType getMaterialType() {
        return ((MaterialTypeComboBox) materialTypeDropdown.getSelectedItem())
                .getMaterialType();
    }

    public void loadPublishers(List<Publisher> publishers) {
        for (Publisher publisher : publishers) {
            publisherComboBox.addItem(new PublisherComboBoxItem(publisher));
        }
    }

    public void handleGoBack(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void handleAuthor(ActionListener listener) {
        createAuthorButton.addActionListener(listener);
    }

    public void handlePublisher(ActionListener listener) {
        createPublisherButton.addActionListener(listener);
    }

    public void handleFormSubmission(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public void handleExpand(ActionListener listener) {
        expandButton.addActionListener(listener);
    }

    public void setExpandButtonText(String text) {
        expandButton.setText(text);
    }

    public JScrollPane getAuthorScrollPane() {
        // Get the currently visible panel from the switchPanel
        Component[] components = switchPanel.getComponents();
        JPanel bookPanel = null;

        for (Component component : components) {
            if (component.isVisible() && component instanceof JPanel) {
                bookPanel = (JPanel) component;
                break;
            }
        }

        if (bookPanel == null) {
            throw new IllegalStateException("Book panel not found");
        }

        // Find the authorsPanel within the bookPanel
        for (Component component : bookPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component childComponent : panel.getComponents()) {
                    if (childComponent instanceof JScrollPane) {
                        return (JScrollPane) childComponent;
                    }
                }
            }
        }

        throw new IllegalStateException("Author scroll pane not found");
    }

    public JList<Author> getAuthorList() {
        return authorList;
    }
}
