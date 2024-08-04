package com.github.creme332.controller.librarian;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Author;
import com.github.creme332.model.Publisher;
import com.github.creme332.view.librarian.AuthorForm;
import com.github.creme332.view.librarian.PublisherForm;
import javax.swing.*;

import java.sql.SQLException;

public class PublisherController {
     private PublisherForm publisherForm;
    AppState app;

    public PublisherController(AppState app, PublisherForm publisherForm) {
        this.publisherForm=publisherForm;
        this.app = app;

        this.publisherForm.handleFormSubmission(e -> {
            savePublisher();
        });

        this.publisherForm.getBackButton().addActionListener(e -> {
            app.setCurrentScreen(app.getPreviousScreen());
        });
    }

    private void savePublisher() {
        Publisher publisher = publisherForm.getPublisher();

        publisherForm.highlightNameField(publisher.getName().length() > 0);
        publisherForm.highlightEmailField(publisher.getEmail().length() > 0);
        publisherForm.highlightCountryField(publisher.getCountry().length() > 0);


        if (publisher.getName().length() == 0 || publisher.getCountry().length()==0
                || publisher.getEmail().length() == 0)
            return;

        try {
            Publisher.save(publisher);
            JOptionPane.showMessageDialog(publisherForm, "Publisher saved successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                    publisherForm.clearForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(publisherForm, "Failed to save publisher.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

