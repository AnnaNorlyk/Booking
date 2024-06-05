
package org.example.booking;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private List<Room> rooms = new ArrayList<>();


    public void addRoom(String roomName, int capacity, String facilities, int roomUsage) {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
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
        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call spGetAllBookrooms}");
             ResultSet resultSet = callableStatement.executeQuery()) {

            while (resultSet.next()) {
                int roomID = resultSet.getInt("fldRoomID");
                String roomName = resultSet.getString("fldRoomName");
                int capacity = resultSet.getInt("fldCapacity");
                String facilities = resultSet.getString("fldFacilities");
                int roomUsage = resultSet.getInt("fldRoomUsage");
                String startTime = resultSet.getTime("fldStartTime") != null ? resultSet.getTime("fldStartTime").toString() : "";
                String endTime = resultSet.getTime("fldEndTime") != null ? resultSet.getTime("fldEndTime").toString() : "";
                String timeRange = startTime.isEmpty() || endTime.isEmpty() ? "" : startTime + " - " + endTime;
                String title = resultSet.getString("fldTitle");
                int refreshments = resultSet.getInt("fldRefreshments");
                int userID = resultSet.getInt("fldUserID");
                String issueDescription = resultSet.getString("fldDescription");
                String userName = resultSet.getString("flduserName");

              //creates the rooms
                Room room = new Room(roomID, roomName, title, capacity, facilities, issueDescription, userName);
                room.setTimeRange(timeRange);
                room.setRefreshments(refreshments);
                room.setUserID(userID);
                room.setIssueDescription(issueDescription); // Set issue description

                // adds the room to the list
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }








    public List<Room> getRooms() {
        return rooms;
    }



    public List<Room> getRoomAvailability(int roomId) {
        List<Room> rooms = new ArrayList<>();
        LocalDate today = LocalDate.now(); // Converts LocalDate into Date
        String sql = "{CALL GetAvailableTimeSlots(?, ?)}"; // Calling stored procedure

        // Formatter to convert SQL Time to "hour:minute" format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setDate(1, Date.valueOf(today));
            stmt.setInt(2, roomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Retrieve and format start and end times
                Time startTimeSql = rs.getTime("fldStartTime");
                Time endTimeSql = rs.getTime("fldEndTime");

                // Convert SQL Time to LocalTime to then format
                String startTime = timeFormatter.format(startTimeSql.toLocalTime());
                String endTime = timeFormatter.format(endTimeSql.toLocalTime());
                String timeRange = startTime + " - " + endTime;

                // Create new Room object and add to the list
                Room room = new Room(
                        rs.getString("fldRoomName"),
                        rs.getString("fldFacilities"),
                        timeRange
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }




}



