package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.view.librarian.ListPage;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

        listPage.getPatronTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listPage.getPatronTable().getSelectedRow() != -1) {
                int selectedRow = listPage.getPatronTable().getSelectedRow();
                int patronId = (int) listPage.getTableModel().getValueAt(selectedRow, 0);
                listPage.getDeleteButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Patron.delete(patronId);
                    }
                });
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
    }
}
