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
    static double arrival_time, service_time, EPS = 1e-17;
    
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
        
        arrival_time = 1 / lambda + EPS;
        service_time = 1 / mu + EPS;
        
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
            
        clearButton.setOnAction(e -> {
            nInput.clear();
            tInput.clear();
            
            answerN_of_t.setText("");
            answerWq_of_n.setText("");
        });
        
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
        
        if(m == 0){
            if(n == 0)
                answer = 0;
            else if(n < lambda * ti)
                answer = (int)(EPS + (service_time - arrival_time) * (n - 1));
            else
                answer = -1;
            
            if(answer == -1){
                if(isMuMultipleOfLambda(lambda, mu)){
                    answerWq_of_n.setText("Customer number " + n + " will wait about " + (int)((service_time - arrival_time)*(lambda * ti - 2) + EPS) + " second(s)");
                }else{
                    answerWq_of_n.setText("Customer number " + n + " will wait " +(int)((service_time - arrival_time)*(lambda * ti - 2) + EPS) + " or " + (int)((service_time - arrival_time)*(lambda * ti - 3) + EPS) + " second(s)");
                }
            }else
                answerWq_of_n.setText("Customer number " + n + " will wait about " + answer + " second(s)");
        }else{
            if(lambda == mu)
                answer = (int)((m - 1) * (1 / mu) + EPS);
            else if(n == 0)
                answer = -1;
            else if(n < (int) (ti * lambda + EPS))
                answer = (int)((m + n - 1) * service_time - n * arrival_time + EPS);
            else 
                answer = 0;
            
            if(answer == -1)
                answerWq_of_n.setText("Customer number " + n + " will wait about " + (int)((m - 1)*(2 * mu) + EPS) + " second(s) in average.");
            else
                answerWq_of_n.setText("Customer number " + n + " will wait about " + answer + " second(s)");


        }
    }
    private static void calcNt(double lambda, double mu, int k_minus_1, int m) {
        int answer = 0;
        int k = k_minus_1 + 1;
        
        if(m == 0){
            if(t < 1 / lambda)
                answer = 0;
            else if(t < ti){
                answer = (int) (EPS + Math.floor(t / arrival_time) - Math.floor((t / service_time) - (arrival_time / service_time)));
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
                answer = m + (int)(t / arrival_time + EPS) - (int)(t / service_time + EPS);
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
        
        if(m == 0){
            int answer = (int)(EPS + (k * arrival_time * service_time - arrival_time * arrival_time)
                                       /(service_time - arrival_time));

            for(double i = answer; i >= 0; i -= arrival_time){
                int res = (int) (EPS + (int)(i * lambda) - (int)((i - arrival_time) / service_time));
//                System.out.println(i + ": " + res);
                if(res != k) break;
                answer = (int)i;
            }
            ti = answer;
        }else{
            int answer = (int)(EPS + (m * service_time * arrival_time) / (arrival_time - service_time));
            
            for(double i = answer; i >= 0; i -= arrival_time){
                int res = (int)(EPS + (Math.floor(mu * i)) - Math.floor(lambda * i));
                if(res != m) break;
                answer = (int)i;
            }
            
            ti = answer;
        }
    }


    private static boolean isMuMultipleOfLambda(double lambda, double mu) {
        double res = mu / lambda + EPS;
        return (int)res == res;
    }
    
    
}
