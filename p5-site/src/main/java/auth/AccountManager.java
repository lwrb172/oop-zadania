package auth;

import database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    private DatabaseConnection databaseConnection;

    public void register(String username, String password) {
        try (Connection connection = databaseConnection.getConnection()) {
            String insertSQL = "INSERT INTO account(username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        try (Connection connection = databaseConnection.getConnection()) {
            String selectSQL = "SELECT id, username, password FROM account WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}
