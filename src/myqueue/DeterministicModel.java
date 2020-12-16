package myqueue;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class DeterministicModel{
    static private Label nLabel, tLabel, answerWq_of_n, answerN_of_t;
    static private TextField nInput, tInput;
    static private Button calculateButton, clearButton, closeButton;
    static private HBox buttons;
    static private int n, t, Wq;
    static private Alert errorAlert;
    static private GridPane layout;
    static private Stage window;
    static private double EPS = 1e-17;
    
    public static void display(double lambda, double mu, int capacityK_minus_1, int initialNumberM){
        
        window = new Stage();
        window.setTitle("D/D/1/" + capacityK_minus_1 + (initialNumberM > 0 ? "/"+ initialNumberM : ""));
        window.setHeight(239);
        window.setWidth(427);
        
        window.setOnCloseRequest(e -> System.out.println("width: " + window.getWidth() + ", Height: " + window.getHeight()));
        
        nLabel = new Label("n: ");
        nInput = new TextField();
        
        tLabel = new Label("t: ");
        tInput = new TextField();
        
        answerN_of_t = new Label();
        answerWq_of_n = new Label();
        
        calculateButton = new Button("Calculate");
        clearButton = new Button("Clear");
        closeButton = new Button("Close");
        
        setToolTips();
            
        
        PromptText:
            nInput.setPromptText("25");
            tInput.setPromptText("15");
            
        
        layout = new GridPane();
        layout.setPadding(new Insets(8, 8, 8, 8));
        layout.setHgap(8);
        layout.setVgap(8);
        
        buttons = new HBox(8);
        buttons.getChildren().addAll(calculateButton, clearButton, closeButton);
        buttons.setPadding(new Insets(8, 8, 8, 8));
        buttons.setSpacing(20);
        
        setConstrains();
        
        ModelDD1K1 m = new ModelDD1K1(lambda, mu, capacityK_minus_1, initialNumberM);
                
        
        calculateButton.setOnAction(e -> {
            if(checkNandT()){
                if(t != -1){
                    long nt = m.calcNt(t);
                    if(initialNumberM == 0){
                        if(nt == -1)
                            answerN_of_t.setText(String.format("Number of customers at time %d will be either %d or %d customer(s).", t, (capacityK_minus_1), (capacityK_minus_1 - 1)));
                        else
                            answerN_of_t.setText(String.format("Number of customers at time %d will be %d", t, nt));
                    }else{
                        if(nt == -1)
                            answerN_of_t.setText(String.format("Number of customers at time %d will be either 0 or 1 customer.", t));
                         else
                            answerN_of_t.setText(String.format("Number of customers at time %d will be %d", t, nt));
                    }
                }
                else answerN_of_t.setText("");
                
                if(n != -1){
                    double Wq = m.calcWq(n);
                    if(initialNumberM == 0){
                        if(Wq == -1){
                            double service_time = 1 / mu, arrival_time = 1 / lambda;
                            double answer1 = ((service_time - arrival_time)*(lambda * m.getTi() - 2) + EPS);
                            double answer2 = ((service_time - arrival_time)*(lambda * m.getTi() - 3) + EPS);
                            answerWq_of_n.setText(String.format("Customer number %d will wait %.3f or %.3f second(s)", n, answer1, answer2));
                        }else
                            answerWq_of_n.setText(String.format("Customer number %d will wait about %.3f second(s)", n, Wq));
                    }else{
                        if(n == 0)
                            answerWq_of_n.setText(String.format("Customer number %d will wait about %.3f second(s) in average.", m, Wq));
                        else
                            answerWq_of_n.setText(String.format("Customer number %d will wait about %.3f second(s)", n, Wq));
                    }
                }
                else answerWq_of_n.setText("");
            }
        });
        
        setInputsWidth(210);
            
            
        clearButton.setOnAction(e -> {
            nInput.clear();
            tInput.clear();
            
            answerN_of_t.setText("");
            answerWq_of_n.setText("");
        });
        
        closeButton.setOnAction(e -> window.close());
        
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(null);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private static void setConstrains() {
        layout.add(nLabel, 0, 0, 1, 1);
        layout.add(nInput, 1, 0, 1, 1);
        layout.add(tLabel, 0, 1, 1, 1);
        layout.add(tInput, 1, 1, 1, 1);
        layout.add(answerWq_of_n, 0, 2, 2, 1);
        layout.add(answerN_of_t, 0, 3, 2, 1);
        layout.add(buttons, 1, 4, 3, 1);
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
            errorAlert.setContentText("You must enter an integer number for n");
            errorAlert.show();
            return false;
        }
        
        try{
            if(tInput.getText().trim().length() == 0)
                t = -1;
            else
                t = Integer.parseInt(tInput.getText().trim());
        }catch(NumberFormatException e){
           errorAlert.setContentText("You must enter an integer number for t");
           errorAlert.show();
            return false;
        }
        
        return true;
    }
    
    private static void setInputsWidth(int width) {
        nInput.setMaxWidth(width);   
        tInput.setMaxWidth(width);
        nInput.setMinWidth(width);   
        tInput.setMinWidth(width);
    }
    
}
