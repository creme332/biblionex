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
                    case "BOOK":
                        handleBookSubmission();
                        break;
                    case "JOURNAL":
                        handleJournalSubmission();
                        break;
                    case "VIDEO":
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
            loadDropdownData(selectedItem);
        });

        loadMaterialTypes();
    }

    private void loadMaterialTypes() {
        try {
            materialForm.getMaterialTypeDropdown().removeAllItems();
            for (MaterialType type : MaterialType.values()) {
                materialForm.getMaterialTypeDropdown().addItem(type.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load material types!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDropdownData(String materialType) {
        try {
            // Clear dropdowns to avoid overlapping data
            materialForm.getPublisherComboBox().removeAllItems();
            materialForm.getAuthorComboBox().removeAllItems();
            materialForm.getFrequencyComboBox().removeAllItems();

            // Load common data for all material types
            List<Publisher> publishers = Publisher.findAll();
            for (Publisher publisher : publishers) {
                materialForm.getPublisherComboBox().addItem(publisher.getName());
            }

            if (materialType.equals("BOOK")) {
                List<Author> authors = Author.findAll();
                for (Author author : authors) {
                    materialForm.getAuthorComboBox().addItem(author.getFirstName() + " " + author.getLastName());
                }
            } else if (materialType.equals("JOURNAL")) {
                for (JournalFrequency frequency : JournalFrequency.values()) {
                    materialForm.getFrequencyComboBox().addItem(frequency.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load dropdown data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        materialForm.getTitleField().setText("");
        materialForm.getGenreField().setText("");
        materialForm.getDescriptionField().setText("");
        materialForm.getAgeSpinner().setValue(0);
        materialForm.getImageUrlField().setText("");
        materialForm.getPageCountSpinner().setValue(0);
        materialForm.getIsbnField().setText("");
        materialForm.getIssnField().setText("");
        materialForm.getWebsiteField().setText("");
        materialForm.getStartDateField().setText("");
        materialForm.getLanguageField().setText("");
        materialForm.getRatingSpinner().setValue(0);
        materialForm.getDurationField().setText("");
        materialForm.getFormatField().setText("");
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
            clearForm();
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
            clearForm();
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
            clearForm();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to submit video!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
