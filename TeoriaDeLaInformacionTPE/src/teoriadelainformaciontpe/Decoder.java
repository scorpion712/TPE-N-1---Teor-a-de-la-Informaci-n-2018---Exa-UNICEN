/*
 * Teoria de la Informacion - 2018
 */
package teoriadelainformaciontpe;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ileitao
 * @author ldiez
 */
public class Decoder {

    private BmpImage image;
    private Map<Integer, String> codification = new HashMap<>();

    public Decoder() {

    }

    private Integer getSymbol(String key) {
        if (codification.containsKey(key)) {
            for (Integer i : codification.keySet()) {
                if (codification.get(i).equals(key)) {
                    return i;
                }
            }
        }

        return -1;
    }


    private void headerDecode(BufferedReader in) {
        try {
            // Decode image size
            
            int width = Integer.parseInt(in.readLine());
            int height = Integer.parseInt(in.readLine()); 

            image = new BmpImage(new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY), "decode_image");
            // Decode symbols amount
            int symbols = Integer.parseInt(in.readLine());  
            // Decode symbols and its occurence
            Vector<Integer> vectorSymb = new Vector<>(); // A vector with image symbols (pixels)
            double[] vectorProb = new double[symbols]; // A vector with pixels probabilities
            for (int i = 0; i < symbols; i++) {
                vectorSymb.add(Integer.parseInt(in.readLine()));
                vectorProb[i] = (double) Double.valueOf(in.readLine());
            }
            Huffman huffman = new Huffman(vectorSymb, vectorProb); // Encode the image
            codification = huffman.getCodification(); // Get Huffman codification 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Decoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Decoder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public BmpImage decodeImage() {
        try {
            String path = "huffman.txt";
            File file = new File(path);
            // BufferedReader to read the file using UTF-16
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-16"));
 
			StringBuffer simbolos = new StringBuffer();
            boolean painted = false;
            if (file.exists()) {
                headerDecode(in);
                /**
                 *  This part doesn't work
                 */
                for (int i = 0; i < image.getHeight(); i++) {
                    for (int j = 0; j < image.getWidth(); j++) {
                        // paint image
                        int pixel = getSymbol(in.readLine());
                        if (pixel != -1) {
                            BmpHelper.writeBmpPixels(image, i, j, pixel);
                            painted = true;
                        }
                    }
                } 
            }
            if (painted) {
                return image;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
