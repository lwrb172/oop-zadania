public class SolidFilledEllipse extends Ellipse {
    private String color;

    public SolidFilledEllipse(Vec2 center, double rx, double ry, String color) {
        super(center, rx, ry);
        this.color = color;
    }

    @Override
    public String toSvg(String parameters) {
        String fill = String.format("fill=\"%s\" %s", color, parameters);
        return super.toSvg(fill);
    }
}
