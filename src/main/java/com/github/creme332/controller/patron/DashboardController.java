package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.view.patron.Dashboard;

public class DashboardController {
    AppState app;
    Dashboard dashboard;

    public DashboardController(AppState app, Dashboard dashboard) {
        this.app = app;
        this.dashboard = dashboard;
    }
}
