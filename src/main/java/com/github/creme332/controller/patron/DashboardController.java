package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.view.patron.Dashboard;

import java.util.List;

public class DashboardController {
    private AppState app;
    private Dashboard dashboard;

    public DashboardController(AppState app, Dashboard dashboard) {
        this.app = app;
        this.dashboard = dashboard;
        this.dashboard.setController(this);
        loadAllPatrons();
    }

    public void loadAllPatrons() {
        List<Patron> patrons = Patron.findAll();
        dashboard.displayPatrons(patrons);
    }

    public void searchPatronById(int id) {
        Patron patron = Patron.findById(id);
        dashboard.displayPatron(patron);
    }

    public void searchPatronByEmail(String email) {
        Patron patron = Patron.findByEmail(email);
        dashboard.displayPatron(patron);
    }

    public void updatePatron(Patron patron) {
        Patron.update(patron);
        loadAllPatrons();
    }

    public void deletePatron(int id) {
        Patron.delete(id);
        loadAllPatrons();
    }
}
