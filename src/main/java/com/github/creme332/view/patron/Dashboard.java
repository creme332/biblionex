package com.github.creme332.view.patron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.github.creme332.controller.patron.DashboardController;
import com.github.creme332.model.Patron;

public class Dashboard extends JPanel {
    private DashboardController controller;
    private JTable patronTable;
    private JTextField searchField;
    private JButton searchByIdButton;
    private JButton searchByEmailButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField idField;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneNoField;
    private JTextField addressField;

    public Dashboard() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome to Patron Dashboard");
        add(label, BorderLayout.NORTH);

        patronTable = new JTable();
        add(new JScrollPane(patronTable), BorderLayout.CENTER);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchField = new JTextField(20);
        searchByIdButton = new JButton("Search by ID");
        searchByEmailButton = new JButton("Search by Email");
        searchPanel.add(searchField);
        searchPanel.add(searchByIdButton);
        searchPanel.add(searchByEmailButton);
        add(searchPanel, BorderLayout.SOUTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2));

        formPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Phone No:"));
        phoneNoField = new JTextField();
        formPanel.add(phoneNoField);

        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        formPanel.add(updateButton);
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.EAST);

        addActionListeners();
    }

    public void setController(DashboardController controller) {
        this.controller = controller;
    }

    private void addActionListeners() {
        searchByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(searchField.getText());
                controller.searchPatronById(id);
            }
        });

        searchByEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = searchField.getText();
                controller.searchPatronByEmail(email);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Patron patron = new Patron();
                patron.setUserId(Integer.parseInt(idField.getText()));
                patron.setEmail(emailField.getText());
                patron.setFirstName(firstNameField.getText());
                patron.setLastName(lastNameField.getText());
                patron.setPhoneNo(phoneNoField.getText());
                patron.setAddress(addressField.getText());
                controller.updatePatron(patron);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                controller.deletePatron(id);
            }
        });
    }

    public void displayPatrons(List<Patron> patrons) {
        String[] columnNames = {"ID", "Email", "First Name", "Last Name", "Phone No", "Address"};
        String[][] data = new String[patrons.size()][6];

        for (int i = 0; i < patrons.size(); i++) {
            Patron patron = patrons.get(i);
            data[i][0] = String.valueOf(patron.getUserId());
            data[i][1] = patron.getEmail();
            data[i][2] = patron.getFirstName();
            data[i][3] = patron.getLastName();
            data[i][4] = patron.getPhoneNo();
            data[i][5] = patron.getAddress();
        }

        patronTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    public void displayPatron(Patron patron) {
        if (patron != null) {
            idField.setText(String.valueOf(patron.getUserId()));
            emailField.setText(patron.getEmail());
            firstNameField.setText(patron.getFirstName());
            lastNameField.setText(patron.getLastName());
            phoneNoField.setText(patron.getPhoneNo());
            addressField.setText(patron.getAddress());
        } else {
            JOptionPane.showMessageDialog(this, "Patron not found");
        }
    }
}
