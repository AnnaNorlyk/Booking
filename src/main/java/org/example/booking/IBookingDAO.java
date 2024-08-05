package org.example.booking;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface IBookingDAO {
    int getUserIdByUsername(String username);

    void getThoseRooms();

    List<String> getAllRoomNames();

    void addIssue(String roomName, String description, String unilogin);

    void addRoom(String roomName, int capacity, String facilities, int roomUsage);

    List<Room> getRooms();

    List<Room> getAllAvailableTimeSlots();

    List<String> getRoomTimeSlots(String roomName);

    User getUserDetailsByUnilogin(String unilogin) throws SQLException;

    void addBooking(int roomID, int userID, Date date, Time startTime, Time endTime, String title) throws SQLException;

    Room getRoomByName(String roomName) throws SQLException;
}
