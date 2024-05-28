package org.example.booking;

import javafx.scene.control.Button;

import java.awt.event.ActionEvent;

public class AvailabilityController {

    public Button returnButton;
    private MainLaunch mainlaunch;
    public void setMainApplication(MainLaunch mainLaunch) { this.mainlaunch = mainLaunch;}


    public void handleReturnButton(javafx.event.ActionEvent actionEvent) {
        try {
            if (mainlaunch != null) {
                mainlaunch.showInfoScreen();
            } else {
                System.out.println("Error.");
            }
        } catch (Exception e) {
            System.out.println("Error while returning to Infoscreen: " + e.getMessage());
        }
    }
}
