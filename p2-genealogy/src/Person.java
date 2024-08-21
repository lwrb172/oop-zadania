import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Person> fromCsv(String path) {
        File file = new File(path);
        List<Person> people = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Person person = Person.fromCsvLine(line);
                try {
                    person.validateLifespan();
                    person.validateAmbiguity(people);
                    people.add(person);
                } catch (NegativeLifespanException | AmbiguousPersonException e) {
                    System.err.println(e.getMessage());
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading CSV file" + e.getMessage());
        }
        return people;
    }

    public void validateLifespan() throws NegativeLifespanException {
        if (deathDate != null && deathDate.isBefore(birthDate)) {
            throw new NegativeLifespanException(this);
        }
    }

    public void validateAmbiguity(List<Person> people) throws AmbiguousPersonException {
        for (Person person : people) {
            if (person.getName().equals(name)) {
                throw new AmbiguousPersonException(person);
            }
        }
    }

    public String getName() {
        return name;
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
