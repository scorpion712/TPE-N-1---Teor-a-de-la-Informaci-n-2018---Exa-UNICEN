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

    private int headerDecode(char cbuf[]) {
        int n = 3;
        try {
            int width = cbuf[0];
            int height = cbuf[1];
            image = new BmpImage(new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY), "decode_image");
            int symbols = cbuf[3];
            Vector<Integer> symbVec = new Vector<>();
            double[] probVec = new double[symbols];
            for (int i = 0; i < symbols; i++) {
                int s = cbuf[n];
                symbVec.add(s);
                probVec[i] = (double) cbuf[n + 1] / (width * height);
                n++;
            }
            System.out.println("symbVec: " + symbVec.toString());
            codification = new Huffman(symbVec, probVec).getCodification();
            return n;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    private int n=0;
    public BmpImage decodeImage() {
        try {
            String path = "huffman.txt";
            // Cargar datos de archivo txt utf-16
            InputStream inputStream = new FileInputStream(path);
            Reader ois = new InputStreamReader(inputStream, "UTF-16");
            // ...
            char[] cbuf = new char[2048];
            headerDecode(ois);
            // NOTA: Leer la documentación del método read para entender como funciona 
            int nChars = ois.read(cbuf);
            char character;
            StringBuffer symbols = new StringBuffer();

            //int n = headerDecode(cbuf);
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    boolean painted = false;
                    while (!painted && n < nChars) {
                        Integer decodeSymb = decodeSymbol(symbols);
                        if (decodeSymb != null) {
                            image.getBmp().setRGB(i, j, decodeSymb);
                            BmpHelper.writeBmpPixels(image, i, j, decodeSymb);
                            painted = true;
                        } else {
                            character = cbuf[n];
                            binarizeString(character, symbols);
                        }
                        n++;
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
            n=3;
            Vector<Integer> symbVec = new Vector<>();
            double[] probVec = new double[symbols];
            for (int i = 0; i < symbols; i++) {
                symbVec.add(ois.read());
                probVec[i] = (double) ois.read() / (width * height); 
                n++;
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

    private Integer getSymbol(String code) {
        if (codification.containsValue(code)) {
            for (Integer i : codification.keySet()) {
                if (codification.get(i).equals(code)) {
                    return i;
                }
            }
        }
        return null;
    }

    private void binarizeString(char character, StringBuffer symbols) {
        char mask = 1 << 15;
        for (int i = 0; i < 16; i++) {
            if ((character & mask) == 32768) {
                symbols.append("1");
            } else {
                symbols.append("0");
            }
            character = (char) (character << 1);
        }
    }

}
