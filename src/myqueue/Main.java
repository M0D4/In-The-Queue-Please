/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue;

import java.util.ArrayList;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Moustafa Mohamed
 */
public class Main extends Application {
    
    
    private Stage homeWindow;
    private GridPane homeLayout;
    private int last_model_selected = 0;
    private Label modelLabel, lambdaLabel, muLabel, capacityKLAbel, capacityK_minus1_label, serversCLabel, initialNumberMLabel;
    private Label lambdaUnit, muUnit, capacityKUnit, capacityK_minus1_Unit, serversCUnit, initialNumberMUnit;
    private TextField lambdaInput, muInput, capacityKInput, capacityK_minus1_input, initialNumberMInput, ServersCInput;
    private Button queryButton, graphButton, clearButton;
    private HBox buttonsBox;
    private ComboBox<String> modelBox;
    private Alert errorAlert, infoAlert;
    private ScriptEngine engine;
    
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
        lambdaUnit = new Label(" customers/sec");
        
        muLabel = new Label("μ: ");
        muInput = new TextField();
        muUnit = new Label(" customers/sec");
        
        capacityKLAbel = new Label("K: ");
        capacityKInput = new TextField();
        capacityKUnit = new Label(" customers");
        
        capacityK_minus1_label = new Label("K-1: ");
        capacityK_minus1_input = new TextField();
        capacityK_minus1_Unit = new Label(" customers");
        
        serversCLabel = new Label("C: ");
        ServersCInput = new TextField();
        serversCUnit = new Label(" servers");
                
        initialNumberMLabel = new Label("M: ");
        initialNumberMInput = new TextField();
        initialNumberMUnit = new Label(" customers");
        
        
        
        setMaxWidthForInputs(260);
        
        queryButton = new Button("Query");
        graphButton = new Button("Graph");
        clearButton = new Button("Clear");
        clearButton.setId("clearButton");
                    
        
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
            
