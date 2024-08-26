import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Person> people = Person.fromCsv("p2-genealogy/family.csv");

        // list to binary file
//        String binFilePath = "p2-genealogy/test.bin";
//        Person.toBinaryFile(people, binFilePath);
//        Person.fromBinaryFile(binFilePath);

        // PlantUML diagram
        PlantUMLRunner.setJarPath("./p2-genealogy/plantuml-1.2024.6.jar");
        PlantUMLRunner.generate(
                Person.generateDiagram(people), "p2-genealogy/image_output", "diagram"
        );

        // output
        System.out.println("filtered by name:");
        Person.filerByName(people, "Dąb").forEach(System.out::println);
        System.out.println("sorted by birth date:");
        Person.sortByBirthDate(people).forEach(System.out::println);
        System.out.println("sorted by lifespan: ");
        Person.sortByLifespan(people).forEach(System.out::println);
    }
}