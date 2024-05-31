package org.example.booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<Room> getRoomAvailability(String string, int roomId) {
        List<Room> rooms = new ArrayList<>();
        LocalDate today = LocalDate.now(); //converts LocalDate into Date
        String sql = "{CALL GetAvailableTimeSlots(?, ?)}"; //Calling stored procedure

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setDate(1, Date.valueOf(today));
            stmt.setInt(2, roomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Room room = new Room(
                        rs.getString("fldRoomName"),
                        rs.getString("fldFacilities"),
                        rs.getString("fldStartTime") + " - " + rs.getString("fldEndTime")
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
