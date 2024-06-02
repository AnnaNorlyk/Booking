package org.example.booking;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends MainLaunch {

    public static void initializeconnetion(){
        try{
            Connection connection = databaseAccess.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
