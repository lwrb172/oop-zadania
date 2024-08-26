import java.io.IOException;
import java.util.List;

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
    }
}