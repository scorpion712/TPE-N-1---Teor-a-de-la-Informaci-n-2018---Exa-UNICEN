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
        System.out.println("Codifiation: " + codification);
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
                vectorProb[i] = Double.valueOf(in.readLine()) / (width * height);
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
            // Cargar datos de archivo txt utf-16
            InputStream inputStream = new FileInputStream(path);
            Reader ois = new InputStreamReader(inputStream, "UTF-16");
            // ...
            char[] cbuf = new char[2048];
            // NOTA: Leer la documentación del método read para entender como funciona 
           // int nChars = inputStreamWriter.read(cbuf);

            FileInputStream archivoentrada = new FileInputStream(path);
          //  ObjectInputStream ois = new ObjectInputStream(archivoentrada, "UTF-16");

            char character;
            StringBuffer symbols = new StringBuffer();
            headerDecode(ois);

            for (int j = 0; j < image.getHeight(); j++) {
                for (int i = 0; i < image.getWidth(); i++) {
                    boolean painted = false;
                    while (!painted) {
                        Integer decodeSymb = decodeSymbol(symbols);
                            System.out.println("decodeSymb: " + decodeSymb);
                        if (decodeSymb != null && decodeSymb > -1) {
                            BmpHelper.writeBmpPixels(image, i, j, decodeSymb);
                            painted = true;
                        } else {
                            character = (char) ois.read();
                            System.out.println("character: " + character);
                            character = binarizeString(character, symbols);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    private void headerDecode(Reader ois) {
        try {
            int width = ois.read();
            int height = ois.read();
            image = new BmpImage(new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY), "decode_image");
            int symbols = ois.read();

            Vector<Integer> symbVec = new Vector<>();
            double[] probVec = new double[symbols];
            for (int i = 0; i < symbols; i++) {
                symbVec.add(ois.read());
                probVec[i] = (double) ois.read() / (width * height);
            }
            codification = new Huffman(symbVec, probVec).getCodification();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer decodeSymbol(StringBuffer symbols) {
        for (int i = 0; i < symbols.length(); i++) {
            String subst = symbols.substring(0, i);
            Integer retorno = getSymbol(subst);
            if (retorno != null) {
                symbols.delete(0, i);
                return retorno;
            }
        }
        return null;
    }

    private char binarizeString(char character, StringBuffer symbols) {
        char mascara = 1 << 15;
        for (int i = 0; i < 16; i++) {
            if ((character & mascara) == 32768) {
                symbols.append("1");
            } else {
                symbols.append("0");
            }
            character = (char) (character << 1);
        }
        return character;
    }
}
