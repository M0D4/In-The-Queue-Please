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
    private HBox hb;
    private ComboBox<String> modelBox;
    
    @Override
    public void start(Stage primaryStage) {
        homeWindow = primaryStage;
        homeWindow.setMinWidth(330);
        homeWindow.setMinHeight(350);
//        homeWindow.setOnCloseRequest(e -> System.out.println("width: " + homeWindow.getWidth() + ", Height: " + homeWindow.getHeight()));
        
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
        
        queryButton = new Button("Query");
        graphButton = new Button("Graph");
        clearButton = new Button("Clear");
                    
        setToolTips();
        
        
        initModel(1);
        
        clearButton.setOnAction(e -> clear());
        
        modelLabel = new Label("Model: ");
        
        modelBox = new ComboBox<>();
        modelBox.getItems().add("D/D/1/K-1");
        modelBox.getItems().add("M/M/1");
        modelBox.getItems().add("M/M/1/K");
        modelBox.getItems().add("M/M/C");
        modelBox.getItems().add("M/M/C/K");
        modelBox.getSelectionModel().selectFirst();
        
        modelBox.setOnAction(e -> {
            int selected_index = modelBox.getSelectionModel().getSelectedIndex();
            if(selected_index == 0){
                queryButton.setText("Query");
                graphButton.setDisable(false);
            }
            else {
                queryButton.setText("Solve");
                graphButton.setDisable(true);
            }
            initModel(selected_index + 1);
        });

        queryButton.setOnAction(e -> {
            int selected_model = modelBox.getSelectionModel().getSelectedIndex() + 1;
            
            if(selected_model == 1){
                queryModel1();
            }
        });
        
        
        homeLayout.setPadding(new Insets(8, 8, 8, 8));
        homeLayout.setHgap(10);
        homeLayout.setVgap(10);
        homeLayout.getChildren().add(modelBox);
        
        hb = new HBox(queryButton, graphButton, clearButton);
        hb.setPadding(new Insets(8, 8, 8, 8));
        hb.setSpacing(20);
        
        setConstraints();
        
        
        
        homeLayout.getChildren().addAll(
         initialNumberMLabel,
         initialNumberMInput, 
         modelLabel, lambdaLabel,
         muLabel, capacityKLAbel,
         capacityK_minus1_label,
         serversCLabel, hb,
         lambdaInput, muInput,
         capacityKInput, capacityK_minus1_input,
         ServersCInput
        );
        
        Scene scene = new Scene(homeLayout);
        
        homeWindow.setTitle("In The Queue, Please!");
        homeWindow.setScene(scene);
        homeWindow.show();
    }
    
    private void initModel(int n){
        switch (n) {
            case 1:
                // D/D/1/K-1
                if(last_model_selected == 1) return;
                setDisableAll(true);
                initialNumberMInput.setDisable(false);
                initialNumberMInput.setText("0");
                capacityK_minus1_input.setDisable(false);
                last_model_selected = 1;
                break;
            case 2:
                // M/M/1
                if(last_model_selected == 2) return;
                setDisableAll(true);
                last_model_selected = 2;
                break;
            case 3:
                // M/M/1/K
                if(last_model_selected == 3) return;
                setDisableAll(true);
                capacityKInput.setDisable(false);
                last_model_selected = 3;
                break;
            case 4:
                // M/M/C
                if(last_model_selected == 4) return;
                setDisableAll(true);
                ServersCInput.setDisable(false);
                last_model_selected = 4;
                break;
            case 5:
                // M/M/C/K
                if(last_model_selected == 5) return;
                setDisableAll(true);
                ServersCInput.setDisable(false);
                capacityKInput.setDisable(false);
                last_model_selected = 5;
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
    
    private void setDisableAll(boolean status){
        capacityKInput.setDisable(status);
        capacityK_minus1_input.setDisable(status);
        initialNumberMInput.setDisable(status);
        ServersCInput.setDisable(status);
    }
    
    private void queryModel1(){
        double lambda, mu;
        int k_minus_1, initial_number_M;
        if(!checkLambdaAndMu()) return;
        
        lambda = Double.parseDouble(lambdaInput.getText());
         mu = Double.parseDouble(muInput.getText());
        
        try{
            if(capacityK_minus1_input.getText().trim().length() == 0)
                throw new NumberFormatException();
            k_minus_1 = Integer.parseInt(capacityK_minus1_input.getText());
            
            if(k_minus_1 < 0)
                throw new NumberFormatException();
            
        }catch(NumberFormatException e){
            ErrorBox.display("Error!", "You must enter a non-negative integer number for K-1");
            return;
        }
        
        try{
            if(initialNumberMInput.getText().trim().length() == 0)
                initial_number_M = 0;
            else 
                initial_number_M = Integer.parseInt(initialNumberMInput.getText());
            
            if(initial_number_M < 0)
                throw  new NumberFormatException();
            
        }catch(NumberFormatException e){
            ErrorBox.display("Error!", "You must enter a non-negative integer number for M");
            return;
        }
        
        if(lambda > mu && initial_number_M != 0){
            AlertBox.display("Alert", "M is ignored becuase λ is greater than μ");
            initial_number_M = 0;
        }
        
        Model1.solve(lambda, mu, k_minus_1, initial_number_M);

    }
    
    private boolean checkLambdaAndMu(){
        try{
            if(lambdaInput.getText().trim().length() == 0)
                throw new NumberFormatException();
            if(Double.parseDouble(lambdaInput.getText().trim()) <= 0)
                throw new NumberFormatException();
        }catch(NumberFormatException e){
            ErrorBox.display("Error!", "You must enter a positive real number for λ");
            return false;
        }
        
        try{
            if(muInput.getText().trim().length() == 0)
                throw new NumberFormatException();
            if(Double.parseDouble(muInput.getText().trim()) <= 0)
                throw new NumberFormatException();
        }catch(NumberFormatException e){
            ErrorBox.display("Error!", "You must enter a positive real number for μ");
            return false;
        }
        
        return true;
    }
    
    
    private void setToolTips(){
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
        clearButton.setTooltip(new Tooltip("Clear inputs"));
    }
    
    private void setConstraints() {
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
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
}