import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Person> people = Person.fromCsv("p2-genealogy/family.csv");

        // list to binary file
        String binFilePath = "p2-genealogy/test.bin";
        Person.toBinaryFile(people, binFilePath);
        Person.fromBinaryFile(binFilePath);

        // PlantUML diagram
        PlantUMLRunner.setJarPath("./p2-genealogy/plantuml-1.2024.6.jar");
        PlantUMLRunner.generate("@startuml\n" +
                        "Class11 <|.. Class12\n" +
                        "Class13 --> Class14\n" +
                        "Class15 ..> Class16\n" +
                        "Class17 ..|> Class18\n" +
                        "Class19 <--* Class20\n" +
                        "@enduml\n",
                "p2-genealogy/image_output",
                "testUML");
    }
}