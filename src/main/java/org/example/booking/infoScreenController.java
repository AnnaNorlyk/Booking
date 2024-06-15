package org.example.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class infoScreenController {

    private MainLaunch mainlaunch;

    @FXML
    private TableView<Room> displayBookingsTable;

    @FXML
    private TableColumn<Room, String> ReportErrorColumn;

    @FXML
    private TableColumn<Room, String> roomNameColumn;

    @FXML
    private TableColumn<Room, String> titleColumn;

    @FXML
    private TableColumn<Room, String> responsibleColumn;

    @FXML
    private TableColumn<Room, String> timeRangeColumn;

    @FXML
    private TableColumn<Room, Integer> refreshmentsColumn;

    @FXML
    private Label DateDisplay;

    public void setMainApplication(MainLaunch mainLaunch) {
        this.mainlaunch = mainLaunch;
    }

    @FXML
    private void handleBookLokaleButton(ActionEvent actionEvent) {
        mainlaunch.showAvailabilityScreen();
    }

    @FXML
    public void handleFejlmeldingsButton(ActionEvent actionEvent) {
        this.mainlaunch.showErrorPage();
    }

    public void initialize() {
        DateDisplay.setText("Dato: " + LocalDate.now());
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        refreshmentsColumn.setCellValueFactory(new PropertyValueFactory<>("refreshments"));
        timeRangeColumn.setCellValueFactory(new PropertyValueFactory<>("timeRange"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("roomTitle"));
        ReportErrorColumn.setCellValueFactory(new PropertyValueFactory<>("issueDescription"));
        responsibleColumn.setCellValueFactory(new PropertyValueFactory<>("actualName"));

        // custom factory for refreshments
        refreshmentsColumn.setCellFactory(column -> new TableCell<Room, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item == 0) {
                    setText("");
                } else {
                    setText("X");
                }
            }
        });

        // custom factory for issues
        ReportErrorColumn.setCellFactory(column -> new TableCell<Room, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.trim().isEmpty()) {
                    setText("");
                } else {
                    setText("X");
                }
            }
        });

        populateTableView();
    }

    private void populateTableView() {
        BookingDAO bookingDAO = new BookingDAO();
        try {
            bookingDAO.getThoseRooms();
            List<Room> rooms = bookingDAO.getRooms();
            displayBookingsTable.getItems().clear();
            displayBookingsTable.getItems().addAll(rooms);
            displayBookingsTable.refresh();
        } catch (Exception e) {
            System.out.println("Error populating TableView: " + e.getMessage());
        }
    }
}
