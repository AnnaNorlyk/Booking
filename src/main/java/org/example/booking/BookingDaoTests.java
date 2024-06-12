import org.example.booking.DatabaseConnection;
import org.example.booking.Room;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoTests {

    public List<Room> getThoseRoomsTest() {
        List<Room> rooms = new ArrayList<>();

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
                String actualName = resultSet.getString("fldActualName");

                // Print the retrieved data
                System.out.println("Room ID: " + roomID);
                System.out.println("Room Name: " + roomName);
                System.out.println("Capacity: " + capacity);
                System.out.println("Facilities: " + facilities);
                System.out.println("Room Usage: " + roomUsage);
                System.out.println("Time Range: " + timeRange);
                System.out.println("Title: " + title);
                System.out.println("Refreshments: " + refreshments);
                System.out.println("User ID: " + userID);
                System.out.println("Issue Description: " + issueDescription);
                System.out.println("Actual Name: " + actualName);


            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Print out all the rooms
        System.out.println("List of Rooms:");
        for (Room room : rooms) {
            System.out.println(room);
        }

        return rooms;
    }

    public List<Room> getAllAvailableTimeSlots() {
        List<Room> rooms = new ArrayList<>();
        LocalDate today = LocalDate.now();
        String sql = "{CALL GetAllAvailableTimeSlots(?)}"; // Calling stored procedure

        //Sets formatter to hh:mm instead of hh:mm:ss
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setDate(1, Date.valueOf(today));
            ResultSet rs = stmt.executeQuery();

            //Retrieves info base don parameter
            while (rs.next()) {
                int roomID = rs.getInt("fldRoomID");
                String roomName = rs.getString("fldRoomName");
                String facilities = rs.getString("fldFacilities");
                Time startTimeSql = rs.getTime("fldStartTime");
                Time endTimeSql = rs.getTime("fldEndTime");

                String startTime = timeFormatter.format(startTimeSql.toLocalTime());
                String endTime = timeFormatter.format(endTimeSql.toLocalTime());
                String timeRange = startTime + " - " + endTime;

                //Creates Room object and adds to list
                Room room = new Room(roomID, roomName, facilities, timeRange);
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Error in BookingDAO: " + e.getMessage());
        }
        return rooms;
    }



}