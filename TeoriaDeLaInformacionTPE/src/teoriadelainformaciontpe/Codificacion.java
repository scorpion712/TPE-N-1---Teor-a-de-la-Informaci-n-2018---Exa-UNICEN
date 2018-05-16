/*
 * Teoria de la Informacion - 2018
 */
package teoriadelainformaciontpe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    /*
    Solo genero el código, falta decodificar... 
	
    private BmpImage image;
    
    
    public Codificacion(BmpImage image) {
        this.image = image;
    }

    // Header encode. Contains image size, symbols amount and its occurrences
    private void headerEncode(BufferedWriter bw) {
        try {
            // Write image size
            bw.write(image.getBmp().getWidth());
            bw.write(image.getBmp().getHeight());

            Hashtable<Integer, Integer> readImageTable = BmpHelper.readImage(image.getBmp()); // symbol, times its appear
            Vector<Integer> vectorSimbolos = new Vector<Integer>(readImageTable.keySet().size()); // Write symbols amount
            Collections.sort(vectorSimbolos);
            Vector<Double> vectorProb = BmpHelper.getProb(vectorSimbolos, readImageTable, image.getBmp()); // symbol probability
            for (Double d : vectorProb) {
                bw.write(d.toString()); // symbol occurrences
            }
        } catch (IOException ex) {
            Logger.getLogger(Codificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateFile() {
        Map<Integer, String> codification = new Huffman(image).getCodification(); // gets Huffman codification
        try {
            String path = "huffman.txt";
            File file = new File(path);
            BufferedWriter bw;
            if (file.exists()) {
                bw = new BufferedWriter(new FileWriter(file));

                // Header to encode 
                headerEncode(bw);

                Iterator it = codification.keySet().iterator();
                while (it.hasNext()) {
                    Integer key = (Integer) it.next();
                    bw.write(codification.get(key));
                    //bw.write(key + " = " + codification.get(key));
                    //System.out.println(key + " = " + codification.get(key));
                }
            } else {
                bw = new BufferedWriter(new FileWriter(file));

                Iterator it = codification.keySet().iterator();
                while (it.hasNext()) {
                    Integer key = (Integer) it.next();
                    bw.write(key + " = " + codification.get(key));
                    System.out.println(key + " = " + codification.get(key));
                }
                bw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



     */
}
