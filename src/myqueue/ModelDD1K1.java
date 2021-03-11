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
public class ModelDD1K1 {
    
     private int ti = -1;
     private double arrival_time, service_time, EPS = 1e-17;
     private double lambda,  mu;
     private int k_minus_1, initialNumberM, k;
     
     ModelDD1K1(double lambda, double mu, int k_minus_1, int initialNumberM){
        this.lambda = lambda;
        this.k_minus_1 = k_minus_1;
        this.mu = mu;
        this.initialNumberM = initialNumberM;
        
        k = k_minus_1 + 1;
        
        arrival_time = 1 / lambda + EPS;
        service_time = 1 / mu + EPS;
        
        calcTi();
        System.out.println(ti);
    }

    public int getTi() {
        return ti;
    }
     
     public long calcNt(double t) {
        long answer = 0;
        
        if(initialNumberM == 0){
            if(t < 1 / lambda)
                answer = 0;
            else if(t < ti){
                answer = (int) (EPS + Math.floor(t / arrival_time) - Math.floor((t / service_time) - (arrival_time / service_time)));
            }else if(isMultiple(lambda, mu)){
                answer = k_minus_1;
            }else 
                answer = -1;
        }else{
            if(lambda == mu)
                answer = initialNumberM;
            else if(t < ti)
                answer = initialNumberM + (int)(t / arrival_time + EPS) - (int)(t / service_time + EPS);
            else 
                answer = -1;
        }
        return answer;
    }

    private void calcTi() {        
        if(initialNumberM == 0){
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
            int answer = (int)(EPS + (initialNumberM * service_time * arrival_time) / (arrival_time - service_time));
            
            for(double i = answer; i >= 0; i -= arrival_time){
                int res = (int)(EPS + (Math.floor(mu * i)) - Math.floor(lambda * i));
                if(res != initialNumberM) break;
                answer = (int)i;
            }
            
            ti = answer;
        }
    }


    private static boolean isMultiple(double a, double b) {
        double res = a / b;
        return (long)res == res;
    }

    public double calcWq(int n) {
        double answer = 0;
        int k = k_minus_1 + 1;
        
        if(initialNumberM == 0){
            if(n == 0)
                answer = 0;
            else if(n < lambda * ti)
                answer = (EPS + (service_time - arrival_time) * (n - 1));
            else
                answer = -1;
            
            if(answer == -1){
                if(isMultiple(mu, lambda)){
                     answer = ((service_time - arrival_time)*(lambda * ti - 2) + EPS);
                }else{
                    answer = -1;
                }
            }
        }else{
            if(lambda == mu)
                answer = ((initialNumberM - 1) * (1 / mu) + EPS);
            else if(n == 0)
                answer = ((initialNumberM - 1) / (2 * mu) + EPS);
            else if(n < (int) (ti * lambda + EPS))
                answer = ((initialNumberM + n - 1) * service_time - n * arrival_time + EPS);
            else 
                answer = 0;
        }
        
        return answer;
    }
}
