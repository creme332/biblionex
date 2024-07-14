package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.creme332.utils.DatabaseConnection;

public class Author {
    private int authorId;
    private String lastName;
    private String firstName;
    private String email;
    private Set<Book> books = new HashSet<>();

    public Author(int authorId, String lastName, String firstName, String email) {
        this.authorId = authorId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public Author(String lastName, String firstName, String email) {
        this.authorId = -1;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public void addBook(Book book) {
        if (books.add(book)) {
            book.getAuthors().add(this);
        }
    }

    public void removeBook(Book book) {
        if (books.remove(book)) {
            book.getAuthors().remove(this);
        }
    }

    public Set<Book> getBooks() {
        return books;
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

    public static Author findById(int authorId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        Author author = null;
        String query = "SELECT * FROM author WHERE author_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                author = new Author(
                        resultSet.getInt("author_id"),
                        resultSet.getString("last_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("email"));
            }

        }
        return author;
    }

    /**
     * Retrieves all authors from the database.
     * 
     * @return A list of all authors.
     */
    public static List<Author> findAll() throws SQLException {
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
        }
        return authors;
    }

    /**
     * Saves a new author to the database. Author ID is generated by the database.
     * 
     * @param author The author to save.
     */
    public static void save(Author author) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO author (last_name, first_name, email) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, author.getLastName());
            preparedStatement.setString(2, author.getFirstName());
            preparedStatement.setString(3, author.getEmail());
            preparedStatement.executeUpdate();
        }
    }
}
