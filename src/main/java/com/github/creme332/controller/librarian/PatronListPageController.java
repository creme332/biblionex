package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.view.librarian.PatronListPage;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatronListPageController {
    private AppState app;
    private PatronListPage listPage;

    public PatronListPageController(AppState app, PatronListPage listPage) {
        this.app = app;
        this.listPage = listPage;
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

        listPage.addPropertyChangeListener("deleteUser", evt -> {
            Integer userId = (Integer) evt.getNewValue();
            deletePatron(userId);
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
                    matchingPatrons.removeIf(patron -> !StringUtil.isSimilar(patron.getFirstName(), searchText)
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

        try {
            Patron patron = Patron.findById(userId);
            if (patron != null) {
                patron.setFirstName(firstName);
                patron.setLastName(lastName);
                patron.setEmail(email);
                patron.setPhoneNo(phoneNo);
                Patron.update(patron);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deletePatron(Integer userId) {
        try {
            Patron patron = Patron.findById(userId);
            if (patron != null) {
                Patron.delete(patron.getUserId());
                searchPatrons(); // Refresh the table after deletion
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
