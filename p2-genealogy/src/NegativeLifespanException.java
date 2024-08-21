public class NegativeLifespanException extends Exception {
    public NegativeLifespanException(Person person) {
        super("death before birthdate!: " + person.getName());
    }
}
