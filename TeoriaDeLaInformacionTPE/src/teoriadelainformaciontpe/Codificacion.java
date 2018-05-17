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
            Collections.sort(vectorSimbolos);
            Vector<Double> vectorProb = BmpHelper.getProb(vectorSimbolos, readImageTable, image.getBmp()); // symbol probability

            for (int i=0; i < vectorSimbolos.size(); i++) {
                bw.write(vectorSimbolos.elementAt(i).toString()); // symbol
                bw.newLine();
                bw.write(vectorProb.elementAt(i).toString()); // symbol occurrences
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
            Writer outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16");

            // Buffered to write the file using UTF-16
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter (new FileOutputStream(file), "UTF-16"));

            if (file.exists()) {
                // Header to encode 
                headerEncode(bw);
                Iterator it = codification.keySet().iterator();
                while (it.hasNext()) {
                    Integer key = (Integer) it.next();
                    bw.write(codification.get(key)); // 1's y 0's codificados en un String
                    bw.newLine();
                }
            } else {
                headerEncode(bw);
                Iterator it = codification.keySet().iterator();
                while (it.hasNext()) {
                    Integer key = (Integer) it.next();
                    bw.write(codification.get(key)); // 1's y 0's codificados en un String
                    bw.newLine();
                }
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
