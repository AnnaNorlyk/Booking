
package org.example.booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=dbBookingSystem; encrypt=false";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "1234";
    private static Connection connection;


    // gets connection to database
    public static Connection getConnection() throws SQLException {

        //Establish connection
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        System.out.println("Connected to database");

        return connection;
    }
}

