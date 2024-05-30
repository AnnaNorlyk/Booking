package org.example.booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;




public class InfoScreenController {

    private MainLaunch mainlaunch;

    @FXML
    private Label dateDisplayLabel;

    public void setMainApplication(MainLaunch mainLaunch) {
        this.mainlaunch = mainLaunch;
    }

    @FXML
    private void handleBookLokaleButton(ActionEvent actionEvent) {
        mainlaunch.showAvailabilityScreen();
    }

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


    public void initialize() {
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        facilitiesColumn.setCellValueFactory(new PropertyValueFactory<>("facilities"));
        roomUsageColumn.setCellValueFactory(new PropertyValueFactory<>("roomUsage"));

        populateTableView();
    }

    private void populateTableView(){
        try{
            showRooms();
            displayBookingsTable.getItems().clear();
            displayBookingsTable.getItems().addAll(rooms);
        }catch(Exception e){
            System.out.println("error");
        }
    }



    private List<Room> rooms = new ArrayList<>();
    public void showRooms() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAccess.getConnection();
            String query = "{call spGetAllRooms}";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                int RoomID = resultSet.getInt("fldRoomID");
                String RoomName = resultSet.getString("fldRoomName");
                int Capacity = resultSet.getInt("fldCapacity");
                String facilities = resultSet.getString("fldFacilities");
                int RoomUsage = resultSet.getInt("fldRoomUsage");

                Room room = new Room(RoomID, RoomName, Capacity, facilities, RoomUsage);
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error:");
        }
    }





    public List<Room>getRooms(){
        return rooms;
    }


}


