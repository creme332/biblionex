package com.github.creme332.controller.patron;

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
        // Fetch data from the database and update the view
        try {
            int patronId = patron.getUserId();
            int pendingFines = patron.getDueFine();
            int totalFinesPaid = patron.getTotalFinePaid();
            int activeLoans = calculateActiveLoans(patronId);

            List<Book> bookRecommendations = fetchBookRecommendations();

            // find authors of each book from database
            for (Book book : bookRecommendations) {
                book.setAuthors(Book.findAuthors(book));
            }

            dashboard.setPendingFines(pendingFines);
            dashboard.setTotalFinesPaid(totalFinesPaid);
            dashboard.setActiveLoans(activeLoans);
            dashboard.setBookRecommendations(bookRecommendations);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int calculateActiveLoans(int patronId) throws SQLException {
        // Count the number of active loans for the patron
        List<Loan> loans = Loan.findAll();
        return (int) loans.stream()
                .filter(loan -> loan.getPatronId() == patronId && loan.getReturnDate() == null)
                .count();
    }

    private List<Book> fetchBookRecommendations() throws SQLException {
        return Book.findAll().subList(0, 4);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("loggedInUser") && ((User) evt.getNewValue()).getUserType() == UserType.PATRON) {
            patron = (Patron) evt.getNewValue();

            Thread th = new Thread() {
                @Override
                public void run() {
                    refreshDashboard();
                }
            };
            th.start();
        }
    }
}
