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
    private double W, Wq, p0, lambda, mu;
    private double L, Lq;
    private int k, c;
    
    private double r, rho, lambda_dash, rhoPowK;
    
    public ModelMMCK(double lambda, double mu, int k, int c){
        this.lambda = lambda;
        this.mu = mu;
        this.k = k;
        this.c = c;
        
        
        r = lambda / mu;
        rho = r / c;
        
        rhoPowK = Math.pow(rho, k);
        p0 = calcP0();
        
        lambda_dash = calcLambda_dash();
        
        Lq = calcLq();
        L = calcL();
        Wq = calcWq();
        W = calcW();
    }
    
    public double getW() {
        return W;
    }
    public double getWq() {
        return Wq;
    }
    public double getL() {
        return L;
    }
    public double getLq() {
        return Lq;
    }
    
    private long calcL(){
        double sum = 0;
        for(int i=0; i<c; i++){
            double numerator = (c - i) * Math.pow(r, i);
            double denominator = fact(i);
            sum += numerator / denominator;
        }
        return Math.round(getLq() + c - p0 * sum);
    }
    private double calcLq(){
        double ans = Math.pow(rho, k-c) * (k + 1.0 - c) * (1.0 - rho);
        ans += Math.pow(rho, k + 1.0 - c);
        ans = 1.0 - ans;
        ans *= p0 * rho * Math.pow(r, c);
        ans /= ((1.0 - rho) * (1.0 - rho)) * fact(c);
        return (ans);
    }
    private double calcW(){
        return (double)L / lambda_dash;
    }
    private double calcWq(){
        return (double)Lq / lambda_dash;
    }
    
    private double calcP0(){
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
    private double p(int n){
        double numerator, denominator;
        numerator = Math.pow(lambda, n);
        
        if(n < c)
            denominator = fact(n) * Math.pow(mu, n);
        else
            denominator = Math.pow(c, n - c) * fact(c) * Math.pow(mu, n);
        
        return p0 * (numerator / denominator);
    }    
    private double calcLambda_dash() {
        return lambda * (1 - p(k));
    }
    private long fact(int c){
        long factorial = 1;
        for(int i=2; i<=c; i++) 
            factorial *= i;
        return factorial;
    }
}
