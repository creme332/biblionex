package com.github.creme332.model;

import com.github.creme332.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Book extends Material {
    private int pageCount;
    private String isbn;

    public Book(int materialId, int publisherId, String description, String imageUrl, int ageRestriction,
            String title, int pageCount, String isbn) {
        super(materialId, publisherId, description, imageUrl, ageRestriction, MaterialType.BOOK, title);
        this.pageCount = pageCount;
        this.isbn = isbn;
    }

    /**
     * Constructor for creating a new book. Material ID is unknown and will be
     * set by database.
     * 
     * @param publisherId
     * @param description
     * @param imageUrl
     * @param ageRestriction
     * @param type
     * @param title
     * @param pageCount
     * @param isbn
     */
    public Book(int publisherId, String description, String imageUrl, int ageRestriction,
            String title, int pageCount, String isbn) {
        super(publisherId, description, imageUrl, ageRestriction, MaterialType.BOOK, title);
        this.pageCount = pageCount;
        this.isbn = isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public static void save(Book book) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String materialQuery = """
                INSERT INTO material
                 (publisher_id, description, image_url, age_restriction, type, title)
                 VALUES (?, ?, ?, ?, ?, ?, ?)
                 """;

        // start a transaction
        connection.setAutoCommit(false);

        try (PreparedStatement createMaterial = connection.prepareStatement(materialQuery,
                Statement.RETURN_GENERATED_KEYS)) {

            // perform insertion in material table
            createMaterial.setInt(1, book.getPublisherId());
            createMaterial.setString(2, book.getDescription());
            createMaterial.setString(3, book.getImageUrl());
            createMaterial.setInt(4, book.getAgeRestriction());
            createMaterial.setString(5, book.getType().toString());
            createMaterial.setString(6, book.getTitle());

            createMaterial.executeUpdate();

            int affectedRows = createMaterial.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = createMaterial.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setMaterialId((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating material failed, no ID obtained.");
                }
            }

            // perform insertion in book table
            String bookQuery = "INSERT INTO book (material_id, page_count, isbn) VALUES (?, ?, ?)";
            try (PreparedStatement createBook = connection.prepareStatement(bookQuery)) {
                createBook.setInt(1, book.getMaterialId());
                createBook.setInt(2, book.getPageCount());
                createBook.setString(3, book.getIsbn());
                createBook.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }

    }

    public static Book getBookById(int materialId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = """
                SELECT * FROM book
                INNER JOIN material
                ON book.material_id = material.material_id
                WHERE book.material_id = ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, materialId);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Book(
                        resultSet.getInt("material_id"),
                        resultSet.getInt("publisher_id"),
                        resultSet.getString("description"),
                        resultSet.getString("image_url"),
                        resultSet.getInt("age_restriction"),
                        resultSet.getString("title"),
                        resultSet.getInt("page_count"),
                        resultSet.getString("isbn"));
            }
        }

        return null;
    }

    public static List<Book> getAllBooks() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        List<Book> books = new ArrayList<>();
        try {
            String query = "SELECT * FROM book INNER JOIN material ON book.material_id = material.material_id";
            try (Statement stmt = connection.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(query);
                while (resultSet.next()) {
                    books.add(new Book(
                            resultSet.getInt("material_id"),
                            resultSet.getInt("publisher_id"),
                            resultSet.getString("description"),
                            resultSet.getString("image_url"),
                            resultSet.getInt("age_restriction"),
                            resultSet.getString("title"),
                            resultSet.getInt("page_count"),
                            resultSet.getString("isbn")));
                }
            }
        } finally {
            connection.close();
        }
        return books;
    }

    public static void updateBook(Book book) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String materialQuery = """
                    UPDATE material
                        SET publisher_id = ?,
                        description = ?,
                        image_url = ?,
                        age_restriction = ?,
                        type = ?,
                        title = ?
                        WHERE material_id = ?
                    """;
            ;
            try (PreparedStatement pstmt = connection.prepareStatement(materialQuery)) {
                pstmt.setInt(1, book.getPublisherId());
                pstmt.setString(2, book.getDescription());
                pstmt.setString(3, book.getImageUrl());
                pstmt.setInt(4, book.getAgeRestriction());
                pstmt.setString(5, book.getType().toString());
                pstmt.setString(6, book.getTitle());
                pstmt.setInt(7, book.getMaterialId());
                pstmt.executeUpdate();
            }

            String bookQuery = "UPDATE book SET page_count = ?, isbn = ? WHERE material_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(bookQuery)) {
                pstmt.setInt(1, book.getPageCount());
                pstmt.setString(2, book.getIsbn());
                pstmt.setInt(3, book.getMaterialId());
                pstmt.executeUpdate();
            }
        } finally {
            connection.close();
        }
    }

    public static void deleteBook(int materialId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String bookQuery = "DELETE FROM book WHERE material_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(bookQuery)) {
                pstmt.setInt(1, materialId);
                pstmt.executeUpdate();
            }

            String materialQuery = "DELETE FROM material WHERE material_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(materialQuery)) {
                pstmt.setInt(1, materialId);
                pstmt.executeUpdate();
            }
        } finally {
            connection.close();
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "pageCount=" + pageCount +
                ", isbn='" + isbn + '\'' +
                ", materialId=" + getMaterialId() +
                ", publisherId=" + getPublisherId() +
                ", description='" + getDescription() + '\'' +
                ", imageUrl='" + getImageUrl() + '\'' +
                ", ageRestriction=" + getAgeRestriction() +
                ", type='" + getType() + '\'' +
                ", title='" + getTitle() + '\'' +
                '}';
    }
}
