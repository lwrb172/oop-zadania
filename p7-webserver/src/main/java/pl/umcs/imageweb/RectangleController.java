package pl.umcs.imageweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RectangleController {
    private List<Rectangle> rectangleList = new ArrayList<>();

    @GetMapping
    public String hello() {
        return "Hello world";
    }

    @GetMapping("rect")
    public Rectangle rect() {
        return new Rectangle(4, 2, 6, 9, "red");
    }

    @GetMapping("addRect")
    public void addRect() {
        rectangleList.add(new Rectangle(5, 8, 3, 7, "green"));
    }

    @GetMapping("getRect")
    public List<Rectangle> getRect() {
        return rectangleList;
    }

    @GetMapping("toSvg")
    public String toSvg() {
        StringBuilder result = new StringBuilder();
        result.append("<svg width=\"1000\" height=\"1000\">");
        for (Rectangle rect : rectangleList) {
            result.append(String.format(
                    "<rect width=\"%d\" height=\"%d\" x=\"%d\" y=\"%d\" fill=\"%s\"/>",
                    rect.getWidth(), rect.getHeight(),
                    rect.getX(), rect.getY(), rect.getColor()));
        }
        result.append("</svg>");
        return result.toString();
    }
}
