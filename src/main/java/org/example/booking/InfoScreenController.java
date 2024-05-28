package org.example.booking;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import java.awt.*;

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


}
