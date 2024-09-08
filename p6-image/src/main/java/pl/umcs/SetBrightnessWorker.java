package pl.umcs;

import java.awt.image.BufferedImage;

import static java.lang.Math.clamp;

public class SetBrightnessWorker implements Runnable {
    private int begin, end;
    private int brightness;
    private BufferedImage image;

    public SetBrightnessWorker(int begin, int end, int brightness, BufferedImage image) {
        this.begin = begin;
        this.end = end;
        this.brightness = brightness;
        this.image = image;
    }

    @Override
    public void run() {
        for (int y = begin; y < end; y++) {
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
}
