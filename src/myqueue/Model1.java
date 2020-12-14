/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class Model1{
    static private Label nLabel, tLabel, answerWq_of_n, answerN_of_t;
    static private TextField nInput, tInput;
    static private Button calculateButton, clearButton;
    static private HBox twoButtons;
    static private int n, t, ti = -1;
    
    public static void solve(double lambda, double mu, int capacityK_minus_1, int initialNumberM){
        
        Stage window = new Stage();
        window.setTitle("Query Model 1");
        window.setHeight(300);
        window.setWidth(300);
        
        nLabel = new Label("n: ");
        nInput = new TextField();
        
        tLabel = new Label("t: ");
        tInput = new TextField();
        
        answerN_of_t = new Label();
        answerWq_of_n = new Label();
        
        calculateButton = new Button("Calculate");
        clearButton = new Button("Clear");
        
        setToolTips();
            
        
        PromptText:
            nInput.setPromptText("25");
            tInput.setPromptText("15");
            
        
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(8, 8, 8, 8));
        layout.setHgap(2);
        layout.setVgap(2);
        layout.setAlignment(Pos.CENTER);
        
        twoButtons = new HBox(8);
        twoButtons.getChildren().addAll(calculateButton, clearButton);
        twoButtons.setPadding(new Insets(8, 8, 8, 8));
        twoButtons.setSpacing(5);
        
        setConstrains();
        
        calcTi(lambda, mu, capacityK_minus_1, initialNumberM);
        System.out.println(ti);
        calculateButton.setOnAction(e -> {
            if(checkNandT()){
                if(t != -1) calcNt(lambda, mu, capacityK_minus_1, initialNumberM);
                else answerN_of_t.setText("");
                
                if(n != -1) calcWq(lambda, mu, capacityK_minus_1, initialNumberM);
                else answerWq_of_n.setText("");
            }
        });
        
        setMaxWidth:
            nInput.setMaxWidth(200);
            tInput.setMaxWidth(200);
            
        layout.getChildren().addAll(nLabel, nInput, tLabel, tInput, twoButtons, answerWq_of_n, answerN_of_t);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private static void setConstrains() {
        GridPane.setConstraints(nLabel, 0, 0);
        GridPane.setConstraints(nInput, 1, 0);
        GridPane.setConstraints(tLabel, 0, 1);
        GridPane.setConstraints(tInput, 1, 1);
        GridPane.setConstraints(answerWq_of_n, 0, 2);
        GridPane.setConstraints(answerN_of_t, 0, 3);
        GridPane.setConstraints(twoButtons, 0, 4);
    }

    private static void setToolTips() {
        nLabel.setTooltip(new Tooltip("Customer number n"));
        nInput.setTooltip(new Tooltip("Customer number n"));
        tLabel.setTooltip(new Tooltip("time t"));
        tInput.setTooltip(new Tooltip("time t"));
        clearButton.setTooltip(new Tooltip("Clear inputs"));
    }

  

    private static boolean checkNandT() {
        try{
            if(nInput.getText().trim().length() == 0)
                n = -1;
            else 
                n = Integer.parseInt(nInput.getText().trim());
        }catch(NumberFormatException e){
            ErrorBox.display("Error!", "You must enter an integer number for n");
            return false;
        }
        
        try{
            if(tInput.getText().trim().length() == 0)
                t = -1;
            else
                t = Integer.parseInt(tInput.getText().trim());
        }catch(NumberFormatException e){
            ErrorBox.display("Error!", "You must enter an integer number for t");
            return false;
        }
        
        return true;
    }

    private static void calcWq(double lambda, double mu, int k_minus_1, int m) {
        int answer = 0;
        int k = k_minus_1 + 1;
        double arrival_rate = 1 / lambda, service_rate = 1 / mu;
        
        if(m == 0){
            if(n == 0)
                answer = 0;
            else if(n < lambda * ti)
                answer = (int)((service_rate - arrival_rate) * (n - 1));
            else
                answer = -1;
            
            if(answer == -1){
                if(isMuMultipleOfLambda(lambda, mu)){
                    answerWq_of_n.setText("Customer number " + n + " will wait about " + (int)((service_rate - arrival_rate)*(lambda * ti - 2)) + " second(s)");
                }else{
                    answerWq_of_n.setText("Customer number " + n + " will wait " +(int)((service_rate - arrival_rate)*(lambda * ti - 2)) + " or " + (int)((service_rate - arrival_rate)*(lambda * ti - 3)) + " second(s)");
                }
            }else
                answerWq_of_n.setText("Customer number " + n + " will wait about " + answer + " second(s)");
        }else{
            if(lambda == mu)
                answer = (int)((m - 1) * (1 / mu));
            else if(n == 0)
                answer = -1;
            else if(n < (int) ti * lambda)
                answer = (int)((m + n - 1) * service_rate - n * arrival_rate);
            else 
                n = 0;
            
            if(n == -1)
                answerWq_of_n.setText("Customer number " + n + " will wait about " + (int)((m - 1)*(2 * mu)) + " second(s) in average.");
            else
                answerWq_of_n.setText("Customer number " + n + " will wait about " + answer + " second(s)");


        }
    }
    private static void calcNt(double lambda, double mu, int k_minus_1, int m) {
        int answer = 0;
        int k = k_minus_1 + 1;
        double arrival_rate = 1 / lambda, service_rate = 1 / mu;
        if(m == 0){
            if(t < 1 / lambda)
                answer = 0;
            else if(t < ti){
                answer = (int) (Math.floor(t / arrival_rate) - Math.floor((t / service_rate) - (arrival_rate / service_rate)));
            }else{
                answer = -1;
            }
            
            if(answer == -1)
                answerN_of_t.setText("Number of customers at time " + t + " will be either " + (k - 1) + " or " + (k - 2));
            else
                answerN_of_t.setText("Number of customers at time " + t + " will be " + answer);
        }else{
            if(lambda == mu)
                answer = m;
            else if(t < ti)
                answer = m + (int)(t / arrival_rate) - (int)(t / service_rate);
            else 
                answer = -1;
            
            if(answer == -1)
               answerN_of_t.setText("Number of customer at time " + t + " will be either 0 or 1");
            else
               answerN_of_t.setText("Number of customer at time " + t + " will be " + answer);
        }
    }

    private static void calcTi(double lambda, double mu, int k_minus_1, int m) {
        int k = k_minus_1 + 1;
        double arrival_rate = 1 / lambda, service_rate = 1 / mu;
        
        
        if(m == 0){
            int answer = (int)((k * arrival_rate * service_rate - arrival_rate * arrival_rate)
                                       /(service_rate - arrival_rate));

            for(double i = answer; i >= 0; i -= arrival_rate){
                int res = (int) ((int)(i * lambda) - (int)((i - arrival_rate) / service_rate));
//                System.out.println(i + ": " + res);
                if(res != k) break;
                answer = (int)i;
            }
            ti = answer;
        }else{
            int answer = (int)((m * service_rate * arrival_rate) / (arrival_rate - service_rate));
            
            for(int i = answer; i >= 0; i--){
                int res = (int)((Math.floor(mu * i)) - Math.floor(lambda * i));
                if(res != m) break;
                answer = i;
            }
            
            ti = answer;
        }
    }


    private static boolean isMuMultipleOfLambda(double lambda, double mu) {
        double res = mu / lambda;
        return (int)res == res;
    }
    
    
}
