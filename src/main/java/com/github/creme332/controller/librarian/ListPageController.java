package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.view.librarian.ListPage;
import com.github.creme332.utils.ButtonEditor;
import com.github.creme332.utils.ButtonRenderer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListPageController {
    private AppState app;
    private ListPage listPage;

    public ListPageController(AppState app, ListPage listPage) {
        this.app = app;
        this.listPage = listPage;
        initController();
    }

    private void initController() {
        listPage.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));
        listPage.getNewPatronButton().addActionListener(e -> app.setCurrentScreen(Screen.PATRON_REGISTRATION_SCREEN));

        ActionListener searchAction = e -> searchPatrons();
        listPage.getSearchButton().addActionListener(searchAction);
        listPage.getSearchField().addActionListener(searchAction);

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

        listPage.getPatronTable().getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
        listPage.getPatronTable().getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());

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
        String searchText = listPage.getSearchField().getText();
        List<Patron> patrons;
        if (listPage.getByNameRadio().isSelected()) {
            patrons = Patron.findAll();
            patrons.removeIf(patron -> !StringUtil.isSimilar(patron.getFirstName(), searchText)
                    && !StringUtil.isSimilar(patron.getLastName(), searchText));
        } else {
            patrons = Patron.findBy("patron_id", searchText);
        }
        listPage.populateTable(patrons);
    }
}
