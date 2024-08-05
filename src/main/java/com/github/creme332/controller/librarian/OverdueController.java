package com.github.creme332.controller.librarian;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Loan;
import com.github.creme332.view.librarian.OverdueLoansPage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class OverdueController {
    private OverdueLoansPage view;
    AppState app;

    public OverdueController(AppState app, OverdueLoansPage view) {
        this.app = app;
        this.view = view;

        // Handle back button action
        view.getBackButton().addActionListener(e -> app.setCurrentScreen(app.getPreviousScreen()));

        view.getSearchButton().addActionListener(new SearchListener());
        loadOverdueLoans();
    }

    private void loadOverdueLoans() {
        try {
            List<Loan> overdueLoans = Loan.findAllOverdue();
            view.updateTableModel(overdueLoans);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                view, "Error loading overdue loans: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String barcodeText = view.getBarcode();
            if (barcodeText.isEmpty()) {
                loadOverdueLoans(); // Reload all overdue loans if the search field is empty
                return;
            }

            try {
                int barcode = Integer.parseInt(barcodeText);
                Loan loan = Loan.findActiveLoanByBarcode(barcode);

                if (loan != null && loan.isOverdue()) {
                    view.updateTableModel(List.of(loan));
                } else {
                    JOptionPane.showMessageDialog(
                        view, "No overdue loan found for the provided barcode.", "No Results", JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    view, "Invalid barcode. Please enter a numeric barcode.", "Invalid Input", JOptionPane.ERROR_MESSAGE
                );
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                    view, "Error searching for overdue loans: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
