package com.github.creme332.dao;

import com.github.creme332.model.Book;

import java.util.List;
import java.sql.SQLException;


public interface BookDAO {
    void addBook(Book book) throws SQLException;
    Book getBookById(int materialId) throws SQLException;
    List<Book> getAllBooks() throws SQLException;
    void updateBook(Book book) throws SQLException;
    void deleteBook(int materialId) throws SQLException;
}
