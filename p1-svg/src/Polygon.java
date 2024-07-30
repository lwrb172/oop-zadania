public class Polygon {
    private Point[] points;
    private Style style;

    public Polygon(Point[] points) {
        this.points = points;
        this.style = new Style();
    }

    public Polygon(Point[] points, Style style) {
        this.points = points;
        this.style = style;
    }

    public Polygon(Polygon other) {
        Point[] pointsCopy = new Point[other.points.length];
        for (int i = 0; i < other.points.length; i++) {
            pointsCopy[i] = new Point(other.points[i].getX(), other.points[i].getY());
        }
        this.points = pointsCopy;
        this.style = new Style(other.style.getFillColor(), other.style.getStrokeColor(), other.style.getStrokeWidth());
    }

    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        sb.append("<svg height=\"280\" width=\"500\" xmlns=\"http://www.w3.org/2000/svg\"> " +
                "<polygon points=\"");
        for (Point point : points) {
            sb.append(point.x).append(",").append(point.y).append(" ");
        }
        sb.append("\" ").append(this.style.toSvg()).append(" /> ").append("</svg>");
        return sb.toString();
    }
}
