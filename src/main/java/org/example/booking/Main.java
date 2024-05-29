package org.example.booking;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void initializeconnetion(){
        try{
            Connection connection = databaseAccess.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//test
    /*
    public static void main(String[] args) {
        InfoScreenController controller = new InfoScreenController();
        controller.showRooms();
        for (Room room : controller.getRooms()) {
            System.out.println("Room ID: " + room.getRoomID());
            System.out.println("Room Name: " + room.getRoomName());
            System.out.println("Capacity: " + room.getCapacity());
            System.out.println("Facilities: " + room.getFacilities());
            System.out.println();
        }
    }
     */



}
