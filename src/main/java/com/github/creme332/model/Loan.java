package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;

public class Loan {
    private int loanId;
    private int patronId;
    private int barcode;
    private int checkoutLibrarianId;
    private int checkinLibrarianId;
    private String issueDate;
    private String returnDate;
    private String dueDate;
    private int renewalCount;

    public Loan(int loanId, int patronId, int barcode, int checkoutLibrarianId, int checkinLibrarianId, String issueDate, String returnDate, String dueDate, int renewalCount) {
        this.loanId = loanId;
        this.patronId = patronId;
        this.barcode = barcode;
        this.checkoutLibrarianId = checkoutLibrarianId;
        this.checkinLibrarianId = checkinLibrarianId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.renewalCount = renewalCount;
    }

    public int getLoanId() {
        return loanId;
    }

    public int getPatronId() {
        return patronId;
    }

    public int getBarcode() {
        return barcode;
    }

    public int getCheckoutLibrarianId() {
        return checkoutLibrarianId;
    }

    public int getCheckinLibrarianId() {
        return checkinLibrarianId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getRenewalCount() {
        return renewalCount;
    }

    public static List<Loan> findBy(String column, String value) {
        final Connection conn = DatabaseConnection.getConnection();
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM loan WHERE " + column + " = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("loan_id"),
                        resultSet.getInt("patron_id"),
                        resultSet.getInt("barcode"),
                        resultSet.getInt("checkout_librarian_id"),
                        resultSet.getInt("checkin_librarian_id"),
                        resultSet.getString("issue_date"),
                        resultSet.getString("return_date"),
                        resultSet.getString("due_date"),
                        resultSet.getInt("renewal_count")
                );
                loans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    public static List<Loan> findAll() {
        final Connection conn = DatabaseConnection.getConnection();
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM loan";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("loan_id"),
                        resultSet.getInt("patron_id"),
                        resultSet.getInt("barcode"),
                        resultSet.getInt("checkout_librarian_id"),
                        resultSet.getInt("checkin_librarian_id"),
                        resultSet.getString("issue_date"),
                        resultSet.getString("return_date"),
                        resultSet.getString("due_date"),
                        resultSet.getInt("renewal_count")
                );
                loans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    public static void save(Loan loan) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO loan (patron_id, barcode, checkout_librarian_id, checkin_librarian_id, issue_date, return_date, due_date, renewal_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, loan.getPatronId());
            preparedStatement.setInt(2, loan.getBarcode());
            preparedStatement.setInt(3, loan.getCheckoutLibrarianId());
            preparedStatement.setInt(4, loan.getCheckinLibrarianId());
            preparedStatement.setString(5, loan.getIssueDate());
            preparedStatement.setString(6, loan.getReturnDate());
            preparedStatement.setString(7, loan.getDueDate());
            preparedStatement.setInt(8, loan.getRenewalCount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Loan loan) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE loan SET patron_id = ?, barcode = ?, checkout_librarian_id = ?, checkin_librarian_id = ?, issue_date = ?, return_date = ?, due_date = ?, renewal_count = ? WHERE loan_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, loan.getPatronId());
            preparedStatement.setInt(2, loan.getBarcode());
            preparedStatement.setInt(3, loan.getCheckoutLibrarianId());
            preparedStatement.setInt(4, loan.getCheckinLibrarianId());
            preparedStatement.setString(5, loan.getIssueDate());
            preparedStatement.setString(6, loan.getReturnDate());
            preparedStatement.setString(7, loan.getDueDate());
            preparedStatement.setInt(8, loan.getRenewalCount());
            preparedStatement.setInt(9, loan.getLoanId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int loanId) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM loan WHERE loan_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
