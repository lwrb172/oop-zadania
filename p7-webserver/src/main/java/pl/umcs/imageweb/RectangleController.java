package pl.umcs.imageweb;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RectangleController {
    private List<Rectangle> rectangleList = new ArrayList<>();

    @GetMapping
    public String hello() {
        return "Hello world";
    }

    @GetMapping("addRect")
    public void addRect() {
        rectangleList.add(new Rectangle(5, 8, 3, 7, "green"));
    }

    @GetMapping("rect")
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

    @PostMapping("postRect")
    public void postRect(@RequestBody Rectangle rectangle) {
        rectangleList.add(rectangle);
    }
    // curl -X POST -H 'Content-Type:application/json' -d '{"x": 50, "y": 70, "w": 30, "h": 60, "color": "blue"}' localhost:8080/rect

    @GetMapping("rect/{index}")
    public Rectangle getRectByIndex(@PathVariable int index) {
        return rectangleList.get(index);
    }

    @PutMapping("rect/{index}")
    public void updateRect(@PathVariable int index, @RequestBody Rectangle rectangle) {
        rectangleList.set(index, rectangle);
    }

    @DeleteMapping("rect/{index}")
    public void deleteRect(@PathVariable int index) {
        rectangleList.remove(index);
    }

}
