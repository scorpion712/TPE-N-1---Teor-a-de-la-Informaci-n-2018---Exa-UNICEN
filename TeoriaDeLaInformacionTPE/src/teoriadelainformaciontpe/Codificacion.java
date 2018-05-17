/*
 * Teoria de la Informacion - 2018
 */
package teoriadelainformaciontpe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ileitao
 * @author ldiez
 */
public class Codificacion {

    public static void generarBits(char num) {

    }

    public static String generarCodificacion(Vector<String> msg, HashMap<String, String[]> codif) {
        return new String();
    }

    private BmpImage image;

    public Codificacion(BmpImage image) {
        this.image = image;
    }

    // Header encode. Contains image size, symbols amount and its occurrences
    private void headerEncode(BufferedWriter bw) {
        try {
            // Write image size
            bw.write(String.valueOf(image.getWidth()));
            bw.newLine();
            bw.write(String.valueOf(image.getHeight()));
            bw.newLine();
            Hashtable<Integer, Integer> readImageTable = BmpHelper.readImage(image.getBmp()); // symbol, times its appear
            Vector<Integer> vectorSimbolos = new Vector<Integer>(readImageTable.keySet());
            bw.write(String.valueOf(vectorSimbolos.size())); // Write symbols amount
            bw.newLine();

            for (int i = 0; i < vectorSimbolos.size(); i++) {
                bw.write(vectorSimbolos.elementAt(i).toString()); // symbol
                bw.newLine();
                bw.write(readImageTable.get(vectorSimbolos.elementAt(i)).toString()); // symbol occurrences
                bw.newLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(Codificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateFile() {
        Map<Integer, String> codification = new Huffman(image).doCodification(); // gets Huffman codification 
        try {

            String path = "huffman.txt";
            File file = new File(path);
            // Guardar archivo txt utf-16
            OutputStream outputStream = new FileOutputStream(path);
            Writer writer = new OutputStreamWriter(outputStream, "UTF-16");

            headearEncode(writer);

            String code = new String();
            int pixel;
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    pixel = image.getPixel(i, j);
                    code += codification.get(pixel);
                    if (code.length() > 16) { //15
                        String subS = code.substring(16, code.length());
                        writer.write(getBits(code));
                        code = subS;
                    }
                }
            }

            if (code.length() != 0) {
                int j = code.length();
                for (int i = j; i < 16; i++) {
                    code += "0";
                }
                writer.write(getBits(code));
            } else {
                String eof = "0000000000000000";
                writer.write(getBits(eof));
            }

            writer.close();

            /*
            // Guardar archivo txt utf-16
            OutputStream outputStream = new FileOutputStream(path);
            Writer outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16");

            // Buffered to write the file using UTF-16
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-16"));

            if (file.exists()) {
                // Header to encode 
                headerEncode(bw);
                System.out.println("Símbolo : codificación");
                for (Integer i : codification.keySet()) {
                    System.out.println(i + " : " + codification.get(i));
                }
                for (int i = 0; i < image.getWidth(); i++) {
                    for (int j = 0; j < image.getHeight(); j++) {
                        bw.write(codification.get(image.getPixel(i, j))); // 1's y 0's codificados en un String
                        bw.newLine();
                    }
                }
 
            } else {
                headerEncode(bw);

                for (int i = 0; i < image.getWidth(); i++) {
                    for (int j = 0; j < image.getHeight(); j++) {
                        bw.write(codification.get(image.getPixel(i, j))); // 1's y 0's codificados en un String
                        bw.newLine();
                    }
                }
            }
            bw.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void headearEncode(Writer writer) {
        try {
            // Write image size
            writer.write(image.getWidth());
            writer.write(image.getHeight());
            Hashtable<Integer, Integer> readImageTable = BmpHelper.readImage(image.getBmp()); // symbol, times its appear
            Vector<Integer> vectorSimbolos = new Vector<Integer>(readImageTable.keySet());
            writer.write(vectorSimbolos.size()); // Write symbols amount

            for (int i = 0; i < vectorSimbolos.size(); i++) {
                writer.write(vectorSimbolos.elementAt(i)); // symbol 
                writer.write(readImageTable.get(vectorSimbolos.elementAt(i))); // symbol occurrences
            }
        } catch (IOException ex) {
            Logger.getLogger(Codificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
	Dado un código de tipo String que contiene una secuencia de unos y ceros, se devuelve el char equivalente a su contenido, tomando la secuencia como si fuera un número binario.
Para tal fin se realizan corrimientos del char, 16 veces, ya que como se mencionó previamente, en Java una variable de este tipo ocupa 16 bits.

     */
    private int getBits(String code) {
        char buffer = 0;
        for (int j = 0; j < 16; j++) {
            buffer = (char) (buffer << 1);
            if (code.charAt(j) == '1') {
                buffer = (char) (buffer | 1);
            }
        }
        return buffer;
    }

}
