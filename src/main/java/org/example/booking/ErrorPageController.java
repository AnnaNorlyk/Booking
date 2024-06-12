// ErrorPageController.java
package org.example.booking;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class ErrorPageController {

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField uniloginField;

    @FXML
    private Button doneButton;

    @FXML
    private Button returnButton;

    @FXML
    private Label labelLabel;

    @FXML
    private ComboBox<String> roomComboBox;

    private BookingDAO bookingDAO;

    private MainLaunch mainLaunch;

    public ErrorPageController() {
        this.bookingDAO = new BookingDAO();
    }

    @FXML
    private void initialize() {
        List<String> roomNames = null;
        roomNames = bookingDAO.getAllRoomNames();
        roomComboBox.setItems(FXCollections.observableArrayList(roomNames));
        labelLabel.setVisible(false);
    }

    @FXML
    private void doneButtonaction() {
        try {
            String room = roomComboBox.getValue();
            String description = descriptionField.getText();
            String unilogin = uniloginField.getText();

            if (room == null || room.isEmpty() || description.isEmpty() || unilogin.isEmpty()) {
                setLabelText("Intast oplysninger");
                labelLabel.setVisible(true);
                return;
            }

            bookingDAO.addIssue(room, description, unilogin);
            labelLabel.setVisible(true);
            setLabelText("Fejl oprettet.");

        } catch (Exception e) {
            e.printStackTrace();
            labelLabel.setVisible(true);
            setLabelText("Kæmpe Error");
        }
    }

    private void setLabelText(String text) {
        if (labelLabel != null) {
            Platform.runLater(() -> labelLabel.setText(text));
        } else {
            System.err.println("error");
        }
    }

    public void setMainApplication(MainLaunch mainLaunch) {
        this.mainLaunch = mainLaunch;
    }

    @FXML
    private void handleReturnButton() {
        mainLaunch.showInfoScreen();
    }
}
