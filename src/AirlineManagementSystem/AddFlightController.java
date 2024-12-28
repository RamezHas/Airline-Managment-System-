package AirlineManagementSystem;

import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Button AddFlight;
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
    @SuppressWarnings("unused")
    @FXML
    private void AddFlight(ActionEvent event) {
        String f_code = AddFlightFlightCodeTextField.getText();
        String f_name = AddFlightFlightNameTextField.getText();
        String f_source = AddFlightFlightSourceTextField.getText();
        String f_destination = AddFlightFlightDestinationTextField.getText();
        String f_capacity = AddFlightFlightCapacityTextField.getText();
        String f_dateofjourney = (AddFlightFlightDateOfJourneyDatePicker.getValue()).toString();

        // Check if any field is empty
        if (f_code.isEmpty() || f_name.isEmpty() || f_source.isEmpty() || f_destination.isEmpty() || f_capacity.isEmpty() || f_dateofjourney.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Do Not Leave Any Field Empty");
            alert.showAndWait();
            return;
        }

        // Validate capacity is a valid number
        try {
            int capacity = Integer.parseInt(f_capacity);
            if (capacity <= 0) {
                throw new NumberFormatException("Capacity must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid capacity (positive integer).");
            alert.showAndWait();
            return;
        }

        // Create the new flight object
        Flight newFlight = new Flight(f_code, f_name, f_source, f_destination, Integer.parseInt(f_capacity), f_dateofjourney);

        // Here you would save the flight to a database or list
        // flightService.addFlight(newFlight); // Example method for adding flight

        // Show success alert
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setHeaderText(null);
        successAlert.setContentText("Flight added successfully!");
        successAlert.showAndWait();

        // Optionally reset the form after successful submission
        clearAll(event);
    }

    // Closes the current window when the close button is clicked
    @FXML
    private void close(ActionEvent event) {
        Stage stage1 = (Stage) AddFlightCloseButton.getScene().getWindow();
        stage1.close();
    }
}
