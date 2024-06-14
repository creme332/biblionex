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

    /**
     * Finds publishers by the specified column and value.
     * 
     * @param column The column name to search by.
     * @param value  The value to search for in the specified column.
     * @return A list of publishers matching the search criteria.
     */
    public static List<Publisher> findBy(String column, String value) {
        final Connection conn = DatabaseConnection.getConnection();
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT * FROM publisher WHERE " + column + " = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Publisher publisher = new Publisher(resultSet.getInt("publisher_id"), resultSet.getString("name"),
                        resultSet.getString("email"), resultSet.getString("country"));
                publishers.add(publisher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishers;
    }

    /**
     * Retrieves all publishers from the database.
     * 
     * @return A list of all publishers.
     */
    public static List<Publisher> findAll() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishers;
    }

    /**
     * Saves a new publisher to the database.
     * 
     * @param publisher The publisher to save.
     */
    public static void save(Publisher publisher) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO publisher (name, email, country) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getEmail());
            preparedStatement.setString(3, publisher.getCountry());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing publisher in the database.
     * 
     * @param publisher The publisher to update.
     */
    public static void update(Publisher publisher) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE publisher SET name = ?, email = ?, country = ? WHERE publisher_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getEmail());
            preparedStatement.setString(3, publisher.getCountry());
            preparedStatement.setInt(4, publisher.getPublisherId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a publisher from the database by ID.
     * 
     * @param id The ID of the publisher to delete.
     */
    public static void delete(int id) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM publisher WHERE publisher_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

}
