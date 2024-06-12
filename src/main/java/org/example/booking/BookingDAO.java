package org.example.booking;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private List<Room> rooms = new ArrayList<>();


    public int getUserIdByUsername(String username) {
        int userId = -1;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT fldUserID FROM tblUser WHERE fldUsername = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("fldUserID");
            }
        } catch (SQLException e) {
            System.err.println("error");
        }
        return userId;
    }


    public List<String> getAllRoomNames() throws SQLException {
        List<String> roomNames = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT roomName FROM rooms";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomNames.add(resultSet.getString("roomName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomNames;
    }



    // adds issue
    public void addIssue(String roomName, String description, String unilogin) {
        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement callableStatement = connection.prepareCall("{call spInsertErrorReport(?, ?, ?)}")) {

            // Get the userID corresponding to the provided unilogin
            int userId = getUserIdByUsername(unilogin);

            // Set the parameters for the stored procedure
            callableStatement.setString(1, roomName);
            callableStatement.setString(2, description);
            callableStatement.setString(3, unilogin);

            // Execute the stored procedure
            callableStatement.executeUpdate();
            System.out.println("Issue reported successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding issue: " + e.getMessage());
        }
    }






    // adds a room
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



    // gets the info for the infoscreen "rooms"
    public void getThoseRooms() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "{call spGetAllBookrooms}"; // the storedprocedure needs to be changed so only non-booked rooms will show (???no Only BOOKED rooms should show)
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

    /**
     * Retrieves a list of all available time slots for all rooms.
     *
     * This method calls a stored procedure named 'GetAllAvailableTimeSlots' that takes the current date as a parameter
     * and returns all available time slots. The time slots are formatted and stored in Room objects, then returned in a list.
     *
     * @return A list of Room objects, each containing room details and available time slots formatted as "HH:mm - HH:mm".
     */
    public List<Room> getAllAvailableTimeSlots() {
        List<Room> rooms = new ArrayList<>();
        LocalDate today = LocalDate.now();
        String sql = "{CALL GetAllAvailableTimeSlots(?)}"; // Calling stored procedure

        //Sets formatter to hh:mm instead of hh:mm:ss
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            // Sets date to the current day and room ID
            stmt.setDate(1, Date.valueOf(today));
            ResultSet rs = stmt.executeQuery();

            //Retrieves info based on parameter
            while (rs.next()) {
                int roomID = rs.getInt("fldRoomID");
                String roomName = rs.getString("fldRoomName");
                String facilities = rs.getString("fldFacilities");
                Time startTimeSql = rs.getTime("fldStartTime");
                Time endTimeSql = rs.getTime("fldEndTime");

                // Convert SQL Time to LocalTime to then format
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


    /**
     * Retrieves a list of available time slots for a room by its name.
     *
     * This method calls a stored procedure named 'GetAllAvailableTimeSlots' that takes the current today's date as a parameter
     * and returns all available time slots for rooms. The method filters these time slots to return only those for the specified room.
     *
     * @param roomName Name of the room from which available timeslots are being retrieved
     * @return A list of available time slots formatted as strings in "HH:mm" format.
     */
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

            // Increment room usage count
            incrementRoomUsage(roomID);
        }
    }

    private void incrementRoomUsage(int roomID) throws SQLException {
        String sql = "{CALL IncrementRoomUsage(?)}";
        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setInt(1, roomID);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a Room object from the database by its name.
     *
     * This method calls the stored procedure named 'GetRoomByName' that takes a room name as a parameter
     * and returns the details of the room from the database.
     *
     * @param roomName The name of the room to be retrieved.
     * @return A Room object containing the room details if found, otherwise null.
     * @throws SQLException If a database access error occurs or the stored procedure fails.
     */
    public Room getRoomByName(String roomName) throws SQLException {
        String sql = "{CALL GetRoomByName(?)}";
        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, roomName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int roomID = rs.getInt("fldRoomID");
                String name = rs.getString("fldRoomName");
                int capacity = rs.getInt("fldCapacity");
                String facilities = rs.getString("fldFacilities");
                int roomUsage = rs.getInt("fldRoomUsage");

                return new Room(roomID, name, capacity, facilities, roomUsage, "", "", 0, 0, "", "");
            } else {
                return null;
            }
        }
    }



}



