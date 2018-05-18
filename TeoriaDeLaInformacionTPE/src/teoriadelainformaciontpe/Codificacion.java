/*
 * Teoria de la Informacion - 2018
 */
package teoriadelainformaciontpe;
 
import java.io.File; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer; 
import java.util.HashMap;
import java.util.Hashtable; 
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ileitao
 * @author ldiez
 */
public class Encoder {

    private BmpImage image;
    private long compressedSize;
    
    public Encoder(BmpImage image) {
        this.image = image;
    }
    

    public void generateFile() {
        Map<Integer, String> codification = new Huffman(image).doCodification(); // gets Huffman codification 
        try {

            String path = "huffman.txt";
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
                    if (code.length() > 15) { // pass a string into a char (binary)
                        String subS = code.substring(15, code.length());
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
            
            // Setting original image size
            File file = new File(path);
            compressedSize = file.length();
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
            Logger.getLogger(Encoder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Given string contains a binary sequence, so we shift left the string chars' (char in Java = 16 bits)
    private char getBits(String code) {
        char buffer = 0;
        for (int j = 0; j < 16; j++) {
            buffer = (char) (buffer << 1);
            if (code.charAt(j) == '1') {
                buffer = (char) (buffer | 1);
            }
        }
        return buffer;
    }

    public long getCompressedSize() {
        return compressedSize;
    }
}
