public class Main {
    public static void main(String[] args) {
        Point p1 = new Point(0, 2);
        Point p2 = new Point (2, 4);
        Segment s1 = new Segment(p1, p2);
//        System.out.println(s1.toSvg());
//        Segment[] perp = Segment.perpendicularSegments(s1, new Point(3, 6));
//        System.out.println(perp[0].toSvg());
//        System.out.println(perp[1].toSvg());
        Point[] points = new Point[]{p1, p2};
        Polygon pol1 = new Polygon(points);
        System.out.println(pol1.toSvg());
    }
}