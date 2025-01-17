package AirlineManagementSystem;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AirlineHomeController {

    @FXML
    private TableView<Flight> HomeTab2Table;
    @FXML
    private TableColumn<Flight, String> HomeTab2FlightCodeColumn;
    @FXML
    private TableColumn<Flight, String> HomeTab2NameColumn;
    @FXML
    private TableColumn<Flight, String> HomeTab2SourceColumn;
    @FXML
    private TableColumn<Flight, String> HomeTab2DestinationColumn;
    @FXML
    private TableColumn<Flight, Integer> HomeTab2CapacityColumn;
    @FXML
    private TableColumn<Flight, LocalDate> HomeTab2DateOfJourneyColumn;

    private ObservableList<Flight> flightList = FXCollections.observableArrayList();

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
    public void initialize() {
        HomeTab2FlightCodeColumn.setCellValueFactory(new PropertyValueFactory<>("flightCode"));
        HomeTab2NameColumn.setCellValueFactory(new PropertyValueFactory<>("flightName"));
        HomeTab2SourceColumn.setCellValueFactory(new PropertyValueFactory<>("flightSource"));
        HomeTab2DestinationColumn.setCellValueFactory(new PropertyValueFactory<>("flightDestination"));
        HomeTab2CapacityColumn.setCellValueFactory(new PropertyValueFactory<>("flightCapacity"));
        HomeTab2DateOfJourneyColumn.setCellValueFactory(new PropertyValueFactory<>("flightDateOfJourney"));

        loadFlightData();
    }

    private void loadFlightData() {
        String sql = "SELECT * FROM flights";

        try (Connection conn = MySQLConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String flightCode = rs.getString("flight_code");
                String flightName = rs.getString("flight_name");
                String flightSource = rs.getString("flight_source");
                String flightDestination = rs.getString("flight_destination");
                int flightCapacity = rs.getInt("flight_capacity");
                LocalDate flightDateOfJourney = rs.getDate("flight_date_of_journey").toLocalDate();

                Flight flight = new Flight(flightCode, flightName, flightSource, flightDestination, flightCapacity, flightDateOfJourney);
                flightList.add(flight);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        HomeTab2Table.setItems(flightList);
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
