import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SvgScene {
    private List<Shape> shapes = new ArrayList<>();
    private static SvgScene instance = null;
    protected List<String> defs = new ArrayList<>();
    private static int index = 0;
    private static int gradientIndex = 0;

    public SvgScene() {}

    public static SvgScene getInstance() {
        if (instance == null) {
            instance = new SvgScene();
        }
        return instance;
    }

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public int addFilter(String filter) {
        defs.add(
                String.format(filter, ++index)
        );
        return index;
    }

    public int addGradient(String filter) {
        defs.add(
                String.format(filter, ++gradientIndex)
        );
        return gradientIndex;
    }

    public void save(String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<body>\n");
            writer.write(
                    "<svg width=\"1000\" height=\"1000\">\n"
            );
            writer.write("<defs>\n");
            for (String def : defs) {
                writer.write(def + "\n");
            }
            writer.write("</defs>\n");
            writer.write("\n");
            for (Shape shape : shapes) {
                writer.write(shape.toSvg(""));
            }
            writer.write("</svg>\n");
            writer.write("</body>\n");
            writer.write("</html>\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("can't write to " + path);
        }
    }
}
