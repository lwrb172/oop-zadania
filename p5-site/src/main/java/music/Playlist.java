package music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {

    public Song atSecond(int seconds) {
        int sumOfSeconds = 0;
        for (Song song : this) {
            if ((seconds >= sumOfSeconds) && (seconds < (sumOfSeconds + song.duration())))
                return song;
            sumOfSeconds += song.duration();
        }
        return null;
    }

}
