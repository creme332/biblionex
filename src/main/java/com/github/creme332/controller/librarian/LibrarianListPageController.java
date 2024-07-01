package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Librarian;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.view.librarian.LibrarianListPage;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibrarianListPageController {
    private AppState app;
    private LibrarianListPage listPage;

    public LibrarianListPageController(AppState app, LibrarianListPage listPage) {
        this.app = app;
        this.listPage = listPage;

        try {
            listPage.populateTable(new ArrayList<>(Librarian.findAll()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initController();
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
}