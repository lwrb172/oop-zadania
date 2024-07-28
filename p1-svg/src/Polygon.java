public class Polygon {
    private Point[] points;

    public Polygon(Point[] points) {
        this.points = points;
    }

    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        sb.append("<svg height=\"220\" width=\"500\" xmlns=\"http://www.w3.org/2000/svg\"> " +
                "<polygon points=\"");
        for (Point point : points) {
            sb.append(point.x).append(",").append(point.y).append(" ");
        }
        sb.append("\"").append(" /> ").append("</svg>");
        return sb.toString();
    }
}
