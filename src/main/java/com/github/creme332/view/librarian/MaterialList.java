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

public class MaterialList extends JPanel {
    private JButton backButton;
    private JButton newMaterialButton;
    private JTextField searchField;
    private JTable materialTable;
    private DefaultTableModel tableModel;
    private ActionCellRenderer actionCellRenderer;

    public MaterialList() {
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);

        String[] columnNames = { "Material ID", "Title", "Publisher ID", "Description", "Age Restriction", "Type",
                "Action" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only the last column editable to allow button clicks
                return column == columnNames.length - 1;
            }
        };
        materialTable = new JTable(tableModel);

        actionCellRenderer = new ActionCellRenderer();
        materialTable.getColumn("Action").setCellRenderer(actionCellRenderer);
        materialTable.getColumn("Action").setCellEditor(actionCellRenderer);

        JScrollPane scrollPane = new JScrollPane(materialTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        // Add back button to topPanel
        backButton = new JButton("Back");
        topPanel.add(backButton, BorderLayout.WEST);

        // Add search field and search button to topPanel
        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSearchIcon());
        searchContainer.add(searchField);
        topPanel.add(searchContainer, BorderLayout.CENTER);

        // Add new material button to topPanel
        newMaterialButton = new JButton("+ New Material");
        topPanel.add(newMaterialButton, BorderLayout.EAST);

        return topPanel;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getNewMaterialButton() {
        return newMaterialButton;
    }

    public JTable getTable() {
        return materialTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public ActionCellRenderer getActionCellRenderer() {
        return actionCellRenderer;
    }

    /**
     * Class responsible for rendering all cells in the Action column.
     */
    public class ActionCellRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
        private final JButton viewButton;
        private final JButton deleteButton;
        private JPanel panel;

        public ActionCellRenderer() {
            panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);

            viewButton = new JButton("View");
            deleteButton = new JButton("Delete");
            deleteButton.setBackground(Color.RED);
            deleteButton.setEnabled(false);

            panel.add(viewButton, BorderLayout.WEST);
            panel.add(deleteButton, BorderLayout.EAST);
        }

        public void setViewButtonActionListener(ActionListener listener) {
            fireEditingStopped();

            for (ActionListener al : viewButton.getActionListeners()) {
                viewButton.removeActionListener(al);
            }
            viewButton.addActionListener(listener);
        }

        public void setDeleteButtonActionListener(ActionListener listener) {
            fireEditingStopped();

            for (ActionListener al : deleteButton.getActionListeners()) {
                deleteButton.removeActionListener(al);
            }
            deleteButton.addActionListener(listener);
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
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return panel;
        }
    }
}
