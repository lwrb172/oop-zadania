public class TransformationDecorator extends ShapeDecorator {
    String transform;

    public static class Builder {
        private Shape shape;
        String transform;

        public Builder(Shape shape) {
            this.shape = shape;
            this.transform = "";
        }

        public Builder translate(Vec2 translateVector) {
            this.transform += String.format(" translate(%f %f)", translateVector.x, translateVector.y);
            return this;
        }

        public Builder rotate(Vec2 center, double angle) {
            this.transform += String.format(" rotate(%f %f %f)", angle, center.x, center.y);
            return this;
        }

        public Builder scale(Vec2 scaleVector) {
            this.transform += String.format(" scale(%f %f)", scaleVector.x, scaleVector.y);
            return this;
        }

        public TransformationDecorator build() {
            return new TransformationDecorator(shape, transform);
        }
    }

    public TransformationDecorator(Shape shape, String transform) {
        super(shape);
        this.transform = transform;
    }

    @Override
    public String toSvg(String parameters) {
        return super.toSvg(String.format("%s transform=\"%s\"", parameters, this.transform));
    }
}
