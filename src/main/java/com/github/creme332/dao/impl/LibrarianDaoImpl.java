package com.github.creme332.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.github.creme332.dao.LibrarianDao;
import com.github.creme332.model.Librarian;
import com.github.creme332.utils.DatabaseConnection;

public class LibrarianDaoImpl implements LibrarianDao {
    private Connection conn = DatabaseConnection.getConnection();

    @Override
    public void save(Librarian librarian) {
        String query = "INSERT INTO librarian (address, password, last_name, first_name, phone_no, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, librarian.getAddress());
            preparedStatement.setString(2, librarian.getPassword());
            preparedStatement.setString(3, librarian.getLastName());
            preparedStatement.setString(4, librarian.getFirstName());
            preparedStatement.setString(5, librarian.getPhoneNo());
            preparedStatement.setString(6, librarian.getEmail());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new librarian was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., log the error or rethrow it)
        }
    }

    @Override
    public void update(Librarian book) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Librarian findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public ArrayList<Librarian> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Librarian findByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }

}
