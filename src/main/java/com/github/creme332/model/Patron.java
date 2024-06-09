package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;

public class Patron extends User {
    private Date registrationDate;
    private String creditCardNo;
    private Date birthDate;

    public Patron(String email, String password, int userId, String address, String firstName, String lastName,
            String phoneNo) {
        super(email, password, userId, address, firstName, lastName, phoneNo);
        userType = UserType.PATRON;
    }

    public Patron() {
        super();
        userType = UserType.PATRON;
        creditCardNo = "";
    }

    public static void save(Patron librarian) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public static void update(Patron librarian) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public static void delete(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public static Patron findById(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public static Patron findByEmail(String email) {
        final Connection conn = DatabaseConnection.getConnection();

        Patron patron = null;
        String query = "SELECT * FROM patron WHERE email = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                patron = new Patron();
                patron.setUserId(resultSet.getInt("librarian_id"));
                patron.setAddress(resultSet.getString("address"));
                patron.setPassword(resultSet.getString("password"));
                patron.setLastName(resultSet.getString("last_name"));
                patron.setFirstName(resultSet.getString("first_name"));
                patron.setPhoneNo(resultSet.getString("phone_no"));
                patron.setEmail(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patron;
    }

    public static List<Patron> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
}
