public abstract class Shape {
    protected Style style;

    public Shape(Style style) {
        this.style = style;
    }

    public Shape() {
        this.style = new Style();
    }

    abstract public String toSvg();
}
