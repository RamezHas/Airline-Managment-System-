package AirlineManagementSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AirlineHomeController {

    @FXML
    public void addFlight(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AddFlight.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Add Flight Page");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modifyFlight(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ModifyFlight.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Flight Page");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeFlight(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("RemoveFlight.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Remove Flight Page");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void bookTicket(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("BookTicket.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Remove Flight Page");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelTicket() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CancelTicket.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Cancel Ticket Page");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void paymentDetails() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("PaymentDetails.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Payment Details Page");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void passengerDetails() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("PassengerDetails.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Passenger Details Page");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showFlightDetails() {
        System.out.println("Show Flight Details tab selected");
    }
}
