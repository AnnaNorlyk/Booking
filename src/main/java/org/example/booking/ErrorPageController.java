package org.example.booking;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ErrorPageController {

    @FXML
    private TextField roomField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField uniloginField;
    @FXML
    private int issueID;

    @FXML
    private Button doneButton;

    @FXML
    private Button returnButton;
    @FXML
    private Label labelLabel;


    private BookingDAO bookingDAO;

    private MainLaunch mainLaunch;

    public ErrorPageController() {
        this.bookingDAO = new BookingDAO();
    }

    @FXML
    private void initialize() {
        // Any initialization code if needed
    }

    @FXML
    private void doneButtonaction() {
        try {
            String room = roomField.getText();
            String description = descriptionField.getText();
            String unilogin = uniloginField.getText();
            Integer issueID = 1;

            if (room.isEmpty() || description.isEmpty() || unilogin.isEmpty()) {
                // Update label text to display error message
                labelLabel.setText("Alle Felter skal være fyldt ud.");
                return;
            }

            bookingDAO.addIssue(issueID, room, description, unilogin);

            labelLabel.setText("Fejl oprettet.");
        } catch (Exception e) {
            e.printStackTrace();
            // Update label text to display error message
            labelLabel.setText("An error occurred while reporting the issue: " + e.getMessage());
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