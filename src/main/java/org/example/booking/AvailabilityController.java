package org.example.booking;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

import static java.time.LocalDate.*;

public class AvailabilityController {

    @FXML
    private Button returnButton;
    private MainLaunch mainLaunch; // Use consistent naming
    @FXML
    private TableView<Room> availabilitiesTable;
    @FXML
    private TableColumn<Room, String> lokaleColumn;
    @FXML
    private TableColumn<Room, String> faciliteterColumn;
    @FXML
    private TableColumn<Room, String> ledigeTiderColumn;

    private BookingDAO bookingDAO;

    @FXML
    public void initialize() {
        bookingDAO = new BookingDAO();  // Initialize BookingDAO

        lokaleColumn.setCellValueFactory(cellData -> cellData.getValue().roomNameProperty());
        faciliteterColumn.setCellValueFactory(cellData -> cellData.getValue().facilitiesProperty());
        ledigeTiderColumn.setCellValueFactory(cellData -> cellData.getValue().availableTimesProperty());

        loadRoomAvailabilities(); // Call the method to load room availabilities
    }

    private void loadRoomAvailabilities() {
        try {
            // Provide valid parameters for date and roomId
            ObservableList<Room> roomList = FXCollections.observableArrayList(
                    bookingDAO.getRoomAvailability(now().toString(), 1)
            );
            availabilitiesTable.setItems(roomList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setMainApplication(MainLaunch mainLaunch) {
        this.mainLaunch = mainLaunch;
    }

    // Handles the onAction for the return button
    public void handleReturnButton(ActionEvent actionEvent) {
        try {
            if (mainLaunch != null) {
                mainLaunch.showInfoScreen();
            } else {
                System.out.println("Main application context not set.");
            }
        } catch (Exception e) {
            System.out.println("Error while returning to Infoscreen: " + e.getMessage());
        }
    }
}
