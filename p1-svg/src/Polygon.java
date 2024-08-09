public class Polygon extends Shape {
    private Point[] points;

    public Polygon(Point[] points, Style style) {
        super(style);
        this.points = points;
    }

    public Polygon(Point[] points) {
        this.points = points;
    }

    public Polygon(Polygon other) {
        Point[] pointsCopy = new Point[other.points.length];
        for (int i = 0; i < other.points.length; i++) {
            pointsCopy[i] = new Point(other.points[i].getX(), other.points[i].getY());
        }
        this.points = pointsCopy;
        this.style = new Style(other.style.getFillColor(), other.style.getStrokeColor(), other.style.getStrokeWidth());
    }

    @Override
    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        sb.append("<svg height=\"280\" width=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "\t<polygon points=\"");
        for (Point point : points) {
            sb.append(point.x).append(",").append(point.y).append(" ");
        }
        sb.append("\"\n\t").append(this.style.toSvg()).append(" />\n").append("</svg>\n");
        return sb.toString();
    }

    public static Polygon square(Segment diagonal, Style style) {
        double cx = (diagonal.getP1().getX() + diagonal.getP2().getX()) / 2;
        double cy = (diagonal.getP1().getY() + diagonal.getP2().getY()) / 2;
        Point center = new Point(cx, cy);
        Segment[] perpendiculars = Segment.perpendicularSegments(diagonal, center, diagonal.length() / 2);
        Point[] vertices = new Point[4];
        vertices[0] = diagonal.getP1();
        vertices[1] = perpendiculars[1].getP2();
        vertices[2] = diagonal.getP2();
        vertices[3] = perpendiculars[0].getP2();
        return new Polygon(vertices, style);
    }
}
