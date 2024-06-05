
package org.example.booking;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;




public class InfoScreenController {

    private MainLaunch mainlaunch;


    @FXML
    private TableView<Room> displayBookingsTable;

    @FXML
    private TableColumn<Room, String> ReportErrorColumn;
    @FXML
    private TableColumn<Room, String> roomNameColumn;
    @FXML
    private TableColumn<Room, Integer> refreshmentsColumn;
    @FXML
    private TableColumn<Room, Integer> capacityColumn;
    @FXML
    private TableColumn<Room, String> titleColumn;
    @FXML
    private TableColumn<Room, String> responsibleColumn;
    @FXML
    private TableColumn<Room, String> timeRangeColumn;

    public void setMainApplication(MainLaunch mainLaunch) {
        this.mainlaunch = mainLaunch;
    }

    @FXML
    private void handleBookLokaleButton(ActionEvent actionEvent) {
        mainlaunch.showAvailabilityScreen();
    }


    public void initialize() {
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        refreshmentsColumn.setCellValueFactory(new PropertyValueFactory<>("refreshments"));
        timeRangeColumn.setCellValueFactory(new PropertyValueFactory<>("timeRange"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("roomTitle"));
        ReportErrorColumn.setCellValueFactory(new PropertyValueFactory<>("issueDescription")); // Update this line
        responsibleColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        populateTableView();
    }

    private void populateTableView() {
        BookingDAO bookdao = new BookingDAO();
        try {
            bookdao.getThoseRooms();
            List<Room> rooms = bookdao.getRooms();

            // Clear the table view and add the updated list of rooms
            displayBookingsTable.getItems().clear();
            displayBookingsTable.getItems().addAll(rooms);

            // Refresh the TableView
            displayBookingsTable.refresh();
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}