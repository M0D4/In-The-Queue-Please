package myqueue;

/**
 *
 * @author Moustafa Mohamed
 */
public class ModelMM1K {
    private double W, Wq;
    private double L, Lq;
    
    private double rho, lambda_dash, rhoPowK;
    private final double EPS = 1e-17;
    
    public ModelMM1K(double lambda, double mu, int k){
        rho = lambda / mu;
        rhoPowK = Math.pow(rho, k);

        L = calcL(k);


        lambda_dash = calcLambda_dash(lambda, k);
        
        W = L / lambda_dash;
        
        Wq = W - (1 / mu);
        
        Lq = lambda_dash * Wq + EPS;
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

    private double calcL(int k) {
        if(rho == 1){
            return k / 2;
        }
        double numerator = 1 - (k + 1) * rhoPowK + k * rhoPowK * rho;
        double denominator = (1 - rho) * (1 - rhoPowK * rho);
        
        return rho * (numerator / denominator) + EPS;
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
