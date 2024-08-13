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

    public void save(String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<body>\n");
            writer.write("\n");
            for (Shape shape : shapes) {
                writer.write(shape.toSvg(""));
            }
            writer.write("\n");
            writer.write("</body>\n");
            writer.write("</html>\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("can't write to " + path);
        }
    }
}
