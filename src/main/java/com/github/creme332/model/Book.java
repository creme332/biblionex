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
            MaterialType type, String title, int pageCount, String isbn) {
        super(materialId, publisherId, description, imageUrl, ageRestriction, type, title);
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
        try {
            String materialQuery = "INSERT INTO material (material_id, publisher_id, description, image_url, age_restriction, type, title) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(materialQuery)) {
                pstmt.setInt(1, book.getMaterialId());
                pstmt.setInt(2, book.getPublisherId());
                pstmt.setString(3, book.getDescription());
                pstmt.setString(4, book.getImageUrl());
                pstmt.setInt(5, book.getAgeRestriction());
                pstmt.setString(6, book.getType().toString());
                pstmt.setString(7, book.getTitle());
                pstmt.executeUpdate();
            }

            String bookQuery = "INSERT INTO book (material_id, page_count, isbn) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(bookQuery)) {
                pstmt.setInt(1, book.getMaterialId());
                pstmt.setInt(2, book.getPageCount());
                pstmt.setString(3, book.getIsbn());
                pstmt.executeUpdate();
            }
        } finally {
            connection.close();
        }
    }

    public static Book findById(int materialId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM book INNER JOIN material ON book.material_id = material.material_id WHERE book.material_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, materialId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new Book(
                            rs.getInt("material_id"),
                            rs.getInt("publisher_id"),
                            rs.getString("description"),
                            rs.getString("image_url"),
                            rs.getInt("age_restriction"),
                            MaterialType.valueOf(rs.getString("type")),
                            rs.getString("title"),
                            rs.getInt("page_count"),
                            rs.getString("isbn"));
                }
            }
        } finally {
            connection.close();
        }
        return null;
    }

    public static List<Book> findAll() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        List<Book> books = new ArrayList<>();
        try {
            String query = "SELECT * FROM book INNER JOIN material ON book.material_id = material.material_id";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("material_id"),
                            rs.getInt("publisher_id"),
                            rs.getString("description"),
                            rs.getString("image_url"),
                            rs.getInt("age_restriction"),
                            MaterialType.valueOf(rs.getString("type")),
                            rs.getString("title"),
                            rs.getInt("page_count"),
                            rs.getString("isbn")));
                }
            }
        } finally {
            connection.close();
        }
        return books;
    }

    public static void update(Book book) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String materialQuery = "UPDATE material SET publisher_id = ?, description = ?, image_url = ?, age_restriction = ?, type = ?, title = ? WHERE material_id = ?";
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

    public static void delete(int materialId) throws SQLException {
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
