package pl.umcs;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ImageProcessor imageProcessor = new ImageProcessor();

        try {
            imageProcessor.readImage("p6-image/src/main/resources/adolf.jpeg");
            imageProcessor.setBrightness(-50);
            imageProcessor.setBrightness(-50);
            imageProcessor.writeImage("p6-image/src/main/resources/output_adolf.jpeg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}