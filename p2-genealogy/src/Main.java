import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Person> people = Person.fromCsv("p2-genealogy/family.csv");
        for (Person person : people) {
            System.out.println(person);
        }
    }
}