package com.github.creme332.model;

import com.github.creme332.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookAuthor {
    private int materialId;
    private int authorId;

    public BookAuthor(int materialId, int authorId) {
        this.materialId = materialId;
        this.authorId = authorId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Saves a BookAuthor relationship to the database.
     * 
     * @throws SQLException
     */
    public static void save(BookAuthor bookAuthor) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO book_author (material_id, author_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookAuthor.getMaterialId());
            statement.setInt(2, bookAuthor.getAuthorId());
            statement.executeUpdate();
        }
    }

    /**
     * Deletes a BookAuthor relationship from the database.
     * 
     * @throws SQLException
     */
    public static void delete(BookAuthor bookAuthor) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "DELETE FROM book_author WHERE material_id = ? AND author_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookAuthor.getMaterialId());
            statement.setInt(2, bookAuthor.getAuthorId());
            statement.executeUpdate();
        }
    }

    /**
     * Checks if a BookAuthor relationship exists in the database.
     * 
     * @throws SQLException
     */
    public static boolean exists(BookAuthor bookAuthor) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT 1 FROM book_author WHERE material_id = ? AND author_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookAuthor.getMaterialId());
            statement.setInt(2, bookAuthor.getAuthorId());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
