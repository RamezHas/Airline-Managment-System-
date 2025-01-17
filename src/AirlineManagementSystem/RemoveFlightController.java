package AirlineManagementSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveFlightController {

    @FXML private ComboBox<String> RemoveFlightComboBox;
    @FXML private TextField RemoveFlightFlightNameTextField;
    @FXML private TextField RemoveFlightFlightSourceTextField;
    @FXML private TextField RemoveFlightFlightDestinationTextField;
    @FXML private TextField RemoveFlightFlightCapacityTextField;
    @FXML private TextField RemoveFlightFlightDateOfJourneyTextField;
    @FXML private Button RemoveFlightRemoveFlightButton;
    @FXML private Button RemoveFlightCloseButton;

    @FXML
    public void initialize() {
        loadFlightData();
        RemoveFlightComboBox.setOnAction(event -> loadFlightDetails());
    }

    private void loadFlightData() {
        String sql = "SELECT flight_code FROM airline_db.flights";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String flightCode = rs.getString("flight_code");
                RemoveFlightComboBox.getItems().add(flightCode);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadFlightDetails() {
        String selectedFlightCode = RemoveFlightComboBox.getValue();
        if (selectedFlightCode == null) {
            return;
        }

        String sql = "SELECT * FROM airline_db.flights WHERE flight_code = ?";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, selectedFlightCode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String flightName = rs.getString("flight_name");
                String flightSource = rs.getString("flight_source");
                String flightDestination = rs.getString("flight_destination");
                int flightCapacity = rs.getInt("flight_capacity");
                String flightDateOfJourney = rs.getString("flight_date_of_journey");

                RemoveFlightFlightNameTextField.setText(flightName);
                RemoveFlightFlightSourceTextField.setText(flightSource);
                RemoveFlightFlightDestinationTextField.setText(flightDestination);
                RemoveFlightFlightCapacityTextField.setText(String.valueOf(flightCapacity));
                RemoveFlightFlightDateOfJourneyTextField.setText(flightDateOfJourney);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void removeFlight(ActionEvent event) {
        String selectedFlightCode = RemoveFlightComboBox.getValue();
        if (selectedFlightCode == null) {
            System.out.println("Please select a flight code");
            return;
        }

        String deletePaymentsSql = "DELETE FROM airline_db.payments WHERE passenger_id IN (SELECT passenger_id FROM airline_db.passengers WHERE flight_code = ?)";
        String deletePassengersSql = "DELETE FROM airline_db.passengers WHERE flight_code = ?";
        String deleteFlightSql = "DELETE FROM airline_db.flights WHERE flight_code = ?";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement deletePaymentsStmt = conn.prepareStatement(deletePaymentsSql);
             PreparedStatement deletePassengersStmt = conn.prepareStatement(deletePassengersSql);
             PreparedStatement deleteFlightStmt = conn.prepareStatement(deleteFlightSql)) {

            // Delete associated payments
            deletePaymentsStmt.setString(1, selectedFlightCode);
            deletePaymentsStmt.executeUpdate();

            // Delete associated passengers
            deletePassengersStmt.setString(1, selectedFlightCode);
            deletePassengersStmt.executeUpdate();

            // Delete the flight
            deleteFlightStmt.setString(1, selectedFlightCode);
            int rowsAffected = deleteFlightStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Flight removed successfully");
                RemoveFlightComboBox.getItems().remove(selectedFlightCode);
                RemoveFlightFlightNameTextField.clear();
                RemoveFlightFlightSourceTextField.clear();
                RemoveFlightFlightDestinationTextField.clear();
                RemoveFlightFlightCapacityTextField.clear();
                RemoveFlightFlightDateOfJourneyTextField.clear();
            } else {
                System.out.println("Flight removal failed");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) RemoveFlightCloseButton.getScene().getWindow();
        stage.close();
    }
}