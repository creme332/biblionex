package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.view.librarian.ListPage;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListPageController {
    private AppState app;
    private ListPage listPage;

    public ListPageController(AppState app, ListPage listPage) {
        this.app = app;
        this.listPage = listPage;

        try {
            listPage.populateTable(Patron.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initController();
    }

    private void initController() {
        listPage.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));
        listPage.getNewPatronButton().addActionListener(e -> app.setCurrentScreen(Screen.PATRON_REGISTRATION_SCREEN));

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

        listPage.getPatronTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column != 0 && e.getType() == TableModelEvent.UPDATE) { // Exclude "Patron ID" from being editable
                    listPage.updatePatronInDatabase(row);
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
                        if (patron != null)
                            matchingPatrons.add(patron);
                    } catch (NumberFormatException e) {
                        // entered data is not an integer
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        listPage.populateTable(matchingPatrons);
    }
}
