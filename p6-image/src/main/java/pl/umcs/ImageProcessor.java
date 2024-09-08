package pl.umcs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.IntStream;

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

    public void setBrightnessThreadPool(int brightness) {
        int threadsCount = Runtime.getRuntime().availableProcessors();
        try (ExecutorService executor = Executors.newFixedThreadPool(threadsCount)) {
            for (int i = 0; i < image.getHeight(); i++) {
                final int y = i;
                executor.execute(() -> {
                    for (int x = 0; x < image.getWidth(); x++) {
                        int rgb = image.getRGB(x, y);
                        int blue = rgb & 0xFF;
                        int green = (rgb & 0xFF00) >> 8;
                        int red = (rgb & 0xFF0000) >> 16;
                        blue = clamp(blue + brightness, 0, 255);
                        green = clamp(green + brightness, 0, 255);
                        red = clamp(red + brightness, 0, 255);
                        rgb = (red << 16) + (green << 8) + blue;
                        image.setRGB(x, y, rgb);
                    }
                });
            }
            executor.shutdown();
            try {
                boolean b = executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public int[] calculatedChannelHistogram(int channel) throws InterruptedException {
        int threadsCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);

        // Use AtomicIntegerArray for thread-safe histogram updates
        AtomicIntegerArray histogram = new AtomicIntegerArray(256);

        // Define a task for each thread
        Runnable task = () -> {
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int value = extractChannelValue(rgb, channel);
                    histogram.incrementAndGet(value);
                }
            }
        };

        // Submit tasks to the executor
        IntStream.range(0, threadsCount).forEach(i -> executor.submit(task));

        // Shutdown and wait for task to finish
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Convert AtomicIntegerArray to a regular int array for return
        return IntStream.range(0, histogram.length()).map(histogram::get).toArray();
    }

    private int extractChannelValue(int rgb, int channel) {
        return switch (channel) {
            case 0 -> // Red channel
                    (rgb & 0xFF0000) >> 16;
            case 1 -> // Green channel
                    (rgb & 0xFF00) >> 8;
            case 2 -> // Blue channel
                    rgb & 0xFF;
            default -> throw new IllegalArgumentException("Invalid channel: " + channel);
        };
    }
}
