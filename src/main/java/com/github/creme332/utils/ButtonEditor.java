package com.github.creme332.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.github.creme332.view.librarian.ListPage;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private ListPage listPage;
    private int row;

    public ButtonEditor(JCheckBox checkBox, ListPage listPage) {
        super(checkBox);
        this.listPage = listPage;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                deleteRow();
            }
        });
    }

    private void deleteRow() {
        System.out.println("Delete button clicked, row: " + row); // Debug print
        if (row >= 0) {
            listPage.notifyDeleteUser(row);
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(UIManager.getColor("Button.background"));
        }
        label = (value == null) ? "Delete" : value.toString();
        button.setText(label);
        isPushed = true;
        this.row = row; // Capture the row index
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
