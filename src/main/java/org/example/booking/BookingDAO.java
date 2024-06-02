package org.example.booking;

import org.example.booking.Room;
import org.example.booking.databaseAccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private List<Room> rooms = new ArrayList<>();



        public void addRoom(String roomName, int capacity, String facilities, int roomUsage) {
            Connection connection = null;
            CallableStatement callableStatement = null;

            try {
                connection = databaseAccess.getConnection();
                String query = "{call spAddRoom(?, ?, ?, ?)}";// might need to look into how we would make the roomusage tick up( im not even sure we have to use the field in this method)
                callableStatement = connection.prepareCall(query);
                callableStatement.setString(1, roomName);
                callableStatement.setInt(2, capacity);
                callableStatement.setString(3, facilities);
                callableStatement.setInt(4, roomUsage);
                callableStatement.executeUpdate();
                System.out.println("Room added");
            } catch (SQLException e) {
                System.out.println("error");
            }
        }




    public void getThoseRooms() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAccess.getConnection();
            String query = "{call spGetAllRooms}"; // the storedprocedure needs to be changed so only non-booked rooms will show
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                int RoomID = resultSet.getInt("fldRoomID");
                String RoomName = resultSet.getString("fldRoomName");
                int Capacity = resultSet.getInt("fldCapacity");
                String facilities = resultSet.getString("fldFacilities");
                int RoomUsage = resultSet.getInt("fldRoomUsage");

                Room room = new Room(RoomID, RoomName, Capacity, facilities, RoomUsage);
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error:");
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }


}