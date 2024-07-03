package com.github.creme332.controller.librarian;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Author;
import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.Publisher;
import com.github.creme332.model.JournalFrequency;
import com.github.creme332.model.MaterialType;
import com.github.creme332.model.Video;
import com.github.creme332.view.librarian.MaterialForm;
import com.github.creme332.controller.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class MaterialFormController {
    MaterialForm materialForm;
    AppState app;

    public MaterialFormController(AppState app, MaterialForm materialForm) {
        this.materialForm = materialForm;
        this.app = app;
        initController();
    }

    private void initController() {
        materialForm.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) materialForm.getMaterialTypeDropdown().getSelectedItem();
                switch (selectedType) {
                    case "Book":
                        handleBookSubmission();
                        break;
                    case "Journal":
                        handleJournalSubmission();
                        break;
                    case "Video":
                        handleVideoSubmission();
                        break;
                }
            }
        });

        materialForm.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));

        materialForm.getMaterialTypeDropdown().addActionListener(e -> {
            CardLayout cl = (CardLayout) (materialForm.getFormPanel().getLayout());
            String selectedItem = (String) materialForm.getMaterialTypeDropdown().getSelectedItem();
            cl.show(materialForm.getFormPanel(), selectedItem);
        });

        loadDropdownData();
    }

    private void loadDropdownData() {
        try {
            // Load material types
            for (MaterialType type : MaterialType.values()) {
                materialForm.getMaterialTypeDropdown().addItem(type.toString());
            }

            // Load publishers
            List<Publisher> publishers = Publisher.findAll();
            for (Publisher publisher : publishers) {
                materialForm.getPublisherComboBox().addItem(publisher.getName());
            }

            // Load authors
            List<Author> authors = Author.findAll();
            for (Author author : authors) {
                materialForm.getAuthorComboBox().addItem(author.getFirstName() + " " + author.getLastName());
            }

            // Load journal frequencies
            for (JournalFrequency frequency : JournalFrequency.values()) {
                materialForm.getFrequencyComboBox().addItem(frequency.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load dropdown data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBookSubmission() {
        try {
            // Retrieve data from the form
            String publisher = (String) materialForm.getPublisherComboBox().getSelectedItem();
            String title = materialForm.getTitleField().getText();
            String genre = materialForm.getGenreField().getText();
            String description = materialForm.getDescriptionField().getText();
            int age = (int) materialForm.getAgeSpinner().getValue();
            String imageUrl = materialForm.getImageUrlField().getText();
            int pageCount = (int) materialForm.getPageCountSpinner().getValue();
            String isbn = materialForm.getIsbnField().getText();
            String author = (String) materialForm.getAuthorComboBox().getSelectedItem();

            // Create a new Book object
            Book book = new Book(
                    Publisher.getPublisherIdByName(publisher),
                    description,
                    imageUrl,
                    age,
                    title,
                    pageCount,
                    isbn);

            Book.save(book);
            JOptionPane.showMessageDialog(null, "Book submitted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to submit book!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleJournalSubmission() {
        try {
            // Retrieve data from the form
            String publisher = (String) materialForm.getPublisherComboBox().getSelectedItem();
            String title = materialForm.getTitleField().getText();
            String genre = materialForm.getGenreField().getText();
            String description = materialForm.getDescriptionField().getText();
            int age = (int) materialForm.getAgeSpinner().getValue();
            String imageUrl = materialForm.getImageUrlField().getText();
            String issn = materialForm.getIssnField().getText();
            String website = materialForm.getWebsiteField().getText();
            String frequency = (String) materialForm.getFrequencyComboBox().getSelectedItem();
            Date startDate = Date.valueOf(materialForm.getStartDateField().getText());

            // Create a new Journal object
            Journal journal = new Journal(
                    Publisher.getPublisherIdByName(publisher),
                    description,
                    imageUrl,
                    age,
                    title,
                    issn,
                    website,
                    JournalFrequency.valueOf(frequency.toUpperCase()),
                    startDate);

            Journal.save(journal);
            JOptionPane.showMessageDialog(null, "Journal submitted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to submit journal!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleVideoSubmission() {
        try {
            // Retrieve data from the form
            String publisher = (String) materialForm.getPublisherComboBox().getSelectedItem();
            String title = materialForm.getTitleField().getText();
            String genre = materialForm.getGenreField().getText();
            String description = materialForm.getDescriptionField().getText();
            int age = (int) materialForm.getAgeSpinner().getValue();
            String imageUrl = materialForm.getImageUrlField().getText();
            String language = materialForm.getLanguageField().getText();
            int rating = (int) materialForm.getRatingSpinner().getValue();
            int duration = Integer.parseInt(materialForm.getDurationField().getText());
            String format = materialForm.getFormatField().getText();

            // Create a new Video object
            Video video = new Video(
                    Publisher.getPublisherIdByName(publisher),
                    description,
                    imageUrl,
                    age,
                    title,
                    language,
                    duration,
                    rating,
                    format);

            Video.save(video);
            JOptionPane.showMessageDialog(null, "Video submitted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to submit video!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
