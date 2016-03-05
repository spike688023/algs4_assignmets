import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] expers;
    private double times = 0;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {

        if (N <= 0 || T <= 0) throw new IllegalArgumentException("illeagalArgument");
        expers = new double[T];  
        times = T;
        for (int i = 0; i < T; i++) {
            expers[i] = 0;
            double count = 0;
            //double differene = 0;
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(N) + 1; 
                int column = StdRandom.uniform(N) + 1;  
                if (!perc.isOpen(row, column)) {
                    perc.open(row, column);
                    count++;
                } 
                //perc.open(row, column);
                //count++;
            }
            expers[i] = count/(N*N);
            //System.out.printf("count  ="+ count + "\n");
            //System.out.printf("T[" + i + "] = "+ expers[i] + "\n");
        }  
        //PercolationStats.mean = StdStats.mean( expers );
        //PercolationStats.stddev = StdStats.stddev( expers );
        //differene = ( 1.96 *  StdStats.stddev( expers )) / Math.sqrt(T);
        //PercolationStats.Hi = PercolationStats.mean - differene;
        //PercolationStats.Lo = PercolationStats.mean + differene;
    }

    // sample mean of percolation threshold 
    public double mean() {
        //return StdStats.mean( expers );
        //return PercolationStats.mean;
        return StdStats.mean(expers);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        //return StdStats.stddev( expers );
        //return PercolationStats.stddev;
        return StdStats.stddev(expers);

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double differene = (1.96 * StdStats.stddev(expers)) / Math.sqrt(times);
        return mean() + differene;
        //return PercolationStats.Hi;
    }

    // low endpointn of 95% confidence interval
    public double confidenceLo() {
        double differene = (1.96 * StdStats.stddev(expers)) / Math.sqrt(times);
        return mean() - differene;
        //return PercolationStats.Lo;
    }

    // test client
    public static void main(String[] args) {
        PercolationStats pec = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.printf("mean = " +  pec.mean() + "\n");
        System.out.printf("stddev = " +  pec.stddev() + "\n");
        System.out.printf(pec.confidenceLo() + ", " + pec.confidenceHi() + "\n");
        //System.out.printf("confidenceHi= " +  pec.confidenceHi() + "\n");
        //System.out.printf("confidenceLo= " +  pec.confidenceLo() + "\n");
    }
}
