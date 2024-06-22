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
            String phoneNo, String creditCardNo, Date birthDate, Date registrationDate) {
        super(email, password, userId, address, firstName, lastName, phoneNo);
        userType = UserType.PATRON;
        this.creditCardNo = creditCardNo;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }

    public Patron(String email, String password, String address, String firstName, String lastName,
            String phoneNo, String creditCardNo, Date birthDate) {
        super(email, password, address, firstName, lastName, phoneNo);
        userType = UserType.PATRON;
        this.creditCardNo = creditCardNo;
        this.birthDate = birthDate;
        this.registrationDate = new Date();
    }

    /**
     * Saves a patron to database. patron ID and registration date are automatically
     * set by database.
     * 
     * @param patron
     * @throws SQLException
     */
    public static void save(Patron patron) throws SQLException {
        if (!User.validateEmail(patron.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        final Connection conn = DatabaseConnection.getConnection();
        String query = """
                INSERT INTO patron (
                    address,
                    password,
                    last_name,
                    first_name,
                    phone_no,
                    email,
                    credit_card_no,
                    birth_date
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                 """;

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
            preparedStatement.setDate(8, new java.sql.Date(patron.getBirthDate().getTime()));
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Updates all patron attributes except patron ID, password, registration date.
     * 
     * @throws SQLException
     */
    public static void update(Patron patron) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = """
                UPDATE patron
                SET address = ?,
                    last_name = ?,
                    first_name = ?,
                    phone_no = ?,
                    email = ?,
                    birth_date = ?,
                    credit_card_no = ?
                WHERE patron_id = ?
                """;

        try (PreparedStatement updatePatron = conn.prepareStatement(query)) {
            updatePatron.setString(1, patron.getAddress());
            updatePatron.setString(2, patron.getLastName());
            updatePatron.setString(3, patron.getFirstName());
            updatePatron.setString(4, patron.getPhoneNo());
            updatePatron.setString(5, patron.getEmail());
            updatePatron.setDate(6, new java.sql.Date(patron.getBirthDate().getTime()));
            updatePatron.setString(7, patron.getCreditCardNo());
            updatePatron.setInt(8, patron.getUserId());
            updatePatron.executeUpdate();
        }
    }

    public static void delete(int patronId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM patron WHERE patron_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, patronId);
            preparedStatement.executeUpdate();
        }
    }

    public static Patron findById(int patronId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        Patron patron = null;
        String query = "SELECT * FROM patron WHERE patron_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, patronId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                patron = new Patron(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("patron_id"),
                        resultSet.getString("address"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("credit_card_no"),
                        resultSet.getDate("birth_date"),
                        resultSet.getDate("registration_date"));
            }
        }
        return patron;
    }

    public static Patron findByEmail(String email) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();

        Patron patron = null;
        String query = "SELECT * FROM patron WHERE email = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                patron = new Patron(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("patron_id"),
                        resultSet.getString("address"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("credit_card_no"),
                        resultSet.getDate("birth_date"),
                        resultSet.getDate("registration_date"));
            }
        }
        return patron;
    }

    public static List<Patron> findAll() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<Patron> patrons = new ArrayList<>();
        String query = "SELECT * FROM patron";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Patron patron = new Patron(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("patron_id"),
                        resultSet.getString("address"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("credit_card_no"),
                        resultSet.getDate("birth_date"),
                        resultSet.getDate("registration_date"));
                patrons.add(patron);
            }
        }
        return patrons;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setRegistrationDate(Date newDate) {
        this.registrationDate = newDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return "Patron{" +
                "patron_id=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", last_name='" + lastName + '\'' +
                ", first_name='" + firstName + '\'' +
                ", phone_no='" + phoneNo + '\'' +
                ", address='" + address + '\'' +
                ", birth_date='" + birthDate + '\'' +
                ", registration_date='" + registrationDate + '\'' +
                ", credit_card_no='" + creditCardNo + '\'' +
                '}';
    }
}
