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
        }

    }
}