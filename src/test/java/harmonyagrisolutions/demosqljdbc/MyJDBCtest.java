package harmonyagrisolutions.demosqljdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyJDBCtest {

    private static final Logger LOGGER = Logger.getLogger(MyJDBCtest.class.getName());

    public static void main(String[] args) {
        try {
            /* Check local host name by connection IP address or just localhost */
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-agri", null, null);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users ");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("password"));
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An exception occurred", e);
        }
    }
}