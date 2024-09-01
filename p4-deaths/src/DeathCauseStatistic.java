import java.util.Arrays;

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

    public String getICD10() {
        return ICD10;
    }

    @Override
    public String toString() {
        return "DeathCauseStatistic{" +
                "ICD10='" + ICD10 + '\'' +
                ", deaths=" + Arrays.toString(deaths) +
                '}';
    }
}
