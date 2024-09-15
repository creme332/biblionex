package com.github.creme332.controller.librarian;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Loan;
import com.github.creme332.model.User;
import com.github.creme332.model.UserType;
import com.github.creme332.model.Librarian;
import com.github.creme332.view.librarian.CheckInPage;

public class CheckInController implements PropertyChangeListener {
    private CheckInPage view;
    private Librarian librarian;

    public CheckInController(AppState app, CheckInPage view) {
        this.view = view;
        app.addPropertyChangeListener(this);

        view.getSearchButton().addActionListener(e -> searchLoans());

        view.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));

        Thread th = new Thread() {
            @Override
            public void run() {
                refreshTable();
                addTableButtonListeners();
            }
        };
        th.start();
    }

    /**
     * Add listeners to check in and renew buttons.
     */
    private void addTableButtonListeners() {
        JTable table = view.getTable();
        CheckInPage.ActionCellRenderer rendererEditor = view.getActionCellRenderer();

        rendererEditor.setRenewButtonActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                int loanId = (int) table.getValueAt(selectedRow, 0);
                handleLoanRenew(loanId);
            }
        });

        rendererEditor.setCheckInButtonActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int loanId = (int) table.getValueAt(selectedRow, 0);
                handleCheckIn(loanId);
            }
        });
    }

    /**
     * Updates list of active loans in table.
     */
    private void refreshTable() {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);

        try {
            List<Loan> loans = Loan.findAllActive();
            for (Loan loan : loans) {
                Object[] rowData = { loan.getLoanId(), loan.getPatronId(), loan.getBarcode(), loan.getIssueDate(),
                        loan.getDueDate(), loan.getRenewalCount() };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while fetching loans.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchLoans() {
        String barcodeText = view.getBarcode();

        if (barcodeText.isBlank()) {
            // display all loans
            refreshTable();
            return;
        }

        // convert barcode to integer
        int barcode;
        try {
            barcode = Integer.parseInt(barcodeText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid barcode.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);

        try {
            Loan loan = Loan.findActiveLoanByBarcode(barcode);
            if (loan != null) {
                Object[] rowData = { loan.getLoanId(), loan.getPatronId(), loan.getBarcode(),
                        loan.getDueDate(), loan.getRenewalCount(), "Action" };
                tableModel.addRow(rowData);
            } else {
                JOptionPane.showMessageDialog(view, "No active loan found for the given barcode.",
                        "No Results",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while fetching loans.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void handleLoanRenew(int loanId) {
        try {
            Loan loan = Loan.findById(loanId);
            librarian.renewLoan(loan);
            JOptionPane.showMessageDialog(view, "Loan renewed successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Renewal Limit Reached", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while renewing the loan.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void handleCheckIn(int loanId) {
        try {
            Loan loan = Loan.findById(loanId);
            librarian.checkIn(loan);
            JOptionPane.showMessageDialog(view, "Loan checked in successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while checking in the loan.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        if (propertyName.equals("currentScreen") && (Screen) evt.getNewValue() == Screen.LIBRARIAN_CHECKIN_SCREEN) {
            // each time user switches to check-in page, refresh page
            refreshTable();
            view.clearBarcodeField();
        }

        if (propertyName.equals("loggedInUser")) {

            // ignore event if logged in user is not a librarian
            User newUser = (User) evt.getNewValue();
            if (newUser == null
                    || newUser.getUserType() != UserType.LIBRARIAN)
                return;

            // initialize librarian
            librarian = (Librarian) newUser;
        }
    }
}
