package hw2;


import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;


public class PercolationStats {
   private double[] threshold;
   private int T;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        this.threshold = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation per = pf.make(N);
            while (!per.percolates()) {
                int x = StdRandom.uniform(N);
                int y = StdRandom.uniform(N);
                per.open(x, y);
            }
            threshold[i] = (double) per.numberOfOpenSites() / (N * N);
        }

    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLow() {
        return (mean() - (1.96 * stddev()/ Math.sqrt(T)));
    }

    public double confidenceHigh() {
        return (mean() + (1.96 * stddev() / Math.sqrt(T)));
    }

    public static void main(String[] args) {
        
    }
}
