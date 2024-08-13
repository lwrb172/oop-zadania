import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // shapes list and scene
        ArrayList<Shape> shapes = new ArrayList<>();
        SvgScene scene = new SvgScene(shapes);
        // triangle
        Vec2 t1 = new Vec2(100, 10);
        Vec2 t2 = new Vec2(150, 190);
        Vec2 t3 = new Vec2(50, 190);
        Vec2[] trianglePoints = new Vec2[]{t1, t2, t3};
        SolidFilledPolygon triangle = new SolidFilledPolygon(trianglePoints, "yellow");
        // six-sides
        Vec2 ss1 = new Vec2(150, 15);
        Vec2 ss2 = new Vec2(258, 77);
        Vec2 ss3 = new Vec2(258, 202);
        Vec2 ss4 = new Vec2(150, 265);
        Vec2 ss5 = new Vec2(42, 202);
        Vec2 ss6 = new Vec2(42, 77);
        Vec2[] sixSidesPoints = new Vec2[]{ss1, ss2, ss3, ss4, ss5, ss6};
        SolidFilledPolygon sixSides = new SolidFilledPolygon(sixSidesPoints, "green");
        // starS
        Vec2 s1 = new Vec2(100, 10);
        Vec2 s2 = new Vec2(40, 198);
        Vec2 s3 = new Vec2(190, 78);
        Vec2 s4 = new Vec2(10, 78);
        Vec2 s5 = new Vec2(160, 198);
        Vec2[] starPoints = new Vec2[]{s1, s2, s3, s4, s5};
        SolidFilledPolygon star = new SolidFilledPolygon(starPoints, "orange");
        // square
        Segment diagonal = new Segment(new Vec2(25, 25), new Vec2(175, 175));
        Vec2[] vertices = Polygon.square(diagonal);
        SolidFilledPolygon square = new SolidFilledPolygon(vertices, "purple");
        // ellipse
        Vec2 center = new Vec2(120, 80);
        SolidFilledEllipse ellipse = new SolidFilledEllipse(center, 100, 50, "red");
        // scene
        scene.add(triangle);
        scene.add(sixSides);
        scene.add(star);
        scene.add(square);
        scene.add(ellipse);
        scene.save("scene.html");
    }
}