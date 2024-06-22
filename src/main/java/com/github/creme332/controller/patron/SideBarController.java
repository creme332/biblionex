package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.view.patron.SideBar;
import com.github.creme332.controller.*;;

public class SideBarController {
    private SideBar sideBar;
    private AppState app;

    public SideBarController(AppState app, SideBar sideBar) {
        this.sideBar = sideBar;
        this.app = app;

        this.sideBar.getDashboardButton().addActionListener(e -> {
            app.setCurrentScreen(Screen.PATRON_DASHBOARD_SCREEN);
            sideBar.highlightButton(sideBar.getDashboardButton());
        });

        this.sideBar.getLoansButton().addActionListener(e -> {
            // app.setCurrentScreen(Screen.LOANS);
            // sideBar.highlightButton(sideBar.getLoansButton());
        });

        this.sideBar.getCatalogButton().addActionListener(e -> {
            // app.setCurrentScreen(Screen.CATALOG);
            // sideBar.highlightButton(sideBar.getCatalogButton());
        });

        this.sideBar.getAccountButton().addActionListener(e -> {
            // app.setCurrentScreen(Screen.ACCOUNT);
            // sideBar.highlightButton(sideBar.getAccountButton());
        });

        this.sideBar.getLogOutButton().addActionListener(e -> {
            app.logOut();
        });
    }
}
