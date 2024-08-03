package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Librarian;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.view.librarian.UserListPage;

import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibrarianListPageController implements PropertyChangeListener {
    private AppState app;
    private UserListPage listPage;

    public LibrarianListPageController(AppState app, UserListPage listPage) {
        this.app = app;
        this.listPage = listPage;
        initController();

        app.addPropertyChangeListener(this);

        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    listPage.populateTable(new ArrayList<>(Librarian.findAll()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        };
        th.start();
    }

    private void initController() {
        listPage.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));
        listPage.getNewUserButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_REGISTRATION_SCREEN));

        listPage.getSearchButton().addActionListener(e -> searchLibrarians());
        listPage.getSearchField().addActionListener(e -> searchLibrarians());

        listPage.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchLibrarians();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchLibrarians();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchLibrarians();
            }
        });

        listPage.getUserTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column != 0 && e.getType() == TableModelEvent.UPDATE) {
                    updateLibrarianInDatabase(row);
                }
            }
        });

        // handle deletion
        JTable table = listPage.getTable();
        UserListPage.DeleteButtonEditor editor = listPage.getDeleteButtonEditor();
        editor.handleDelete(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                int librarianId = (int) table.getValueAt(selectedRow, 0);
                try {
                    Librarian.delete(librarianId);
                    listPage.getTableModel().removeRow(selectedRow);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void searchLibrarians() {
        String searchText = listPage.getSearchField().getText().trim();
        List<Librarian> matchingLibrarians = new ArrayList<>();

        try {
            if (searchText.isEmpty()) {
                matchingLibrarians = Librarian.findAll();
            } else {
                if (listPage.getByNameRadio().isSelected()) {
                    matchingLibrarians = Librarian.findAll();
                    matchingLibrarians.removeIf(librarian -> !StringUtil.isSimilar(librarian.getFirstName(),
                            searchText)
                            && !StringUtil.isSimilar(librarian.getLastName(), searchText));
                } else {
                    try {
                        Librarian librarian = Librarian.findById(Integer.parseInt(searchText));
                        if (librarian != null) {
                            matchingLibrarians.add(librarian);
                        }
                    } catch (NumberFormatException e) {
                        matchingLibrarians = new ArrayList<>();
                    }
                }
            }

            listPage.populateTable(new ArrayList<>(matchingLibrarians));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateLibrarianInDatabase(int row) {
        Integer userId = (Integer) listPage.getTableModel().getValueAt(row, 0);
        String firstName = (String) listPage.getTableModel().getValueAt(row, 1);
        String lastName = (String) listPage.getTableModel().getValueAt(row, 2);
        String email = (String) listPage.getTableModel().getValueAt(row, 3);
        String phoneNo = (String) listPage.getTableModel().getValueAt(row, 4);

        try {
            Librarian librarian = Librarian.findById(userId);
            if (librarian != null) {
                librarian.setFirstName(firstName);
                librarian.setLastName(lastName);
                librarian.setEmail(email);
                librarian.setPhoneNo(phoneNo);
                Librarian.update(librarian);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        if (propertyName.equals("currentScreen")
                && (Screen) evt.getNewValue() == Screen.LIBRARIAN_LIBRARIAN_LIST_SCREEN) {
            // each time user switches to this page, refresh page
            try {
                listPage.populateTable(new ArrayList<>(Librarian.findAll()));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}