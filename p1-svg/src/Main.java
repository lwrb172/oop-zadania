public class Main {
    public static void main(String[] args) {
        Point p1 = new Point(1, 2);
        Point p2 = new Point (3, 4);
        Segment s1 = new Segment(p1, p2);
        System.out.println(s1.Length());
        System.out.println(s1.toSvg());
    }
}