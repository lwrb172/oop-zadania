import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DeathCauseStatistic {
    private String ICD10;
    private int[] deaths;

    public DeathCauseStatistic(String ICD10, int[] deaths) {
        this.ICD10 = ICD10;
        this.deaths = deaths;
    }

    public static DeathCauseStatistic fromCsvLine(String csvLine) {
        String[] split = csvLine.split(",");
        String icd = split[0].trim();
        int[] deaths = Arrays.stream(split).skip(2) // without deaths sum
                .mapToInt(s -> s.equals("-") ? 0 : Integer.parseInt(s))
                .toArray();
        return new DeathCauseStatistic(icd, deaths);
    }

    public int[] getDeaths() {
        return deaths;
    }

    @Override
    public String toString() {
        return "DeathCauseStatistic{" +
                "ICD10='" + ICD10 + '\'' +
                ", deaths=" + Arrays.toString(deaths) +
                '}';
    }

    public record AgeBracketDeaths(int young, int old, int deathCount) {}

    public AgeBracketDeaths getAgeBracketDeaths(Integer age) {
        int index = age >= 100 ? deaths.length - 1 : age / 5;
        return new AgeBracketDeaths(
                index * 5,
                index == deaths.length - 1 ? 999 : index * 5 + 4,
                deaths[index]
        );
    }

}
