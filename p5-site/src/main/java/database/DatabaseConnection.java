package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect(String filePath) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
