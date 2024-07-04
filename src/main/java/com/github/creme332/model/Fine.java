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
 * Fine paid by a patron after overdue loan.
 */
public class Fine {
    private int patronId;
    private int loanId;
    private Date date;
    private double amount;

    public Fine(int patronId, int loanId, Date date, double amount) {
        this.patronId = patronId;
        this.loanId = loanId;
        this.date = date;
        this.amount = amount;
    }

    public int getPatronId() {
        return patronId;
    }

    public int getLoanId() {
        return loanId;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public static Fine findByLoanId(int loanId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM fine WHERE loan_id = ?";
        Fine fine = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fine = new Fine(
                        resultSet.getInt("patron_id"),
                        resultSet.getInt("loan_id"),
                        resultSet.getDate("date"),
                        resultSet.getDouble("amount"));
            }
        }
        return fine;
    }

    public static List<Fine> findAll() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<Fine> fines = new ArrayList<>();
        String query = "SELECT * FROM fine";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Fine fine = new Fine(
                        resultSet.getInt("patron_id"),
                        resultSet.getInt("loan_id"),
                        resultSet.getDate("date"),
                        resultSet.getDouble("amount"));
                fines.add(fine);
            }
        }
        return fines;
    }

    public static void save(Fine fine) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = """
                INSERT INTO fine (patron_id, loan_id, date, amount)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, fine.getPatronId());
            preparedStatement.setInt(2, fine.getLoanId());
            preparedStatement.setDate(3, new java.sql.Date(fine.getDate().getTime()));
            preparedStatement.setDouble(4, fine.getAmount());
            preparedStatement.executeUpdate();
        }
    }

    public static void deleteByLoanId(int loanId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM fine WHERE loan_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);
            preparedStatement.executeUpdate();
        }
    }

    public static List<LoanFineData> findLoanFineRecordsByPatronId(int patronId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<LoanFineData> records = new ArrayList<>();
        String query = """
                SELECT loan.loan_id, loan.barcode, loan.issue_date, loan.return_date, loan.due_date, fine.amount
                FROM loan
                LEFT JOIN fine ON loan.loan_id = fine.loan_id
                WHERE loan.patron_id = ?
                """;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, patronId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LoanFineData record = new LoanFineData(
                        resultSet.getInt("loan_id"),
                        resultSet.getInt("barcode"),
                        resultSet.getDate("issue_date"),
                        resultSet.getDate("return_date"),
                        resultSet.getDate("due_date"),
                        resultSet.getDouble("amount"));
                records.add(record);
            }
        }
        return records;
    }

    public static class LoanFineData {
        private int loanId;
        private int barcode;
        private Date issueDate;
        private Date returnDate;
        private Date dueDate;
        private double amount;

        public LoanFineData(int loanId, int barcode, Date issueDate, Date returnDate, Date dueDate, double amount) {
            this.loanId = loanId;
            this.barcode = barcode;
            this.issueDate = issueDate;
            this.returnDate = returnDate;
            this.dueDate = dueDate;
            this.amount = amount;
        }

        public int getLoanId() {
            return loanId;
        }

        public int getBarcode() {
            return barcode;
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

        public double getAmount() {
            return amount;
        }
    }
}
