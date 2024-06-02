package org.example.booking;
import java.util.List;
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
    private TableColumn<Room, Integer> roomIDColumn;

    @FXML
    private TableColumn<Room, String> roomNameColumn;

    @FXML
    private TableColumn<Room, Integer> capacityColumn;

    @FXML
    private TableColumn<Room, String> facilitiesColumn;

    @FXML
    private TableColumn<Room, Integer> roomUsageColumn;

    public void setMainApplication(MainLaunch mainLaunch) {
        this.mainlaunch = mainLaunch;
    }

    @FXML
    private void handleBookLokaleButton(ActionEvent actionEvent) {
        mainlaunch.showAvailabilityScreen();
    }


//    public void initialize() {
//        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
//        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
//        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
//        facilitiesColumn.setCellValueFactory(new PropertyValueFactory<>("facilities"));
//        roomUsageColumn.setCellValueFactory(new PropertyValueFactory<>("roomUsage"));
//
//        populateTableView();
//    }

//    private void populateTableView() {
//        BookingDAO bookdao = new BookingDAO();
//        try {
//
//            bookdao.getThoseRooms();
//            List<Room> rooms = bookdao.getRooms();
//            displayBookingsTable.getItems().clear();
//            displayBookingsTable.getItems().addAll(rooms);
//
//        } catch (Exception e) {
//            System.out.println("error" );
//        }
//
//
//    }
}