            try {
                solveModel(selected_model, false);
            } catch (ScriptException ex) {
            
            }
        });
        
        
        graphButton.setOnAction(e -> {
            try {
                solveModel(1, true);
            } catch (ScriptException ex) {
            
            }
        });
        
        homeLayout.setPadding(new Insets(8, 8, 8, 8));
        homeLayout.setHgap(10);
        homeLayout.setVgap(10);
        homeLayout.getChildren().add(modelBox);
        
        buttonsBox = new HBox(queryButton, graphButton, clearButton);
        buttonsBox.setPadding(new Insets(8, 8, 8, 8));
        buttonsBox.setSpacing(20);
        
        setToolTips();
        setUnitSize(13);
        setFontSize(20);
        setConstraints();
        
        
        homeLayout.getChildren().addAll(
         initialNumberMLabel,
         initialNumberMInput, 
         modelLabel, lambdaLabel,
         muLabel, capacityKLAbel,
         capacityK_minus1_label,
         serversCLabel, buttonsBox,
         lambdaInput, muInput,
         capacityKInput, capacityK_minus1_input,
         ServersCInput,
         lambdaUnit, muUnit, capacityKUnit, capacityK_minus1_Unit, serversCUnit, initialNumberMUnit
        );
        
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(null);
        
        infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setHeaderText(null);
        
        
        engine = new ScriptEngineManager().getEngineByName("JavaScript");
        
        
        Scene scene = new Scene(homeLayout);
        scene.getStylesheets().add(Main.class.getResource("Light.css").toExternalForm());
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
    
    
    private void setToolTips(){
        lambdaLabel.setTooltip(new Tooltip("Inter-arrival Rate"));
        lambdaInput.setTooltip(new Tooltip("Inter-arrival Rate"));
        muLabel.setTooltip(new Tooltip("Service Rate"));
        muInput.setTooltip(new Tooltip("Service Rate"));
        capacityKLAbel.setTooltip(new Tooltip("Capacity Of The System"));
        capacityKInput.setTooltip(new Tooltip("Capacity Of The System"));
        capacityK_minus1_label.setTooltip(new Tooltip("Capacity Of The System"));
        capacityK_minus1_input.setTooltip(new Tooltip("Capacity Of The System"));
        serversCLabel.setTooltip(new Tooltip("Number Of Parallel Servers"));
        ServersCInput.setTooltip(new Tooltip("Number Of Parallel Servers"));
        initialNumberMInput.setTooltip(new Tooltip("Initial Number Of Customers"));
        initialNumberMLabel.setTooltip(new Tooltip("Initial Number Of Customers"));
        clearButton.setTooltip(new Tooltip("Clear Inputs"));
    }
    
    private void setConstraints() {
        GridPane.setConstraints(modelLabel, 0, 0);
        GridPane.setConstraints(modelBox, 1, 0);
        GridPane.setConstraints(lambdaLabel, 0, 1);
        GridPane.setConstraints(lambdaInput, 1, 1);
        GridPane.setConstraints(lambdaUnit, 2, 1);
        GridPane.setConstraints(muLabel, 0, 2);
        GridPane.setConstraints(muInput, 1, 2);
        GridPane.setConstraints(muUnit, 2, 2);
        GridPane.setConstraints(capacityK_minus1_label, 0, 3);
        GridPane.setConstraints(capacityK_minus1_input, 1, 3);
        GridPane.setConstraints(capacityK_minus1_Unit, 2, 3);
        GridPane.setConstraints(initialNumberMLabel, 0, 4);
        GridPane.setConstraints(initialNumberMInput, 1, 4);
        GridPane.setConstraints(initialNumberMUnit, 2, 4);
        GridPane.setConstraints(capacityKLAbel, 0, 5);
        GridPane.setConstraints(capacityKInput, 1, 5);
        GridPane.setConstraints(capacityKUnit, 2, 5);
        GridPane.setConstraints(serversCLabel, 0, 6);
        GridPane.setConstraints(ServersCInput, 1, 6);
        GridPane.setConstraints(serversCUnit, 2, 6);
        GridPane.setConstraints(buttonsBox, 1, 7);
    }
    
    private void setMaxWidthForInputs(int width) {
        lambdaInput.setMaxWidth(width);
        muInput.setMaxWidth(width);
        capacityKInput.setMaxWidth(width);
        capacityK_minus1_input.setMaxWidth(width);
        ServersCInput.setMaxWidth(width);
        initialNumberMInput.setMaxWidth(width);
    }
    

    private void solveModel(int model, boolean graph) throws ScriptException {
        if(!checkLambdaAndMu()) return;
        
        double lambda = Double.parseDouble(String.valueOf(engine.eval(lambdaInput.getText().trim())));
        double mu = Double.parseDouble(String.valueOf(engine.eval(muInput.getText().trim())));
        
        int k = 0, c = 0, k_minus_1 = 0, initial_number_M = 0;
        
        
        if(model == 1){
            if(!checkK_minus_1()) return;
            if(!checkInitial_M()) return;
            
            k_minus_1 = Integer.parseInt(String.valueOf(engine.eval(capacityK_minus1_input.getText().trim())));
            initial_number_M = Integer.parseInt(String.valueOf(engine.eval(initialNumberMInput.getText().trim())));
            
            if(lambda > mu && initial_number_M != 0){
                infoAlert.setContentText("M is ignored becuase λ is greater than μ");
                infoAlert.showAndWait();
                initial_number_M = 0;
            }
            if(graph){
                ModelDD1K1 m = new ModelDD1K1(lambda, mu, k_minus_1, initial_number_M);
                ArrayList<Pair<Integer, Integer>> points = new ArrayList<>();
                for(int i = 0; i <= m.getTi() + 10; i++){
                    points.add(new Pair<>(i, (int)m.calcNt(i)));
                }
                Graph.display("D/D/1/" + k_minus_1 ,points);
            }
            else DeterministicModel.display(lambda, mu, k_minus_1, initial_number_M);
            return;
        }else if(model == 2){
            ModelMM1 m = new ModelMM1(lambda, mu);
            StochasticModel.display("M/M/1", Math.max(0, Math.round(m.getL())), Math.max(0, Math.round(m.getLq())), Math.max(0, m.getW()), Math.max(0, m.getWq()));
            return;
        }
        if(model == 3 || model == 5) {
            if(!check(capacityKInput, 'K')) return;
            k = Integer.parseInt(String.valueOf(engine.eval(capacityKInput.getText().trim())));
        }
        if(model == 4 || model == 5){
            if(!check(ServersCInput, 'C')) return;
            c = Integer.parseInt(String.valueOf(engine.eval(ServersCInput.getText().trim())));
        }
        switch (model) {
            case 3:
                {
                    ModelMM1K m = new ModelMM1K(lambda, mu, k);
                    StochasticModel.display("M/M/1/" + k, Math.max(0, Math.round(m.getL())), Math.max(0, Math.round(m.getLq())), Math.max(0, m.getW()), Math.max(0, m.getWq()));
                }
                break;
            case 4:
                {
                    ModelMMC m = new ModelMMC(lambda, mu, c);
                    StochasticModel.display("M/M/" + c, Math.max(0, Math.round(m.getL())), Math.max(0, Math.round(m.getLq())), Math.max(0, m.getW()), Math.max(0, m.getWq()));
                }
                break;
            case 5:
                {
                    ModelMMCK m = new ModelMMCK(lambda, mu, k, c);
                    StochasticModel.display("M/M/" + c + "/" + k, Math.max(0, Math.round(m.getL())), Math.max(0, Math.round(m.getLq())), Math.max(0, m.getW()), Math.max(0, m.getWq()));
                }   
                break;
            default:
                break;
        }
    }
    
    private boolean checkLambdaAndMu(){
        try{
            if(lambdaInput.getText().trim().length() == 0)
                throw new NumberFormatException();
            double value = Double.parseDouble(String.valueOf(engine.eval(lambdaInput.getText().trim())));
            if(value <= 0)
                throw new NumberFormatException();
            if(!Double.isFinite(value) || value == Double.NaN)
                throw new ScriptException("");
        }catch(NumberFormatException e){
            errorAlert.setContentText("You must enter a positive real number for λ");
            errorAlert.show();
            return false;
        }catch(ScriptException se){
            errorAlert.setContentText("Please enter a valid input for λ");
            errorAlert.show();
            return false;
        }
        
        try{
            if(muInput.getText().trim().length() == 0)
                throw new NumberFormatException();
            double value = Double.parseDouble(String.valueOf(engine.eval(muInput.getText().trim())));
            if(value <= 0)
                throw new NumberFormatException();
            if(!Double.isFinite(value) || value == Double.NaN)
                throw new NumberFormatException();
        }catch(NumberFormatException | ScriptException e){
            errorAlert.setContentText("μ must be a positive real number less than Infinity");
            errorAlert.show();
            return false;
        }
        
        return true;
    }
    
    private boolean check(TextField txt, char choice) {
        try{
            if(txt.getText().trim().length() == 0)
                throw new NumberFormatException();
            
            int value = Integer.parseInt(String.valueOf(engine.eval(txt.getText().trim())));
            if(value <= 0)
                throw new NumberFormatException();
        }catch(NumberFormatException | ScriptException e){
            errorAlert.setContentText(choice + " must be a positive integer less than Infinity");
            errorAlert.show();
            return false;
        }
        return true;
    }
    
    private boolean checkK_minus_1(){
        try{
            if(capacityK_minus1_input.getText().trim().length() == 0)
                throw new NumberFormatException();
            
            int value = Integer.parseInt(String.valueOf(engine.eval(capacityK_minus1_input.getText().trim())));
            
            if(value <= 0)
                throw new NumberFormatException();
            
        }catch(NumberFormatException | ScriptException e){
            errorAlert.setContentText("K-1 must be a positive integer number");
            errorAlert.show();
            return false;
        }
        
        return true;
    }
    
    private boolean  checkInitial_M(){
        try{
            if(initialNumberMInput.getText().trim().length() == 0)
                initialNumberMInput.setText("0");
           
            int value = Integer.parseInt(String.valueOf(engine.eval(initialNumberMInput.getText().trim())));
            if(Integer.parseInt(initialNumberMInput.getText().trim()) < 0)
               throw  new NumberFormatException();
            
        }catch(NumberFormatException | ScriptException e){
            errorAlert.setContentText("M must be a non-negative integer number");
            errorAlert.show();
            return false;
        }        
        return true;
    }
        

    private void setFontSize(int size) {
       lambdaLabel.setFont(new Font(size));
       muLabel.setFont(new Font(size));
       capacityK_minus1_label.setFont(new Font(size));
       initialNumberMLabel.setFont(new Font(size));
       capacityKLAbel.setFont(new Font(size));
       serversCLabel.setFont(new Font(size));
       modelLabel.setFont(new Font(size-3));
    }

    private void setUnitSize(int size) {
        lambdaUnit.setFont(new Font(size));
        muUnit.setFont(new Font(size));
        capacityKUnit.setFont(new Font(size));
        capacityK_minus1_Unit.setFont(new Font(size));
        serversCUnit.setFont(new Font(size));
        initialNumberMUnit.setFont(new Font(size));
    }
  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
      
    }
}
