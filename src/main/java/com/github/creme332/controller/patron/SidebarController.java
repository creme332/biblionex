package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.model.User;
import com.github.creme332.model.UserType;
import com.github.creme332.view.patron.Sidebar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.github.creme332.controller.*;

public class SidebarController implements PropertyChangeListener {
    private Sidebar sidebar;
    private AppState app;
    private Patron patron;

    public SidebarController(AppState app, Sidebar sidebar) {
        this.sidebar = sidebar;
        this.app = app;
        app.addPropertyChangeListener(this);

        this.sidebar.getDashboardButton().addActionListener(e -> {
            app.setCurrentScreen(Screen.PATRON_DASHBOARD_SCREEN);
            sidebar.highlightButton(sidebar.getDashboardButton());
        });

        this.sidebar.getLoansButton().addActionListener(e -> {
            app.setCurrentScreen(Screen.PATRON_LOAN_SCREEN);
            sidebar.highlightButton(sidebar.getLoansButton());
        });

        this.sidebar.getCatalogButton().addActionListener(e -> {
            app.setCurrentScreen(Screen.PATRON_CATALOG_SCREEN);
            sidebar.highlightButton(sidebar.getCatalogButton());
        });

        this.sidebar.getAccountButton().addActionListener(e -> {
            app.setCurrentScreen(Screen.PATRON_ACCOUNT_SCREEN);
            sidebar.highlightButton(sidebar.getAccountButton());
        });

        this.sidebar.getLogOutButton().addActionListener(e -> {
            app.logOut();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("loggedInUser") && ((User) evt.getNewValue()).getUserType() == UserType.PATRON) {
            // patron just logged in
            patron = (Patron) evt.getNewValue();
            this.sidebar.setPatronDetails(patron.getFirstName(), patron.getLastName());
        }

        // when user first logs in wer want to update the highlighted button
        if (propertyName.equals("currentScreen")) {
            Screen newScreen = (Screen) evt.getNewValue();
            if (newScreen == Screen.PATRON_DASHBOARD_SCREEN) {
                sidebar.highlightButton(sidebar.getDashboardButton());
            }
            if (newScreen == Screen.PATRON_LOAN_SCREEN) {
                sidebar.highlightButton(sidebar.getLoansButton());
            }
            if (newScreen == Screen.PATRON_CATALOG_SCREEN) {
                sidebar.highlightButton(sidebar.getCatalogButton());
            }
            if (newScreen == Screen.PATRON_ACCOUNT_SCREEN) {
                sidebar.highlightButton(sidebar.getAccountButton());
            }
        }
    }
}
