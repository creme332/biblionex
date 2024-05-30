package com.github.creme332.model;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class DatabaseConnection {
    private static Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USERNAME");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            System.out.println("Unable to connect to database: " + e);
            System.exit(0);
        }
        return null;
    }
}