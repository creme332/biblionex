package com.github.creme332.model;

import com.github.creme332.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialLocation {

    private int barcode;
    private int materialId;
    private int orderId;
    private MaterialCondition condition;
    private int shelfNo;
    private int aisleNo;
    private int sectionNo;

    public MaterialLocation(int barcode, int materialId, int orderId, MaterialCondition condition, int shelfNo,
            int aisleNo, int sectionNo) {
        this.barcode = barcode;
        this.materialId = materialId;
        this.orderId = orderId;
        this.condition = condition;
        this.shelfNo = shelfNo;
        this.aisleNo = aisleNo;
        this.sectionNo = sectionNo;
    }

    public int getBarcode() {
        return barcode;
    }

    public int getMaterialId() {
        return materialId;
    }

    public int getOrderId() {
        return orderId;
    }

    public MaterialCondition getCondition() {
        return condition;
    }

    public void setCondition(MaterialCondition condition) {
        this.condition = condition;
    }

    public int getShelfNo() {
        return shelfNo;
    }

    public int getAisleNo() {
        return aisleNo;
    }

    public int getSectionNo() {
        return sectionNo;
    }

    public static List<MaterialLocation> findBy(String column, String value) {
        final Connection conn = DatabaseConnection.getConnection();
        List<MaterialLocation> materialLocations = new ArrayList<>();
        String query = "SELECT * FROM material_copy WHERE " + column + " = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaterialLocation materialLocation = new MaterialLocation(
                        resultSet.getInt("barcode"),
                        resultSet.getInt("material_id"),
                        resultSet.getInt("order_id"),
                        MaterialCondition.valueOf(resultSet.getString("condition")),
                        resultSet.getInt("shelf_no"),
                        resultSet.getInt("aisle_no"),
                        resultSet.getInt("section_no"));
                materialLocations.add(materialLocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materialLocations;
    }

    public static List<MaterialLocation> findAll() {
        final Connection conn = DatabaseConnection.getConnection();
        List<MaterialLocation> materialLocations = new ArrayList<>();
        String query = "SELECT * FROM material_copy";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaterialLocation materialLocation = new MaterialLocation(
                        resultSet.getInt("barcode"),
                        resultSet.getInt("material_id"),
                        resultSet.getInt("order_id"),
                        MaterialCondition.valueOf(resultSet.getString("condition")),
                        resultSet.getInt("shelf_no"),
                        resultSet.getInt("aisle_no"),
                        resultSet.getInt("section_no"));
                materialLocations.add(materialLocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materialLocations;
    }

    public static void save(MaterialLocation materialLocation) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO material_copy (material_id, order_id, condition, shelf_no, aisle_no, section_no) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, materialLocation.getMaterialId());
            preparedStatement.setInt(2, materialLocation.getOrderId());
            preparedStatement.setString(3, materialLocation.getCondition().name());
            preparedStatement.setInt(4, materialLocation.getShelfNo());
            preparedStatement.setInt(5, materialLocation.getAisleNo());
            preparedStatement.setInt(6, materialLocation.getSectionNo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(MaterialLocation materialLocation) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE material_copy SET material_id = ?, shelf_no = ?, condition = ?, aisle_no = ?, section_no = ? WHERE barcode = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, materialLocation.getMaterialId());
            preparedStatement.setInt(2, materialLocation.getShelfNo());
            preparedStatement.setString(3, materialLocation.getCondition().name());
            preparedStatement.setInt(4, materialLocation.getAisleNo());
            preparedStatement.setInt(5, materialLocation.getSectionNo());
            preparedStatement.setInt(6, materialLocation.getBarcode());
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
