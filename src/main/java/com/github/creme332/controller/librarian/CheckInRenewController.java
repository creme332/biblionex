package com.github.creme332.controller.librarian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Loan;
import com.github.creme332.model.Librarian;
import com.github.creme332.view.librarian.CheckInRenew;

public class CheckInRenewController {
    private AppState app;
    private CheckInRenew checkInRenew;

    public CheckInRenewController(AppState app, CheckInRenew checkInRenew) {
        this.app = app;
        this.checkInRenew = checkInRenew;

        checkInRenew.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchLoans();
            }
        });

        checkInRenew.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        checkInRenew.getLoanTable().getColumn("Action").setCellEditor(new CheckInRenew.ActionEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int row, int column) {
                Component component = super.getTableCellEditorComponent(table, value, isSelected, row, column);
                JButton renewButton = (JButton) ((JPanel) component).getComponent(0);
                JButton checkinButton = (JButton) ((JPanel) component).getComponent(1);

                renewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        renewLoan(row);
                        stopCellEditing();
                    }
                });

                checkinButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        checkInLoan(row);
                        stopCellEditing();
                    }
                });

                return component;
            }
        });

        // Display all active loans initially
        displayActiveLoans();
    }

    private void displayActiveLoans() {
        DefaultTableModel tableModel = checkInRenew.getTableModel();
        tableModel.setRowCount(0);

        try {
            List<Loan> loans = Loan.findAllActive();
            for (Loan loan : loans) {
                Object[] rowData = { loan.getLoanId(), loan.getPatronId(), loan.getBarcode(),
                        loan.getDueDate(), loan.getRenewalCount(), "Action" };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(checkInRenew, "Error occurred while fetching loans.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void searchLoans() {
        String barcodeText = checkInRenew.getBarcodeField().getText();
        int barcode;

        try {
            barcode = Integer.parseInt(barcodeText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(checkInRenew, "Please enter a valid barcode.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel tableModel = checkInRenew.getTableModel();
        tableModel.setRowCount(0);

        try {
            Loan loan = Loan.findActiveLoanByBarcode(barcode);
            if (loan != null) {
                Object[] rowData = { loan.getLoanId(), loan.getPatronId(), loan.getBarcode(),
                        loan.getDueDate(), loan.getRenewalCount(), "Action" };
                tableModel.addRow(rowData);
            } else {
                JOptionPane.showMessageDialog(checkInRenew, "No active loans found for the given barcode.", "No Results",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(checkInRenew, "Error occurred while fetching loans.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void renewLoan(int row) {
        DefaultTableModel tableModel = checkInRenew.getTableModel();
        int loanId = (int) tableModel.getValueAt(row, 0);

        try {
            Loan loan = Loan.findById(loanId);
            if (loan.getRenewalCount() >= Loan.RENEWAL_LIMIT) {
                JOptionPane.showMessageDialog(checkInRenew, "This loan has reached the maximum renewal limit.",
                        "Renewal Limit Reached", JOptionPane.WARNING_MESSAGE);
            } else {
                Date newDueDate = new Date(loan.getDueDate().getTime() + (7L * 24 * 60 * 60 * 1000)); // Add 7 days
                loan.setDueDate(newDueDate);
                loan.setRenewalCount(loan.getRenewalCount() + 1);
                Loan.update(loan);
                tableModel.setValueAt(loan.getDueDate(), row, 3);
                tableModel.setValueAt(loan.getRenewalCount(), row, 4);
                JOptionPane.showMessageDialog(checkInRenew, "Loan renewed successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(checkInRenew, "Error occurred while renewing the loan.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void checkInLoan(int row) {
        DefaultTableModel tableModel = checkInRenew.getTableModel();
        int loanId = (int) tableModel.getValueAt(row, 0);

        try {
            Loan loan = Loan.findById(loanId);
            Librarian loggedInLibrarian = (Librarian) app.getLoggedInUser();
            loan.setCheckinLibrarianId(loggedInLibrarian.getUserId());
            loan.setReturnDate(new Date()); // Set the return date to the current date
            Loan.update(loan);
            tableModel.removeRow(row);
            JOptionPane.showMessageDialog(checkInRenew, "Loan checked in successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(checkInRenew, "Error occurred while checking in the loan.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
