public class AmbiguousPersonException extends Exception {
    public AmbiguousPersonException(Person person) {
        super(String.format("Two the same names found: \"%s\"", person.getName()));
    }
}
