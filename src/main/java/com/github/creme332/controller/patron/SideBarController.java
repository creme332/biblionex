package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.view.patron.SideBar;
import com.github.creme332.controller.*;

public class SideBarController {
    private SideBar sideBar;
    private AppState app;
    private Patron patron;

    public SideBarController(AppState app, SideBar sideBar) {
        this.sideBar = sideBar;
        this.app = app;

        this.sideBar.setPatronDetails(patron.getFirstName(), patron.getLastName());

        this.sideBar.getDashboardButton().addActionListener(e -> {
            app.setCurrentScreen(Screen.PATRON_DASHBOARD_SCREEN);
            sideBar.highlightButton(sideBar.getDashboardButton());
        });

        this.sideBar.getLoansButton().addActionListener(e -> {
            // app.setCurrentScreen(Screen.PATRON_LOANS_SCREEN);
            // sideBar.highlightButton(sideBar.getLoansButton());
        });

        this.sideBar.getCatalogButton().addActionListener(e -> {
            // app.setCurrentScreen(Screen.PATRON_CATALOG_SCREEN);
            // sideBar.highlightButton(sideBar.getCatalogButton());
        });

        this.sideBar.getAccountButton().addActionListener(e -> {
            // app.setCurrentScreen(Screen.PATRON_ACCOUNT_SCREEN);
            // sideBar.highlightButton(sideBar.getAccountButton());
        });

        this.sideBar.getLogOutButton().addActionListener(e -> {
            app.logOut();
        });

        // Highlight the initial active screen button
        highlightInitialScreen(app.getCurrentScreen());
    }

    private void highlightInitialScreen(Screen currentScreen) {
        switch (currentScreen) {
            case PATRON_DASHBOARD_SCREEN:
                sideBar.highlightButton(sideBar.getDashboardButton());
                break;
            // case PATRON_LOANS_SCREEN:
            //     sideBar.highlightButton(sideBar.getLoansButton());
            //     break;
            // case PATRON_CATALOG_SCREEN:
            //     sideBar.highlightButton(sideBar.getCatalogButton());
            //     break;
            // case PATRON_ACCOUNT_SCREEN:
            //     sideBar.highlightButton(sideBar.getAccountButton());
            //     break;
            default:
                // No default action
                break;
        }
    }
}
