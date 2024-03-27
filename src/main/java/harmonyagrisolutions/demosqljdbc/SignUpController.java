package harmonyagrisolutions.demosqljdbc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button button_signup;
    @FXML
    private Button button_login;
    @FXML
    private RadioButton rb_harmony;
    @FXML
    private RadioButton rb_harmonyV2;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_harmony.setToggleGroup(toggleGroup);
        rb_harmonyV2.setToggleGroup(toggleGroup);
        rb_harmony.setSelected(true);

        button_signup.setOnAction(event -> {
            String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
            if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                DBUtils.signUpUser(event, tf_username.getText(), tf_password.getText(), toggleName);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in information to sign up!");
                alert.show();
            }
        });

        button_login.setOnAction(event -> {
            try {
                DBUtils.changeScene(event, "sample.fxml", "Log in!", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
