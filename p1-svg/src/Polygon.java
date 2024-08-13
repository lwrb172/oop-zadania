public class Polygon implements Shape {
    private Vec2[] vec2s;
    private Style style;

    public Polygon(Vec2[] vec2s, Style style) {
        this.style = style;
        this.vec2s = vec2s;
    }

    public Polygon(Vec2[] vec2s) {
        this.vec2s = vec2s;
    }

    public Polygon(Polygon other) {
        Vec2[] pointsCopy = new Vec2[other.vec2s.length];
        for (int i = 0; i < other.vec2s.length; i++) {
            pointsCopy[i] = new Vec2(other.vec2s[i].getX(), other.vec2s[i].getY());
        }
        this.vec2s = pointsCopy;
        this.style = new Style(other.style.getFillColor(), other.style.getStrokeColor(), other.style.getStrokeWidth());
    }

    @Override
    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        sb.append("<svg height=\"280\" width=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "\t<polygon points=\"");
        for (Vec2 vec2 : vec2s) {
            sb.append(vec2.x).append(",").append(vec2.y).append(" ");
        }
        sb.append("\"\n\t").append(this.style.toSvg()).append(" />\n").append("</svg>\n");
        return sb.toString();
    }

    public static Polygon square(Segment diagonal, Style style) {
        double cx = (diagonal.getP1().getX() + diagonal.getP2().getX()) / 2;
        double cy = (diagonal.getP1().getY() + diagonal.getP2().getY()) / 2;
        Vec2 center = new Vec2(cx, cy);
        Segment[] perpendiculars = Segment.perpendicularSegments(diagonal, center, diagonal.length() / 2);
        Vec2[] vertices = new Vec2[4];
        vertices[0] = diagonal.getP1();
        vertices[1] = perpendiculars[1].getP2();
        vertices[2] = diagonal.getP2();
        vertices[3] = perpendiculars[0].getP2();
        return new Polygon(vertices, style);
    }
}
