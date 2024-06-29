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
    public void save() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO book_author (material_id, author_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.materialId);
            statement.setInt(2, this.authorId);
            statement.executeUpdate();
        }
    }

    /**
     * Deletes a BookAuthor relationship from the database.
     * 
     * @throws SQLException
     */
    public void delete() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "DELETE FROM book_author WHERE material_id = ? AND author_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.materialId);
            statement.setInt(2, this.authorId);
            statement.executeUpdate();
        }
    }

    /**
     * Checks if a BookAuthor relationship exists in the database.
     * 
     * @throws SQLException
     */
    public boolean exists() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT 1 FROM book_author WHERE material_id = ? AND author_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.materialId);
            statement.setInt(2, this.authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
