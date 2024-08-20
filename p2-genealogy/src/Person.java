import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Person {
    public String name;
    public LocalDate birthDate;
    public LocalDate deathDate;

    public Person(String name, LocalDate birthDate, LocalDate deathDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public static Person fromCsvLine(String csvLine) {
        String[] buffer = csvLine.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(buffer[1], formatter);
        LocalDate deathDate = buffer[2].isEmpty() ? null : LocalDate.parse(buffer[2], formatter);
        return new Person(buffer[0], birthDate, deathDate);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", deathDate=" + deathDate +
                '}';
    }
}
