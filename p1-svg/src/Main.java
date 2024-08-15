public class Main {
    public static void main(String[] args) {
        // triangle
        Vec2 t1 = new Vec2(100, 10);
        Vec2 t2 = new Vec2(150, 190);
        Vec2 t3 = new Vec2(50, 190);
        Vec2[] trianglePoints = new Vec2[]{t1, t2, t3};
        Shape triangle = new Polygon(trianglePoints);
        triangle = new TransformationDecorator.Builder(triangle)
                .translate(new Vec2(50, 50))
                .rotate(new Vec2(0,0), -27)
                .scale(new Vec2(1.2, 1.2))
                .build();
        triangle = new DropShadowDecorator(triangle);
        triangle = new SolidFillShapeDecorator(triangle, "yellow");
        triangle = new StrokeShapeDecorator(triangle, "orange", 5.0);
        // six-sides
        Vec2 ss1 = new Vec2(150, 15);
        Vec2 ss2 = new Vec2(258, 77);
        Vec2 ss3 = new Vec2(258, 202);
        Vec2 ss4 = new Vec2(150, 265);
        Vec2 ss5 = new Vec2(42, 202);
        Vec2 ss6 = new Vec2(42, 77);
        Vec2[] sixSidesPoints = new Vec2[]{ss1, ss2, ss3, ss4, ss5, ss6};
        Shape sixSides = new Polygon(sixSidesPoints);
        sixSides = new TransformationDecorator.Builder(sixSides)
                .translate(new Vec2(100, 100))
                .rotate(new Vec2(0, 0), -45)
                .scale(new Vec2(0.70, 0.70))
                .build();
        sixSides = new DropShadowDecorator(sixSides);
        sixSides = new SolidFillShapeDecorator(sixSides, "green");
        sixSides = new StrokeShapeDecorator(sixSides, "purple", 5.0);
        // starS
        Vec2 s1 = new Vec2(100, 10);
        Vec2 s2 = new Vec2(40, 198);
        Vec2 s3 = new Vec2(190, 78);
        Vec2 s4 = new Vec2(10, 78);
        Vec2 s5 = new Vec2(160, 198);
        Vec2[] starPoints = new Vec2[]{s1, s2, s3, s4, s5};
        Shape star = new Polygon(starPoints);
        star = new TransformationDecorator.Builder(star)
                .translate(new Vec2(100, -30))
                .rotate(new Vec2(0, 0), 15)
                .scale(new Vec2(1.2, 1.2))
                .build();
        star = new DropShadowDecorator(star);
        star = new SolidFillShapeDecorator(star, "red");
        star = new StrokeShapeDecorator(star, "orange", 5.0);
        // square
        Segment diagonal = new Segment(new Vec2(25, 25), new Vec2(175, 175));
        Vec2[] vertices = Polygon.square(diagonal);
        Shape square = new Polygon(vertices);
        square = new TransformationDecorator.Builder(square)
                .translate(new Vec2(100, 50))
                .rotate(new Vec2(0, 0), 50)
                .scale(new Vec2(0.8, 0.8))
                .build();
        square = new DropShadowDecorator(square);
        square = new SolidFillShapeDecorator(square, "pink");
        square = new StrokeShapeDecorator(square, "purple", 5.0);
        // ellipse
        Vec2 center = new Vec2(120, 80);
        Shape ellipse = new Ellipse(center, 100, 50);
        ellipse = new TransformationDecorator.Builder(ellipse)
                .translate(new Vec2(100, 20))
                .rotate(new Vec2(0, 0), -20)
                .scale(new Vec2(0.5, 0.5))
                .build();
        ellipse = new DropShadowDecorator(ellipse);
        ellipse = new SolidFillShapeDecorator(ellipse, "orange");
        ellipse = new StrokeShapeDecorator(ellipse, "blue", 5.0);
        // scene
        SvgScene scene = SvgScene.getInstance();
        scene.add(triangle);
        scene.add(sixSides);
        scene.add(star);
        scene.add(square);
        scene.add(ellipse);
        scene.save("scene.html");
    }
}