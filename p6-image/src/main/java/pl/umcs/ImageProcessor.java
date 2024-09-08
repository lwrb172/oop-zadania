package pl.umcs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.clamp;

public class ImageProcessor {
    private BufferedImage image;

    public void readImage(String path) throws IOException {
        image = ImageIO.read(new File(path));
    }

    public void writeImage(String path) throws IOException {
        ImageIO.write(image, "jpg", new File(path));
    }

    public void setBrightness(int brightness) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int blue = rgb&0xFF;
                int green = (rgb&0xFF00)>>8;
                int red = (rgb&0xFF0000)>>16;
                blue = clamp(blue + brightness, 0, 255);
                green = clamp(green + brightness, 0, 255);
                red = clamp(red + brightness, 0, 255);
                rgb = (red<<16) + (green<<8) + blue;
                image.setRGB(x, y, rgb);
            }
        }
    }

    public void setBrightnessThreads(int brightness) throws InterruptedException {
        int threadsCount = Runtime.getRuntime().availableProcessors();
        Thread[] threads;
        threads = new Thread[threadsCount];
        int chunk = image.getHeight() / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            int begin = i * chunk;
            int end = (i == threadsCount - 1) ? image.getHeight() : (i+1) * chunk;
            threads[i] = new Thread(new SetBrightnessWorker(begin, end, brightness, image));
            threads[i].start();

        }
        for (int i = 0; i < threadsCount; i++)
            threads[i].join();
    }
}
