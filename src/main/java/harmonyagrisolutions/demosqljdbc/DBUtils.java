package harmonyagrisolutions.demosqljdbc;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String favApp) throws IOException {
        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/" + fxmlFile));
        Parent root = loader.load();

        // Retrieve the controller instance
        Object controller = loader.getController();

        // Check the type of the controller and cast accordingly
        if (controller instanceof LoggedInController) {
            LoggedInController loggedInController = (LoggedInController) controller;
            loggedInController.setUserInformation(username, favApp);
        } else if (controller instanceof SignUpController) {
            SignUpController signUpController = (SignUpController) controller;
            // You can perform additional actions if needed for the SignUpController
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }


    public static void signUpUser(ActionEvent event, String username, String password, String favApp) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            String url = "jdbc:mysql://localhost:3306/javafx-agri";
            String user = "null";
            String pass = "null";

            connection = DriverManager.getConnection(url, user, pass);
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.next()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already exists");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, favApp) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, favApp);
                psInsert.executeUpdate();

                changeScene(event, "logged-in.fxml", "Welcome Agri kings & Queens", username, favApp);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (psCheckUserExists != null)
                    psCheckUserExists.close();
                if (psInsert != null)
                    psInsert.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String url = "jdbc:mysql://localhost:3306/javafx-agri";
            String user = "null";
            String pass = "null";

            connection = DriverManager.getConnection(url, user, pass);
            preparedStatement = connection.prepareStatement("SELECT password, favApp FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User not found");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedChannel = resultSet.getString("favApp");
                    if (retrievedPassword.equals(password)) {
                        changeScene(event, "logged-in.fxml", "Welcome", username, retrievedChannel);
                    } else {
                        System.out.println("Incorrect password");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect password");
                        alert.show();
                    }
                }
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
