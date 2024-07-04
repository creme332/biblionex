package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CheckInRenew extends JPanel {
    private JTextField barcodeField;
    private JButton searchButton;
    private JTable loanTable;
    private DefaultTableModel tableModel;
    private JButton backButton;

    public CheckInRenew() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        backButton = new JButton("Back");
        topPanel.add(backButton);
        barcodeField = new JTextField(20);
        barcodeField.setForeground(Color.GRAY);
        barcodeField.setText("Enter barcode");
        barcodeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (barcodeField.getText().equals("Enter barcode")) {
                    barcodeField.setText("");
                    barcodeField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (barcodeField.getText().isEmpty()) {
                    barcodeField.setText("Enter barcode");
                    barcodeField.setForeground(Color.GRAY);
                }
            }
        });
        topPanel.add(barcodeField);
        searchButton = new JButton("Search");
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = { "Loan ID", "Patron ID", "Barcode", "Due Date", "Renewal Count", "Action" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        loanTable = new JTable(tableModel);

        loanTable.getColumn("Action").setCellRenderer(new ActionRenderer());
        loanTable.getColumn("Action").setCellEditor(new ActionEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(loanTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getBarcodeField() {
        return barcodeField;
    }

    public JTable getLoanTable() {
        return loanTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getBackButton() {
        return backButton;
    }

    class ActionRenderer extends JPanel implements TableCellRenderer {
        private final JButton renewButton;
        private final JButton checkinButton;

        public ActionRenderer() {
            renewButton = new JButton("Renew");
            checkinButton = new JButton("Check In");
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            add(renewButton);
            add(checkinButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    public static class ActionEditor extends DefaultCellEditor {
        private final JPanel panel;
        private final JButton renewButton;
        private final JButton checkinButton;

        public ActionEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel();
            renewButton = new JButton("Renew");
            checkinButton = new JButton("Check In");
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.add(renewButton);
            panel.add(checkinButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return panel;
        }
    }
}
