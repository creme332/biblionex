package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.github.creme332.utils.DatabaseConnection;
import com.github.creme332.utils.PasswordAuthentication;

public abstract class User {
    protected String email;
    protected String password;
    protected int userId;
    protected String address;
    protected String firstName;
    protected String lastName;
    protected String phoneNo;
    protected UserType userType;

    protected User(String email, String password, int userId, String address, String firstName, String lastName,
            String phoneNo) {
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
    }

    protected User(String email, String password, String address, String firstName, String lastName,
            String phoneNo) {
        this.email = email;
        this.password = password;
        this.userId = -1;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
    }

    protected User() {
        this.email = "";
        this.password = "";
        this.userId = -1;
        this.address = "";
        this.firstName = "";
        this.lastName = "";
        this.phoneNo = "";
    }

    public boolean authenticate(String email, char[] enteredPassword) {
        boolean isCorrect = true;

        // check if emails do not match
        if (!email.equals(this.email)) {
            Arrays.fill(enteredPassword, '0');
            return false;
        }

        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        isCorrect = passwordAuthentication.authenticate(enteredPassword, this.password);

        // Zero out the password for security purposes.
        Arrays.fill(enteredPassword, '0');

        return isCorrect;
    }

    public static boolean validateEmail(String email) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT COUNT(*) FROM patron WHERE email = ? UNION SELECT COUNT(*) FROM librarian WHERE email = ?";
        
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    return false; // Email already exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // Email is unique
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
