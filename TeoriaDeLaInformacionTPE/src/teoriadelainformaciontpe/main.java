/*
 * Teoria de la Informacion - 2018
 */
package teoriadelainformaciontpe;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Class main
 * @author ileitao
 * @author ldiez
 */
public class main {
  
    private static List<BmpImage> images = new ArrayList<>();
 
    // Crea y muestra un histograma con la biblioteca JFreeChart
    private static void showHistogram(Vector<Integer> simbols, Vector<Double> simbolsProb, BmpImage image) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < simbols.size(); i++) {
            dataset.setValue(simbolsProb.get(i), "Intensidad de gris [0-255]", simbols.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(image.getName(), "Símbolos", "Probabilidad de ocurrencia", dataset, PlotOrientation.VERTICAL, true, true, true);
        ChartFrame frame = new ChartFrame("Histograma de la imagen", chart);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.pack(); // preferred sizes
        frame.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.err.println("****** PUNTO 1 ******");   
        // 1) Ordenar imgs utilizando factor de correlación
        BmpImage original = new BmpImage(BmpHelper.readBmpImage("Images/Will/Will(Original).bmp"), "Original");
        original.setCorrelationFactor(1);
        // Resto de imagenes a comparar contra original
        BmpImage imagen_1 = new BmpImage(BmpHelper.readBmpImage("Images/Will/Will_1.bmp"), "Copia_1");
        BmpImage imagen_2 = new BmpImage(BmpHelper.readBmpImage("Images/Will/Will_2.bmp"), "Copia_2");
        BmpImage imagen_3 = new BmpImage(BmpHelper.readBmpImage("Images/Will/Will_3.bmp"), "Copia_3");
        BmpImage imagen_4 = new BmpImage(BmpHelper.readBmpImage("Images/Will/Will_4.bmp"), "Copia_4");
        BmpImage imagen_5 = new BmpImage(BmpHelper.readBmpImage("Images/Will/Will_5.bmp"), "Copia_5");
        BmpImage imagen_6 = new BmpImage(BmpHelper.readBmpImage("Images/Will/Will_6.bmp"), "Copia_6");
        BmpImage imagen_7 = new BmpImage(BmpHelper.readBmpImage("Images/Will/Will_7.bmp"), "Copia_7");
        images.add(imagen_1);
        images.add(imagen_2);
        images.add(imagen_3);
        images.add(imagen_4);
        images.add(imagen_5);
        images.add(imagen_6);
        images.add(imagen_7);
        // La comparacion con el factor de correlación se hace tono con tono
        // Entre la imagen original y la que estas comparando
        // La fórmula <ab>-<a><b> 
        // A es el tono del pixel de la imagen A y b el de la imagen B
        // Entre <> son medias y es la sumatoria de los píxeles de la imagen
        System.out.println("Imagenes sin ordenar: ");     
        // Imprimir sin orden, factor de correlación y nombre de la imagen
        for (int i = 0; i < images.size(); i++) {
            images.get(i).setCorrelationFactor(BmpHelper.getCorrelationFactor(original, images.get(i)));
            System.out.println(images.get(i).getName() + " --" + images.get(i).getCorrelationFactor());
        }
        
        System.err.println("--------------------------------------");   
        
        // Obtengo la lista ordenada
        //BmpHelper.getSortedListOfImages(original, images);
        Collections.sort(images);
        
        System.out.println("Imagenes ordenadas: ");
        // Imprimo ordenado el factor de correlación y el nombre de la imagen correspondiente
        for (int i = 0; i < images.size(); i++) {
            System.out.println(images.get(i).getName() + "  --  " + images.get(i).getCorrelationFactor());
        }
        
        System.err.println("--------------------------------------");   
        System.err.println("****** PUNTO 2 ******");  
        // 2) Generar el histograma a partir de la distribución de tonos y calcular la media el el desvío de cada distribución
        
        // El histograma de una imagen es el gráfico de una función que asocia un color (tono de gris) con su probabilidad de aparición en la imagen.
        // Imagen original
        Hashtable<Integer, Integer> readImageTable = BmpHelper.readImage(original.getBmp()); // obtengo los símbolos (píxeles) que aparecen y la cantidad de veces 
        Vector<Integer> vectorSimbolos = new Vector<Integer>(readImageTable.keySet()); // Creo un vector con los símbolos que aparecen en la imagen
        Collections.sort(vectorSimbolos); // Ordeno el vector que será el eje X del histograma

        // Ahora debo obtener la probabilidad de cada símbolo: simbolo[i] / cant_símbolos = pixel_i / (ancho*alto)
        Vector<Double> vectorProb = BmpHelper.getProb(vectorSimbolos, readImageTable, original.getBmp()); // eje Y

        // Utilizando la librería JFreeChart creamos el histograma
        showHistogram(vectorSimbolos, vectorProb, original);
        
        // Imagen más parecida
        readImageTable = BmpHelper.readImage(images.get(0).getBmp());
        vectorSimbolos = new Vector<Integer> (readImageTable.keySet());
        Collections.sort(vectorSimbolos);
        vectorProb = BmpHelper.getProb(vectorSimbolos, readImageTable, images.get(0).getBmp());
        showHistogram(vectorSimbolos, vectorProb, images.get(0));

        // Imagen menos parecida
        readImageTable = BmpHelper.readImage(images.get(images.size() - 1).getBmp());
        vectorSimbolos = new Vector<Integer> (readImageTable.keySet());
        Collections.sort(vectorSimbolos);
        vectorProb = BmpHelper.getProb(vectorSimbolos, readImageTable, images.get(images.size() - 1).getBmp());
        showHistogram(vectorSimbolos, vectorProb, images.get(images.size() - 1));
    }
    
}
