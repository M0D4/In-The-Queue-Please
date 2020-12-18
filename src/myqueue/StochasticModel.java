/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Moustafa Mohamed
 */
public class StochasticModel {
    
    private static Stage window;
    private static GridPane layout;
    private static Label WLabel, WqLabel, LLabel, LqLabel;
    private static Button closeButton;
    
    
    public static void display(String title, long L, long Lq, double W, double Wq){
        window = new Stage();
        
        closeButton = new Button("Close");
//        closeButton.getStyleClass().add("button-close");
        closeButton.setOnAction(e -> window.close());
        
        WLabel = new Label(String.format("Expected waiting time in the system: %.3f second(s)", W));
        WqLabel = new Label(String.format("Expected waiting time in the queue: %.3f second(s)", Wq));
        LLabel = new Label(String.format("Expected number of the customers in the system: %d customer(s)" , L));
        LqLabel = new Label(String.format("Expected number of the customers in the queue: %d customer(s)" , Lq));
        
        layout = new GridPane();
        layout.setPadding(new Insets(8, 8, 8, 8));
        layout.setVgap(10);
        layout.setHgap(10);
        
        setConstraints();
        
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(StochasticModel.class.getResource("Light.css").toExternalForm());
        
        setFontSize(20);
        window.setTitle(title);
        window.setScene(scene);
        window.showAndWait();
    }

    private static void setConstraints() {
        layout.add(LLabel, 0, 0, 25, 1);
        layout.add(LqLabel, 0, 1, 25, 1);
        layout.add(WLabel, 0, 2, 25, 1);
        layout.add(WqLabel, 0, 3, 25, 1);
        layout.add(closeButton, 23, 4, 1, 1);
    }

    private static void setFontSize(int size) {
        WLabel.setFont(new Font(size));
        WqLabel.setFont(new Font(size));
        LLabel.setFont(new Font(size));
        LqLabel.setFont(new Font(size));
    }
    
}
