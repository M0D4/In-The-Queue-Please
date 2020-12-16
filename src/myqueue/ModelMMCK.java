/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue;

/**
 *
 * @author ahmad
 */
public class ModelMMCK {
    private double W, Wq;
    private long L, Lq;
    
    private double r, rho, lambda_dash, rhoPowK;
    
    public ModelMMCK(double lambda, double mu, int k, int c){
        r = lambda / mu;
        rho = r / c;
        rhoPowK = Math.pow(rho, k);
        lambda_dash = calcLambda_dash(lambda, k);
        
        Lq = calcLq(k, c);
        L = calcL(k, c);
        Wq = calcWq();
        W = calcW();
    }
    
    public double getW() {
        return W;
    }
    public double getWq() {
        return Wq;
    }
    public long getL() {
        return L;
    }
    public long getLq() {
        return Lq;
    }
    
    private long calcL(int k, int c){
        double sum = 0;
        for(int i=0; i<c; i++){
            double numerator = (double)(c - i) * (double)Math.pow(r, i);
            double denominator = fact(i);
            sum += numerator / denominator;
        }
        return Math.round(sum * p0(k, c) + getLq());
    }
    private long calcLq(int k, int c){
        double ans = Math.pow(rho, k-c) * (k + 1.0 - c) * (1.0 - rho);
        ans += Math.pow(rho, k + 1.0 - c);
        ans = Math.round(1.0 - ans);
        ans /= ((1.0 - rho) * (1.0 - rho)) * fact(c);
        ans *= p0(k, c) * rho * Math.pow(r, c);
        return Math.round(ans);
    }
    private double calcW(){
        return (double)L / lambda_dash;
    }
    private double calcWq(){
        return (double)Lq / lambda_dash;
    }
    
    private double p0(int k, int c){
        double sum = 0;
        if(c != r){
            sum = 1.0 - Math.pow(rho, k + 1.0 - c);
            sum *= Math.pow(r, c);
            sum /= (double)(fact(c) * (1.0 - rho));
        }else{
            sum = k + 1.0 - c;
            sum *= Math.pow(r, c);
            sum /= fact(c);
        }
        for(int i=0; i<c; i++){
            double numerator = Math.pow(r, i);
            double denominator = fact(i);
            sum += numerator / denominator;
        }
        return 1.0 / sum;
    }
    private double p(int k){
        if(rho == 1) return 1 / (k + 1);
        
        double numerator = 1 - rho;
        double denominator = (1 - rhoPowK * rho);
        
        return rhoPowK * (numerator / denominator);
    }    
    private double calcLambda_dash(double lambda, int k) {
        return lambda * (1 - p(k));
    }
    private long fact(int c){
        long factorial = 1;
        for(int i=2; i<=c; i++) 
            factorial *= i;
        return factorial;
    }
}
