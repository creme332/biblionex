package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import com.github.creme332.model.Loan;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;

public class OverdueLoansPage extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField barcodeField;
    private JButton searchButton;
    private JButton backButton;

    public OverdueLoansPage() {
        setLayout(new BorderLayout());

        // Add top panel
        add(createTopPanel(), BorderLayout.NORTH);

        // Setup table
        tableModel = new DefaultTableModel(
            new Object[]{"Loan ID", "Patron ID", "Barcode", "Due Date", "Issue Date", "Return Date"}, 0
        );
        table = new JTable(tableModel);

        // Load overdue loans
        loadOverdueLoans();

        // Add table to the center of the layout
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        // Add back button to topPanel
        backButton = new JButton("Back");
        topPanel.add(backButton, BorderLayout.WEST);

        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Add barcode search field with icon and placeholder
        barcodeField = new JTextField(20);
        barcodeField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSearchIcon());
        barcodeField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter barcode");

        searchContainer.add(barcodeField);
        topPanel.add(searchContainer, BorderLayout.CENTER);

        // Add search button
        searchButton = new JButton("Search");
        searchContainer.add(searchButton);

        return topPanel;
    }

    private void loadOverdueLoans() {
        try {
            List<Loan> overdueLoans = Loan.findAllOverdue();
            for (Loan loan : overdueLoans) {
                tableModel.addRow(new Object[]{
                    loan.getLoanId(),
                    loan.getPatronId(),
                    loan.getBarcode(),
                    loan.getDueDate(),
                    loan.getIssueDate(),
                    loan.getReturnDate()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this, "Error loading overdue loans: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public String getBarcode() {
        return barcodeField.getText().trim();
    }

    public void clearBarcodeField() {
        barcodeField.setText("");
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }
}
