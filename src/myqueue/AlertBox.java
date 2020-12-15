/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Moustafa Mohamed
 */
public class AlertBox {
    
    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        
        Label label = new Label(message);
        Button ok = new Button("Ok");
        ok.setOnAction(e -> window.close());
        
        Image image = new Image("media/alert.png");
         
        VBox layout = new VBox(20);
        layout.getChildren().addAll(new ImageView(image), label, ok);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.setTitle(title);
        window.showAndWait();
    }
}