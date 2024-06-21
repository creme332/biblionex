package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;
import com.github.creme332.utils.PasswordAuthentication;

public class Patron extends User {
    private Date registrationDate;
    private String creditCardNo;
    private Date birthDate;

    public Patron(String email, String password, int userId, String address, String firstName, String lastName,
            String phoneNo, String creditCardNo, Date birthDate) {
        super(email, password, userId, address, firstName, lastName, phoneNo);
        userType = UserType.PATRON;
        this.creditCardNo = creditCardNo;
        this.birthDate = birthDate;
    }

    public Patron(String email, String password, String address, String firstName, String lastName,
            String phoneNo, String creditCardNo, Date birthDate) {
        super(email, password, address, firstName, lastName, phoneNo);
        userType = UserType.PATRON;
        this.creditCardNo = creditCardNo;
        this.birthDate = birthDate;
    }

    public Patron() {
        super();
        userType = UserType.PATRON;
        creditCardNo = "";
    }

    public static void save(Patron patron) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO patron (address, password, last_name, first_name, phone_no, email, credit_card_no) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        String hashedPassword = passwordAuthentication.hash(patron.getPassword().toCharArray());

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, patron.getAddress());
            preparedStatement.setString(2, hashedPassword); // Save hashed password
            preparedStatement.setString(3, patron.getLastName());
            preparedStatement.setString(4, patron.getFirstName());
            preparedStatement.setString(5, patron.getPhoneNo());
            preparedStatement.setString(6, patron.getEmail());
            preparedStatement.setString(7, patron.getCreditCardNo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Patron patron) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE patron SET address = ?, password = ?, last_name = ?, first_name = ?, phone_no = ?, email = ? WHERE patron_id = ?";

        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        String hashedPassword = passwordAuthentication.hash(patron.getPassword().toCharArray());

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, patron.getAddress());
            preparedStatement.setString(2, hashedPassword); // Save hashed password
            preparedStatement.setString(3, patron.getLastName());
            preparedStatement.setString(4, patron.getFirstName());
            preparedStatement.setString(5, patron.getPhoneNo());
            preparedStatement.setString(6, patron.getEmail());
            preparedStatement.setInt(7, patron.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int id) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM patron WHERE patron_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds patrons by the specified column and value.
     * 
     * @param column The column name to search by.
     * @param value  The value to search for in the specified column.
     * @return A list of patrons matching the search criteria.
     */
    public static List<Patron> findBy(String column, String value) {
        final Connection conn = DatabaseConnection.getConnection();
        List<Patron> patrons = new ArrayList<>();
        String query = "SELECT * FROM patron WHERE " + column + " = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Patron patron = new Patron();
                patron.setUserId(resultSet.getInt("patron_id"));
                patron.setAddress(resultSet.getString("address"));
                patron.setPassword(resultSet.getString("password"));
                patron.setLastName(resultSet.getString("last_name"));
                patron.setFirstName(resultSet.getString("first_name"));
                patron.setPhoneNo(resultSet.getString("phone_no"));
                patron.setEmail(resultSet.getString("email"));
                patrons.add(patron);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patrons;
    }

    public static List<Patron> findAll() {
        final Connection conn = DatabaseConnection.getConnection();
        List<Patron> patrons = new ArrayList<>();
        String query = "SELECT * FROM patron";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Patron patron = new Patron();
                patron.setUserId(resultSet.getInt("patron_id"));
                patron.setAddress(resultSet.getString("address"));
                patron.setPassword(resultSet.getString("password"));
                patron.setLastName(resultSet.getString("last_name"));
                patron.setFirstName(resultSet.getString("first_name"));
                patron.setPhoneNo(resultSet.getString("phone_no"));
                patron.setEmail(resultSet.getString("email"));
                patrons.add(patron);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patrons;
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
                patron.setUserId(resultSet.getInt("patron_id"));
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

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }
}
