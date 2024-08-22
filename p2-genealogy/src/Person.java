import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Person implements Serializable {
    private final String name;
    private final LocalDate birthDate;
    private final LocalDate deathDate;
    private final List<Person> parents = new ArrayList<>();

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

    public static List<Person> fromCsv(String path) throws IOException {
        File file = new File(path);
        List<Person> people = new ArrayList<>();
        Map<String, PersonWithParentStrings> peopleWithParentsStrings = new HashMap<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                var personWithParentStrings = PersonWithParentStrings.fromCsvLine(line);
                var person = personWithParentStrings.getPerson(); // Person.fromCsvLine(line);
                try {
                    person.validateLifespan();
                    person.validateAmbiguity(people);
                    people.add(person);
                    peopleWithParentsStrings.put(person.getName(), personWithParentStrings);
                } catch (NegativeLifespanException | AmbiguousPersonException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file" + e.getMessage());
        }

        PersonWithParentStrings.linkRelatives(peopleWithParentsStrings);

        try {
            for (Person person : people) {
                person.validateParentAge();
            }
        } catch (ParentingAgeException e) {
            System.out.println(e.getMessage());
            Scanner scanner = new Scanner(System.in);
            System.out.println("do you want to remove this person? [Y|N]: ");
            String answer = scanner.next();
            if (answer.equals("Y") || answer.equals("y"))
                people.remove(e.getPerson());
        }

        return people;
    }

    public static void toBinaryFile(List<Person> people, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(people);
        } catch (IOException e) {
            System.err.println("Error writing to binary file: " + e.getMessage());
        }
    }

    public static void fromBinaryFile(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            List<Person> people = (List<Person>) ois.readObject();
            for (Person person : people) {
                System.out.println(person);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from binary file: " + e.getMessage());
        }
    }

    public void validateLifespan() throws NegativeLifespanException {
        if (deathDate != null && deathDate.isBefore(birthDate))
            throw new NegativeLifespanException(this);
    }

    public void validateAmbiguity(List<Person> people) throws AmbiguousPersonException {
        for (Person person : people)
            if (person.getName().equals(name))
                throw new AmbiguousPersonException(person);
    }

    public void validateParentAge() throws ParentingAgeException {
        for (Person parent : parents)
            if (birthDate.isBefore(parent.birthDate.plusYears(15)) || (parent.deathDate != null && birthDate.isAfter(parent.deathDate)))
                throw new ParentingAgeException(this, parent);
    }

    public void addParent(Person parent) {
        parents.add(parent);
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", deathDate=" + deathDate +
                ", parents=" + parents +
                '}';
    }
}
