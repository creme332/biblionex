package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;

/**
 * Order for a particular material. Only librarians can order/purchase new
 * materials.
 */
public class Order {
    private int orderId;
    private int librarianId;
    private int vendorId;
    private int materialId;
    private OrderStatus status;
    private Date createdDate;
    private int quantity;
    private Date deliveryDate;
    private double unitPrice;

    public Order(int orderId, int librarianId, int vendorId, int materialId, OrderStatus status,
            Date createdDate, int quantity, Date deliveryDate, double unitPrice) {
        this.orderId = orderId;
        this.librarianId = librarianId;
        this.vendorId = vendorId;
        this.materialId = materialId;
        this.status = status;
        this.createdDate = createdDate;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;
        this.unitPrice = unitPrice;
    }

    /**
     * Constructor for creating a new order. order ID, deliveryDate are unknown at
     * this point.
     * 
     * @param librarianId
     * @param vendorId
     * @param materialId
     * @param status
     * @param quantity
     */
    public Order(int librarianId, int vendorId, int materialId, int quantity) {
        this.librarianId = librarianId;
        this.vendorId = vendorId;
        this.materialId = materialId;
        this.status = OrderStatus.PENDING;
        this.createdDate = new Date();
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    // Database interaction methods

    public static Order findById(int id) {
        final Connection conn = DatabaseConnection.getConnection();
        Order order = null;
        String query = "SELECT * FROM `order` WHERE order_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = new Order(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("librarian_id"),
                        resultSet.getInt("vendor_id"),
                        resultSet.getInt("material_id"),
                        OrderStatus.fromString(resultSet.getString("status")),
                        resultSet.getDate("created_date"),
                        resultSet.getInt("quantity"),
                        resultSet.getDate("delivery_date"),
                        resultSet.getDouble("unit_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public static List<Order> findAll() {
        final Connection conn = DatabaseConnection.getConnection();
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM `order`";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("librarian_id"),
                        resultSet.getInt("vendor_id"),
                        resultSet.getInt("material_id"),
                        OrderStatus.fromString(resultSet.getString("status")),
                        resultSet.getTimestamp("created_date"),
                        resultSet.getInt("quantity"),
                        resultSet.getTimestamp("delivery_date"),
                        resultSet.getDouble("unit_price"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Call this method when an order has been delivered. It will update the order
     * status and create records for the new material copies.
     * 
     * @param order
     */
    public static void completeOrder(Order order) {
        // TODO
    }

    public static void save(Order order) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = """
                INSERT INTO `order` (librarian_id, vendor_id, material_id, status, created_date, quantity, delivery_date, unit_price)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                        """;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, order.getLibrarianId());
            preparedStatement.setInt(2, order.getVendorId());
            preparedStatement.setInt(3, order.getMaterialId());
            preparedStatement.setString(4, order.getStatus().toString());
            preparedStatement.setDate(5, new java.sql.Date(order.getCreatedDate().getTime()));
            preparedStatement.setInt(6, order.getQuantity());
            preparedStatement.setDate(7, new java.sql.Date(order.getDeliveryDate().getTime()));
            preparedStatement.setDouble(8, order.getUnitPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Order order) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = """
                UPDATE `order`
                    SET librarian_id = ?, vendor_id = ?, material_id = ?, status = ?,
                    created_date = ?, quantity = ?, delivery_date = ?, unit_price = ?
                 WHERE order_id = ?
                """;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, order.getLibrarianId());
            preparedStatement.setInt(2, order.getVendorId());
            preparedStatement.setInt(3, order.getMaterialId());
            preparedStatement.setString(4, order.getStatus().toString());
            preparedStatement.setDate(5, new java.sql.Date(order.getCreatedDate().getTime()));
            preparedStatement.setInt(6, order.getQuantity());
            preparedStatement.setDate(7, new java.sql.Date(order.getDeliveryDate().getTime()));
            preparedStatement.setDouble(8, order.getUnitPrice());
            preparedStatement.setInt(9, order.getOrderId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int orderId) {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM `order` WHERE order_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
