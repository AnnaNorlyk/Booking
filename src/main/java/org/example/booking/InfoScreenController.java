package org.example.booking;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import java.awt.*;

public class InfoScreenController {

    private MainLaunch mainlaunch;

    @FXML
    private Label dateDisplayLabel;

    public void setMainApplication(MainLaunch mainLaunch) {
        this.mainlaunch = mainLaunch;
    }

    @FXML
    private void handleBookLokaleButton(ActionEvent actionEvent) {
        mainlaunch.showAvailabilityScreen();
    }


}


    private List<Room> rooms = new ArrayList<>();
    public void showRooms(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAccess.getConnection();
            statement = connection.createStatement();



            String query = "SELECT * FROM tblRoom";
            resultSet = statement.executeQuery(query);



            while (resultSet.next()) {
                int roomID = resultSet.getInt("fldRoomID");
                String roomName = resultSet.getString("fldRoomName");
                int capacity = resultSet.getInt("fldCapacity");
                String facilities = resultSet.getString("fldFacilities");


                Room room = new Room(roomID, roomName, capacity, facilities);
                rooms.add(room);
            }
        }catch(SQLException e){
            System.out.println("error");

        }
    }


    public List<Room>getRooms(){
        return rooms;
    }


}


