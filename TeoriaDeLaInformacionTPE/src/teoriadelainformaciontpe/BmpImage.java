/*
 * Teoria de la Informacion - 2018
 */
package teoriadelainformaciontpe;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.imageio.ImageIO;

/**
 * Class BmpHelper
 * @author ileitao
 * @author ldiez
 */
public class BmpHelper {

    public static BufferedImage readBmpImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println(e);
        }

        return img;
    }

    public static BufferedImage writeBmpImage(BufferedImage img, String path) {
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println(e);
        }

        return img;
    }


    public static void writeBmpPixels(BmpImage image, int i, int j, int rgb) {
        image.getBmp().setRGB(i, j, new Color(rgb,0,0).getRed());
    //  return image;
    }

    /*
     * Return <bmp>
     */
    public static double getBmpMedia(BmpImage bmp) {
        double media = 0.0f;
        int width = bmp.getBmp().getWidth();
        int height = bmp.getBmp().getHeight();
        int bmpPixels = width * height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = bmp.getBmp().getRGB(x, y);
                Color color = new Color(rgb, true);
                int r = color.getRed();
                media += r;
            }
        }

        return media / bmpPixels;
    }

    /*
     * Return <ab>
     */
    public static double getCorrelation(BmpImage bmp1, BmpImage bmp2) {
        double correlation = 0.0f;
        int width = bmp1.getBmp().getWidth();
        int height = bmp1.getBmp().getHeight();
        int bmpPixels = width * height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Color 1
                int rgb_color1 = bmp1.getBmp().getRGB(x, y);
                Color color1 = new Color(rgb_color1, true);
                int red1 = color1.getRed();
                // Color 2
                int rgb_color2 = bmp2.getBmp().getRGB(x, y);
                Color color2 = new Color(rgb_color2, true);
                int red2 = color2.getRed();
                correlation += red1 * red2;
            }
        }

        return correlation / bmpPixels;
    }

    /*
     * Return <bmp^2>-<bmp>^2
     */
    public static double getVariance(BmpImage bmp) {
        double variance = 0.0f;
        int width = bmp.getBmp().getWidth();
        int height = bmp.getBmp().getHeight();
        int bmpPixels = width * height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = bmp.getBmp().getRGB(x, y);
                Color color = new Color(rgb, true);
                int r = color.getRed();
                variance += Math.pow(r, 2);
            }
        }

        variance = (variance / bmpPixels) - Math.pow(bmp.getMedia(), 2);
        return variance;
    }

    /*
     * Return square root of Variance
     */
    public static double getStandardDeviation(BmpImage bmp) {
        double variance = 0.0f;
        int width = bmp.getBmp().getWidth();
        int height = bmp.getBmp().getHeight();
        int bmpPixels = width * height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = bmp.getBmp().getRGB(x, y);
                Color color = new Color(rgb, true);
                int r = color.getRed();
                variance += (r * r);
            }
        }

        variance = (variance / bmpPixels) - (bmp.getMedia() * bmp.getMedia());
        return Math.sqrt(variance);
    }

    /*
     * Return <ab> - <a><b>
     */
    public static double getCovariance(BmpImage bmp1, BmpImage bmp2) {
        return (BmpHelper.getCorrelation(bmp1, bmp2) - (bmp1.getMedia() * bmp2.getMedia()));
    }

    /*
     * Return Covariance(a,b) / (Standard Deviation(a) * Standard Deviation(b))
     */
    public static double getCorrelationFactor(BmpImage bmp1, BmpImage bmp2) {
        return BmpHelper.getCovariance(bmp1, bmp2) / (BmpHelper.getStandardDeviation(bmp1) * BmpHelper.getStandardDeviation(bmp2));
    }
    
    // Reading the image and counting pixels amount
    public static Hashtable<Integer, Integer> readImage(BufferedImage imageBmp) {
        Hashtable<Integer, Integer> hashTable = new Hashtable<Integer, Integer>(); // <pixel, amount>
        int pixel = 0;
        for (int i = 0; i < imageBmp.getWidth(); i++) {
            for (int j = 0; j < imageBmp.getHeight(); j++) {
                //pixel = imageBmp.getRGB(i, j) / 256; // obtain gray
                pixel = new Color(imageBmp.getRGB(i, j), true).getRed(); // obtain gray 
                if (hashTable.containsKey(pixel)) {
                    hashTable.put(pixel, hashTable.get(pixel) + 1); // adding one occurrence 
                } else {
                    hashTable.put(pixel, new Integer(1)); // a new occurrence
                }
            }
        }
        return hashTable;
    }
    
    
    // Getting a vector with the pixel's probabilities
    public static Vector<Double> getProb(Vector<Integer> simbols, Hashtable<Integer, Integer> htable, BufferedImage image) {
        Vector<Double> vector = new Vector<Double>();
        double pixels_amount = image.getHeight() * image.getWidth();
        for (int i = 0; i < simbols.size(); i++) {
            vector.add(htable.get(simbols.get(i)) / pixels_amount);
        }
        return vector;
    }
    
    // Getting a Map with the pixel (key) and its probability
    public static Map<Integer,Double> getDistribution(BmpImage image) {
        Map<Integer, Double> distribution = new HashMap<>();
        Hashtable<Integer, Integer> readImage = readImage(image.getBmp());
        for (Integer i : readImage.keySet()) {
            distribution.put(i, readImage.get(i)/(double)image.size());
        }
        return distribution;
    }
}
