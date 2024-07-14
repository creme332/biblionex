package com.github.creme332.controller.librarian;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Author;
import com.github.creme332.view.librarian.AuthorForm;
import javax.swing.*;

import java.sql.SQLException;

public class AuthorController {
    private AuthorForm authorForm;
    AppState app;

    public AuthorController(AppState app, AuthorForm authorForm) {
        this.authorForm = authorForm;
        this.app = app;

        this.authorForm.handleFormSubmission(e -> {
            saveAuthor();
        });

        this.authorForm.getBackButton().addActionListener(e -> {
            app.setCurrentScreen(app.getPreviousScreen());
        });
    }

    private void saveAuthor() {
        Author author = authorForm.getAuthor();

        authorForm.highlightFirstNameField(author.getFirstName().length() > 0);
        authorForm.highlightLastNameField(author.getLastName().length() > 0);
        authorForm.highlightEmailField(author.getEmail().length() > 0);

        if (author.getFirstName().length() == 0 || author.getLastName().length() == 0
                || author.getEmail().length() == 0)
            return;

        try {
            Author.save(author);
            JOptionPane.showMessageDialog(authorForm, "Author saved successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            authorForm.clearForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(authorForm, "Failed to save author.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
