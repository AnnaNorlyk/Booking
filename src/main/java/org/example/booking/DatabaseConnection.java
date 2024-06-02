package org.example.booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=dbBookingSystem; encrypt=false";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "1234";
    private static Connection connection;


    // gets connection to database
    public static Connection getConnection() throws SQLException {

        try {
            System.out.println("lol");
            // Load the JDBC driver class
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to the database.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: SQL Server JDBC Driver not found.");
            e.printStackTrace();
        }
        return connection;
    }
}

