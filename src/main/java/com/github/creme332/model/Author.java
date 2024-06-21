package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;

public class Author {
    private int authorId;
    private String lastName;
    private String firstName;
    private String email;

    public Author(int authorId, String lastName, String firstName, String email) {
        this.authorId = authorId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Finds authors by the specified column and value.
     * 
     * @param column The column name to search by.
     * @param value  The value to search for in the specified column.
     * @return A list of authors matching the search criteria.
     */
    public static List<Author> findBy(String column, String value) {
        final Connection conn = DatabaseConnection.getConnection();
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM author WHERE " + column + " = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Author author = new Author(
                        resultSet.getInt("author_id"),
                        resultSet.getString("last_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("email"));
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    /**
     * Retrieves all authors from the database.
     * 
     * @return A list of all authors.
     */
    public static List<Author> findAll() {
        final Connection conn = DatabaseConnection.getConnection();
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM author";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Author author = new Author(
                        resultSet.getInt("author_id"),
                        resultSet.getString("last_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("email"));
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    /**
     * Saves a new author to the database.
     * 
     * @param author The author to save.
     */
    public static void save(Author author) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO author (last_name, first_name, email) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, author.getLastName());
            preparedStatement.setString(2, author.getFirstName());
            preparedStatement.setString(3, author.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
