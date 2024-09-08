package music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaylistTest {
    @Test
    void testEmptyPlaylist() {
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());
    }

    @Test
    void testSingleElement() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Atr", "000", 180));
        assertEquals(1, playlist.size());
    }

    @Test
    void testSameElement() {
        Playlist playlist = new Playlist();
        Song song = new Song("Atr", "000", 180);
        playlist.add(song);
        assertEquals(song, playlist.getFirst());
    }

    @Test
    void testEqualElement() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Atr", "000", 180));
        assertEquals(1, playlist.size());
        Song song = new Song("Atr", "000", 180);
        playlist.add(song);
        Song sameSong = new Song("Atr", "000", 180);
        assertEquals(sameSong, song);
    }
}
