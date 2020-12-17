/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue;

/**
 *
 * @author Khaled Diab
 */
public class ModelMMC {
    private double W, Wq;
    private long L, Lq;
    
    private double p0,r, rho,mu,lambda;
    private int c;
    
    public ModelMMC(double lambda, double mu, int c){
        r = lambda / mu;
        rho = r / c;
        this.mu = mu;
        this.lambda= lambda;
        this.c=c;
        p0=calcP0();
        Lq = calcLq();
        L =Lq+(long)r;
        Wq =Lq/lambda;
        W = Wq+(1/mu);
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
    
   
    private long calcLq(){
      double  numerator=Math.pow(r,c+1)/c;
      double  denominator= fact(c)*Math.pow((1-r/c),2);
      return  (long)(numerator/denominator*p0);
    }
 
    
    private double calcP0(){
        double sum = 0;
        if(c > r){
        for(int i=0; i<c; i++){   
             sum += (Math.pow(r, i)/fact(i))+(c*Math.pow(r, c)/fact(c)*(c-r));
        }
        }else {
             for(int i=0; i<c; i++){
        
            sum +=(1/fact(i))*Math.pow(r,i)+(1/fact(c))*Math.pow(r, c)*((c*mu)/(c*mu)-lambda);
             }   
        }
       
        return   1.0 / sum;
    }
    private double p(int n){
        double  numerator;
        double denominator;
        numerator=Math.pow(lambda,n);
        if(n>=0 && n<c){
            denominator=fact(n)*Math.pow(mu,n);
            }
        else {
            denominator=Math.pow(c,n-c)*fact(c)*Math.pow(mu, n);
        }

        return (numerator / denominator)*p0;
    }    
  
    private long fact(int c){
        long factorial = 1;
        for(int i=2; i<=c; i++) 
            factorial *= i;
        return factorial;
    }
}
    
