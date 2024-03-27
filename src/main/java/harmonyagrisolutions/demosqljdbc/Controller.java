package harmonyagrisolutions.demosqljdbc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField; // Import PasswordField here

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button button_login;
    @FXML
    private Button button_signup;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField tf_password; // Change TextField to PasswordField for password input

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_login.setOnAction(event -> handleLogin(event));
        button_signup.setOnAction(event -> handleSignup(event));
    }

    private void handleLogin(ActionEvent event) {
        try {
            DBUtils.logInUser(event, tf_username.getText(), tf_password.getText());
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace of the caught exception
            // You can also use a logging framework like Log4j or java.util.logging to log the exception
        }
    }

    private void handleSignup(ActionEvent event) {
        try {
            DBUtils.changeScene(event, "sign-up.fxml", "Sign Up", null, null);
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace of the caught exception
            // You can also use a logging framework like Log4j or java.util.logging to log the exception
        }
    }
}
