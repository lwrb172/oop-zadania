import java.util.Arrays;
import java.util.Locale;

public class GradientFillShapeDecorator extends ShapeDecorator {
    private static int index = 1;
    private record Stop(double offset, String color) {};
    private Stop[] stops;

    public static class Builder {
        private Shape shape;
        private Stop[] stops = new Stop[0];
        private int gradientIndex;

        public GradientFillShapeDecorator.Builder setShape(Shape shape) {
            this.shape = shape;
            return this;
        }

        public GradientFillShapeDecorator.Builder addStop(double offset, String color) {
            stops = Arrays.copyOf(stops, stops.length + 1);
            stops[stops.length - 1] = new Stop(offset, color);
            return this;
        }

        public GradientFillShapeDecorator build() {
            GradientFillShapeDecorator result = new GradientFillShapeDecorator(shape);
            result.stops = this.stops;
            StringBuilder defs = new StringBuilder("\t<linearGradient id=\"g%d\" >\n");
            for (var stop : stops) {
                defs.append(String.format(
                        Locale.ENGLISH,
                        "\t\t<stop offset=\"%f\" style=\"stop-color:%s\" />\n",
                        stop.offset, stop.color
                ));
            }
            defs.append("\t</linearGradient>");
            gradientIndex = SvgScene.getInstance().addGradient(String.valueOf(defs));
            return result;
        }
    }

    public GradientFillShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    private int addRequiredDefToScene() {
        SvgScene scene = SvgScene.getInstance();
        StringBuilder result = new StringBuilder("\t<linearGradient id=\"g%d\" >\n");
        for (var stop : stops) {
            result.append(String.format(
                    Locale.ENGLISH,
                    "\t\t<stop offset=\"%f\" style=\"stop-color:%s\" />\n",
                    stop.offset, stop.color
            ));
        }
        result.append("\t</linearGradient>");
//        SvgScene.getInstance().defs.add(result.toString());
        scene.addFilter(String.valueOf(result));
        return index++;
    }

    @Override
    public String toSvg(String parameters) {
        int index = addRequiredDefToScene();
        return decoratedShape.toSvg(parameters + String.format(" fill=\"url(#g%d)\" ", index));
    }
}
