package org.example.booking;

public class Room {
    private int roomID;
    private String roomName;
    private int capacity;
    private String facilities;
    private int roomUsage;
    private String timeRange;
    private String issueDescription;
    private String roomTitle;
    private int refreshments;
    private int userID;
    private String userName;
    private String actualName;



    public Room(int roomID, String roomName, String facilities, String timeRange) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.facilities = facilities;
        this.timeRange = timeRange;

    }

    public Room(int roomID, String roomName, int capacity, String facilities, int roomUsage, String timeRange, String title, int refreshments, int userID, String issueDescription, String actualName, String name) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.capacity = capacity;
        this.facilities = facilities;
        this.roomUsage = roomUsage;
        this.timeRange = timeRange;
        this.roomTitle = title;
        this.refreshments = refreshments;
        this.userID = userID;
        this.issueDescription = issueDescription;
        this.actualName = actualName;
    }


    //roomnamelist
    public Room(int roomID, String roomName, int capacity, String facilities, int roomUsage, String timeRange, String title, int refreshments, int userID, String issueDescription, String actualName) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.capacity = capacity;
        this.facilities = facilities;
        this.roomUsage = roomUsage;
        this.timeRange = timeRange;
        this.roomTitle = title;
        this.refreshments = refreshments;
        this.userID = userID;
        this.issueDescription = issueDescription;
        this.actualName = actualName;
    }

    public Room(int roomID, String name, int capacity, String facilities, int roomUsage) {
        this.roomID = roomID;
        this.roomName = name;
        this.capacity = capacity;
        this. facilities = facilities;
        this.roomUsage = roomUsage;
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

    public int getRoomUsage() {

        return roomUsage;
    }


    public String getTimeRange() {

        return timeRange;
    }

    public void setTimeRange(String timeRange) { // Setter for timeRange

        this.timeRange = timeRange;
    }

    public void getErrors() {

    }

    public String getIssueDescription() {

        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {

        this.issueDescription = issueDescription;
    }

    public void setTitle(String title) {

        this.roomTitle = title;
    }

    public void setRefreshments(int refreshments) {

        this.refreshments = refreshments;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {

        return roomTitle;
    }

    public int getRefreshments() {

        return refreshments;
    }

    public int getUserID() {

        return userID;
    }

    public String getRoomTitle() {

        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }
}




