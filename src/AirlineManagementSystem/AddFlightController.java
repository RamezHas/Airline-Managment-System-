package AirlineManagementSystem;

import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddFlightController {

    @FXML
    private Label AddFlightLabel;
    @FXML
    private TextField AddFlightFlightCodeTextField;
    @FXML
    private TextField AddFlightFlightNameTextField;
    @FXML
    private TextField AddFlightFlightSourceTextField;
    @FXML
    private TextField AddFlightFlightDestinationTextField;
    @FXML
    private TextField AddFlightFlightCapacityTextField;
    @FXML
    private Button AddFlightClearAllButton;
    @FXML
    private Button AddFlightButton;
    @FXML
    private Button AddFlightCloseButton;
    @FXML
    private DatePicker AddFlightFlightDateOfJourneyDatePicker;

    // Clears all fields when the clear button is clicked
    @FXML
    private void clearAll(ActionEvent event) {
        AddFlightFlightCodeTextField.setText("");
        AddFlightFlightNameTextField.setText("");
        AddFlightFlightSourceTextField.setText("");
        AddFlightFlightDestinationTextField.setText("");
        AddFlightFlightCapacityTextField.setText("");
        AddFlightFlightDateOfJourneyDatePicker.setValue(LocalDate.now());
    }

    // Adds a flight after validation
    @FXML
    private void addFlight(ActionEvent event) {

        String flightCode = AddFlightFlightCodeTextField.getText();
        String flightName = AddFlightFlightNameTextField.getText();
        String flightSource = AddFlightFlightSourceTextField.getText();
        String flightDestination = AddFlightFlightDestinationTextField.getText();
        String flightCapacityStr = AddFlightFlightCapacityTextField.getText();
        LocalDate flightDateOfJourney = AddFlightFlightDateOfJourneyDatePicker.getValue();

        if (flightCode.isEmpty() || flightName.isEmpty() || flightSource.isEmpty() || flightDestination.isEmpty() || flightCapacityStr.isEmpty() || flightDateOfJourney == null) {
            // Show error message
            System.out.println("Please fill all fields");
            return;
        }

        int flightCapacity;
        try {
            flightCapacity = Integer.parseInt(flightCapacityStr);
        } catch (NumberFormatException e) {
            // Show error message
            System.out.println("Invalid capacity");
            return;
        }

        // Insert flight into database
        String sql = "INSERT INTO flights (flight_code, flight_name, flight_source, flight_destination, flight_capacity, flight_date_of_journey) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flightCode);
            pstmt.setString(2, flightName);
            pstmt.setString(3, flightSource);
            pstmt.setString(4, flightDestination);
            pstmt.setInt(5, flightCapacity);
            pstmt.setDate(6, java.sql.Date.valueOf(flightDateOfJourney));

            pstmt.executeUpdate();
            System.out.println("Flight added successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    // Closes the current window when the close button is clicked
    @FXML
    private void close(ActionEvent event) {
        Stage stage1 = (Stage) AddFlightCloseButton.getScene().getWindow();
        stage1.close();
    }
}
