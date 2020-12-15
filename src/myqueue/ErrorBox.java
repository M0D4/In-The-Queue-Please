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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Moustafa Mohamed
 */
public class ErrorBox {
    
    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setHeight(202);
        window.setWidth(363);
        window.setTitle(title);
        
        window.setOnCloseRequest(e -> System.out.println("width: " + window.getWidth() + ", Height: " + window.getHeight()));

        
        Label message_label = new Label(message);
        message_label.setFont(new Font(18));
        
        Image image = new Image("myqueue/error.png");
        
        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> window.close());
        
        VBox layout = new VBox(10);
        
        layout.setAlignment(Pos.CENTER);
        
        layout.getChildren().addAll(new ImageView(image), message_label, okButton);
        
        Scene scene = new Scene(layout);
        
        window.setScene(scene);
        window.showAndWait();
    }
}
