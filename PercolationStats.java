import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    double[] iterations;
    int number_of_trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 | trials <= 0) throw new IllegalArgumentException();

        number_of_trials = trials;
        iterations = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation trial = new Percolation(n);
            while (!trial.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                trial.open(row, col);
            }
            iterations[i] = ((double) trial.numberOfOpenSites) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(iterations);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(iterations);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / java.lang.Math.sqrt(number_of_trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / java.lang.Math.sqrt(number_of_trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) throw new IllegalArgumentException();
        PercolationStats run = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.print("mean: ");
        StdOut.println(run.mean());
        StdOut.print("stddev: ");
        StdOut.println(run.stddev());
        StdOut.print("95% confidence interval: ");
        StdOut.println(run.confidenceLo() + ", " + run.confidenceHi());
//        StdOut.println("-----");
//        for (double i : run.iterations) {
//            StdOut.println(i);
//        }
    }

}
