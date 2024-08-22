import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(Person.fromCsvLine("Marek Kowalski,15.05.1899,25.06.1957,,"));

        List<Person> people = Person.fromCsv("p2-genealogy/family.csv");
        for (Person person : people) {
            System.out.println(person);
        }
    }
}