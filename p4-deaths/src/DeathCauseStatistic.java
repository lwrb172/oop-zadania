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

    public class AgeBracketDeaths {
        public final int young, old, deathCount;

        public AgeBracketDeaths(int young, int old, int deathCount) {
            this.young = young;
            this.old = old;
            this.deathCount = deathCount;
        }

        private AgeBracketDeaths createAgeBracket(String bracket, int index) {
            int start, end;
            String[] parts = bracket.split(" - ");
            start = Integer.parseInt(parts[0]);
            end = Integer.parseInt(parts[1]);
            return new AgeBracketDeaths(start, end, getDeaths()[index]);
        }

        public AgeBracketDeaths getBracket(int age) {
            String header = "Kod,Razem,0 – 4,5 - 9,10 - 14,15 - 19,20 - 24,25 - 29,30 - 34,35 - 39,40 - 44,45 - 49,50 - 54,55 - 59,60 - 64,65 - 69,70 - 74,75 - 79,80 - 84,85 - 89,90 - 94,95 lat i więcej\n";
            List<String> ageBrackets = Stream.of(header.split(","))
                    .skip(2) // pomija pierwsze dwa elementy
                    .toList();
            return IntStream.range(0, ageBrackets.size())
                    .mapToObj(i -> createAgeBracket(ageBrackets.get(i), i))
                    .filter(abd -> age >= abd.young && age <= abd.old)
                    .findFirst()
                    .orElse(null);
        }
    }
}
