package org.example.booking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingDetailsController {

    public javafx.scene.control.Label selectedRoomLabel;
    public javafx.scene.control.Label availableTimeRangeLabel;

    private BookingDAO bookingDAO;
    private MainLaunch mainLaunch;
    private Room selectedRoom;

    @FXML
    private Button returnButton;
    @FXML
    private TableView<Room> facilitiesTable;

    @FXML
    private TableView<Room> capacityTable;
    @FXML
    public TableView<Room> errorsTable;
    @FXML
    private TextField uniloginField;
    @FXML
    private ChoiceBox<String> startTimeMenu;
    @FXML
    private ChoiceBox<String> endTimeMenu;
    @FXML
    private TextField nameField;
    @FXML
    private TextField titleField;
    @FXML
    private Button bookRoomButton;
    @FXML
    private ListView<String> facilitiesListView;
    @FXML
    private ListView<String> capacityListView;
    @FXML
    private ListView<String> errorsListView;

    public void setMainApplication(MainLaunch mainLaunch) {

        this.mainLaunch = mainLaunch;
    }

    @FXML
    public void initialize() {
        bookingDAO = new BookingDAO();  // Initialize BookingDAO
        bookingDAO.getThoseRooms(); // Load rooms from database
        initializeLists();

        //Sets a listener
        startTimeMenu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateEndTime(newValue);
            }
        });

        bookRoomButton.setOnAction(this::handleBookRoomButton);
    }

    private void initializeLists() {

        facilitiesListView.setItems(FXCollections.observableArrayList());
        capacityListView.setItems(FXCollections.observableArrayList());
        errorsListView.setItems(FXCollections.observableArrayList());
    }

    private void loadRoomDetails() {
        if (selectedRoom != null) {
            // Debugging
            System.out.println("Room name: " + selectedRoom.getRoomName());
            System.out.println("Room ID: " + selectedRoom.getRoomID());
            System.out.println("Room capacity: " + selectedRoom.getCapacity());

            // Split string into substrings to display in list
            List<String> facilities = Arrays.asList(selectedRoom.getFacilities().split(","));
            facilitiesListView.setItems(FXCollections.observableArrayList(facilities));

            // Convert SQL value of int to a string
            String capacityToString = String.valueOf(selectedRoom.getCapacity());
            capacityListView.setItems(FXCollections.observableArrayList(capacityToString));

            // Retrieve list of potential issues in the room
            List<String> errors = new ArrayList<>();
            errorsListView.setItems(FXCollections.observableArrayList(errors));

            populateStartTime();
        }
    }

    // Handles the onAction for the return button
    public void handleReturnButton(ActionEvent actionEvent) {
        try {
            if (mainLaunch != null) {
                mainLaunch.showAvailabilityScreen();
            }
        } catch (Exception e) {
            System.out.println("Error while returning to AvailabilityScreen: " + e.getMessage());
        }
    }

    //uses the selected room and sets label
    public void setSelectedRoom(Room room) {
        this.selectedRoom = room;
        selectedRoomLabel.setText(room.getRoomName());

        loadRoomDetails();
    }

    public void populateStartTime() {
        if (selectedRoom != null) {

            //Retrieves available timeslots from BookingDAO, and populates the StartTime menu choicebox
            List<String> availableTimeSlots = bookingDAO.getRoomTimeSlots(selectedRoom.getRoomName());
            startTimeMenu.setItems(FXCollections.observableArrayList(availableTimeSlots));
            endTimeMenu.setItems(FXCollections.observableArrayList());
        }

    }

    public void populateEndTime(String startTime) {
        if (selectedRoom != null) {
            List<String> availableTimeSlots = bookingDAO.getRoomTimeSlots(selectedRoom.getRoomName());
            List<String> endTimeSlots = new ArrayList<>();

            // Sets a flag to control which times to add
            boolean addSlot = false;
            for (String timeSlot : availableTimeSlots) {
                if (addSlot) {
                    endTimeSlots.add(timeSlot);
                }
                // Sets flag to true when the timeslot matches startTime
                if (timeSlot.equals(startTime)) {
                    addSlot = true;
                }
            }
            endTimeMenu.setItems(FXCollections.observableArrayList(endTimeSlots));
        }
    }

    @FXML
    private void handleBookRoomButton(ActionEvent event) {
        try {
            String unilogin = uniloginField.getText();
            String title = titleField.getText();
            String startTime = startTimeMenu.getValue();
            String endTime = endTimeMenu.getValue();

            // Check if all required fields are filled
            if (selectedRoom == null || unilogin.isEmpty() || title.isEmpty() || startTime == null || endTime == null) {
                System.out.println("Form Error! Please fill in all fields.");
                return;
            }

            // Get the user details based on the unilogin
            User user;
            try {
                user = bookingDAO.getUserDetailsByUnilogin(unilogin);
                nameField.setText(user.getName()); // Set the name field with the retrieved name
            } catch (SQLException e) {
                System.out.println("User Error! User does not exist.");
                return;
            }

            // Ensure we have the selected room's details
            Room room;
            try {
                room = bookingDAO.getRoomByName(selectedRoom.getRoomName());
            } catch (SQLException e) {
                System.out.println("Database Error! Error retrieving room details: " + e.getMessage());
                return;
            }

            if (room == null) {
                System.out.println("Room Error! Room does not exist.");
                return;
            }

            // Debugging: Print room details
            System.out.println("Selected Room Name: " + selectedRoom.getRoomName());
            System.out.println("Retrieved Room Name: " + room.getRoomName());
            System.out.println("Booking Room ID: " + room.getRoomID());
            System.out.println("Booking Room Capacity: " + room.getCapacity());

            // Add the booking and increment room usage
            bookingDAO.addBooking(room.getRoomID(), user.getUserID(), new java.util.Date(), Time.valueOf(startTime + ":00"), Time.valueOf(endTime + ":00"), title);

            System.out.println("Booking Success! Booking successfully created.");
        } catch (SQLException e) {
            System.out.println("Database Error! Error while booking the room: " + e.getMessage());
        }
    }


}


