import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // shapes list and scene
        ArrayList<Shape> shapes = new ArrayList<>();
        SvgScene scene = new SvgScene(shapes);
        // styles
        Style unit00 = new Style("steelblue", "darkblue", 5.0);
        Style unit01 = new Style("mediumpurple", "green", 5.0);
        Style unit02 = new Style("crimson", "goldenrod", 5.0);
        Style unit03 = new Style("darkslateblue", "black", 5.0);
        Style unit04 = new Style("gainsboro", "black", 5.0);
        // triangle
        Vec2 t1 = new Vec2(100, 10);
        Vec2 t2 = new Vec2(150, 190);
        Vec2 t3 = new Vec2(50, 190);
        Vec2[] triangleVec2s = new Vec2[]{t1, t2, t3};
        Polygon triangle = new Polygon(triangleVec2s, unit00);
        // six-sides
        Vec2 ss1 = new Vec2(150, 15);
        Vec2 ss2 = new Vec2(258, 77);
        Vec2 ss3 = new Vec2(258, 202);
        Vec2 ss4 = new Vec2(150, 265);
        Vec2 ss5 = new Vec2(42, 202);
        Vec2 ss6 = new Vec2(42, 77);
        Vec2[] sixSidesVec2s = new Vec2[]{ss1, ss2, ss3, ss4, ss5, ss6};
        Polygon sixSides = new Polygon(sixSidesVec2s, unit01);
        // starS
        Vec2 s1 = new Vec2(100, 10);
        Vec2 s2 = new Vec2(40, 198);
        Vec2 s3 = new Vec2(190, 78);
        Vec2 s4 = new Vec2(10, 78);
        Vec2 s5 = new Vec2(160, 198);
        Vec2[] starVec2s = new Vec2[]{s1, s2, s3, s4, s5};
        Polygon star = new Polygon(starVec2s, unit02);
        // square
        Segment diagonal = new Segment(new Vec2(25,25), new Vec2(175, 175));
        Polygon square = Polygon.square(diagonal, unit03);
        // ellipse
        Vec2 center = new Vec2(120, 80);
        Ellipse ellipse = new Ellipse(center, 100, 50, unit04);
        // scene
        scene.add(triangle);
        scene.add(sixSides);
        scene.add(star);
        scene.add(square);
        scene.add(ellipse);
        try {
            scene.save("scene.html");
        } catch (IOException e) {
            System.err.println("file error " + e.getMessage());
        }
    }
}