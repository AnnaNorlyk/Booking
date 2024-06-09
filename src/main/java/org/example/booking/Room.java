package org.example.booking;

public class Room {
    private int roomID;
    private String roomName;
    private int capacity;
    private String facilities;
    private int RoomUsage;
    private String timeRange;

    //Constructor for general room information
    public Room(int roomID, String roomName, int capacity, String facilities, int roomUsage) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.capacity = capacity;
        this.facilities = facilities;
        this.RoomUsage = roomUsage;
    }

    //Constructor for displaying available times in Availablity
    public Room(String roomName, String facilities, String timeRange) {
        this.roomName = roomName;
        this.facilities = facilities;
        this.timeRange = timeRange;

    }

    //getters and setters
    public int getRoomID() {

        return roomID;
    }


    public void setRoomID(int roomID) {

        this.roomID = roomID;
    }


    public String getRoomName() {

        return roomName;
    }


    public void setRoomName(String roomName) {

        this.roomName = roomName;
    }


    public int getCapacity() {

        return capacity;
    }


    public void setCapacity(int capacity) {

        this.capacity = capacity;
    }


    public String getFacilities() {

        return facilities;
    }



    public int getRoomUsage(){
        return RoomUsage;
    }


    public String getTimeRange() {return timeRange;}

    public void setTimeRange(String timeRange) { // Setter for timeRange
    this.timeRange = timeRange;
    }

    public void getErrors() {

    }
}
