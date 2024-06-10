
package org.example.booking;
import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


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
        responsibleColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        // custom factory for refreshments
        refreshmentsColumn.setCellFactory(column -> new TextFieldTableCell<Room, Integer>() {
            @Override
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item == 0) {
                    setText("");
                } else {
                    setText("X");
                }
            }
        });

        // custom factory for issues
        ReportErrorColumn.setCellFactory(column -> new TextFieldTableCell<Room, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
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