package AirlineManagementSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModifyFlightController {

    @FXML private ComboBox<String> ModifyFlightComboBox;
    @FXML private TextField ModifyFlightFlightNameTextField;
    @FXML private TextField ModifyFlightFlightSourceTextField;
    @FXML private TextField ModifyFlightFlightDestinationTextField;
    @FXML private TextField ModifyFlightFlightCapacityTextField;
    @FXML private DatePicker ModifyFlightFlightDateOfJourneyDatePicker;
    @FXML private Button ModifyFlightModifyFlightButton;
    @FXML private Button ModifyFlightCloseButton;
    @FXML private Label ModifyFlightStatusLabel;

    @FXML
    public void initialize() {
        loadFlightData();
        ModifyFlightComboBox.setOnAction(event -> loadFlightDetails());
    }

    private void loadFlightData() {
        String sql = "SELECT flight_code FROM flights";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String flightCode = rs.getString("flight_code");
                ModifyFlightComboBox.getItems().add(flightCode);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadFlightDetails() {
        String selectedFlightCode = ModifyFlightComboBox.getValue();
        if (selectedFlightCode == null) {
            return;
        }

        String sql = "SELECT * FROM flights WHERE flight_code = ?";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, selectedFlightCode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String flightName = rs.getString("flight_name");
                String flightSource = rs.getString("flight_source");
                String flightDestination = rs.getString("flight_destination");
                int flightCapacity = rs.getInt("flight_capacity");
                LocalDate flightDateOfJourney = rs.getDate("flight_date_of_journey").toLocalDate();

                ModifyFlightFlightNameTextField.setText(flightName);
                ModifyFlightFlightSourceTextField.setText(flightSource);
                ModifyFlightFlightDestinationTextField.setText(flightDestination);
                ModifyFlightFlightCapacityTextField.setText(String.valueOf(flightCapacity));
                ModifyFlightFlightDateOfJourneyDatePicker.setValue(flightDateOfJourney);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void modifyFlight(ActionEvent event) {
        String selectedFlightCode = ModifyFlightComboBox.getValue();
        String flightName = ModifyFlightFlightNameTextField.getText();
        String flightSource = ModifyFlightFlightSourceTextField.getText();
        String flightDestination = ModifyFlightFlightDestinationTextField.getText();
        String flightCapacityStr = ModifyFlightFlightCapacityTextField.getText();
        LocalDate flightDateOfJourney = ModifyFlightFlightDateOfJourneyDatePicker.getValue();

        if (selectedFlightCode == null || flightName.isEmpty() || flightSource.isEmpty() || flightDestination.isEmpty() || flightCapacityStr.isEmpty() || flightDateOfJourney == null) {
            ModifyFlightStatusLabel.setText("Please fill all fields");
            return;
        }

        int flightCapacity;
        try {
            flightCapacity = Integer.parseInt(flightCapacityStr);
        } catch (NumberFormatException e) {
            ModifyFlightStatusLabel.setText("Invalid capacity");
            return;
        }

        String sql = "UPDATE flights SET flight_name = ?, flight_source = ?, flight_destination = ?, flight_capacity = ?, flight_date_of_journey = ? WHERE flight_code = ?";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flightName);
            pstmt.setString(2, flightSource);
            pstmt.setString(3, flightDestination);
            pstmt.setInt(4, flightCapacity);
            pstmt.setDate(5, java.sql.Date.valueOf(flightDateOfJourney));
            pstmt.setString(6, selectedFlightCode);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                ModifyFlightStatusLabel.setText("Flight details updated successfully");
            } else {
                ModifyFlightStatusLabel.setText("Flight update failed");
            }

        } catch (SQLException e) {
            ModifyFlightStatusLabel.setText("Error updating flight");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) ModifyFlightCloseButton.getScene().getWindow();
        stage.close();
    }
}