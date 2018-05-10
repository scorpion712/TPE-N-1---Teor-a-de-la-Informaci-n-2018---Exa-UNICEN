/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformaciontpe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

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

    //*
    private BmpImage image;
    private Map<Integer, String> codification = new HashMap<>(); // it will contains the huffman code for each symbol (pixel in this case)

    public Codificacion(BmpImage image) {
        this.image = image;
    }

    // Getting huffman's code for the given image
    public void huffman() {
        Map<Integer, Double> distribution = BmpHelper.getDistribution(image);

        Map<Integer, Node> symbol_node = new HashMap<Integer, Node>();
        Vector<Node> middle = new Vector<Node>();

        for (Integer symbol : distribution.keySet()) {
            Node node = new Node(distribution.get(symbol), -1, null);
            middle.add(node);
            symbol_node.put(symbol, node);
        }
        encode(middle);
        for (Integer symbol : distribution.keySet()) {
            Node node = symbol_node.get(symbol);
            codification.put(symbol, recursion(node));
        }
    }

    // Creating huffman tree
    public void encode(Vector<Node> nodeVector) {
        if (nodeVector.size() > 1) { // we need the last two
            Collections.sort(nodeVector); // probabilities order
            Vector<Node> nextVectorNode = new Vector<Node>();
            for (int i = 0; i < nodeVector.size() - 2; i++) {
                Node next = new Node(nodeVector.get(i).getProbability(), -1, null); // -1 is an invalid code
                nodeVector.elementAt(i).setNext(next);
                nextVectorNode.add(next);
            }
            double suma = (nodeVector.get(nodeVector.size() - 1).getProbability() + nodeVector.elementAt(nodeVector.size() - 2).getProbability()); // add the lower probabilities 
            Node newNode = new Node(suma, -1, null);
            //lower branch tree code = 0, 1 for the other branch
            nodeVector.elementAt(nodeVector.size() - 2).setNext(newNode);
            nodeVector.elementAt(nodeVector.size() - 2).setCode(1);
            nodeVector.get(nodeVector.size() - 1).setNext(newNode);
            nodeVector.elementAt(nodeVector.size() - 1).setCode(0);
            nextVectorNode.add(newNode);
            encode(nextVectorNode); // calls itself with the new next vector
        }
    }

    public String recursion(Node node) {
        if (node != null && node.getProbability() < 1) {
            if (node.getCode() == 1) {
                return recursion(node.getNext()) + "1";
            }   // not else because code can be -1 (by default)
            if (node.getCode() == 0) {
                return recursion(node.getNext()) + "0";
            }
            return recursion(node.getNext());
        }
        return "";
    }

    
    public void generateFile() {
        huffman();
        try {
            String path = "huffman.txt";
            File file = new File(path);
            BufferedWriter bw;
            if (file.exists()) {
                bw = new BufferedWriter(new FileWriter(file));

                Iterator it = codification.keySet().iterator();
                while (it.hasNext()) {
                    Integer key = (Integer) it.next();
                    bw.write(key + " = " + codification.get(key));
                    System.out.println(key + " = " + codification.get(key));
                }
                //bw.write("El fichero de texto ya estaba creado.");
            } else {
                bw = new BufferedWriter(new FileWriter(file));

                Iterator it = codification.keySet().iterator();
                while (it.hasNext()) {
                    Integer key = (Integer) it.next();
                    bw.write(key + " = " + codification.get(key));
                    System.out.println(key + " = " + codification.get(key));
                    //bw.write("Acabo de crear el fichero de texto.");
                }
                bw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
