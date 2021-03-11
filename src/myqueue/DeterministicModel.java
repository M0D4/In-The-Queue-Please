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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Moustafa Mohamed
 */
public class DeterministicModel{
    static private Label nLabel, tLabel, answerWq_of_n, answerN_of_t;
    static private TextField nInput, tInput;
    static private Button calculateButton, clearButton, closeButton;
    static private HBox buttonsBox;
    static private int n;
    static private double Wq, t;
    static private Alert errorAlert;
    static private GridPane layout;
    static private Stage window;
    static private final double EPS = 1e-17;
    static private ScriptEngine engine;
    
    
    public static void display(double lambda, double mu, int capacityK_minus_1, int initialNumberM){
        
        window = new Stage();
        window.setTitle("D/D/1/" + capacityK_minus_1 + (initialNumberM > 0 ? ("/"+ initialNumberM) : ""));
        window.setHeight(263);
        window.setWidth(590);
        
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
        
            
        
        PromptText:
            nInput.setPromptText("25");
            tInput.setPromptText("15");
            
        
        layout = new GridPane();
        layout.setPadding(new Insets(8, 8, 8, 8));
        layout.setHgap(8);
        layout.setVgap(8);
        
        buttonsBox = new HBox(8);
        buttonsBox.getChildren().addAll(calculateButton, clearButton, closeButton);
        buttonsBox.setPadding(new Insets(8, 8, 8, 8));
        buttonsBox.setSpacing(20);
        
        
        ModelDD1K1 m = new ModelDD1K1(lambda, mu, capacityK_minus_1, initialNumberM);
                
        engine = new ScriptEngineManager().getEngineByName("JavaScript");
        
        calculateButton.setOnAction(e -> {
            if(checkNandT()){
                if(t != -1){
                    long nt = m.calcNt(t);
                    if(initialNumberM == 0){
                        if(nt == -1){
                            answerN_of_t.setText(String.format("Number of customers at time %.3f will be either %d or %d customer(s).", t, (capacityK_minus_1), (capacityK_minus_1 - 1)));
                        }else
                            answerN_of_t.setText(String.format("Number of customers at time %.3f will be %d", t, nt));
                    }else{
                        if(nt == -1)
                            answerN_of_t.setText(String.format("Number of customers at time %.3f will be either 0 or 1 customer.", t));
                         else
                            answerN_of_t.setText(String.format("Number of customers at time %.3f will be %d", t, nt));
                    }
                }
                else answerN_of_t.setText("");
                
                if(n != -1){
                    Wq = m.calcWq(n);
                    if(initialNumberM == 0){
                        if(Wq == -1){
                            double service_time = 1 / mu, arrival_time = 1 / lambda;
                            double answer1 = ((service_time - arrival_time)*(lambda * m.getTi() - 2) + EPS);
                            double answer2 = ((service_time - arrival_time)*(lambda * m.getTi() - 3) + EPS);
                            answerWq_of_n.setText(String.format("Customer number %d will wait %.3f or %.3f second(s)", n, answer1, answer2));
                        }else
                            answerWq_of_n.setText(String.format("Customer number %d will wait about %.3f second(s)", n, Wq));
                    }else{
                        if(n == 0 && lambda != mu)
                            answerWq_of_n.setText(String.format("Customer number %d will wait about %.3f second(s) in average.", n, Wq));
                        else
                            answerWq_of_n.setText(String.format("Customer number %d will wait about %.3f second(s)", n, Wq));
                    }
                }
                else answerWq_of_n.setText("");
            }
        });
        
        setFontSize(18);
        setInputsWidth(210);
        setConstrains();
        setToolTips();
            
            
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
        scene.getStylesheets().add(DeterministicModel.class.getResource("Light.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();
    }

    private static void setConstrains() {
        layout.add(nLabel, 0, 0, 1, 1);
        layout.add(nInput, 1, 0, 15, 1);
        layout.add(tLabel, 0, 1, 1, 1);
        layout.add(tInput, 1, 1, 15, 1);
        layout.add(answerWq_of_n, 0, 2, 15, 1);
        layout.add(answerN_of_t, 0, 3, 15, 1);
        layout.add(buttonsBox, 12, 4, 1, 1);
    }

    private static void setToolTips() {
        nLabel.setTooltip(new Tooltip("Customer number n"));
        nInput.setTooltip(new Tooltip("Customer number n"));
        tLabel.setTooltip(new Tooltip("time t"));
        tInput.setTooltip(new Tooltip("time t"));
        clearButton.setTooltip(new Tooltip("Clear inputs"));
    }
    

    private static boolean checkNandT() {
        if(nInput.getText().trim().length() != 0){
            try{
                n = Integer.parseInt(String.valueOf(engine.eval(nInput.getText().trim())));
                if(n < 0)  throw new NumberFormatException();
            }catch(NumberFormatException | ScriptException e){
                errorAlert.setContentText("You must enter a non-negative integer number for n");
                errorAlert.show();
                return false;
            }
        }else n = -1;
        
        if(tInput.getText().trim().length() != 0){
            try{
                t = Double.parseDouble(String.valueOf(engine.eval(tInput.getText().trim())));

                if(t < 0)  throw new NumberFormatException();
            }catch(NumberFormatException | ScriptException e){
                errorAlert.setContentText("You must enter a non-negative real number for t");
                errorAlert.show();
                return false;
            }
        }else t = -1;
        return true;
    }
    
    private static void setInputsWidth(int width) {
        nInput.setMaxWidth(width);   
        tInput.setMaxWidth(width);
        nInput.setMinWidth(width);   
        tInput.setMinWidth(width);
    }
    
    private static void setFontSize(int size) {
        nLabel.setFont(new Font(size));
        tLabel.setFont(new Font(size));
        answerN_of_t.setFont(new Font(size));
        answerWq_of_n.setFont(new Font(size));
    }
}
