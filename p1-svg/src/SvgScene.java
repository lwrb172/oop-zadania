import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SvgScene {
    private ArrayList<Polygon> polygons;

    public SvgScene(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }

    public void add(Polygon polygon) {
        polygons.add(polygon);
    }

    public void save(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.append("<!DOCTYPE html>\n<html>\n<body>\n\n");
        for (Polygon polygon : polygons) {
            writer.append(polygon.toSvg()).append("\n");
        }
        writer.append("\n</body>\n</html>");
        writer.close();
    }
}
