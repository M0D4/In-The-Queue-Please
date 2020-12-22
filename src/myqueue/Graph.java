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
    
    public static void display(String title, ArrayList<Pair<Integer, Integer>> points){
        Stage window = new Stage();
        
        if(points.isEmpty()) points.add(new Pair<>(0, 0));
        
        Pair<Integer, Integer> last = points.get(0);
        
        int maxXValue = 0, maxYValue = 0;
        for(Pair<Integer, Integer> point: points){
            maxXValue = Math.max(maxXValue, point.getKey());
            maxYValue = Math.max(maxYValue, point.getValue());
        }
//        maxXValue = Math.max(20, maxXValue + 5);
        maxYValue = Math.max(10, maxYValue + 5);
        
        final NumberAxis xAxis = new NumberAxis(0, maxXValue, 1);
        final NumberAxis yAxis = new NumberAxis(0, maxYValue, 1);
        
        xAxis.setLabel("Time t");
        yAxis.setLabel("Number of Customers");
 
        final AreaChart<Number,Number> ac = 
            new AreaChart(xAxis,yAxis);
        XYChart.Series series = new XYChart.Series();
        
        for(Pair<Integer, Integer> point: points){
            series.getData().add(new XYChart.Data(point.getKey(), last.getValue()));
            series.getData().add(new XYChart.Data(point.getKey(), point.getValue()));
            last = point;
        }
        
        series.setName("Number of Customers at time t");
        
        Scene scene  = new Scene(ac, 800, 600);
        
        ac.getData().addAll(series);
       
        
        window.setTitle(title + " Graph");
        window.setScene(scene);
        window.show();
    }
}
