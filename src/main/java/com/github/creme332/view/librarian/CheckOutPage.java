package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import java.awt.*;
import java.awt.event.ActionListener;

public class CheckOutPage extends JPanel {
    private JTextField barcodeField;
    private JTextField patronIdField;
    private JButton checkOutButton;
    private JTable loanTable;
    private DefaultTableModel tableModel;
    private JButton backButton;

    public CheckOutPage() {
        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);

        String[] columnNames = { "Loan ID", "Patron ID", "Barcode", "Due Date", "Renewal Count" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // no cells should be editable
            }
        };
        loanTable = new JTable(tableModel);
        loanTable.setEnabled(false); // Disable table editing

        JScrollPane scrollPane = new JScrollPane(loanTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        // Back button
        backButton = new JButton("Back");
        topPanel.add(backButton, BorderLayout.WEST);

        // Barcode field
        JPanel barcodePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barcodeField = new JTextField(20);
        barcodeField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter item barcode");
        barcodePanel.add(barcodeField);
        topPanel.add(barcodePanel, BorderLayout.CENTER);

        // Patron ID field
        JPanel patronIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        patronIdField = new JTextField(20);
        patronIdField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter patron ID");

        patronIdPanel.add(patronIdField);
        topPanel.add(patronIdPanel, BorderLayout.EAST);

        // Check Out button
        checkOutButton = new JButton("Check Out");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(checkOutButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        return topPanel;
    }

    public void clearFields() {
        barcodeField.setText("");
        patronIdField.setText("");
    }

    public void handleBackButton(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void handleCheckout(ActionListener listener) {
        checkOutButton.addActionListener(listener);
    }

    public String getBarcode() {
        return barcodeField.getText().trim();
    }

    public String getPatronId() {
        return patronIdField.getText().trim();
    }

    public JTable getLoanTable() {
        return loanTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
