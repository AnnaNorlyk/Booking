package org.example.booking;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

import static java.time.LocalDate.*;

public class AvailabilityController {

    @FXML
    private Button returnButton;
    private MainLaunch mainLaunch;
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

        // Assuming Room class getters for the properties match the names below
        lokaleColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        faciliteterColumn.setCellValueFactory(new PropertyValueFactory<>("facilities"));
        ledigeTiderColumn.setCellValueFactory(new PropertyValueFactory<>("timeRange"));

        loadRoomAvailabilities(); // Call the method to load room availabilities
    }

    private void loadRoomAvailabilities() {
        ObservableList<Room> roomList = FXCollections.observableArrayList(
                bookingDAO.getRoomAvailability(1) // Temp ID for testing
        );
        availabilitiesTable.setItems(roomList);
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
