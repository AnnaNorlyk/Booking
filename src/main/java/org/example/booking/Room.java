package org.example.booking;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final StringProperty roomName;
    private final StringProperty facilities;
    private final StringProperty availableTimes;

    public Room(String roomName, String facilities, String availableTimes) {
        this.roomName = new SimpleStringProperty(roomName);
        this.facilities = new SimpleStringProperty(facilities);
        this.availableTimes = new SimpleStringProperty(availableTimes);
    }

    public String getRoomName() {
        return roomName.get();
    }

    public void setRoomName(String value) {
        roomName.set(value);
    }

    public StringProperty roomNameProperty() {
        return roomName;
    }

    public String getFacilities() {
        return facilities.get();
    }

    public void setFacilities(String value) {
        facilities.set(value);
    }

    public StringProperty facilitiesProperty() {
        return facilities;
    }

    public String getAvailableTimes() {
        return availableTimes.get();
    }

    public void setAvailableTimes(String value) {
        availableTimes.set(value);
    }

    public StringProperty availableTimesProperty() {
        return availableTimes;
    }
}
