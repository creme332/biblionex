package com.github.creme332.controller.librarian;

import com.github.creme332.model.*;
import com.github.creme332.utils.exception.UserVisibleException;
import com.github.creme332.view.librarian.CheckOutPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class CheckOutController implements PropertyChangeListener {
    private CheckOutPage view;
    private Librarian librarian;

    public CheckOutController(AppState app, CheckOutPage view) {
        this.view = view;
        app.addPropertyChangeListener(this);

        view.handleBackButton(e -> app.setCurrentScreen(app.getPreviousScreen()));
        view.handleCheckout(e -> checkOutLoan());
    }

    /**
     * Checks if barcode entered is valid
     * 
     * @param barcodeText
     * @return True if barcodeText is valid
     */
    private boolean validateBarcode(String barcodeText) {
        // check if barcode field is not blank
        if (barcodeText.isBlank()) {
            return false;
        }

        // check if barcode field is a valid barcode in database
        try {
            int barcode = Integer.parseInt(barcodeText);
            MaterialCopy item = MaterialCopy.findById(barcode);
            return item != null;
        } catch (NumberFormatException | SQLException e) {
            return false;
        }
    }

    /**
     * Checks if patron ID entered is valid
     * 
     * @param patronIdText
     * @return True if patronIdText is valid
     */
    private boolean validatePatronId(String patronIdText) {
        // check if barcode field is not blank
        if (patronIdText.isBlank()) {
            return false;
        }

        try {
            int patronId = Integer.parseInt(patronIdText);
            Patron patron = Patron.findById(patronId);
            return patron != null;
        } catch (NumberFormatException | SQLException e) {
            return false;
        }
    }

    private void checkOutLoan() {
        String barcodeText = view.getBarcode();
        String patronIdText = view.getPatronId();

        // Validate input fields
        if (!validateBarcode(barcodeText)) {
            JOptionPane.showMessageDialog(view, "Please enter a valid item barcode.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validatePatronId(patronIdText)) {
            JOptionPane.showMessageDialog(view, "Please enter a valid patron ID", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int barcode;
        int patronId;

        try {
            barcode = Integer.parseInt(barcodeText);
            patronId = Integer.parseInt(patronIdText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter valid numbers for item barcode and patron ID.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perform check out logic
        try {
            Loan loan = librarian.checkOut(patronId, barcode);

            JOptionPane.showMessageDialog(view, "Loan checked out successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Update table with new loan information
            DefaultTableModel tableModel = view.getTableModel();
            Object[] rowData = { loan.getLoanId(), loan.getPatronId(), loan.getBarcode(),
                    loan.getDueDate(), loan.getRenewalCount(), "Action" };
            tableModel.addRow(rowData);

            // Clear fields after successful checkout
            view.clearFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error occurred while checking out the loan.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (UserVisibleException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Failure",
                    JOptionPane.ERROR_MESSAGE);
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
        }
    }
}