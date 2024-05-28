package org.example.booking;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseAccess {

    //Connection details
    private static final String URL ="jdbc:sqlserver://localhost:1433;databaseName= dbBookingSystem; ";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "1234";


    public static Connection getConnection() throws SQLException {

        // Establish the connection
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        System.out.println("Connected to the database.");

        return connection;
    }
}
