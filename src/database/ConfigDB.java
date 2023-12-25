package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {
    // JDBC database URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/sr_project";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // JDBC variables for opening, closing, and managing the connection
    private static Connection connection;

    // Method to establish a connection to the database
    public static Connection getConnection() {
        try {
            // Check if the connection is not already established
            if (connection == null || connection.isClosed()) {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the connection
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle errors appropriately in a real application
        }

        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle errors appropriately in a real application
        }
    }
}

