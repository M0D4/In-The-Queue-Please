/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private HBox buttons;
    private ComboBox<String> modelBox;
    private Alert errorAlert, infoAlert;
    
    @Override
    public void start(Stage primaryStage) {
        homeWindow = primaryStage;
        homeWindow.setMinWidth(391);
        homeWindow.setMinHeight(399);
        homeWindow.setResizable(false);
        homeWindow.setOnCloseRequest(e ->{
            System.out.println("width: " + homeWindow.getWidth() + ", Height: " + homeWindow.getHeight());
            homeWindow.close();
        });
        
        homeLayout = new GridPane();
        
        lambdaLabel = new Label("λ: ");
        lambdaInput = new TextField();
        
        muLabel = new Label("μ: ");
        muInput = new TextField();
        
        capacityKLAbel = new Label("K: ");
        capacityKInput = new TextField();
        
        capacityK_minus1_label = new Label("K-1: ");
        capacityK_minus1_input = new TextField();
        
        serversCLabel = new Label("C: ");
        ServersCInput = new TextField();
        
        initialNumberMLabel = new Label("M: ");
        initialNumberMInput = new TextField();
        
        
        setMaxWidthForInputs(260);
        
        
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
            
            solveModel(selected_model, false);
        });
        
        
        graphButton.setOnAction(e -> {
            solveModel(1, true);
        });
        
        homeLayout.setPadding(new Insets(8, 8, 8, 8));
        homeLayout.setHgap(10);
        homeLayout.setVgap(10);
        homeLayout.getChildren().add(modelBox);
        
        buttons = new HBox(queryButton, graphButton, clearButton);
        buttons.setPadding(new Insets(8, 8, 8, 8));
        buttons.setSpacing(20);
        
        setConstraints();
        
        
        homeLayout.getChildren().addAll(
         initialNumberMLabel,
         initialNumberMInput, 
         modelLabel, lambdaLabel,
         muLabel, capacityKLAbel,
         capacityK_minus1_label,
         serversCLabel, buttons,
         lambdaInput, muInput,
         capacityKInput, capacityK_minus1_input,
         ServersCInput
        );
        
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(null);
        
        infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setHeaderText(null);
        
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
    
    private boolean checkLambdaAndMu(){
        try{
            if(lambdaInput.getText().trim().length() == 0)
                throw new NumberFormatException();
            if(Double.parseDouble(lambdaInput.getText().trim()) <= 0)
                throw new NumberFormatException();
        }catch(NumberFormatException e){
            errorAlert.setContentText("You must enter a positive real number for λ");
            errorAlert.show();
            return false;
        }
        
        try{
            if(muInput.getText().trim().length() == 0)
                throw new NumberFormatException();
            if(Double.parseDouble(muInput.getText().trim()) <= 0)
                throw new NumberFormatException();
        }catch(NumberFormatException e){
              errorAlert.setContentText("You must enter a positive real number for μ");
              errorAlert.show();
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
        GridPane.setConstraints(buttons, 1, 7);
    }
    
    private void setMaxWidthForInputs(int width) {
        lambdaInput.setMaxWidth(width);
        muInput.setMaxWidth(width);
        capacityKInput.setMaxWidth(width);
        capacityK_minus1_input.setMaxWidth(width);
        ServersCInput.setMaxWidth(width);
        initialNumberMInput.setMaxWidth(width);
    }
    

    private void solveModel(int model, boolean graph) {
        if(!checkLambdaAndMu()) return;
        
        double lambda = Double.parseDouble(lambdaInput.getText().trim());
        double mu = Double.parseDouble(muInput.getText().trim());
        
        int k = 0, c = 0, k_minus_1 = 0, initial_number_M = 0;
        
        
        if(model == 1){
            if(!checkK_minus_1()) return;
            if(!checkInitial_M()) return;
            
            k_minus_1 = Integer.parseInt(capacityK_minus1_input.getText().trim());
            initial_number_M = Integer.parseInt(initialNumberMInput.getText().trim());
            
            if(lambda > mu && initial_number_M != 0){
                infoAlert.setContentText("M is ignored becuase λ is greater than μ");
                infoAlert.showAndWait();
                initial_number_M = 0;
            }
            if(graph){
                Graph.display();
            }
            else DeterministicModel.display(lambda, mu, k_minus_1, initial_number_M);
            return;
        }else if(model == 2){
            if(mu <= lambda){
                StochasticModel.display("M/M/1", 0, 0, 0, 0);
            }else{
                ModelMM1 m = new ModelMM1(lambda, mu);
                StochasticModel.display("M/M/1", m.getL(), m.getLq(), m.getW(), m.getWq());
            }
            return;
        }
        if(model == 3 || model == 5) {
            if(!check(capacityKInput, 'K')) return;
            k = Integer.parseInt(capacityKInput.getText().trim());   
        }
        if(model == 4 || model == 5){
            if(!check(ServersCInput, 'C')) return;
            c = Integer.parseInt(ServersCInput.getText().trim());
        }else if(model == 3){
            if(mu <= lambda){
                StochasticModel.display("M/M/1/K", 0, 0, 0, 0);
            }else{
                ModelMM1K m = new ModelMM1K(lambda, mu, k);
                StochasticModel.display("M/M/1/K", m.getL(), m.getLq(), m.getW(), m.getWq());
            }
            return;
        }
        if(model == 4){
            
        }else if(model == 5){
            if(!check(capacityKInput, 'K')) return;
            if(!check(ServersCInput, 'C')) return;
            
            ModelMMCK m = new ModelMMCK(lambda, mu, k, c);
            StochasticModel.display("M/M/C/K", m.getL(), m.getLq(), m.getW(), m.getWq());
        }
    }

    private boolean check(TextField txt, char choice) {
        try{
            if(txt.getText().trim().length() == 0)
                throw new NumberFormatException();
            if(Integer.parseInt(txt.getText().trim()) <= 0)
                throw new NumberFormatException();
        }catch(NumberFormatException e){
            errorAlert.setContentText("You must enter a positive integer number for "+choice);
            errorAlert.show();
            return false;
        }
        return true;
    }
    
    private boolean checkK_minus_1(){
        try{
            if(capacityK_minus1_input.getText().trim().length() == 0)
                throw new NumberFormatException();
            if(Integer.parseInt(capacityK_minus1_input.getText().trim()) <= 0)
                throw new NumberFormatException();
            
        }catch(NumberFormatException e){
            errorAlert.setContentText("You must enter a positive integer number for K-1");
            errorAlert.show();
            return false;
        }
        
        return true;
    }
    
    private boolean  checkInitial_M(){
        try{
            if(initialNumberMInput.getText().trim().length() == 0)
                initialNumberMInput.setText("0");
           
             if(Integer.parseInt(initialNumberMInput.getText().trim()) < 0)
                throw  new NumberFormatException();
            
        }catch(NumberFormatException e){
            errorAlert.setContentText("You must enter a non-negative integer number for M");
            errorAlert.show();
            return false;
        }        
        return true;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
