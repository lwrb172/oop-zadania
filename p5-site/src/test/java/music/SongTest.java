package music;

import database.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SongTest {

    @BeforeAll
    static void openDatabase() {
        DatabaseConnection.connect("src/main/resources/songs.db", "songs");
    }

    @Test
    void readSingle() {
        Optional<Song> testSong = Song.Persistence.read(5);
        Song expectedSong = new Song("Queen", "Bohemian Rhapsody", 355);
        assertTrue(testSong.isPresent());
        assertEquals(expectedSong, testSong.get());
    }

    @AfterAll
    static void closeDatabase() {
        DatabaseConnection.disconnect("songs");
    }
}
