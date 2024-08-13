public class Segment {
    private Vec2 p1, p2;

    public Segment(Vec2 p1, Vec2 p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Vec2 getP1() {
        return p1;
    }

    public Vec2 getP2() {
        return p2;
    }

    public double Length() {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    public String toSvg() {
        return "<svg height=\"200\" width=\"300\" xmlns=\"http://www.w3.org/2000/svg\"> " +
                "<line x1=\"" + p1.x +"\" y1=\"" + p1.y + "\" x2=\"" + p2.x + "\" y2=\"" + p2.y +
                "\" /> " +
                "</svg>";
    }

    public double length() {
        return Math.hypot(p2.x - p1.x, p2.y - p1.y);
    }

    static public Segment[] perpendicularSegments(Segment segment, Vec2 vec2, double length) {
        double dx = (segment.p2.x - segment.p1.x) / segment.length() * length;
        double dy = (segment.p2.y - segment.p1.y) / segment.length() * length;

        return new Segment[]{
                new Segment(vec2, new Vec2(vec2.x - dy, vec2.y + dx)),
                        new Segment(vec2, new Vec2(vec2.x + dy, vec2.y - dx))};
    }
}
