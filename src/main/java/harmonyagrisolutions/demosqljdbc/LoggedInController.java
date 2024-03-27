package harmonyagrisolutions.demosqljdbc;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Button button_logout;
    @FXML
    private Label label_welcome;
    @FXML
    private Label label_fav_app;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_logout.setOnAction(event -> {
            try {
                /*// Change the scene to sign-up.fxml when the logout button is clicked*/
                DBUtils.changeScene(event, "sign-up.fxml", "Sign Up", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setUserInformation(String username, String fav_app) {
        label_welcome.setText("Welcome " + username + "!");
        label_fav_app.setText("Your favourite app is " + fav_app + "!");
    }
}
