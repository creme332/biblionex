package com.github.creme332.view.patron;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.github.creme332.model.Loan;

/**
 * A page that displays loans of a patron.
 */
public class LoanPage extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private transient ActionListener actionListener;

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
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void updateTableModel(List<Loan> loans) {
        tableModel.setRowCount(0); // Clear existing rows

        for (Loan loan : loans) {
            String returnDate = loan.getReturnDate() != null ? loan.getReturnDate().toString() : "Pending";
            String status = determineLoanStatus(loan);

            tableModel.addRow(new Object[] { loan.getLoanId(), loan.getBarcode(), loan.getIssueDate(),
                    returnDate, loan.getDueDate(), status, status.equals("Overdue") ? "Pay" : "" });
        }
    }

    public void addLoanActionListener(ActionListener listener) {
        this.actionListener = listener;
    }

    private String determineLoanStatus(Loan loan) {
        if (loan.getReturnDate() != null) {
            return "Complete";
        } else if (loan.getDueDate().before(new java.util.Date())) {
            return "Overdue";
        } else {
            return "Pending";
        }
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int loanId;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            loanId = (int) table.getValueAt(row, 0); // Assuming Loan ID is in the first column
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed && "Pay".equals(label)) {
                firePayAction(loanId);
            }
            isPushed = false;
            return new String(label);
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        private void firePayAction(int loanId) {
            if (actionListener != null) {
                ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, Integer.toString(loanId));
                actionListener.actionPerformed(e);
            }
        }
    }
}
