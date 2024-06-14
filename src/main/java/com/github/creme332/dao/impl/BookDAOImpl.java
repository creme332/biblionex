package com.github.creme332.dao.impl;

import com.github.creme332.dao.BookDAO;
import com.github.creme332.model.Book;
import com.github.creme332.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    private Connection connection;

    public BookDAOImpl() {
        connection = DatabaseConnection.getConnection();
    }

    @Override
    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO book (material_id, page_count, isbn) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, book.getMaterialId());
            pstmt.setInt(2, book.getPageCount());
            pstmt.setString(3, book.getIsbn());
            pstmt.executeUpdate();
        }

        String materialQuery = "INSERT INTO material (material_id, publisher_id, description, image_url, age_restriction, type, title) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(materialQuery)) {
            pstmt.setInt(1, book.getMaterialId());
            pstmt.setInt(2, book.getPublisherId());
            pstmt.setString(3, book.getDescription());
            pstmt.setString(4, book.getImageUrl());
            pstmt.setInt(5, book.getAgeRestriction());
            pstmt.setString(6, book.getType());
            pstmt.setString(7, book.getTitle());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Book getBookById(int materialId) throws SQLException {
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
                        rs.getString("type"),
                        rs.getString("title"),
                        rs.getInt("page_count"),
                        rs.getString("isbn")
                );
            }
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
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
                        rs.getString("type"),
                        rs.getString("title"),
                        rs.getInt("page_count"),
                        rs.getString("isbn")
                ));
            }
        }
        return books;
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        String query = "UPDATE book SET page_count = ?, isbn = ? WHERE material_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, book.getPageCount());
            pstmt.setString(2, book.getIsbn());
            pstmt.setInt(3, book.getMaterialId());
            pstmt.executeUpdate();
        }

        String materialQuery = "UPDATE material SET publisher_id = ?, description = ?, image_url = ?, age_restriction = ?, type = ?, title = ? WHERE material_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(materialQuery)) {
            pstmt.setInt(1, book.getPublisherId());
            pstmt.setString(2, book.getDescription());
            pstmt.setString(3, book.getImageUrl());
            pstmt.setInt(4, book.getAgeRestriction());
            pstmt.setString(5, book.getType());
            pstmt.setString(6, book.getTitle());
            pstmt.setInt(7, book.getMaterialId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteBook(int materialId) throws SQLException {
        String query = "DELETE FROM book WHERE material_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, materialId);
            pstmt.executeUpdate();
        }

        String materialQuery = "DELETE FROM material WHERE material_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(materialQuery)) {
            pstmt.setInt(1, materialId);
            pstmt.executeUpdate();
        }
    }
}
