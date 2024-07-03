package com.github.creme332.controller.librarian;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.JournalFrequency;
import com.github.creme332.model.Video;
import com.github.creme332.view.librarian.MaterialForm;
import com.github.creme332.controller.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
        // Retrieve data from the database and populate dropdowns
        // This is just an example, you need to implement actual data fetching
        materialForm.getMaterialTypeDropdown().addItem("Book");
        materialForm.getMaterialTypeDropdown().addItem("Journal");
        materialForm.getMaterialTypeDropdown().addItem("Video");

        materialForm.getPublisherComboBox().addItem("Publisher 1");
        materialForm.getPublisherComboBox().addItem("Publisher 2");

        materialForm.getAuthorComboBox().addItem("Author 1");
        materialForm.getAuthorComboBox().addItem("Author 2");

        materialForm.getFrequencyComboBox().addItem("Monthly");
        materialForm.getFrequencyComboBox().addItem("Weekly");
    }

    private void handleBookSubmission() {
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
                getPublisherIdByName(publisher), // Replace with actual method to get publisher ID
                description,
                imageUrl,
                age,
                title,
                pageCount,
                isbn
        );

        try {
            Book.save(book);
            JOptionPane.showMessageDialog(null, "Book submited successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to submit book!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleJournalSubmission() {
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
        Date startDate = parseDate(materialForm.getStartDateField().getText());

        // Create a new Journal object
        Journal journal = new Journal(
                getPublisherIdByName(publisher), 
                description,
                imageUrl,
                age,
                title,
                issn,
                website,
                JournalFrequency.valueOf(frequency),
                startDate
        );

        try {
            Journal.save(journal);
            JOptionPane.showMessageDialog(null, "Journal submited successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to submit journal!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleVideoSubmission() {
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
                getPublisherIdByName(publisher), 
                description,
                imageUrl,
                age,
                title,
                language,
                duration,
                rating,
                format
        );

        try {
            Video.save(video);
            JOptionPane.showMessageDialog(null, "Video submited successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to submit video!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
