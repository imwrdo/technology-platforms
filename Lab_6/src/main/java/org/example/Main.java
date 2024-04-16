package org.example;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

// input - D:\201267\Lab_6\photos
// output - D:\201267\Lab_6\photos_out

public class Main {
    private static String INPUT_DIRECTORY;
    private static String OUTPUT_DIRECTORY;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args){

        INPUT_DIRECTORY = args[0];
        OUTPUT_DIRECTORY = args[1];

        long time = System.currentTimeMillis();
        try {
            List<Pair<String, BufferedImage>> imagePairs = Files.list(Path.of(INPUT_DIRECTORY))
                    .parallel()
                    .map(Main::loadImagePair)
                    .collect(Collectors.toList());
            System.out.println("Załadowano " + imagePairs.size() + " zdjęć");

            ForkJoinPool customThreadPool = new ForkJoinPool(THREAD_POOL_SIZE);
            customThreadPool.submit(() ->
                    imagePairs.parallelStream()
                            .map(Main::transformImagePair)
                            .forEach(Main::saveImage)
            ).get();
            long operationTime = System.currentTimeMillis() - time;
            System.out.println("Czas wykonania: "+ operationTime/1000.0 + " s");

        }catch (Exception e){
            System.out.println("Bład: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static Pair<String, BufferedImage> loadImagePair(Path path){
        try {
            BufferedImage image = ImageIO.read(path.toFile());
            String name = path.getFileName().toString();
            return Pair.of(name, image);
        }catch (IOException e){
            System.out.println("Się nie udało załadować zdjęcia: " + path);
            e.printStackTrace();
            return null;
        }
    }
    private static Pair<String, BufferedImage> transformImagePair(Pair<String, BufferedImage> pair) {
        String name = pair.getLeft();
        BufferedImage image = pair.getRight();
        if (image == null) {
            System.out.println("Się nie udało zmienić zdjęcia: " + name + " - Image is null");
            return null;
        }
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int red = color.getRed();
                int blue = color.getGreen();
                int green = color.getBlue();
                Color outColor = new Color(green, blue, red);
                transformedImage.setRGB(image.getWidth()-x-1,image.getHeight()-y -1, outColor.getRGB());
            }
        }

        return Pair.of(name, transformedImage);
    }

    private static void saveImage(Pair<String, BufferedImage> pair) {
        String name = pair.getLeft();
        BufferedImage image = pair.getRight();
        if (image == null) {
            System.out.println("Się nie udało zapisać zdjęcia: " + name + " - Transformed image is null");
            return;
        }
        Path outputPath = Path.of(OUTPUT_DIRECTORY, name);
        try {
            ImageIO.write(image, "png", outputPath.toFile());
            System.out.println("Załadowano zdjęcie: " + outputPath);
        } catch (IOException e) {
            System.out.println("Się nie udało zapisać zdjęcia: " + outputPath);
            e.printStackTrace();
        }
    }
}
