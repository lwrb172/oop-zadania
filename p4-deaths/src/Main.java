public class Main {
    public static void main(String[] args) {
        DeathCauseStatisticList deathCauseStatistics = new DeathCauseStatisticList();
        deathCauseStatistics.repopulate("./p4-deaths/zgony.csv");

        DeathCauseStatistic df = deathCauseStatistics.getStatistics().getFirst();
        System.out.println(df.getAgeBracketDeaths(100));

        ICDCodeTabularOptimizedForTime icdTime =
                new ICDCodeTabularOptimizedForTime("/p4-deaths/icd10.txt");

        ICDCodeTabularOptimizedForMemory icdMemory =
                new ICDCodeTabularOptimizedForMemory("/p4-deaths/icd10.txt");

        System.out.println("TIME:");
        System.out.println(icdTime.getDescription("C00"));
        System.out.println("MEMORY:");
        System.out.println(icdMemory.getDescription("C00"));
    }
}