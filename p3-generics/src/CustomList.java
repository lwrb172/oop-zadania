import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CustomList<T> extends AbstractList<T> {
    private class Node {
        public Node next;
        public T value;

        public Node(T value) {
            this.next = null;
            this.value = value;
        }
    }

    private Node head, tail;
    private int size = 0;

    public CustomList() {
        this.head = null;
        this.tail = null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T get(int index) {
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
            if (node == null) {
                throw new NoSuchElementException("index is out of bounds");
            }
        }
        return node.value;
    }

    public void addLast(T value) {
        if (tail == null) {
            assert head == null;
            tail = new Node(value);
            head = tail;
        } else {
            tail.next = new Node(value);
            tail = tail.next;
        }
        size++;
    }

    public void addFirst(T value) {
        if (head == null) {
            assert tail == null;
            head = new Node(value);
            tail = head;
        } else {
            Node newHead = new Node(value);
            newHead.next = head;
            head = newHead;
        }
        size++;
    }

    public T getLast() {
        if (tail == null) throw new NoSuchElementException("List is empty");
        return tail.value;
    }

    public T getFirst() {
        if (tail == null) throw new NoSuchElementException("List is empty");
        return head.value;
    }

    public T removeLast() {
        if (tail == null) throw new NoSuchElementException("Trying to remove element from empty list");
        assert head != null;

        T result;
        if (head == tail) {
            result = tail.value;
            head = tail = null;
        } else {
            Node node = head;
            while (node.next != tail)
                node = node.next;
            tail = node;
            node.next = null;
            result = node.value;
        }
        size--;
        return result;
    }

    public T removeFirst() {
        if (head == null) throw new NoSuchElementException("Trying to remove element from empty list");
        T result = head.value;
        head = head.next;
        size--;
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node node = head;
            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                T result = node.value;
                node = node.next;
                return result;
            }
        };
    }

    @Override
    public Stream<T> stream() {
        Stream.Builder<T> builder = Stream.builder();
        for (T i : this)
            builder.accept(i);
        return builder.build();
    }

    @Override
    public boolean add(T t) {
        this.addLast(t);
        return true;
    }

    public static <S> List<S> filterByClass(List<S> list, Class<?> cls) {
        return list.stream().filter(cls::isInstance).toList();
    }

    public static <T extends Comparable<T>> Predicate<T> inOpenInterval(T lowerBound, T upperBound) {
        return value -> value.compareTo(lowerBound) > 0 && value.compareTo(upperBound) < 0;
    }

    public static <T extends Comparable<T>> long countElementsInInterval(List<T> list, T lowerBound, T upperBound) {
        return list.stream()
                .filter(inOpenInterval(lowerBound, upperBound))
                .count();
    }

    @Override
    public String toString() {
        if (head == null) return "CustomList []";
        StringBuilder result = new StringBuilder("CustomList [ ");
        Node node = head;
        while (node != null) {
            result.append(node.value).append(" ");
            node = node.next;
        }
        result.append("] size=");
        result.append(size);
        return result.toString();
    }
}
