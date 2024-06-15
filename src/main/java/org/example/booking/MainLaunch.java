package org.example.booking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainLaunch extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showInfoScreen();
    }

    public void showInfoScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/booking/InfoScreen.fxml"));
            Parent root = loader.load();

            infoScreenController controller = loader.getController();
            controller.setMainApplication(this);

            Scene scene = new Scene(root, 1200, 800);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Infoscreen");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAvailabilityScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/booking/AvailabilityScreen.fxml"));
            Parent root = loader.load();
            AvailabilityController controller = loader.getController();
            controller.setMainApplication(this);
            primaryStage.setScene(new Scene(root, 1200, 800));
            primaryStage.setTitle("Check Availability");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBookingDetails(Room roomSelected) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/booking/BookingDetails.fxml"));
            Parent root = loader.load();

            BookingDetailsController controller = loader.getController();
            controller.setMainApplication(this);
            controller.setSelectedRoom(roomSelected);

            primaryStage.setScene(new Scene(root, 1200, 800));
            primaryStage.setTitle("Booking Details");
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Error loading booking details screen: " + e.getMessage());
        }
    }

    public void showErrorPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/booking/ErrorPage.fxml"));
            Parent root = loader.load();
            ErrorPageController controller = loader.getController();
            controller.setMainApplication(this);
            primaryStage.setScene(new Scene(root, 1200, 800));
            primaryStage.setTitle("Report Issue");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}