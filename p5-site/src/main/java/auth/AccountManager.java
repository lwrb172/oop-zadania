package auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    private DatabaseConnection databaseConnection;

    public AccountManager(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void init() {
        try (Connection connection = databaseConnection.getConnection()) {
            String createSQLTable = "CREATE TABLE IF NOT EXISTS users( " +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL)";
            PreparedStatement statement = connection.prepareStatement(createSQLTable);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void register(String username, String password) {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        try (Connection connection = databaseConnection.getConnection()) {
            String insertSQL = "INSERT INTO account(username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Account authenticate(String username, String password) {
        try (Connection connection = databaseConnection.getConnection()) {
            String selectSQL = "SELECT id, username, password FROM account WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            statement.setString(1, username);
            if (!statement.execute()) throw new RuntimeException("SELECT failed!");
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) throw new ArithmeticException("No such user");
            String hashedPassword = resultSet.getString(3);
            boolean okay = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray()).verified;
            if (!okay) throw new ArithmeticException("Wrong password");
            return new Account(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
