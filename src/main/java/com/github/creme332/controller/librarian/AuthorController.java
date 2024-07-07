package com.github.creme332.controller.librarian;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Author;
import com.github.creme332.view.librarian.AuthorForm;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AuthorController {
    private AuthorForm authorForm;
    AppState app;

    public AuthorController(AppState app, AuthorForm authorForm) {
        this.authorForm = authorForm;
        this.app = app;

        this.authorForm.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAuthor();
            }
        });

        this.authorForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // back button functionality
            }
        });
    }

    private void saveAuthor() {
        String firstName = authorForm.getFirstNameField().getText();
        String lastName = authorForm.getLastNameField().getText();
        String email = authorForm.getEmailField().getText();

        Author author = new Author(0, lastName, firstName, email);
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

