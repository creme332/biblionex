package com.github.creme332.controller.patron;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.view.Frame;
import com.github.creme332.view.patron.Account;
import com.github.creme332.view.patron.Catalog;
import com.github.creme332.view.patron.Dashboard;
import com.github.creme332.view.patron.Loan;

/**
 * Instantiates all controllers for pages accessible by patron only after log
 * in.
 */
public class Controller {
    public Controller(AppState app, Frame frame) {

        // controller for patron sidebar
        new SidebarController(app, frame.getSidebar());

        // initialize controller for patron dashboard
        new DashboardController(app, (Dashboard) frame.getPage(Screen.PATRON_DASHBOARD_SCREEN));

        // controller for page listing all materials
        new CatalogController(app, (Catalog) frame.getPage(Screen.PATRON_CATALOG_SCREEN));

        // controller for account page
        new AccountController(app, (Account) frame.getPage(Screen.PATRON_ACCOUNT_SCREEN));

        // controller for loan page
        new LoanController(app, (Loan) frame.getPage(Screen.PATRON_LOAN_SCREEN));
    }
}
