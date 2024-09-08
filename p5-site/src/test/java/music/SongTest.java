package music;

import database.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

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

    @Test
    void readSingleWrongID() {
        Optional<Song> testSong = Song.Persistence.read(53);
        assertFalse(testSong.isPresent());
    }

     static private Stream<Arguments> provideSongData() {
        return Stream.of(
                Arguments.of(5, "Queen", "Bohemian Rhapsody", 355),
                Arguments.of(25, "John Lennon", "Imagine", 183),
                Arguments.of(1, "The Beatles", "Hey Jude", 431)
        );
     }

     @ParameterizedTest
     @MethodSource("provideSongData")
     void readSongs(int id, String artist, String title, int duration) {
        Optional<Song> testSong = Song.Persistence.read(id);
        Song expectedSong = new Song(artist, title, duration);
        assertTrue(testSong.isPresent());
        assertEquals(expectedSong, testSong.get());
     }


    @AfterAll
    static void closeDatabase() {
        DatabaseConnection.disconnect("songs");
    }
}
