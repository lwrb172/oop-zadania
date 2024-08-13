import java.util.Locale;

public class Polygon implements Shape {
    private Vec2[] points;

    public Polygon(Vec2[] points) {
        this.points = points;
    }

    @Override
    public String toSvg() {
        return "";
    }

    @Override
    public String toSvg(String parameters) {
        StringBuilder result = new StringBuilder();
        String scene = "<svg height=\"280\" width=\"500\">\n";
        for (Vec2 vec2 : points)
            result.append(String.format("%f %f ", vec2.x, vec2.y));
        return String.format(Locale.ENGLISH, "%s\t<polygon points=\"%s\"\n\t%s/>\n</svg>\n",
                scene, result.toString(), parameters);
    }

    public static Vec2[] square(Segment diagonal) {
        double cx = (diagonal.getP1().getX() + diagonal.getP2().getX()) / 2;
        double cy = (diagonal.getP1().getY() + diagonal.getP2().getY()) / 2;
        Vec2 center = new Vec2(cx, cy);
        Segment[] perpendiculars = Segment.perpendicularSegments(diagonal, center, diagonal.length() / 2);
        Vec2[] vertices = new Vec2[4];
        vertices[0] = diagonal.getP1();
        vertices[1] = perpendiculars[1].getP2();
        vertices[2] = diagonal.getP2();
        vertices[3] = perpendiculars[0].getP2();
        return vertices;
    }
}
