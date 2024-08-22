import java.time.Period;

public class ParentingAgeException extends Exception {
    private final Person person;

    private static int calculateLifespan(Person person) {
        Period lifespan = Period.between(person.getBirthDate(), person.getDeathDate());
        return lifespan.getYears();
    }

    public ParentingAgeException(Person person, Person problematicParent) {
        super(String.format("Not a valid parent age! %s\nChild age: %d Parent age: %d\nDifference: %d",
                person.getName(), calculateLifespan(person), calculateLifespan(problematicParent),
                calculateLifespan(problematicParent) - calculateLifespan(person)));
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
