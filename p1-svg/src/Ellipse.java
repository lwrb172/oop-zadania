import java.util.Locale;

public class Ellipse implements Shape {
    private Vec2 center;
    private double rx, ry;

    public Ellipse(Vec2 center, double rx, double ry) {
        this.center = center;
        this.rx = rx;
        this.ry = ry;
    }

    @Override
    public String toSvg(String parameters) {
        return String.format(Locale.ENGLISH,
                "<svg height=\"140\" width=\"500\">\n" +
                        "\t<ellipse rx=\"%f\" ry=\"%f\" cx=\"%f\" cy=\"%f\"\n\t%s/>\n" +
                        "</svg>\n",
                        this.rx, this.ry, this.center.x, this.center.y, parameters);
    }
}
