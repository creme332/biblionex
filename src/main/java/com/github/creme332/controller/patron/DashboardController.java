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
     * This method should be called only AFTER user has logged in. Inside
     * constructor of DashboardController(), USER HAS NOT LOGGED IN YET.
     */
    private void refreshDashboard() {
        // Fetch data from the database and update the view
        try {
            int patronId = patron.getUserId();
            int pendingFines = calculatePendingFines(patronId);
            int totalFinesPaid = calculateTotalFinesPaid(patronId);
            int activeLoans = calculateActiveLoans(patronId);

            List<Book> bookRecommendations = fetchBookRecommendations();
            List<Author> authors = fetchAuthors(bookRecommendations);
            List<Publisher> publishers = fetchPublishers(bookRecommendations);

            dashboard.setPendingFines(String.valueOf(pendingFines));
            dashboard.setTotalFinesPaid(String.valueOf(totalFinesPaid));
            dashboard.setActiveLoans(String.valueOf(activeLoans));
            dashboard.setBookRecommendations(bookRecommendations, authors, publishers);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int calculatePendingFines(int patronId) {
        // Calculate the sum of unpaid fines for the patron
        List<Fine> fines = Fine.findBy("patron_id", String.valueOf(patronId));
        return fines.stream()
                .filter(fine -> fine.getDate() == null) // Assuming fines with null date are unpaid
                .mapToInt(fine -> (int) fine.getAmount())
                .sum();
    }

    private int calculateTotalFinesPaid(int patronId) {
        // Calculate the sum of all fines paid by the patron
        List<Fine> fines = Fine.findBy("patron_id", String.valueOf(patronId));
        return fines.stream()
                .filter(fine -> fine.getDate() != null) // Assuming fines with non-null date are paid
                .mapToInt(fine -> (int) fine.getAmount())
                .sum();
    }

    private int calculateActiveLoans(int patronId) throws SQLException {
        // Count the number of active loans for the patron
        List<Loan> loans = Loan.findAll();
        return (int) loans.stream()
                .filter(loan -> loan.getPatronId() == patronId && loan.getReturnDate() == null)
                .count();
    }

    private List<Book> fetchBookRecommendations() throws SQLException {
        return Book.findAll();
    }

    private List<Author> fetchAuthors(List<Book> books) throws SQLException {
        List<Author> authors = new ArrayList<>();
        for (Book book : books) {
            authors.addAll(Book.findAuthorsByBookId(book.getMaterialId()));
        }
        return authors;
    }

    private List<Publisher> fetchPublishers(List<Book> books) throws SQLException {
        List<Publisher> publishers = new ArrayList<>();
        for (Book book : books) {
            Publisher publisher = Publisher.findById(book.getPublisherId());
            if (publisher != null) {
                publishers.add(publisher);
            }
        }
        return publishers;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("loggedInUser") && ((User) evt.getNewValue()).getUserType() == UserType.PATRON) {
            patron = (Patron) evt.getNewValue();
            refreshDashboard();
        }
    }
}
