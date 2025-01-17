package AirlineManagementSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class BookTicketController {

    @FXML
    private Label BookTicketLabel;
    @FXML
    private ComboBox<String> BookTicketComboBox;
    @FXML
    private Button BookTicketBookTicketButton;
    @FXML
    private Button BookTicketCloseButton;
    @FXML
    private TextField BookTicketNationalityTextField;
    @FXML
    private TextField BookTicketPhoneNoTextField;
    @FXML
    private TextField BookTicketPassportNoTextField;
    @FXML
    private TextField BookTicketCardNumberTextField;
    @FXML
    private TextField BookTicketNameTextField;
    @FXML
    private Label BookTicketPassengerDetailsLabel;
    @FXML
    private Label BookTicketPaymentDetailsLabel;
    @FXML
    private DatePicker BookTicketPaymentDateDatePicker;
    @FXML
    private TextField BookTicketAmountTextField;
    @FXML
    private Label BookTicketPaymentDateLabel;
    @FXML
    private Label BookTicketAmountLabel;
    @FXML
    private TableView<Flight> BookTicketTable;
    @FXML
    private TableColumn<Flight, String> BookTicketFlightCodeColumn;
    @FXML
    private TableColumn<Flight, String> BookTicketNameColumn;
    @FXML
    private TableColumn<Flight, String> BookTicketSourceColumn;
    @FXML
    private TableColumn<Flight, String> BookTicketDestinationColumn;
    @FXML
    private TableColumn<Flight, Integer> BookTicketCapacityColumn;
    @FXML
    private TableColumn<Flight, LocalDate> BookTicketDateOfJourneyColumn;

    private ObservableList<Flight> flightList = FXCollections.observableArrayList();
    private ObservableList<String> flightCodeList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        BookTicketFlightCodeColumn.setCellValueFactory(new PropertyValueFactory<>("flightCode"));
        BookTicketNameColumn.setCellValueFactory(new PropertyValueFactory<>("flightName"));
        BookTicketSourceColumn.setCellValueFactory(new PropertyValueFactory<>("flightSource"));
        BookTicketDestinationColumn.setCellValueFactory(new PropertyValueFactory<>("flightDestination"));
        BookTicketCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("flightCapacity"));
        BookTicketDateOfJourneyColumn.setCellValueFactory(new PropertyValueFactory<>("flightDateOfJourney"));

        loadFlightData();

        BookTicketComboBox.setOnAction(event -> updatePaymentDetails());
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
                flightCodeList.add(flightCode);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        BookTicketTable.setItems(flightList);
        BookTicketComboBox.setItems(flightCodeList);
    }

    private void updatePaymentDetails() {
        String selectedFlightCode = BookTicketComboBox.getValue();
        if (selectedFlightCode != null) {
            // Enable the payment date and amount fields
            BookTicketPaymentDateDatePicker.setDisable(false);
            BookTicketAmountTextField.setDisable(false);

            // Set the payment date to the current date
            BookTicketPaymentDateDatePicker.setValue(LocalDate.now());

            // Set a default amount (this can be customized as needed)
            BookTicketAmountTextField.setText("100.00");
        }
    }

    @FXML
    private void bookTicket(ActionEvent event) {
        String selectedFlightCode = BookTicketComboBox.getValue();
        String passengerName = BookTicketNameTextField.getText();
        String passengerPhone = BookTicketPhoneNoTextField.getText();
        String passengerNationality = BookTicketNationalityTextField.getText();
        String passengerPassport = BookTicketPassportNoTextField.getText();
        String cardNumber = BookTicketCardNumberTextField.getText();
        LocalDate paymentDate = BookTicketPaymentDateDatePicker.getValue();
        String amountStr = BookTicketAmountTextField.getText();

        if (selectedFlightCode == null || passengerName.isEmpty() || passengerPhone.isEmpty() || passengerNationality.isEmpty() || passengerPassport.isEmpty() || cardNumber.isEmpty() || paymentDate == null || amountStr.isEmpty()) {
            System.out.println("Please fill all fields");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount");
            return;
        }

        // Insert passenger details into the database
        String passengerSql = "INSERT INTO passengers (passenger_name, passenger_phone, passenger_nationality, passenger_passport, flight_code) VALUES (?, ?, ?, ?, ?)";
        String paymentSql = "INSERT INTO payments (passenger_id, card_number, payment_date, amount) VALUES (?, ?, ?, ?)";
        String ticketSql = "INSERT INTO tickets (flight_code, passenger_id) VALUES (?, ?)";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement passengerPstmt = conn.prepareStatement(passengerSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement paymentPstmt = conn.prepareStatement(paymentSql);
             PreparedStatement ticketPstmt = conn.prepareStatement(ticketSql)) {

            // Insert passenger details
            passengerPstmt.setString(1, passengerName);
            passengerPstmt.setString(2, passengerPhone);
            passengerPstmt.setString(3, passengerNationality);
            passengerPstmt.setString(4, passengerPassport);
            passengerPstmt.setString(5, selectedFlightCode);
            passengerPstmt.executeUpdate();

            // Get the generated passenger ID
            ResultSet generatedKeys = passengerPstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int passengerId = generatedKeys.getInt(1);

                // Insert payment details
                paymentPstmt.setInt(1, passengerId);
                paymentPstmt.setString(2, cardNumber);
                paymentPstmt.setDate(3, java.sql.Date.valueOf(paymentDate));
                paymentPstmt.setDouble(4, amount);
                paymentPstmt.executeUpdate();

                // Insert ticket details
                ticketPstmt.setString(1, selectedFlightCode);
                ticketPstmt.setInt(2, passengerId);
                ticketPstmt.executeUpdate();

                System.out.println("Ticket booked successfully for flight code: " + selectedFlightCode);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) BookTicketCloseButton.getScene().getWindow();
        stage.close();
    }
}