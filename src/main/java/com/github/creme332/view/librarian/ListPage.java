package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import com.github.creme332.utils.ButtonRenderer;
import com.github.creme332.utils.ButtonEditor;
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
    private String userType;

    public ListPage() {
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("Back");
        searchField = new JTextField(20);
        searchButton = new JButton("Enter");
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
        userTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        userTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), this));
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // New User Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newUserButton = new JButton("+ New " + getUserType());
        bottomPanel.add(newUserButton);
        add(bottomPanel, BorderLayout.SOUTH);
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
