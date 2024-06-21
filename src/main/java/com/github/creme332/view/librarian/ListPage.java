package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.github.creme332.model.Patron;

public class ListPage extends JPanel {
    private JButton backButton;
    private JTextField searchField;
    private JRadioButton byNameRadio;
    private JRadioButton byIdRadio;
    private JTable patronTable;
    private JButton newPatronButton;
    private DefaultTableModel tableModel;
    private JButton deleteButton;

    public ListPage(List<Patron> patrons) {
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("Back");
        searchField = new JTextField(20);
        byNameRadio = new JRadioButton("By name");
        byIdRadio = new JRadioButton("By ID");
        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(byNameRadio);
        searchGroup.add(byIdRadio);
        byNameRadio.setSelected(true);
        topPanel.add(backButton);
        topPanel.add(searchField);
        topPanel.add(byNameRadio);
        topPanel.add(byIdRadio);
        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "Patron ID", "First Name", "Last Name", "Email", "Phone No", "Action" };
        tableModel = new DefaultTableModel(columnNames, 0);
        patronTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patronTable);
        add(scrollPane, BorderLayout.CENTER);

        // Populate table
        for (Patron patron : patrons) {
            Object[] rowData = {
                    patron.getUserId(),
                    patron.getFirstName(),
                    patron.getLastName(),
                    patron.getEmail(),
                    patron.getPhoneNo(),
                    "Delete"
            };
            tableModel.addRow(rowData);
        }

        // New Patron Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newPatronButton = new JButton("+ New patron");
        bottomPanel.add(newPatronButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Getter methods for controller
    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JRadioButton getByNameRadio() {
        return byNameRadio;
    }

    public JRadioButton getByIdRadio() {
        return byIdRadio;
    }

    public JTable getPatronTable() {
        return patronTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getNewPatronButton() {
        return newPatronButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
