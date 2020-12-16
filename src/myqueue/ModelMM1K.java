/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue;

/**
 *
 * @author Moustafa Mohamed
 */
public class ModelMM1K {
    private double W, Wq;
    private long L, Lq;
    
    private double rho, EPS = 1e-17, lambda_dash, rhoPowK;
    
    public ModelMM1K(double lambda, double mu, int k){
        rho = lambda / mu;
        rhoPowK = Math.pow(rho, k);

        L = calcL(k);


        lambda_dash = calcLambda_dash(lambda, k);
        
        W = L / lambda_dash;
        
        Wq = W - (1 / mu);
        
        Lq = (long) Math.round(lambda_dash * Wq);
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

    private long calcL(int k) {
        if(rho == 1){
            return k / 2;
        }
        double numerator = 1 - (k + 1) * rhoPowK + k * rhoPowK * rho;
        double denominator = (1 - rho) * (1 - rhoPowK * rho);
        
        return (long)Math.round(rho * (numerator / denominator));
    }

    private double calcLambda_dash(double lambda, int k) {
        return lambda * (1 - p(k));
    }
    
    private double p(int k){
        if(rho == 1) return 1 / (k + 1);
        
        double numerator = 1 - rho;
        double denominator = (1 - rhoPowK * rho);
        
        return rhoPowK * (numerator / denominator);
    }
}
