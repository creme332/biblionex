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
}
