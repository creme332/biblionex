package com.github.creme332.controller.patron;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.view.Frame;
import com.github.creme332.view.patron.Catalog;
import com.github.creme332.view.patron.Dashboard;

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
    }
}
