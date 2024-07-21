package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.github.creme332.model.Librarian;
import com.github.creme332.model.Patron;
import com.github.creme332.model.User;
import com.github.creme332.model.UserType;

/**
 * Displays user details in a table.
 */
public class UserListPage extends JPanel {
    private JButton backButton;
    private JTextField searchField;
    private JButton searchButton;
    private JRadioButton byNameRadio;
    private JRadioButton byIdRadio;
    private JTable userTable;
    private JButton newUserButton;
    private DefaultTableModel tableModel;
    private UserType userType;

    private DeleteButtonEditor deleteButtonEditor;

    /**
     * 
     * @param userType Type of users displayed on page
     */
    public UserListPage(UserType userType) {
        setLayout(new BorderLayout());

        this.userType = userType;

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

        // Determine column names based on user type
        String[] columnNames;
        if (userType == UserType.PATRON) {
            columnNames = new String[] { " ID", "First Name", "Last Name", "Email", "Phone No", "Registration Date",
                    "Credit Card", "Birthday", "Action" };
        } else {
            columnNames = new String[] { " ID", "First Name", "Last Name", "Email", "Phone No", "Role", "Action" };
        }
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Prevent editing of User ID
            }
        };
        userTable = new JTable(tableModel);
        userTable.getColumn("Action").setCellRenderer(new DeleteButtonRenderer());
        deleteButtonEditor = new DeleteButtonEditor(new JCheckBox());
        userTable.getColumn("Action").setCellEditor(deleteButtonEditor);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // New User Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newUserButton = new JButton("+ New User");
        bottomPanel.add(newUserButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public DeleteButtonEditor getDeleteButtonEditor() {
        return deleteButtonEditor;
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

    public class DeleteButtonEditor extends DefaultCellEditor {
        protected JButton deleteButton;
        private String label;

        public DeleteButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            deleteButton = new JButton();
            deleteButton.setOpaque(true);

        }

        public void handleDelete(ActionListener listener) {
            fireEditingStopped();
            for (ActionListener al : deleteButton.getActionListeners()) {
                deleteButton.removeActionListener(al);
            }

            deleteButton.addActionListener(listener);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            if (isSelected) {
                deleteButton.setForeground(table.getSelectionForeground());
                deleteButton.setBackground(table.getSelectionBackground());
            } else {
                deleteButton.setForeground(table.getForeground());
                deleteButton.setBackground(UIManager.getColor("Button.background"));
            }
            label = (value == null) ? "Delete" : value.toString();
            deleteButton.setText(label);
            return deleteButton;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }

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
            if (user.getUserType() == UserType.PATRON) {
                Patron patron = (Patron) user;
                tableModel.addRow(new Object[] {
                        patron.getUserId(),
                        patron.getFirstName(),
                        patron.getLastName(),
                        patron.getEmail(),
                        patron.getPhoneNo(),
                        patron.getRegistrationDate(),
                        patron.getCreditCardNo(),
                        patron.getBirthDate()
                });
            } else {
                Librarian librarian = (Librarian) user;
                tableModel.addRow(new Object[] {
                        librarian.getUserId(),
                        librarian.getFirstName(),
                        librarian.getLastName(),
                        librarian.getEmail(),
                        librarian.getPhoneNo(),
                        librarian.getRole()
                });
            }

        }
    }

    public JTable getTable() {
        return userTable;
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
