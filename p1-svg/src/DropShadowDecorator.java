public class DropShadowDecorator extends ShapeDecorator {
    private int index;

    public DropShadowDecorator(Shape decoratedShape) {
        super(decoratedShape);

        index = SvgScene.getInstance().addFilter("""
                \t<filter id="f%d" x="-100%%" y="-100%%" width="300%%" height="300%%">
                \t\t<feOffset result="offOut" in="SourceAlpha" dx="5" dy="5" />
                \t\t<feGaussianBlur result="blurOut" in="offOut" stdDeviation="5" />
                \t\t<feBlend in="SourceGraphic" in2="blurOut" mode="normal" />
                \t</filter>"""
        );
    }

    @Override
    public String toSvg(String parameters) {
        String filterWithId = String.format("filter=\"url(#f%d)\" %s", index, parameters);
        return decoratedShape.toSvg(filterWithId);
    }
}
