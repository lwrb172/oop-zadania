public class Ellipse implements Shape {
    private Vec2 center;
    private double rx, ry;
    private Style style;

    public Ellipse(Vec2 center, double rx, double ry, Style style) {
        this.center = center;
        this.rx = rx;
        this.ry = ry;
        this.style = style;
    }

    public Ellipse(Vec2 center, double rx, double ry) {
        this.center = center;
        this.rx = rx;
        this.ry = ry;
    }

    @Override
    public String toSvg() {
        return "<svg height=\"140\" width=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "\t<ellipse rx=\"" + rx + "\" ry=\"" + ry +
                "\" cx=\"" + center.getX() + "\" cy=\"" + center.getY() + "\"\n" +
                "\t" + style.toSvg() + " />\n </svg>\n";
    }
}
