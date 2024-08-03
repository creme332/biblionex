package com.github.creme332.controller.librarian;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Author;
import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.MaterialType;
import com.github.creme332.model.Publisher;
import com.github.creme332.model.Video;
import com.github.creme332.view.librarian.MaterialForm;
import com.github.creme332.controller.Screen;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.*;

public class MaterialFormController {
    MaterialForm materialForm;
    AppState app;

    public MaterialFormController(AppState app, MaterialForm materialForm) {
        this.materialForm = materialForm;
        this.app = app;

        materialForm.handleGoBack(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));
        // materialForm.handlePublisher(e -> app.setCurrentScreen(Screen.LIBRARIAN_PUBLISHER_SCREEN));
        materialForm.handleAuthor(e -> app.setCurrentScreen(Screen.LIBRARIAN_AUTHOR_SCREEN));


        materialForm.handleExpand(e -> {
            JScrollPane authorScrollPane = materialForm.getAuthorScrollPane();
            Dimension size = authorScrollPane.getPreferredSize();
            if (size.height == 100) {
                authorScrollPane.setPreferredSize(new Dimension(150, 200));
                materialForm.setExpandButtonText("Collapse");
            } else {
                authorScrollPane.setPreferredSize(new Dimension(150, 100));
                materialForm.setExpandButtonText("Expand");
            }
            materialForm.revalidate();
            materialForm.repaint();
        });

        materialForm.handleFormSubmission(e -> {
            MaterialType type = materialForm.getMaterialType();

            if (type == MaterialType.BOOK) {
                handleBookSubmission();
            }

            if (type == MaterialType.JOURNAL) {
                handleJournalSubmission();
            }

            if (type == MaterialType.VIDEO) {
                handleVideoSubmission();
            }

            materialForm.clearForm();
        });

        Thread th = new Thread() {
            @Override
            public void run() {
                loadDropdownData();
            }
        };
        th.start();
    }

    private void loadDropdownData() {
        try {
            materialForm.loadAuthors(Author.findAll());
            materialForm.loadPublishers(Publisher.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load dropdown data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBookSubmission() {
        try {
            Book.save(materialForm.getBookData());
            JOptionPane.showMessageDialog(null, "Book submitted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            materialForm.clearForm();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to submit book!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleJournalSubmission() {
        try {
            Journal.save(materialForm.getJournalData());
            JOptionPane.showMessageDialog(null, "Journal submitted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            materialForm.clearForm();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to submit journal!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleVideoSubmission() {
        try {
            Video.save(materialForm.getVideoData());
            JOptionPane.showMessageDialog(null, "Video submitted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            materialForm.clearForm();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to submit video!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
