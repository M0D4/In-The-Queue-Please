/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Moustafa Mohamed
 */
public class Main extends Application {
    
    
    private Stage homeWindow;
    private GridPane homeLayout;
    private int last_model_selected = 0;
    private Label modelLabel, lambdaLabel, muLabel, capacityKLAbel, capacityK_minus1_label, serversCLabel, initialNumberMLabel;
    private TextField lambdaInput, muInput, capacityKInput, capacityK_minus1_input, initialNumberMInput, ServersCInput;
    private Button queryButton, graphButton, clearButton;
    
    @Override
    public void start(Stage primaryStage) {
        homeWindow = primaryStage;
        homeWindow.setMinWidth(330);
        homeWindow.setMinHeight(350);
        homeWindow.setOnCloseRequest(e -> System.out.println("width: " + homeWindow.getWidth() + ", Height: " + homeWindow.getHeight()));
        
        homeLayout = new GridPane();
        
        lambdaLabel = new Label("λ: ");
        lambdaInput = new TextField();
        lambdaInput.setMaxWidth(200);
        
        muLabel = new Label("μ: ");
        muInput = new TextField();
        muInput.setMaxWidth(200);
        
        capacityKLAbel = new Label("K: ");
        capacityKInput = new TextField();
        capacityKInput.setMaxWidth(200);
        
        capacityK_minus1_label = new Label("K-1: ");
        capacityK_minus1_input = new TextField();
        capacityK_minus1_input.setMaxWidth(200);
        
        serversCLabel = new Label("C: ");
        ServersCInput = new TextField();
        ServersCInput.setMaxWidth(200);
        
        initialNumberMLabel = new Label("M: ");
        initialNumberMInput = new TextField();
        initialNumberMInput.setMaxWidth(200);
        
        ToolTips:
            lambdaLabel.setTooltip(new Tooltip("Inter-arrival rate"));
            lambdaInput.setTooltip(new Tooltip("Inter-arrival rate"));
            muLabel.setTooltip(new Tooltip("service rate"));
            muInput.setTooltip(new Tooltip("service rate"));
            capacityKLAbel.setTooltip(new Tooltip("capacity of the system"));
            capacityKInput.setTooltip(new Tooltip("capacity of the system"));
            capacityK_minus1_label.setTooltip(new Tooltip("capacity of the system"));
            capacityK_minus1_input.setTooltip(new Tooltip("capacity of the system"));
            serversCLabel.setTooltip(new Tooltip("Number of parallel servers"));
            ServersCInput.setTooltip(new Tooltip("Number of parallel servers"));
            initialNumberMInput.setTooltip(new Tooltip("Initial Number of customers"));
            initialNumberMLabel.setTooltip(new Tooltip("Initial Number of customers"));
        
        queryButton = new Button("Query");
        graphButton = new Button("Graph");
        clearButton = new Button("Clear");
        
        clearButton.setOnAction(e -> clear());
        
        initModel(1);
        
        modelLabel = new Label("Model: ");
        
        ComboBox<String> modelBox = new ComboBox<>();
        modelBox.getItems().add("D/D/1/K-1");
        modelBox.getItems().add("M/M/1");
        modelBox.getItems().add("M/M/1/K");
        modelBox.getItems().add("M/M/1/K");
        modelBox.getSelectionModel().selectFirst();
        
        modelBox.setOnAction(e -> {
            initModel(modelBox.getSelectionModel().getSelectedIndex() + 1);
        });

        homeLayout.setPadding(new Insets(8, 8, 8, 8));
        homeLayout.setHgap(10);
        homeLayout.setVgap(10);
        homeLayout.getChildren().add(modelBox);
        
        HBox hb = new HBox(queryButton, graphButton, clearButton);
        hb.setPadding(new Insets(8, 8, 8, 8));
        hb.setSpacing(20);
        
        Constraints:
            GridPane.setConstraints(modelLabel, 0, 0);
            GridPane.setConstraints(modelBox, 1, 0);
            GridPane.setConstraints(lambdaLabel, 0, 1);
            GridPane.setConstraints(lambdaInput, 1, 1);
            GridPane.setConstraints(muLabel, 0, 2);
            GridPane.setConstraints(muInput, 1, 2);
            GridPane.setConstraints(capacityK_minus1_label, 0, 3);
            GridPane.setConstraints(capacityK_minus1_input, 1, 3);
            GridPane.setConstraints(initialNumberMLabel, 0, 4);
            GridPane.setConstraints(initialNumberMInput, 1, 4);
            GridPane.setConstraints(capacityKLAbel, 0, 5);
            GridPane.setConstraints(capacityKInput, 1, 5);
            GridPane.setConstraints(serversCLabel, 0, 6);
            GridPane.setConstraints(ServersCInput, 1, 6);
            GridPane.setConstraints(hb, 1, 7);
        
        
        
        homeLayout.getChildren().addAll(initialNumberMLabel,initialNumberMInput, modelLabel, lambdaLabel, muLabel, capacityKLAbel, capacityK_minus1_label, serversCLabel, hb
        ,lambdaInput, muInput, capacityKInput, capacityK_minus1_input, ServersCInput);
        
        Scene scene = new Scene(homeLayout);
        
        homeWindow.setTitle("In The Queue, Please!");
        homeWindow.setScene(scene);
        homeWindow.show();
    }
    
    private void initModel(int n){
        switch (n) {
            case 1:
                if(last_model_selected == 1) return;
                capacityKInput.setDisable(true);
                ServersCInput.setDisable(true);
                
                initialNumberMInput.setDisable(false);
                initialNumberMInput.setText("0");
                last_model_selected = 1;
                
                queryButton.setText("Query");
                break;
            case 2:
                // M/M/1
                if(last_model_selected == 2) return;
                capacityKInput.setDisable(true);
                capacityK_minus1_input.setDisable(true);
                ServersCInput.setDisable(true);
                initialNumberMInput.setDisable(true);
                last_model_selected = 2;
                
                queryButton.setText("Solve");
                break;
            case 3:
                // M/M/1/K
                if(last_model_selected == 3) return;
                capacityK_minus1_input.setDisable(true);
                initialNumberMInput.setDisable(true);
                serversCLabel.setDisable(true);
                capacityKInput.setDisable(false);
                last_model_selected = 3;
                queryButton.setText("Solve");
                break;
            default:
                break;
        }
    }
    
    private void clear(){
        lambdaInput.clear();
        muInput.clear();
        capacityKInput.clear();
        capacityK_minus1_input.clear();
        initialNumberMInput.setText("0");
        ServersCInput.clear();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
