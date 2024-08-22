import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonWithParentStrings {
    private final Person person;
    private final List<String> parentsNames;

    public PersonWithParentStrings(Person person, List<String> parentsNames) {
        this.person = person;
        this.parentsNames = parentsNames;
    }

    public static PersonWithParentStrings fromCsvLine(String csvLine) {
        Person person = Person.fromCsvLine(csvLine);
        List<String> parentsNames = new ArrayList<>();
        String[] split = csvLine.split(",", -1);

        for (int i = 3; i <= 4; i++) {
            if (!split[i].isEmpty()) {
                parentsNames.add(split[i]);
            }
        }

        return new PersonWithParentStrings(person, parentsNames);
    }

    public static void linkRelatives(Map<String, PersonWithParentStrings> people) {
        for (var personWithParentStrings : people.values()) {
            for (var parentName : personWithParentStrings.parentsNames) {
                personWithParentStrings.person.addParent(people.get(parentName).person);
            }
        }
    }

    public Person getPerson() {
        return person;
    }
}
