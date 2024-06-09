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
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "{call spGetAllRooms}"; // the storedprocedure needs to be changed so only non-booked rooms will show (???no Only BOOKED rooms should show)
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();

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

                Room room = new Room(roomID, roomName, capacity, facilities, roomUsage, timeRange, title, refreshments, userID, userName, issueDescription);
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
        String sql = "{CALL GetAllAvailableTimeSlots(?, ?)}"; // Calling stored procedure

        // Formatter to convert SQL Time to "hour:minute" format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            // Sets date to the current day and room ID
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
            System.err.println("Error in BookingDAO: " + e.getMessage());
        }
        return rooms;
    }





    public List<String> getRoomTimeSlots(String roomName) {
            List<String> timeSlots = new ArrayList<>();
            LocalDate today = LocalDate.now();
            String sql = "{CALL GetAllAvailableTimeSlots(?)}";

            //Sets timeformatter
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            //Sets date parameter for stored procedure
            stmt.setDate(1, Date.valueOf(today));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String selectedRoom = rs.getString("fldRoomName");
                if (selectedRoom.equals(roomName)) {
                    Time SQLStartTime = rs.getTime("fldStartTime");

                    //Formats the time retrieved into hh:mm format and adds to the timeSlots list
                    String startTime = timeFormatter.format(SQLStartTime.toLocalTime());
                    timeSlots.add(startTime);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in BookingDAO: " + e.getMessage());
        }
    return timeSlots;
    }

    public Room getRoomByName(String roomName) {
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    public User getUserDetailsByUnilogin(String unilogin) throws SQLException {
        String sql = "{CALL GetUserDetailsByUnilogin(?, ?, ?, ?)}";
        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            //Input
            stmt.setString(1, unilogin);

            //Output
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.registerOutParameter(4, Types.VARCHAR);
            stmt.execute();

            //Retrieve the output
            int userID = stmt.getInt(2);
            String userName = stmt.getString(3);
            String retrievedUnilogin = stmt.getString(4);

            return new User(userID, userName, retrievedUnilogin);
        }
    }

    public void addBooking(int roomID, int userID, java.util.Date date, Time startTime, Time endTime, String title) throws SQLException {
        String sql = "{CALL AddBooking(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            // Sets input parameters for stored procedure
            stmt.setInt(1, roomID);
            stmt.setInt(2, userID);
            stmt.setDate(3, new Date(date.getTime()));
            stmt.setTime(4, startTime);
            stmt.setTime(5, endTime);
            stmt.setString(6, title);
            stmt.setInt(7, 0); // is set to 0 as refreshments will not be available for ad-hoc bookings
            stmt.executeUpdate();
        }
    }

}



