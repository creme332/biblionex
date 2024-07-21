package com.github.creme332.controller.patron;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.*;
import com.github.creme332.view.patron.Dashboard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.*;

public class DashboardController implements PropertyChangeListener {
    AppState app;
    Dashboard dashboard;

    /**
     * Currently logged in user.
     */
    Patron patron;

    public DashboardController(AppState app, Dashboard dashboard) {
        this.app = app;
        this.dashboard = dashboard;
        this.patron = null;
        app.addPropertyChangeListener(this);
    }

    /**
     * This method should be called only AFTER user has logged in. Do not this
     * method inside the constructor of DashboardController() because the USER HAS
     * NOT LOGGED IN YET.
     */
    private void refreshDashboard() {
        Thread th = new Thread() {
            @Override
            public void run() {

                // Fetch data from the database and update the view
                try {
                    List<Book> bookRecommendations = Book.findAll().subList(0, 4);

                    // find authors of each book from database
                    for (Book book : bookRecommendations) {
                        book.setAuthors(Book.findAuthors(book));
                    }

                    dashboard.setOverdueLoans(patron.getOverdueLoans().size());
                    dashboard.setTotalFinesPaid(patron.getTotalFinePaid());
                    dashboard.setActiveLoans(patron.getLoans().size());
                    dashboard.setBookRecommendations(bookRecommendations);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        th.start();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        // update logged in user when it changes
        if (propertyName.equals("loggedInUser") && ((User) evt.getNewValue()).getUserType() == UserType.PATRON) {
            patron = (Patron) evt.getNewValue();
        }

        // refresh dashboard each time screen is switched to dashboard
        if (propertyName.equals("currentScreen") && (Screen) evt.getNewValue() == Screen.PATRON_DASHBOARD_SCREEN) {
            refreshDashboard();
        }
    }
}
