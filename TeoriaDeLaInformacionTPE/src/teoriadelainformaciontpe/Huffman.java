/*
 * Teoria de la Informacion - 2018
 */
package teoriadelainformaciontpe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author ileitao
 * @author ldiez
 */
public class Huffman {

    private BmpImage image;

    public Huffman(BmpImage image) {
        this.image = image;
    }

    
    // Getting huffman's code for the image
    public Map<Integer, String> getCodification() {
        Map<Integer, String> codification = new HashMap<>();
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
        return codification;
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
}
