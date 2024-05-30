package org.example.booking;

public class Room {
    private int roomID;
    private String roomName;
    private int capacity;
    private String facilities;


    public Room(int roomID, String roomName, int capacity, String facilities) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.capacity = capacity;
        this.facilities = facilities;
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


}