package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Fine;
import com.github.creme332.model.Loan;
import com.github.creme332.model.Book;
import com.github.creme332.model.Author;
import com.github.creme332.model.Publisher;
import com.github.creme332.view.patron.Dashboard;

import java.sql.SQLException;
import java.util.*;

public class DashboardController {
    AppState app;
    Dashboard dashboard;

    public DashboardController(AppState app, Dashboard dashboard) {
        this.app = app;
        this.dashboard = dashboard;

        // Fetch data from the database and update the view
        try {
            int patronId = app.getLoggedInUser().getUserId();
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

    private int calculatePendingFines(int patronId) throws SQLException {
        // Calculate the sum of unpaid fines for the patron
        List<Fine> fines = Fine.findBy("patron_id", String.valueOf(patronId));
        return fines.stream()
                .filter(fine -> fine.getDate() == null) // Assuming fines with null date are unpaid
                .mapToInt(fine -> (int) fine.getAmount())
                .sum();
    }

    private int calculateTotalFinesPaid(int patronId) throws SQLException {
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
            authors.add(Author.findById(book.getAuthorId()));
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
}
