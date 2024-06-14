package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public static void save(Patron patron) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO patron (address, password, last_name, first_name, phone_no, email, registration_date, birth_date, credit_card_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, patron.getAddress());
            preparedStatement.setString(2, patron.getPassword());
            preparedStatement.setString(3, patron.getLastName());
            preparedStatement.setString(4, patron.getFirstName());
            preparedStatement.setString(5, patron.getPhoneNo());
            preparedStatement.setString(6, patron.getEmail());
            preparedStatement.setDate(7, new java.sql.Date(patron.getRegistrationDate().getTime()));
            preparedStatement.setDate(8, new java.sql.Date(patron.getBirthDate().getTime()));
            preparedStatement.setString(9, patron.getCreditCardNo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Patron patron) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE patron SET address = ?, password = ?, last_name = ?, first_name = ?, phone_no = ?, email = ?, birth_date = ?, credit_card_no = ? WHERE patron_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, patron.getAddress());
            preparedStatement.setString(2, patron.getPassword());
            preparedStatement.setString(3, patron.getLastName());
            preparedStatement.setString(4, patron.getFirstName());
            preparedStatement.setString(5, patron.getPhoneNo());
            preparedStatement.setString(6, patron.getEmail());
            preparedStatement.setDate(7, new java.sql.Date(patron.getBirthDate().getTime()));
            preparedStatement.setString(8, patron.getCreditCardNo());
            preparedStatement.setInt(9, patron.getUserId());
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

    public static Patron findById(int id) {
        final Connection conn = DatabaseConnection.getConnection();
        Patron patron = null;
        String query = "SELECT * FROM patron WHERE patron_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                patron = new Patron();
                patron.setUserId(resultSet.getInt("patron_id"));
                patron.setAddress(resultSet.getString("address"));
                patron.setPassword(resultSet.getString("password"));
                patron.setLastName(resultSet.getString("last_name"));
                patron.setFirstName(resultSet.getString("first_name"));
                patron.setPhoneNo(resultSet.getString("phone_no"));
                patron.setEmail(resultSet.getString("email"));
                patron.setRegistrationDate(resultSet.getDate("registration_date"));
                patron.setBirthDate(resultSet.getDate("birth_date"));
                patron.setCreditCardNo(resultSet.getString("credit_card_no"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patron;
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
                patron.setRegistrationDate(resultSet.getDate("registration_date"));
                patron.setCreditCardNo(resultSet.getString("credit_card_no"));
                patron.setBirthDate(resultSet.getDate("birth_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patron;
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
                patron.setRegistrationDate(resultSet.getDate("registration_date"));
                patron.setCreditCardNo(resultSet.getString("credit_card_no"));
                patron.setBirthDate(resultSet.getDate("birth_date"));
                patrons.add(patron);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patrons;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
