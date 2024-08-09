import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SvgScene {
    private ArrayList<Shape> shapes;

    public SvgScene(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public void save(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.append("<!DOCTYPE html>\n<html>\n<body>\n\n");
        for (Shape shape : shapes) {
            writer.append(shape.toSvg()).append("\n");
        }
        writer.append("</body>\n</html>");
        writer.close();
    }
}
