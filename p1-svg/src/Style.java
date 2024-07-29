public class Style {
    public final String fillColor, strokeColor;
    public final Double strokeWidth;

    public Style() {
        this.fillColor = "transparent";
        this.strokeColor = "black";
        this.strokeWidth = 1.0;
    }

    public Style(String fillColor, String strokeColor, Double strokeWidth) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    public String getFillColor() {
        return fillColor;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public Double getStrokeWidth() {
        return strokeWidth;
    }

    public String toSvg() {
        return "style=\"fill:" + fillColor + ";stroke:" + strokeColor + ";stroke-width:" +strokeWidth + "\"";
    }
}
