package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
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
            // app.setCurrentScreen(Screen.PATRON_CATALOG_SCREEN);
            // sideBar.highlightButton(sideBar.getCatalogButton());
        });

        this.sidebar.getAccountButton().addActionListener(e -> {
            // app.setCurrentScreen(Screen.PATRON_ACCOUNT_SCREEN);
            // sideBar.highlightButton(sideBar.getAccountButton());
        });

        this.sidebar.getLogOutButton().addActionListener(e -> {
            app.logOut();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("loggedInUser")) {
            // user just logged in
            patron = (Patron) evt.getNewValue();
            this.sidebar.setPatronDetails(patron.getFirstName(), patron.getLastName());
        }

        if (propertyName.equals("currentScreen")) {
            Screen newScreen = (Screen) evt.getNewValue();
            if (newScreen == Screen.PATRON_DASHBOARD_SCREEN) {
                sidebar.highlightButton(sidebar.getDashboardButton());
            }
            // TODO : add more screens based on sidebar buttons
        }
    }
}
