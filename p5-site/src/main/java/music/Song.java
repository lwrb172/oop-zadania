package music;

public record Song(String artist, String title, int duration) {
    @Override
    public int duration() {
        return duration;
    }
}
