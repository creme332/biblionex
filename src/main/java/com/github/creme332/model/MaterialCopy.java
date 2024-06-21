package com.github.creme332.model;

import com.github.creme332.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialCopy {

    private MaterialLocation location;
    private int orderId;
    private MaterialCondition condition;

    public MaterialCopy(MaterialLocation location, int orderId, MaterialCondition condition) {
        this.location = location;
        this.orderId = orderId;
        this.condition = condition;
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

    public static List<MaterialCopy> findBy(String column, String value) {
        final Connection conn = DatabaseConnection.getConnection();
        List<MaterialCopy> materialCopies = new ArrayList<>();
        String query = "SELECT * FROM material_copy WHERE " + column + " = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaterialLocation materialLocation = new MaterialLocation(
                        resultSet.getInt("barcode"),
                        resultSet.getInt("material_id"),
                        resultSet.getInt("shelf_no"),
                        resultSet.getInt("aisle_no"),
                        resultSet.getInt("section_no"));

                MaterialCopy materialCopy = new MaterialCopy(
                        materialLocation,
                        resultSet.getInt("order_id"),
                        MaterialCondition.valueOf(resultSet.getString("condition")));
                materialCopies.add(materialCopy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materialCopies;
    }

    public static List<MaterialCopy> findAll() {
        final Connection conn = DatabaseConnection.getConnection();
        List<MaterialCopy> materialCopies = new ArrayList<>();
        String query = "SELECT * FROM material_copy";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaterialLocation materialLocation = new MaterialLocation(
                        resultSet.getInt("barcode"),
                        resultSet.getInt("material_id"),
                        resultSet.getInt("shelf_no"),
                        resultSet.getInt("aisle_no"),
                        resultSet.getInt("section_no"));

                MaterialCopy materialCopy = new MaterialCopy(
                        materialLocation,
                        resultSet.getInt("order_id"),
                        MaterialCondition.valueOf(resultSet.getString("condition")));
                materialCopies.add(materialCopy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materialCopies;
    }

    public static void save(MaterialCopy materialCopy) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO material_copy (barcode, material_id, order_id, condition, shelf_no, aisle_no, section_no) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, materialCopy.getLocation().getBarcode());
            preparedStatement.setInt(2, materialCopy.getLocation().getMaterialId());
            preparedStatement.setInt(3, materialCopy.getOrderId());
            preparedStatement.setString(4, materialCopy.getCondition().name());
            preparedStatement.setInt(5, materialCopy.getLocation().getShelfNo());
            preparedStatement.setInt(6, materialCopy.getLocation().getAisleNo());
            preparedStatement.setInt(7, materialCopy.getLocation().getSectionNo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(MaterialCopy materialCopy) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE material_copy SET material_id = ?, order_id = ?, condition = ?, shelf_no = ?, aisle_no = ?, section_no = ? WHERE barcode = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, materialCopy.getLocation().getMaterialId());
            preparedStatement.setInt(2, materialCopy.getOrderId());
            preparedStatement.setString(3, materialCopy.getCondition().name());
            preparedStatement.setInt(4, materialCopy.getLocation().getShelfNo());
            preparedStatement.setInt(5, materialCopy.getLocation().getAisleNo());
            preparedStatement.setInt(6, materialCopy.getLocation().getSectionNo());
            preparedStatement.setInt(7, materialCopy.getLocation().getBarcode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int barcode) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM material_copy WHERE barcode = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, barcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
