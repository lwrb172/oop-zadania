import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular {
    private String path;

    public ICDCodeTabularOptimizedForMemory(String path) {
        this.path = path;
    }

    @Override
    public String getDescription(String code) throws IndexOutOfBoundsException {
        try (Stream<String> lines = Files.lines(Path.of(path))) {
            return lines.skip(87)
                    .map(String::trim)
                    .filter(string -> string.matches("[A-Z]\\d\\d.*"))
                    .map(string -> string.split(" ", 2))
                    .filter(parts -> parts[0].equals(code))
                    .findFirst()
                    .map(parts -> parts[1].trim())
                    .orElseThrow(() -> new IndexOutOfBoundsException("Wrong Code!"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
}
