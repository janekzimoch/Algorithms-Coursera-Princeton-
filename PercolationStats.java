import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int number_of_trials;

    private double mean;
    private double stddev;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 | trials <= 0) throw new IllegalArgumentException();

        number_of_trials = trials;
        double[] iterations = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation trial = new Percolation(n);
            while (!trial.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                trial.open(row, col);
            }
            iterations[i] = ((double) trial.numberOfOpenSites()) / (n * n);
        }
        mean = StdStats.mean(iterations);
        stddev = StdStats.stddev(iterations);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - 1.96 * stddev / Math.sqrt(number_of_trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + 1.96 * stddev / Math.sqrt(number_of_trials);
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
    }

}
