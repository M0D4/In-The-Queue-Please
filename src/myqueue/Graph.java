package myqueue;

/**
 *
 * @author Moustafa Mohamed
 */

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Pair;
 

public class Graph {
    
    public static void display(ArrayList<Pair<Integer, Integer>> points){
        Stage window = new Stage();
        
        final NumberAxis xAxis = new NumberAxis(0, 20, 1);
        final NumberAxis yAxis = new NumberAxis(0, 10, 1);
        
        final AreaChart<Number,Number> ac = 
            new AreaChart(xAxis,yAxis);
        
        ac.setTitle("Number of Customers at time t");
 
        XYChart.Series series= new XYChart.Series();
        
        Pair<Integer, Integer> last = points.get(0);
        
        for(Pair<Integer, Integer> point: points){
            series.getData().add(new XYChart.Data(point.getKey(), last.getValue()));
            series.getData().add(new XYChart.Data(point.getKey(), point.getValue()));
            last = point;
        }
        
        series.setName("Number of Customers at time t");
        
        
        Scene scene  = new Scene(ac, 800, 600);
        
        ac.getData().addAll(series);
       
        
        window.setTitle("D/D/D/K-1 Graph");
        window.setScene(scene);
        window.show();
    }
}
