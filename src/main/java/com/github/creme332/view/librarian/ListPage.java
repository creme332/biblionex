package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.github.creme332.model.User;

public abstract class ListPage extends JPanel {
    private JButton backButton;
    private JTextField searchField;
    private JButton searchButton;
    private JRadioButton byNameRadio;
    private JRadioButton byIdRadio;
    private JTable userTable;
    private JButton newUserButton;
    private DefaultTableModel tableModel;

    protected ListPage() {
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("Back");
        searchField = new JTextField(20);
        searchField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSearchIcon());
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter name or ID");

        searchButton = new JButton("Search");
        byNameRadio = new JRadioButton("By name");
        byIdRadio = new JRadioButton("By ID");
        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(byNameRadio);
        searchGroup.add(byIdRadio);
        byNameRadio.setSelected(true);

        topPanel.add(backButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(byNameRadio);
        topPanel.add(byIdRadio);
        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { getUserType() + " ID", "First Name", "Last Name", "Email", "Phone No", "Action" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Prevent editing of User ID
            }
        };
        userTable = new JTable(tableModel);
        userTable.getColumn("Action").setCellRenderer(new DeleteButtonRenderer());
        userTable.getColumn("Action").setCellEditor(new DeleteButtonEditor(new JCheckBox(), this));
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // New User Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newUserButton = new JButton("+ New " + getUserType());
        bottomPanel.add(newUserButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public class DeleteButtonRenderer extends JButton implements TableCellRenderer {
        public DeleteButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText((value == null) ? "Delete" : value.toString());
            setBackground(Color.RED);
            return this;
        }
    }

    class DeleteButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private ListPage listPage;
        private int row;

        public DeleteButtonEditor(JCheckBox checkBox, ListPage listPage) {
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
            if (row >= 0) {
                listPage.notifyDeleteUser(row);
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }
            label = (value == null) ? "Delete" : value.toString();
            button.setText(label);
            this.row = row; // Capture the row index
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }

    protected abstract String getUserType();

    public abstract void notifyDeleteUser(int row);

    /**
     * Display a list of users in the table.
     * 
     * @param users
     */
    public void populateTable(List<User> users) {
        tableModel.setRowCount(0);
        if (users.isEmpty())
            return;

        for (User user : users) {
            Object[] rowData = {
                    user.getUserId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPhoneNo(),
                    "Delete"
            };
            tableModel.addRow(rowData);
        }
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JRadioButton getByNameRadio() {
        return byNameRadio;
    }

    public JRadioButton getByIdRadio() {
        return byIdRadio;
    }

    public JTable getUserTable() {
        return userTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getNewUserButton() {
        return newUserButton;
    }
}
