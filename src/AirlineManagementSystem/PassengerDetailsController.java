package AirlineManagementSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PassengerDetailsController {

    @FXML
    private Button PassengerDetailsCloseButton;
    @FXML
    private TableView<Passenger> PassengerDetailsTable;
    @FXML
    private TableColumn<Passenger, Integer> PassengerDetailsPNRColumn;
    @FXML
    private TableColumn<Passenger, String> PassengerDetailsNameColumn;
    @FXML
    private TableColumn<Passenger, String> PassengerDetailsPhoneColumn;
    @FXML
    private TableColumn<Passenger, String> PassengerDetailsNationalityColumn;
    @FXML
    private TableColumn<Passenger, String> PassengerDetailsPassportColumn;
    @FXML
    private TableColumn<Passenger, String> PassengerDetailsFlightCodeColumn;

    private ObservableList<Passenger> passengerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        PassengerDetailsPNRColumn.setCellValueFactory(new PropertyValueFactory<>("passengerId"));
        PassengerDetailsNameColumn.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
        PassengerDetailsPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("passengerPhone"));
        PassengerDetailsNationalityColumn.setCellValueFactory(new PropertyValueFactory<>("passengerNationality"));
        PassengerDetailsPassportColumn.setCellValueFactory(new PropertyValueFactory<>("passengerPassport"));
        PassengerDetailsFlightCodeColumn.setCellValueFactory(new PropertyValueFactory<>("flightCode"));

        loadPassengerData();
    }

    private void loadPassengerData() {
        String sql = "SELECT * FROM passengers";

        try (Connection conn = MySQLConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int passengerId = rs.getInt("passenger_id");
                String passengerName = rs.getString("passenger_name");
                String passengerPhone = rs.getString("passenger_phone");
                String passengerNationality = rs.getString("passenger_nationality");
                String passengerPassport = rs.getString("passenger_passport");
                String flightCode = rs.getString("flight_code");

                Passenger passenger = new Passenger(passengerId, passengerName, passengerPhone, passengerNationality, passengerPassport, flightCode);
                passengerList.add(passenger);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        PassengerDetailsTable.setItems(passengerList);
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) PassengerDetailsCloseButton.getScene().getWindow();
        stage.close();
    }
}