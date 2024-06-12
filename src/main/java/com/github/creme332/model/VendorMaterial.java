package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;

public class VendorMaterial {
    private int vendorId;
    private int materialId;
    private double unitPrice;

    public VendorMaterial(int vendorId, int materialId, double unitPrice) {
        this.vendorId = vendorId;
        this.materialId = materialId;
        this.unitPrice = unitPrice;
    }

    public int getVendorId() {
        return vendorId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public static List<VendorMaterial> findBy(String column, String value) {
        final Connection conn = DatabaseConnection.getConnection();
        List<VendorMaterial> vendorMaterials = new ArrayList<>();
        String query = "SELECT * FROM vendor_material WHERE " + column + " = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VendorMaterial vendorMaterial = new VendorMaterial(
                        resultSet.getInt("vendor_id"),
                        resultSet.getInt("material_id"),
                        resultSet.getDouble("unit_price")
                );
                vendorMaterials.add(vendorMaterial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendorMaterials;
    }

    public static List<VendorMaterial> findAll() {
        final Connection conn = DatabaseConnection.getConnection();
        List<VendorMaterial> vendorMaterials = new ArrayList<>();
        String query = "SELECT * FROM vendor_material";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VendorMaterial vendorMaterial = new VendorMaterial(
                        resultSet.getInt("vendor_id"),
                        resultSet.getInt("material_id"),
                        resultSet.getDouble("unit_price")
                );
                vendorMaterials.add(vendorMaterial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendorMaterials;
    }

    public static void save(VendorMaterial vendorMaterial) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO vendor_material (vendor_id, material_id, unit_price) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, vendorMaterial.getVendorId());
            preparedStatement.setInt(2, vendorMaterial.getMaterialId());
            preparedStatement.setDouble(3, vendorMaterial.getUnitPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
