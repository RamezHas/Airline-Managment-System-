package AirlineManagementSystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CancelTicketController {

    @FXML
    private ComboBox<String> CancelTicketComboBox;
    @FXML
    private Button CancelTicketButton;
    @FXML
    private Button CancelTicketCloseButton;
    @FXML
    private Label CancelTicketStatusLabel;
    @FXML
    private TextField CancelTicketPassengerNameTextField;
    @FXML
    private TextField CancelTicketPassengerPhoneTextField;
    @FXML
    private TextField CancelTicketPassengerNationalityTextField;
    @FXML
    private TextField CancelTicketPassengerPassportTextField;

    @FXML
    public void initialize() {
        loadTicketData();
        CancelTicketComboBox.setOnAction(event -> loadPassengerDetails());
    }

    private void loadTicketData() {
        String sql = "SELECT ticket_id FROM tickets";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String ticketId = rs.getString("ticket_id");
                CancelTicketComboBox.getItems().add(ticketId);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadPassengerDetails() {
        String selectedTicketId = CancelTicketComboBox.getValue();
        if (selectedTicketId == null) {
            return;
        }

        String sql = "SELECT p.passenger_name, p.passenger_phone, p.passenger_nationality, p.passenger_passport " +
                     "FROM passengers p JOIN tickets t ON p.passenger_id = t.passenger_id WHERE t.ticket_id = ?";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, selectedTicketId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String passengerName = rs.getString("passenger_name");
                String passengerPhone = rs.getString("passenger_phone");
                String passengerNationality = rs.getString("passenger_nationality");
                String passengerPassport = rs.getString("passenger_passport");

                CancelTicketPassengerNameTextField.setText(passengerName);
                CancelTicketPassengerPhoneTextField.setText(passengerPhone);
                CancelTicketPassengerNationalityTextField.setText(passengerNationality);
                CancelTicketPassengerPassportTextField.setText(passengerPassport);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void cancelTicket(ActionEvent event) {
        String selectedTicketId = CancelTicketComboBox.getValue();
        if (selectedTicketId == null) {
            CancelTicketStatusLabel.setText("Please select a ticket ID");
            return;
        }

        String getPassengerIdSql = "SELECT passenger_id FROM tickets WHERE ticket_id = ?";
        String deleteTicketSql = "DELETE FROM tickets WHERE ticket_id = ?";
        String deletePaymentSql = "DELETE FROM payments WHERE passenger_id = ?";
        String deletePassengerSql = "DELETE FROM passengers WHERE passenger_id = ?";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement getPassengerIdStmt = conn.prepareStatement(getPassengerIdSql);
             PreparedStatement deleteTicketStmt = conn.prepareStatement(deleteTicketSql);
             PreparedStatement deletePaymentStmt = conn.prepareStatement(deletePaymentSql);
             PreparedStatement deletePassengerStmt = conn.prepareStatement(deletePassengerSql)) {

            // Get the passenger ID associated with the ticket
            getPassengerIdStmt.setString(1, selectedTicketId);
            ResultSet rs = getPassengerIdStmt.executeQuery();
            if (rs.next()) {
                int passengerId = rs.getInt("passenger_id");

                // Delete the ticket
                deleteTicketStmt.setString(1, selectedTicketId);
                int ticketRowsAffected = deleteTicketStmt.executeUpdate();

                // Delete the payment
                deletePaymentStmt.setInt(1, passengerId);
                int paymentRowsAffected = deletePaymentStmt.executeUpdate();

                // Delete the passenger
                deletePassengerStmt.setInt(1, passengerId);
                int passengerRowsAffected = deletePassengerStmt.executeUpdate();

                if (ticketRowsAffected > 0 && paymentRowsAffected > 0 && passengerRowsAffected > 0) {
                    CancelTicketStatusLabel.setText("Ticket, payment, and passenger details cancelled successfully");
                    CancelTicketComboBox.getItems().remove(selectedTicketId);
                    CancelTicketPassengerNameTextField.clear();
                    CancelTicketPassengerPhoneTextField.clear();
                    CancelTicketPassengerNationalityTextField.clear();
                    CancelTicketPassengerPassportTextField.clear();
                } else {
                    CancelTicketStatusLabel.setText("Ticket cancellation failed");
                }
            } else {
                CancelTicketStatusLabel.setText("Passenger not found for the selected ticket");
            }

        } catch (SQLException e) {
            CancelTicketStatusLabel.setText("Error cancelling ticket");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) CancelTicketCloseButton.getScene().getWindow();
        stage.close();
    }
}