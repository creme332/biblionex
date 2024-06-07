package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;

public class Librarian extends User {
    private String role;

    public Librarian(String email, String password, int userId, String address, String firstName, String lastName,
            String phoneNo, String role) {
        super(email, password, userId, address, firstName, lastName, phoneNo);
        this.role = role;
    }

    public Librarian(String email, String password, String address, String firstName, String lastName,
            String phoneNo, String role) {
        super(email, password, address, firstName, lastName, phoneNo);
        this.role = role;
    }

    public Librarian() {
        // this constructor is for testing only
        super("admin@admin.com", "abcd", "street", "john", "peter", "432423423");
        this.role = "Chief Officer";
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static void save(Librarian librarian) {
        final Connection conn = DatabaseConnection.getConnection();

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

    public static void update(Librarian book) {
        final Connection conn = DatabaseConnection.getConnection();

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public static void delete(int id) {
        final Connection conn = DatabaseConnection.getConnection();

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public static Librarian findById(int id) {
        final Connection conn = DatabaseConnection.getConnection();

        Librarian librarian = null;
        String query = "SELECT * FROM librarian WHERE librarian_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                librarian = new Librarian();
                librarian.setUserId(resultSet.getInt("librarian_id"));
                librarian.setAddress(resultSet.getString("address"));
                librarian.setPassword(resultSet.getString("password"));
                librarian.setLastName(resultSet.getString("last_name"));
                librarian.setFirstName(resultSet.getString("first_name"));
                librarian.setPhoneNo(resultSet.getString("phone_no"));
                librarian.setUserId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., log the error or rethrow it)
        }

        return librarian;
    }

    public static List<Librarian> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public static Librarian findByEmail(String email) {
        final Connection conn = DatabaseConnection.getConnection();

        Librarian librarian = null;
        String query = "SELECT * FROM librarian WHERE email = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                librarian = new Librarian();
                librarian.setUserId(resultSet.getInt("librarian_id"));
                librarian.setAddress(resultSet.getString("address"));
                librarian.setPassword(resultSet.getString("password"));
                librarian.setLastName(resultSet.getString("last_name"));
                librarian.setFirstName(resultSet.getString("first_name"));
                librarian.setPhoneNo(resultSet.getString("phone_no"));
                librarian.setEmail(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., log the error or rethrow it)
        }

        return librarian;
    }

    @Override
    public String toString() {
        return "Librarian{" +

                "librarian_id=" + userId +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", last_name='" + lastName + '\'' +
                ", first_name='" + firstName + '\'' +
                ", phone_no='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';

    }
}
