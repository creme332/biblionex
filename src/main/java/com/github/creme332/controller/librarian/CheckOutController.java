package com.github.creme332.controller.librarian;

import com.github.creme332.model.*;
import com.github.creme332.view.librarian.CheckOutPage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.Date;

public class CheckOutController implements PropertyChangeListener {
    private AppState app;
    private CheckOutPage view;
    private Librarian librarian;

    public CheckOutController(AppState app, CheckOutPage view) {
        this.app = app;
        this.view = view;
        app.addPropertyChangeListener(this);

        view.getCheckOutButton().addActionListener(e -> checkOutLoan());
        view.getCheckOutButton().setEnabled(false); // Disable button initially

        view.getLoanTable().setEnabled(false); // Disable table editing

        view.getBarcodeField().setText("Item Barcode");
        view.getPatronIdField().setText("Patron ID");

        view.getBarcodeField().setForeground(Color.GRAY);
        view.getPatronIdField().setForeground(Color.GRAY);

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                toggleCheckOutButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                toggleCheckOutButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                toggleCheckOutButton();
            }

            private void toggleCheckOutButton() {
                boolean barcodeFilled = !view.getBarcodeField().getText().trim().equals("") && !view.getBarcodeField().getText().trim().equals("Item Barcode");
                boolean patronIdFilled = !view.getPatronIdField().getText().trim().equals("") && !view.getPatronIdField().getText().trim().equals("Patron ID");
                view.getCheckOutButton().setEnabled(barcodeFilled && patronIdFilled);
            }
        };

        view.getBarcodeField().getDocument().addDocumentListener(documentListener);
        view.getPatronIdField().getDocument().addDocumentListener(documentListener);

        view.getBarcodeField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle action when Enter is pressed in barcode field
                String barcodeText = view.getBarcodeField().getText().trim();
                if (barcodeText.equals("") || barcodeText.equals("Item Barcode")) {
                    JOptionPane.showMessageDialog(view, "Please enter a valid Item Barcode.", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Perform search or validation logic if needed
                    validateBarcode(barcodeText);
                }
            }
        });

        view.getPatronIdField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle action when Enter is pressed in patron ID field
                String patronIdText = view.getPatronIdField().getText().trim();
                if (patronIdText.equals("") || patronIdText.equals("Patron ID")) {
                    JOptionPane.showMessageDialog(view, "Please enter a valid Patron ID.", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Perform search or validation logic if needed
                    validatePatronId(patronIdText);
                }
            }
        });

        Thread th = new Thread() {
            @Override
            public void run() {
                // Add any initial setup logic here
            }
        };
        th.start();
    }

    private void validateBarcode(String barcodeText) {
        try {
            int barcode = Integer.parseInt(barcodeText);
            MaterialCopy item = MaterialCopy.findById(barcode);
            if (item == null) {
                JOptionPane.showMessageDialog(view, "Item with the given barcode does not exist.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid number for the barcode.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validatePatronId(String patronIdText) {
        try {
            int patronId = Integer.parseInt(patronIdText);
            Patron patron = Patron.findById(patronId);
            if (patron == null) {
                JOptionPane.showMessageDialog(view, "Patron with the given ID does not exist.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid number for the patron ID.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkOutLoan() {
        String barcodeText = view.getBarcodeField().getText().trim();
        String patronIdText = view.getPatronIdField().getText().trim();

        // Validate input fields
        if (barcodeText.isEmpty() || barcodeText.equals("Item Barcode")) {
            JOptionPane.showMessageDialog(view, "Please enter a valid Item Barcode.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (patronIdText.isEmpty() || patronIdText.equals("Patron ID")) {
            JOptionPane.showMessageDialog(view, "Please enter a valid Patron ID.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int barcode;
        int patronId;

        try {
            barcode = Integer.parseInt(barcodeText);
            patronId = Integer.parseInt(patronIdText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter valid numbers for Item Barcode and Patron ID.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perform check out logic
        try {
            Loan loan = new Loan(patronId, barcode, librarian.getUserId(), new Date());
            Loan.save(loan); // Save loan to database

            JOptionPane.showMessageDialog(view, "Loan checked out successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Update table with new loan information
            DefaultTableModel tableModel = view.getTableModel();
            Object[] rowData = {loan.getLoanId(), loan.getPatronId(), loan.getBarcode(),
                    loan.getDueDate(), loan.getRenewalCount(), "Action"};
            tableModel.addRow(rowData);

            // Clear fields after successful checkout
            view.getBarcodeField().setText("Item Barcode");
            view.getPatronIdField().setText("Patron ID");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error occurred while checking out the loan.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("loggedInUser")) {
            // Ignore event if logged in user is not a librarian
            User newUser = (User) evt.getNewValue();
            if (newUser == null || newUser.getUserType() != UserType.LIBRARIAN)
                return;

            // Initialize librarian
            librarian = (Librarian) newUser;
            System.out.println(librarian);
        }
    }
}