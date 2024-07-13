package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.EventObject;

public class CheckInPage extends JPanel {
    private JTextField barcodeField;
    private JButton searchButton;
    private JTable loanTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private ActionCellRenderer actionCellRenderer;

    public CheckInPage() {
        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);

        String[] columnNames = { "Loan ID", "Patron ID", "Barcode", "Due Date", "Renewal Count", "Action" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only last column editable to allow button clicks
                return column == columnNames.length - 1;
            }
        };
        loanTable = new JTable(tableModel);

        actionCellRenderer = new ActionCellRenderer();
        loanTable.getColumn("Action").setCellRenderer(actionCellRenderer);
        loanTable.getColumn("Action").setCellEditor(actionCellRenderer);

        JScrollPane scrollPane = new JScrollPane(loanTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);

        // add back button to topPanel
        backButton = new JButton("Back");
        topPanel.add(backButton, BorderLayout.WEST);

        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));

        barcodeField = new JTextField(20);
        barcodeField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSearchIcon());
        barcodeField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter barcode");

        searchContainer.add(barcodeField);
        topPanel.add(searchContainer, BorderLayout.CENTER);

        searchButton = new JButton("Search");
        searchContainer.add(searchButton);

        return topPanel;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getBarcodeField() {
        return barcodeField;
    }

    public JTable getTable() {
        return loanTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public ActionCellRenderer getActionCellRenderer() {
        return actionCellRenderer;
    }

    /**
     * Class responsible for rendering all cells in Action column and setting action
     * listeners to each button in the Action column.
     */
    public class ActionCellRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
        private final JButton renewButton;
        private final JButton checkInButton;
        private JPanel panel;

        public ActionCellRenderer() {
            panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);

            renewButton = new JButton("Renew");
            checkInButton = new JButton("Check In");

            panel.add(renewButton, BorderLayout.WEST);
            panel.add(checkInButton, BorderLayout.EAST);
        }

        public void setRenewButtonActionListener(ActionListener listener) {
            for (ActionListener al : renewButton.getActionListeners()) {
                renewButton.removeActionListener(al);
            }
            renewButton.addActionListener(listener);
        }

        public void setCheckInButtonActionListener(ActionListener listener) {
            for (ActionListener al : checkInButton.getActionListeners()) {
                checkInButton.removeActionListener(al);
            }
            checkInButton.addActionListener(listener);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            return panel;
        }
    }
}
