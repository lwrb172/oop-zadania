import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        { // test adding last
            CustomList<Integer> list = new CustomList<>();
            System.out.println(list);
            list.addLast(5);
            System.out.println(list);
            list.addLast(4);
            System.out.println(list);
        }

        { // test adding first
            CustomList<Integer> list = new CustomList<>();
            System.out.println(list);
            list.addFirst(6);
            System.out.println(list);
            list.addFirst(9);
            System.out.println(list);
        }

//        { // test throwing exception
//            CustomList<Integer> list = new CustomList<>();
//            System.out.println(list.getLast());
//        }

        {
            CustomList<Integer> list = new CustomList<>();
            for (int i = 0; i < 10; i++) list.addLast(i);
            System.out.println(list);
          /* for (int i = 0; i < 10; i++) {
                list.removeLast();

                System.out.println(list);*/
            //}
            for (Integer integer : list) System.out.print(integer);

            list.stream()
                    .map(integer -> integer + 10)
                    .forEach(System.out::println);
        }

        {
            CustomList<Object> list = new CustomList<>();
            list.addLast(0.123f);
            list.addLast(.99d);
            list.addLast("Hello!");

            var list2 = CustomList.filterByClass(list, Number.class);
            System.out.println(list2);
        }

        {
            CustomList<Integer> list = new CustomList<>();
            for (int i = 0; i < 10; i++) list.addLast(i);
            long count = CustomList.countElementsInInterval(list, 2, 8);
            System.out.println("Liczba elementów w przedziale (2, 8): " + count);

            CustomList<Integer> list1 = new CustomList<>();
            list1.addAll(Arrays.asList(1, 2, 3));
            CustomList<Integer> list2 = new CustomList<>();
            list2.addAll(Arrays.asList(4, 5));

            // Porównanie po liczbie elementów
            // nwm czemu wychodzi zły output
            int comparisonBySize = CustomList.bySizeComparator().compare(Collections.singleton(Collections.singleton(list1)), list2);
            System.out.println("Porównanie po rozmiarze: " + comparisonBySize);

            // Porównanie po sumie
            int comparisonBySum = CustomList.bySumComparator().compare(list1, list2);
            System.out.println("Porównanie po sumie: " + comparisonBySum);
        }

    }
}