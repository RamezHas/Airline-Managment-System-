package AirlineManagementSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PaymentDetailsController {

    @FXML
    private Label PaymentDetailsLabel;
    @FXML
    private Button PaymentDetailsCloseButton;
    @FXML
    private TableView<Payment> PaymentDetailsTable;
    @FXML
    private TableColumn<Payment, Integer> PaymentDetailsPNRColumn;
    @FXML
    private TableColumn<Payment, String> PaymentDetailsPhoneColumn;
    @FXML
    private TableColumn<Payment, String> PaymentDetailsCardColumn;
    @FXML
    private TableColumn<Payment, Double> PaymentDetailsAmountColumn;
    @FXML
    private TableColumn<Payment, LocalDate> PaymentDetailsPaymentDateColumn;

    private ObservableList<Payment> paymentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        PaymentDetailsPNRColumn.setCellValueFactory(new PropertyValueFactory<>("pnrNo"));
        PaymentDetailsPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        PaymentDetailsCardColumn.setCellValueFactory(new PropertyValueFactory<>("cardNo"));
        PaymentDetailsAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        PaymentDetailsPaymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        loadPaymentData();
    }

    private void loadPaymentData() {
        String sql = "SELECT * FROM payments";

        try (Connection conn = MySQLConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int pnrNo = rs.getInt("payment_id");
                String phoneNo = rs.getString("passenger_id");
                String cardNo = rs.getString("card_number");
                double amountPaid = rs.getDouble("amount");
                LocalDate paymentDate = rs.getDate("payment_date").toLocalDate();

                Payment payment = new Payment(pnrNo, phoneNo, cardNo, amountPaid, paymentDate);
                paymentList.add(payment);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        PaymentDetailsTable.setItems(paymentList);
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) PaymentDetailsCloseButton.getScene().getWindow();
        stage.close();
    }
}