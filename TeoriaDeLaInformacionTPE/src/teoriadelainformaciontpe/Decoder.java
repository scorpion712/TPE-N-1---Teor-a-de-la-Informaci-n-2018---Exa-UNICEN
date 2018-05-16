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
import java.io.ObjectInputStream;
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
/*
    private BmpImage image;
    private Map<Integer, String> codification = new HashMap<>();

    private void headerDecode(File file) {
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                // Decode image size
                int width = Integer.parseInt(br.readLine());
                int height = Integer.parseInt(br.readLine());
                image = new BmpImage(new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY), "decode_image");
                // Decode symbols amount
                int symbols = Integer.parseInt(br.readLine());
                // Decode symbols and its occurence
                Vector<Integer> vectorSymb = new Vector<>(); // A vector with image symbols (pixels)
                double[] vectorProb = new double[symbols]; // A vector with pixels probabilities
                for (int i = 0; i < symbols; i++) {
                    vectorSymb.add(Integer.parseInt(br.readLine()));
                    vectorProb[i] = (double) Integer.parseInt(br.readLine()) / (width * height);
                }
                Huffman huffman = new Huffman(image);
                Map<Integer, String> codification = huffman.getCodification(); // Get Huffman codification
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Decoder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Decoder.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void headerDecode(ObjectInputStream ois) {
        try {
            // Decode image size
            int width = ois.readInt();
            int height = ois.readInt();
            image = new BmpImage(new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY), "decode_image");
            // Decode symbols amount
            int symbols = ois.readInt();
            // Decode symbols and its occurence
            Vector<Integer> vectorSymb = new Vector<>(); // A vector with image symbols (pixels)
            double[] vectorProb = new double[symbols]; // A vector with pixels probabilities
            for (int i = 0; i < symbols; i++) {
                vectorSymb.add(ois.readInt());
                vectorProb[i] = (double) ois.readInt() / (width * height);
            }
            Huffman huffman = new Huffman(image);
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

            boolean painted = false;
            if (file.exists()) {
                headerDecode(file);
                for (int i = 0; i < image.getHeight(); i++) {
                    for (int j = 0; j < image.getWidth(); j++) {
                        // paint image
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
*/
}
