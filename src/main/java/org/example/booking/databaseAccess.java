package org.example.booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseAccess {    public static Connection getConnection() throws SQLException {
    Connection connection = null;
    try {
        // Load the JDBC driver class
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // Establish the connection
        connection = DriverManager.getConnection("JDBC", "USERNAME", "PASSWORD");
        System.out.println("Connected to the database.");
    } catch (ClassNotFoundException e) {
        System.out.println("Error: SQL Server JDBC Driver not found.");
        e.printStackTrace();
    }
    return connection;
}
}
