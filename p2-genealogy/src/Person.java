import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public String generateDiagram() {
        // -- starting UML code --
        StringBuilder result = new StringBuilder("@startuml\n");
        // Function : John Doe -> JohnDoe
        Function<Person, String> cleanPersonName = person -> person.getName().replaceAll(" ", "");
        // Function : returning string "object cleanPersonName"
        Function<Person, String> addObject = person -> String.format(
                "object %s", cleanPersonName.apply(person)
        );
        // string with only cleanPersonName
        String personName = cleanPersonName.apply(this);
        // appending second Function to result string
        result.append(addObject.apply(this));
        // formatting and appending parents if existing
        if (!parents.isEmpty()) {
            String parentSections = parents.stream()
                    .map(parent -> "\n" + addObject.apply(parent) + "\n" +
                            cleanPersonName.apply(parent) + " <-- " + personName)
                    .collect(Collectors.joining());
            result.append(parentSections);
        }
        // returning full UML code in string
        return result.append("\n@enduml").toString();
    }

    public static String generateDiagram(List<Person> people, Function<String, String> postProcess) {
        String result = "@startuml\n%s\n%s\n@enduml";
        // Function for formatting names : John Doe -> JohnDoe
        Function<String, String> objectName = str -> str.replaceAll(" ", "");
        // Function for returning uml code string : object "John Doe" as JohnDoe
        Function<String, String> objectLine = str -> String.format(
                "object \"%s\" as %s", str, objectName.apply(str)
                );
        // Function for adding postProcess to previous function at the end of returned string
        Function<String, String> objectLineWithPostProcess = objectLine.andThen(postProcess);
        // HashMaps
        Set<String> objects = new HashSet<>();
        Set<String> relations = new HashSet<>();
        // Consumer (void function) for adding formatted objects and relations to HashSets
        Consumer<Person> addPerson = person -> {
            objects.add(objectLineWithPostProcess.apply(person.name));
            for (Person parent : person.parents)
                relations.add(objectName.apply(parent.name) + "<--" + objectName.apply(person.name));
        };
        people.forEach(addPerson);
        String objectString = String.join("\n", objects);
        String relationString = String.join("\n", relations);
        return String.format(result, objectString, relationString);
    }

    public static List<Person> filerByName(List<Person> people, String substring) {
        return people.stream()
                .filter(person -> person.getName().contains(substring))
                .collect(Collectors.toList());
    }

    public static List<Person> sortByBirthDate(List<Person> people) {
        return people.stream()
                .sorted(Comparator.comparing(Person::getBirthDate))
                .collect(Collectors.toList());
    }

    public static List<Person> sortByLifespan(List<Person> people) {
        Function<Person, Long> getLifespan = person ->
                person.deathDate.toEpochDay() - person.birthDate.toEpochDay();
        // sorting by custom comparator
        return people.stream()
                .filter(person -> person.deathDate != null)
                .sorted(((o1, o2) -> Long.compare(getLifespan.apply(o2), getLifespan.apply(o1))))
                .toList();
    }

    public static Person findTheOldestLiving(List<Person> people) {
        // finding the earliest birt date
        return people.stream()
                .filter(person -> person.deathDate == null)
                .min(Comparator.comparing(Person::getBirthDate))
                .orElse(null);
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
