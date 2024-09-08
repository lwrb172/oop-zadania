package pl.umcs;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ImageProcessor imageProcessor = new ImageProcessor();

        try {
            imageProcessor.readImage("p6-image/src/main/resources/adolf.jpeg");
            {
                long startTime = System.currentTimeMillis();
                imageProcessor.setBrightness(100);
                imageProcessor.setBrightness(10);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
            }
            {
                long startTime = System.currentTimeMillis();
                imageProcessor.setBrightnessThreads(100);
                imageProcessor.setBrightnessThreads(10);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
            }
            {
                long startTime = System.currentTimeMillis();
                imageProcessor.setBrightnessThreadPool(100);
                imageProcessor.setBrightnessThreadPool(10);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
            }
            imageProcessor.writeImage("p6-image/src/main/resources/output_adolf.jpeg");
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }

    }
}