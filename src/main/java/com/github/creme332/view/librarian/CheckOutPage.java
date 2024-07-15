package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CheckOutPage extends JPanel {
    private JTextField barcodeField;
    private JTextField patronIdField;
    private JButton checkOutButton;
    private JTable loanTable;
    private DefaultTableModel tableModel;

    public CheckOutPage() {
        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);

        String[] columnNames = {"Loan ID", "Patron ID", "Barcode", "Due Date", "Renewal Count", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only last column editable to allow button clicks
                return column == columnNames.length - 1;
            }
        };
        loanTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(loanTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        // Back button
        JButton backButton = new JButton("Back");
        topPanel.add(backButton, BorderLayout.WEST);

        // Barcode field
        JPanel barcodePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barcodeField = new JTextField(20);
        barcodeField.setForeground(Color.GRAY);
        barcodeField.setText("Item Barcode");
        barcodeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (barcodeField.getText().equals("Item Barcode")) {
                    barcodeField.setText("");
                    barcodeField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (barcodeField.getText().isEmpty()) {
                    barcodeField.setText("Item Barcode");
                    barcodeField.setForeground(Color.GRAY);
                }
            }
        });
        barcodePanel.add(barcodeField);
        topPanel.add(barcodePanel, BorderLayout.CENTER);

        // Patron ID field
        JPanel patronIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        patronIdField = new JTextField(20);
        patronIdField.setForeground(Color.GRAY);
        patronIdField.setText("Patron ID");
        patronIdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (patronIdField.getText().equals("Patron ID")) {
                    patronIdField.setText("");
                    patronIdField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (patronIdField.getText().isEmpty()) {
                    patronIdField.setText("Patron ID");
                    patronIdField.setForeground(Color.GRAY);
                }
            }
        });
        patronIdPanel.add(patronIdField);
        topPanel.add(patronIdPanel, BorderLayout.EAST);

        // Check Out button
        checkOutButton = new JButton("Check Out");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(checkOutButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        return topPanel;
    }

    public JButton getCheckOutButton() {
        return checkOutButton;
    }

    public JTextField getBarcodeField() {
        return barcodeField;
    }

    public JTextField getPatronIdField() {
        return patronIdField;
    }

    public JTable getLoanTable() {
        return loanTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
