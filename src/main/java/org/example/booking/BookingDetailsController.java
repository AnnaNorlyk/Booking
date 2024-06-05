package org.example.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class BookingDetailsController {
    private BookingDAO bookingDAO;
    private MainLaunch mainLaunch;

    public void setMainApplication(MainLaunch mainLaunch) {
        this.mainLaunch = mainLaunch;
    }
    @FXML
    public void initialize() {
        bookingDAO = new BookingDAO();  // Initialize BookingDAO
    }

    // Handles the onAction for the return button
    public void handleReturnButton(ActionEvent actionEvent) {
        try {
            if (mainLaunch != null) {
                mainLaunch.showAvailabilityScreen();
            }
        } catch (Exception e) {
            System.out.println("Error while returning to AvailabilityScreen: " + e.getMessage());
        }
    }
}
