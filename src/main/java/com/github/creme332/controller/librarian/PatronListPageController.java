package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.view.librarian.UserListPage;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PatronListPageController implements PropertyChangeListener {
    private AppState app;
    private UserListPage listPage;

    public PatronListPageController(AppState app, UserListPage listPage) {
        this.app = app;
        this.listPage = listPage;

        app.addPropertyChangeListener(this);

        initController();

        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    listPage.populateTable(new ArrayList<>(Patron.findAll()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        };
        th.start();
    }

    private void initController() {
        listPage.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));
        listPage.getNewUserButton().addActionListener(e -> app.setCurrentScreen(Screen.PATRON_REGISTRATION_SCREEN));

        listPage.getSearchButton().addActionListener(e -> searchPatrons());
        listPage.getSearchField().addActionListener(e -> searchPatrons());

        listPage.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchPatrons();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchPatrons();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchPatrons();
            }
        });

        listPage.getUserTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column != 0 && e.getType() == TableModelEvent.UPDATE) {
                    updatePatronInDatabase(row);
                }
            }
        });

        // handle deletion
        JTable table = listPage.getUserTable();
        UserListPage.DeleteButtonEditor editor = listPage.getDeleteButtonEditor();
        editor.handleDelete(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                int patronId = (int) table.getValueAt(selectedRow, 0);
                int response = JOptionPane.showConfirmDialog(null,
                        String.format("Are you sure you want to delete patron with ID %d?", patronId),
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    try {
                        Patron.delete(patronId);
                        listPage.getTableModel().removeRow(selectedRow);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void searchPatrons() {
        String searchText = listPage.getSearchField().getText().trim();
        List<Patron> matchingPatrons = new ArrayList<>();

        try {
            if (searchText.isEmpty()) {
                matchingPatrons = Patron.findAll();
            } else {
                if (listPage.getByNameRadio().isSelected()) {
                    matchingPatrons = Patron.findAll();
                    matchingPatrons.removeIf(patron -> !StringUtil.isSimilar(patron.getFirstName(),
                            searchText)
                            && !StringUtil.isSimilar(patron.getLastName(), searchText));
                } else {
                    try {
                        Patron patron = Patron.findById(Integer.parseInt(searchText));
                        if (patron != null) {
                            matchingPatrons.add(patron);
                        }
                    } catch (NumberFormatException e) {
                        matchingPatrons = new ArrayList<>();
                    }
                }
            }

            listPage.populateTable(new ArrayList<>(matchingPatrons));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePatronInDatabase(int row) {
        Integer userId = (Integer) listPage.getTableModel().getValueAt(row, 0);
        String firstName = (String) listPage.getTableModel().getValueAt(row, 1);
        String lastName = (String) listPage.getTableModel().getValueAt(row, 2);
        String email = (String) listPage.getTableModel().getValueAt(row, 3);
        String phoneNo = (String) listPage.getTableModel().getValueAt(row, 4);
        String address = (String) listPage.getTableModel().getValueAt(row, 5);
        String registerDateAsString = (String) listPage.getTableModel().getValueAt(row, 6);
        String creditCardNo = (String) listPage.getTableModel().getValueAt(row, 7);
        String birthDateAsString = (String) listPage.getTableModel().getValueAt(row, 8);

        Date birthDate = stringToDate(birthDateAsString);
        Date registrationDate = stringToDate(registerDateAsString);

        if (birthDate == null) {
            JOptionPane.showMessageDialog(null, "Invalid birth date: " + birthDateAsString, "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (registrationDate == null) {
            JOptionPane.showMessageDialog(null, "Invalid registration date: " + registerDateAsString, "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean missingPatron = true;
        try {
            Patron patron = Patron.findById(userId);
            if (patron != null) {
                missingPatron = false;
                patron.setFirstName(firstName);
                patron.setLastName(lastName);
                patron.setEmail(email);
                patron.setPhoneNo(phoneNo);
                patron.setAddress(address);
                patron.setRegistrationDate(registrationDate);
                patron.setCreditCardNo(creditCardNo);
                patron.setBirthDate(birthDate);
                Patron.update(patron);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (missingPatron) {
            JOptionPane.showMessageDialog(null, "Patron no longer exists in database", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Converts a string to Date.
     * 
     * @param dateStr Date following format: yyyy-MM-dd
     * @return Null if conversion is not possible
     */
    private Date stringToDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        if (dateStr != null && dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            try {
                return new Date(dateFormat.parse(dateStr).getTime());
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        if (propertyName.equals("currentScreen") && (Screen) evt.getNewValue() == Screen.LIBRARIAN_PATRON_LIST_SCREEN) {
            // each time user switches to this page, refresh page
            try {
                listPage.populateTable(new ArrayList<>(Patron.findAll()));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
