package com.github.creme332.view.patron;

import java.util.List;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.github.creme332.model.Loan;
import com.github.creme332.model.LoanStatus;

/**
 * A page that displays loans of a patron.
 */
public class LoanPage extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private PayButtonEditor payButtonEditor;

    public LoanPage() {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(
                new Object[] { "Loan ID", "Barcode", "Issue Date", "Return Date", "Due Date", "Status", "Action" }, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == tableModel.getColumnCount() - 1; // Only the "Action" column is editable
            }
        };

        payButtonEditor = new PayButtonEditor(new JCheckBox());
        table.getColumn("Action").setCellEditor(payButtonEditor);
        table.getColumn("Action").setCellRenderer(new PayButtonRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public int getSelectedLoanID() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            return (int) table.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    public PayButtonEditor getPayButtonEditor() {
        return payButtonEditor;
    }

    public void updateTableModel(List<Loan> loans) {
        tableModel.setRowCount(0); // Clear existing rows

        for (Loan loan : loans) {
            String returnDate = loan.getReturnDate() != null ? loan.getReturnDate().toString() : "Pending";

            tableModel.addRow(new Object[] { loan.getLoanId(), loan.getBarcode(), loan.getIssueDate(),
                    returnDate, loan.getDueDate(), loan.getLoanStatus(), loan.isOverdue() });
        }
    }

    // Custom renderer for pay button
    class PayButtonRenderer extends JButton implements TableCellRenderer {
        public PayButtonRenderer() {
            setText("Pay");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            // Determine if the button should be enabled or disabled
            setEnabled((LoanStatus) table.getValueAt(row, 5) == LoanStatus.OVERDUE);
            return this;
        }
    }

    // Custom editor for pay button
    public class PayButtonEditor extends DefaultCellEditor {
        protected JButton payButton;

        public PayButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            payButton = new JButton("Pay");
        }

        public void handlePayment(ActionListener listener) {
            payButton.addActionListener(listener);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            boolean enabledButton = (LoanStatus) table.getValueAt(row, 5) == LoanStatus.OVERDUE;
            if (!enabledButton)
                return null;
            return payButton;
        }
    }

}