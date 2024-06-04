package org.example.booking;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class AvailabilityController {

    @FXML
    private Button returnButton;
    private MainLaunch mainLaunch;
    @FXML
    private TableView<Room> availabilitiesTable;
    @FXML
    private TableColumn<Room, String> roomColumn;
    @FXML
    private TableColumn<Room, String> facilitiesColumn;
    @FXML
    private TableColumn<Room, String> availableTimeSlotsColumn;

    private BookingDAO bookingDAO;

    @FXML
    public void initialize() {
        bookingDAO = new BookingDAO();  // Initialize BookingDAO

        //Accesses cells for setting info
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        facilitiesColumn.setCellValueFactory(new PropertyValueFactory<>("facilities"));
        availableTimeSlotsColumn.setCellValueFactory(new PropertyValueFactory<>("timeRange"));

        loadRoomAvailabilities(); // Call the method to load room availabilities
    }

    private void loadRoomAvailabilities() {
        ObservableList<Room> roomList = FXCollections.observableArrayList(
                bookingDAO.getAllAvailableTimeSlots()
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
            }
        } catch (Exception e) {
            System.out.println("Error while returning to Infoscreen: " + e.getMessage());
        }
    }
}
