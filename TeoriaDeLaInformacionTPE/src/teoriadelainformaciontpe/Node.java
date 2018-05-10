/*
 * Teoria de la Informacion - 2018
 */
package teoriadelainformaciontpe;

/**
 *
 * @author ileitao
 * @author ldiez
 */
public class Node implements Comparable<Object> {

    // A node to use in Huffman's tree

    private int code;
    private double probability;
    private Node next;
    
    public Node(double probability, int code, Node next){
        this.probability = probability;
        this.code = code;
        this.next = next;
    }
    
    public double getProbability(){
        return probability;
    }
    
    public int getCode(){
        return code;
    }
    
    public void setNext (Node next){
        this.next = next;
    }
    
     public Node getNext(){
        return next;
    }
    
    public void setCode(int code){
        this.code = code;
    }
    
    @Override
    public int compareTo(Object o) {
        Node newNode = (Node) o;
        if (this.probability < newNode.getProbability())
            return 1;
        if (this.probability > newNode.getProbability())
            return -1;
        return 0;
    }
}
