package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;

/**
 * Stores data about the checkout and check-in of a material.
 */
public class Loan {
    private int loanId;

    /**
     * ID of patron who is renting a material copy.
     */
    private int patronId;

    /**
     * Barcode of material copy being rented.
     */
    private int barcode;

    /**
     * ID of librarian who carried out checkout.
     */
    private int checkoutLibrarianId;

    /**
     * ID of librarian who carried out check-in. If not checked in yet, value is
     * negative.
     */
    private int checkinLibrarianId;
    private Date issueDate;
    private Date returnDate;
    private Date dueDate;

    /**
     * Number of times the due date has been updated since creation.
     */
    private int renewalCount;

    /**
     * Maximum number of times a loan can be renewed.
     */
    public static final int RENEWAL_LIMIT = 3;

    /**
     * Initializes all attributes of the loan.
     * 
     * @param loanId
     * @param patronId
     * @param barcode
     * @param checkoutLibrarianId
     * @param checkinLibrarianId
     * @param issueDate
     * @param returnDate
     * @param dueDate
     * @param renewalCount
     */
    public Loan(int loanId, int patronId, int barcode, int checkoutLibrarianId, int checkinLibrarianId,
            Date issueDate, Date returnDate, Date dueDate, int renewalCount) {
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

    /**
     * Constructor for creating a new loan. Loan ID will be set by database.
     * 
     * @param patronId
     * @param barcode
     * @param checkoutLibrarianId
     * @param dueDate
     */
    public Loan(int patronId, int barcode, int checkoutLibrarianId, Date dueDate) {
        this.patronId = patronId;
        this.barcode = barcode;
        this.checkoutLibrarianId = checkoutLibrarianId;
        this.issueDate = new Date();
        this.returnDate = null;
        this.dueDate = dueDate;
        this.renewalCount = 0;
    }

    public boolean isOverdue() {
        return dueDate.after(new Date());
    }

    public float getAmountDue() {
        return isOverdue() ? 100 : 0;
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

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getRenewalCount() {
        return renewalCount;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setRenewalCount(int renewalCount) {
        this.renewalCount = renewalCount;
    }

    public void setCheckinLibrarianId(int checkinLibrarianId) {
        this.checkinLibrarianId = checkinLibrarianId;
    }

    public static Loan findById(int loanId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM loan WHERE loan_id = ?";
        Loan loan = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                loan = new Loan(
                        resultSet.getInt("loan_id"),
                        resultSet.getInt("patron_id"),
                        resultSet.getInt("barcode"),
                        resultSet.getInt("checkout_librarian_id"),
                        resultSet.getInt("checkin_librarian_id"),
                        resultSet.getDate("issue_date"),
                        resultSet.getDate("return_date"),
                        resultSet.getDate("due_date"),
                        resultSet.getInt("renewal_count"));

                // deal with possible null values since getInt() returns 0 for null
                resultSet.getInt("checkin_librarian_id");
                if (resultSet.wasNull()) {
                    loan.setCheckinLibrarianId(-1);
                }

            }
        }
        return loan;
    }

    public static List<Loan> findAll() throws SQLException {
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
                        resultSet.getDate("issue_date"),
                        resultSet.getDate("return_date"),
                        resultSet.getDate("due_date"),
                        resultSet.getInt("renewal_count"));
                loans.add(loan);
            }
        }
        return loans;
    }

    public static void save(Loan loan) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = """
                INSERT INTO loan (patron_id, barcode, checkout_librarian_id, checkin_librarian_id,
                                 issue_date, return_date, due_date, renewal_count)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                 """;
        try (PreparedStatement createLoan = conn.prepareStatement(query)) {
            createLoan.setInt(1, loan.getPatronId());
            createLoan.setInt(2, loan.getBarcode());
            createLoan.setInt(3, loan.getCheckoutLibrarianId());
            createLoan.setInt(4, loan.getCheckinLibrarianId());
            createLoan.setDate(5, new java.sql.Date(loan.getIssueDate().getTime()));
            if (loan.getReturnDate() != null) {
                createLoan.setDate(6, new java.sql.Date(loan.getReturnDate().getTime()));
            } else {
                createLoan.setNull(6, java.sql.Types.DATE);
            }
            createLoan.setDate(7, new java.sql.Date(loan.getDueDate().getTime()));
            createLoan.setInt(8, loan.getRenewalCount());
            createLoan.executeUpdate();
        }
    }

    public static void delete(int loanId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM loan WHERE loan_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Finds all active loans where the return date is null.
     * 
     * @return List of active loans.
     * @throws SQLException if a database access error occurs.
     */
    public static List<Loan> findAllActive() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM loan WHERE return_date IS NULL";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("loan_id"),
                        resultSet.getInt("patron_id"),
                        resultSet.getInt("barcode"),
                        resultSet.getInt("checkout_librarian_id"),
                        resultSet.getInt("checkin_librarian_id"),
                        resultSet.getDate("issue_date"),
                        resultSet.getDate("return_date"),
                        resultSet.getDate("due_date"),
                        resultSet.getInt("renewal_count"));
                loans.add(loan);
            }
        }
        return loans;
    }

    /**
     * Finds an active loan by barcode where the return date is null.
     * 
     * @param barcode The barcode of the loan to find.
     * @return The active loan with the given barcode, or null if not found.
     * @throws SQLException if a database access error occurs.
     */
    public static Loan findActiveLoanByBarcode(int barcode) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM loan WHERE barcode = ? AND return_date IS NULL";
        Loan loan = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, barcode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                loan = new Loan(
                        resultSet.getInt("loan_id"),
                        resultSet.getInt("patron_id"),
                        resultSet.getInt("barcode"),
                        resultSet.getInt("checkout_librarian_id"),
                        resultSet.getInt("checkin_librarian_id"),
                        resultSet.getDate("issue_date"),
                        resultSet.getDate("return_date"),
                        resultSet.getDate("due_date"),
                        resultSet.getInt("renewal_count"));
            }
        }
        return loan;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", patronId=" + patronId +
                ", barcode=" + barcode +
                ", checkoutLibrarianId=" + checkoutLibrarianId +
                ", checkinLibrarianId=" + checkinLibrarianId +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                ", dueDate=" + dueDate +
                ", renewalCount=" + renewalCount +
                '}';
    }
}
