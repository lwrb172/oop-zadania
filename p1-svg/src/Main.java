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
        Point t1 = new Point(100, 10);
        Point t2 = new Point(150, 190);
        Point t3 = new Point(50, 190);
        Point[] trianglePoints = new Point[]{t1, t2, t3};
        Polygon triangle = new Polygon(trianglePoints, unit00);
        // six-sides
        Point ss1 = new Point(150, 15);
        Point ss2 = new Point(258, 77);
        Point ss3 = new Point(258, 202);
        Point ss4 = new Point(150, 265);
        Point ss5 = new Point(42, 202);
        Point ss6 = new Point(42, 77);
        Point[] sixSidesPoints = new Point[]{ss1, ss2, ss3, ss4, ss5, ss6};
        Polygon sixSides = new Polygon(sixSidesPoints, unit01);
        // starS
        Point s1 = new Point(100, 10);
        Point s2 = new Point(40, 198);
        Point s3 = new Point(190, 78);
        Point s4 = new Point(10, 78);
        Point s5 = new Point(160, 198);
        Point[] starPoints = new Point[]{s1, s2, s3, s4, s5};
        Polygon star = new Polygon(starPoints, unit02);
        // square
        Segment diagonal = new Segment(new Point(25,25), new Point(175, 175));
        Polygon square = Polygon.square(diagonal, unit03);
        // ellipse
        Point center = new Point(120, 80);
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