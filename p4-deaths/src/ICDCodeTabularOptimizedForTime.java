import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular {
    Map<String, String> diseases = new HashMap<>();

    @Override
    public String getDescription(String code) throws IndexOutOfBoundsException {
        return Optional.ofNullable(diseases.get(code))
                .orElseThrow(() -> new IndexOutOfBoundsException("Wrong code!"));
    }

    public ICDCodeTabularOptimizedForTime(String path) {
        try (Stream<String> lines = Files.lines(Path.of(path))) {
            diseases = lines.skip(87)
                    .map(String::trim)
                    .filter(string -> string.matches("[A-Z]\\d\\d.*"))
                    .map(string -> string.split(" ", 2))
                    .collect(Collectors.toMap(
                            sp -> sp[0],
                            sp -> sp[1].trim(),
                            (existingValue, newValue) -> existingValue
                    ));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
