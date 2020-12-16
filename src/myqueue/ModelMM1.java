/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue;

/**
 *
 * @author Ahmad AbdulRahman
 */
public class ModelMM1 {
    private long L, Lq;
    private double W, Wq;    
    
    public ModelMM1(double lambda, double mu){
        
        L = calcL(lambda, mu);
        Lq = calcLq(lambda, mu);
        W = calcW(lambda, mu);
        Wq = calcWq(lambda, mu);
        
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

    private long calcL(double lambda, double mu) {
        return (long) Math.round(lambda / (mu - lambda));
    }
    private long calcLq(double lambda, double mu) {
        return (long) Math.round((lambda * lambda) / (mu * (mu - lambda)));
    }
    private double calcW(double lambda, double mu) {
        return (1.0 / (mu - lambda));
    }
    private double calcWq(double lambda, double mu) {
        return (lambda / (mu * (mu - lambda)));
    }

}
