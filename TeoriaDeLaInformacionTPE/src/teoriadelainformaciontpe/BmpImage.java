/*
 * Teoria de la Informacion - 2018
 */
package teoriadelainformaciontpe;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Class BmpImage
 * @author ileitao
 * @author ldiez
 */
public class BmpImage implements Comparable<BmpImage> {
    
    private BufferedImage bmp;
    
    private String name;
    
    private double media;
    
    private double variance;
    
    private double standardDeviation;
    
    private double correlationFactor;
    
    public BmpImage(BufferedImage bmp, String name) {
        this.bmp = bmp;
        this.name = name;
        this.media = BmpHelper.getBmpMedia(this);
        this.variance = BmpHelper.getVariance(this);
    }
    
    public BufferedImage getBmp() {
        return this.bmp;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getMedia() {
        return this.media;
    }
    
    public double getVariance() {
        return this.variance;
    }
    
    public double getStandardDeviation() {
        return this.standardDeviation;
    }
        
    public double getCorrelationFactor() {
        return this.correlationFactor;
    }
    
    public void setCorrelationFactor(double correlationFactor) {
        this.correlationFactor = correlationFactor;
    }

    public int size() {
        return bmp.getHeight() * bmp.getWidth();
    }
    
    @Override
    public int compareTo(BmpImage o) {
        BmpImage otherBmp = (BmpImage)o;
        if (this.correlationFactor > otherBmp.getCorrelationFactor()) {
            return -1;
        } else if (this.correlationFactor < otherBmp.getCorrelationFactor()) {
            return 1;
        }
        
        return 0;
    }
   
    public int getHeight()  {
        return bmp.getHeight();
    }
    
    public int getWidth() {
        return bmp.getWidth();
    }
    
    public int getPixel(int i, int j) {
        return new Color(bmp.getRGB(i, j), true).getRed();
    }
    
}
