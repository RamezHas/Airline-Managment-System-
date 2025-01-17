package AirlineManagementSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;


public class AirlineLoginController {

    @FXML
    private TextField LoginUsernameTextField;

    @FXML
    private PasswordField LoginPasswordPasswordField;

    @FXML
    private Label LoginStatusLabel;

    @FXML
    private Button LoginLoginButton;

    @FXML
    public void Login(ActionEvent event) {
        String username = LoginUsernameTextField.getText();
        String password = LoginPasswordPasswordField.getText();

        if ("admin".equals(username) && "pass".equals(password)) {
            try {
                LoginStatusLabel.setText("Login Successful");
                Parent root = FXMLLoader.load(getClass().getResource("AirlineHome.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Airline Management System - Home");
                stage.setScene(new Scene(root));
                stage.show();
                Stage stage1= (Stage)LoginLoginButton.getScene().getWindow();
                stage1.close();

            } catch (IOException e) {
                LoginStatusLabel.setText("Error loading home screen.");
                e.printStackTrace();
            }
        } else {
            LoginStatusLabel.setText("Login Failed");
        }
    }



    @FXML
    private void Close() {
        System.exit(0);
    }
}
