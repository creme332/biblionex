package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;

public class Publisher {
    private int publisherId;
    private String name;
    private String email;
    private String country;

    public Publisher(int publisherId, String name, String email, String country) {
        this.publisherId = publisherId;
        this.name = name;
        this.email = email;
        this.country = country;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public static List<Publisher> findById(int publisherId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT * FROM publisher WHERE publisher_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, publisherId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Publisher publisher = new Publisher(resultSet.getInt("publisher_id"), resultSet.getString("name"),
                        resultSet.getString("email"), resultSet.getString("country"));
                publishers.add(publisher);
            }
        }
        return publishers;
    }

    /**
     * Retrieves all publishers from the database.
     * 
     * @return A list of all publishers.
     */
    public static List<Publisher> findAll() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT * FROM publisher";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Publisher publisher = new Publisher(resultSet.getInt("publisher_id"), resultSet.getString("name"),
                        resultSet.getString("email"), resultSet.getString("country"));
                publishers.add(publisher);
            }
        }
        return publishers;
    }

    /**
     * Saves a new publisher to the database.
     * 
     * @param publisher The publisher to save.
     */
    public static void save(Publisher publisher) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO publisher (name, email, country) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getEmail());
            preparedStatement.setString(3, publisher.getCountry());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Updates an existing publisher in the database.
     * 
     * @param publisher The publisher to update.
     */
    public static void update(Publisher publisher) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE publisher SET name = ?, email = ?, country = ? WHERE publisher_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getEmail());
            preparedStatement.setString(3, publisher.getCountry());
            preparedStatement.setInt(4, publisher.getPublisherId());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Deletes a publisher from the database by ID.
     * 
     * @param id The ID of the publisher to delete.
     */
    public static void delete(int id) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM publisher WHERE publisher_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
