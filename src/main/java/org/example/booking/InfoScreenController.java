package org.example.booking;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class InfoScreenController {

    private MainLaunch mainlaunch;



    public void setMainApplication(MainLaunch mainLaunch) {
        this.mainlaunch = mainLaunch;
    }

    @FXML
    private void handleBookLokaleButton(ActionEvent actionEvent) {
        mainlaunch.showAvailabilityScreen();
    }


}
