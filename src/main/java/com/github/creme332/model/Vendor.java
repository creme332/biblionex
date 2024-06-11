package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;

public class Vendor {
    private int vendorId;
    private String email;
    private String name;
    private String contactPerson;

    public Vendor(int vendorId, String email, String name, String contactPerson) {
        this.vendorId = vendorId;
        this.email = email;
        this.name = name;
        this.contactPerson = contactPerson;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * Finds vendors by the specified column and value.
     * @param column The column name to search by.
     * @param value The value to search for in the specified column.
     * @return A list of vendors matching the search criteria.
     */
    public static List<Vendor> findBy(String column, String value) {
        final Connection conn = DatabaseConnection.getConnection();
        List<Vendor> vendors = new ArrayList<>();
        String query = "SELECT * FROM vendor WHERE " + column + " = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vendor vendor = new Vendor(
                    resultSet.getInt("vendor_id"),
                    resultSet.getString("email"),
                    resultSet.getString("name"),
                    resultSet.getString("contact_person")
                );
                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendors;
    }

    /**
     * Retrieves all vendors from the database.
     * @return A list of all vendors.
     */
    public static List<Vendor> findAll() {
        final Connection conn = DatabaseConnection.getConnection();
        List<Vendor> vendors = new ArrayList<>();
        String query = "SELECT * FROM vendor";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vendor vendor = new Vendor(
                    resultSet.getInt("vendor_id"),
                    resultSet.getString("email"),
                    resultSet.getString("name"),
                    resultSet.getString("contact_person")
                );
                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendors;
    }

    /**
     * Saves a new vendor to the database.
     * @param vendor The vendor to save.
     */
    public static void save(Vendor vendor) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO vendor (email, name, contact_person) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, vendor.getEmail());
            preparedStatement.setString(2, vendor.getName());
            preparedStatement.setString(3, vendor.getContactPerson());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
