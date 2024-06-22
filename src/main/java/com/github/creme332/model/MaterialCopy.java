package com.github.creme332.model;

import com.github.creme332.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * An object storing information about a physical copy of a material. A
 * material can have multiple copies.
 */
public class MaterialCopy {

    /**
     * Unique identifier for a physical copy of a material.
     */
    private int barcode;

    private MaterialLocation location;

    /**
     * ID of order from which copy was purchased.
     */
    private int orderId;
    private MaterialCondition condition;
    private int materialId;

    public MaterialCopy(MaterialLocation location, int orderId, MaterialCondition condition) {
        this.location = location;
        this.orderId = orderId;
        this.condition = condition;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;

    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public MaterialLocation getLocation() {
        return location;
    }

    public void setLocation(MaterialLocation location) {
        this.location = location;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public MaterialCondition getCondition() {
        return condition;
    }

    public void setCondition(MaterialCondition condition) {
        this.condition = condition;
    }

    /**
     * 
     * @return A list of loans for current material copy.
     */
    public List<Loan> getLoanHistory() {
        // TODO
        return new ArrayList<>();
    }

    public static MaterialCopy findById(int barcode) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM material_copy WHERE barcode = ?";
        MaterialCopy result = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, barcode);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            MaterialLocation materialLocation = new MaterialLocation(
                    resultSet.getInt("shelf_no"),
                    resultSet.getInt("aisle_no"),
                    resultSet.getInt("section_no"));

            result = new MaterialCopy(
                    materialLocation,
                    resultSet.getInt("order_id"),
                    MaterialCondition.fromString(resultSet.getString("condition")));
        }
        return result;
    }

    public static List<MaterialCopy> findAll() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<MaterialCopy> materialCopies = new ArrayList<>();
        String query = "SELECT * FROM material_copy";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaterialLocation materialLocation = new MaterialLocation(
                        resultSet.getInt("shelf_no"),
                        resultSet.getInt("aisle_no"),
                        resultSet.getInt("section_no"));

                MaterialCopy materialCopy = new MaterialCopy(
                        materialLocation,
                        resultSet.getInt("order_id"),
                        MaterialCondition.fromString(resultSet.getString("condition")));
                materialCopies.add(materialCopy);
            }
        }

        return materialCopies;
    }

    public static void save(MaterialCopy materialCopy) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO material_copy (barcode, material_id, order_id, condition, shelf_no, aisle_no, section_no) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, materialCopy.getBarcode());
            preparedStatement.setInt(2, materialCopy.getMaterialId());
            preparedStatement.setInt(3, materialCopy.getOrderId());
            preparedStatement.setString(4, materialCopy.getCondition().toString());
            preparedStatement.setInt(5, materialCopy.getLocation().getShelfNo());
            preparedStatement.setInt(6, materialCopy.getLocation().getAisleNo());
            preparedStatement.setInt(7, materialCopy.getLocation().getSectionNo());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException(
                        String.format("Could not save material copy with barcode %d and material ID %d",
                                materialCopy.getBarcode(), materialCopy.getMaterialId()));
            }
        }
    }

    /**
     * Updates all attributes of material copy except material ID, order ID and
     * barcode.
     * 
     * @param materialCopy
     * @throws SQLException
     */
    public static void update(MaterialCopy materialCopy) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE material_copy SET condition = ?, shelf_no = ?, aisle_no = ?, section_no = ? WHERE barcode = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, materialCopy.getCondition().toString());
            preparedStatement.setInt(2, materialCopy.getLocation().getShelfNo());
            preparedStatement.setInt(3, materialCopy.getLocation().getAisleNo());
            preparedStatement.setInt(4, materialCopy.getLocation().getSectionNo());
            preparedStatement.setInt(5, materialCopy.getBarcode());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException(
                        String.format("Could not update material copy with barcode %d and material ID %d",
                                materialCopy.getBarcode(), materialCopy.getMaterialId()));
            }
        }
    }

    /**
     * Deletes a particular material copy.
     * 
     * @param materialCopy Material copy to be deleted.
     * @throws SQLException
     */
    public static void delete(MaterialCopy materialCopy) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM material_copy WHERE barcode = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, materialCopy.getBarcode());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException(
                        String.format("Could not delete material copy with barcode %d and material ID %d",
                                materialCopy.getBarcode(), materialCopy.getMaterialId()));
            }
        }
    }
}
