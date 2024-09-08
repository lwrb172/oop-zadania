package music;

import database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public record Song(String artist, String title, int duration) {
    @Override
    public int duration() {
        return duration;
    }

    static public class Persistence {
        /* ---- Optional ----

         -- without Optional
         String name = null;
         int length = name.length(); // This will throw a NullPointerException

         -- with optional
         Optional<String> maybeName = Optional.ofNullable(name);
         int length = maybeName.map(String::length).orElse(0);

        */
        public static Optional<Song> read(int id) {
            String query = "SELECT artist, title, length FROM song WHERE id = ?";
            try (PreparedStatement statement = DatabaseConnection.getConnection("songs").prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(new Song(
                            resultSet.getString("artist"),
                            resultSet.getString("title"),
                            resultSet.getInt("length")
                    ));
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            return Optional.empty();
        }
    }
}
