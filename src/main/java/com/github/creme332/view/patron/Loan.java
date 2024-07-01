package com.github.creme332.view.patron;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.creme332.utils.ButtonEditor;
import com.github.creme332.utils.ButtonRenderer;
import com.github.creme332.model.Fine;

public class Loan extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ActionListener actionListener;

    public Loan() {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"Loan ID", "Barcode", "Issue Date", "Return Date", "Due Date", "Amount", "Action"}, 0);
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only the "Action" column is editable
            }
        };
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setLoanFineRecords(List<Fine.LoanFineData> records) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Fine.LoanFineData record : records) {
            tableModel.addRow(new Object[]{record.getLoanId(), record.getBarcode(), record.getIssueDate(), record.getReturnDate(), record.getDueDate(), record.getAmount(), "Pay"});
        }
    }

    public void addLoanActionListener(ActionListener listener) {
        this.actionListener = listener;
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int loanId;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            loanId = (int) table.getValueAt(row, 0); // Assuming Loan ID is in the first column
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                firePayAction(loanId);
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        private void firePayAction(int loanId) {
            if (actionListener != null) {
                ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, Integer.toString(loanId));
                actionListener.actionPerformed(e);
            }
        }
    }
}
