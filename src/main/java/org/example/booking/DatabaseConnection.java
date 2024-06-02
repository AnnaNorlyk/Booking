package org.example.booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    //Connection details
    private static final String URL ="jdbc:sqlserver://localhost:1433;databaseName=dbBookingSystem;encrypt=false ";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "1234";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load SQL Server JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {

        // Establish the connection
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        System.out.println("Connected to the database.");

        return connection;
    }
}
