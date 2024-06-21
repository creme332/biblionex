package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import com.github.creme332.utils.ButtonRenderer;
import com.github.creme332.utils.ButtonEditor;
import com.github.creme332.model.Patron;

public class ListPage extends JPanel {
    private JButton backButton;
    private JTextField searchField;
    private JButton searchButton;
    private JRadioButton byNameRadio;
    private JRadioButton byIdRadio;
    private JTable patronTable;
    private JButton newPatronButton;
    private DefaultTableModel tableModel;

    public ListPage(List<Patron> patrons) {
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
        String[] columnNames = { "Patron ID", "First Name", "Last Name", "Email", "Phone No", "Action" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Prevent editing of Patron ID
            }
        };
        patronTable = new JTable(tableModel);
        patronTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        patronTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane scrollPane = new JScrollPane(patronTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Populate table
        populateTable(patrons);

        // New Patron Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newPatronButton = new JButton("+ New patron");
        bottomPanel.add(newPatronButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Handle cell updates
        patronTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int row = patronTable.getSelectedRow();
                int column = patronTable.getSelectedColumn();
                if (row != -1 && column != -1) {
                    updatePatronInDatabase(row);
                }
            }
        });
    }
    
    // Populate table method
    public void populateTable(List<Patron> patrons) {
        tableModel.setRowCount(0);
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
    }

    // Update patron in the database method
    public void updatePatronInDatabase(int row) {
        int patronId = (int) tableModel.getValueAt(row, 0);
        String firstName = (String) tableModel.getValueAt(row, 1);
        String lastName = (String) tableModel.getValueAt(row, 2);
        String email = (String) tableModel.getValueAt(row, 3);
        String phoneNo = (String) tableModel.getValueAt(row, 4);

        Patron patron = new Patron();
        patron.setUserId(patronId);
        patron.setFirstName(firstName);
        patron.setLastName(lastName);
        patron.setEmail(email);
        patron.setPhoneNo(phoneNo);

        Patron.update(patron);
    }

    // Getter methods for controller
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

    public JTable getPatronTable() {
        return patronTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getNewPatronButton() {
        return newPatronButton;
    }
}
