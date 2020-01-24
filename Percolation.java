import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid_open;
    private int numberOfOpenSites;

    final private WeightedQuickUnionUF quickUnionUF;
    final private int grid_size;
    final private int top;
    final private int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        grid_size = n;
        grid_open = new boolean[n][n];  // [rows, columns]
        quickUnionUF = new WeightedQuickUnionUF(n * n + 2);

        top = n * n;
        bottom = n * n + 1;

        numberOfOpenSites = 0;

    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > grid_size | col > grid_size) throw new IllegalArgumentException();
        if (row < 1 | col < 1) throw new IllegalArgumentException();

        if (isOpen(row, col)) {
            return;
        }

        // open cell
        grid_open[row - 1][col - 1] = true;
        numberOfOpenSites++;
        if (grid_size == 1) {
            return;
        }

        // add cell to a group - i.e. modify 'grid_id'
        // Neighbours
        int n_L = map2Dto1D(row, col - 1);
        int n_R = map2Dto1D(row, col + 1);
        int n_T = map2Dto1D(row - 1, col);
        int n_B = map2Dto1D(row + 1, col);

        int my_id = map2Dto1D(row, col);

        if (row == 1) {
            quickUnionUF.union(my_id, top);
        }
        if (row == grid_size) {
            quickUnionUF.union(my_id, bottom);
        }


        if (n_L != -1) {
            if (isOpen(row, col - 1)) {
                quickUnionUF.union(my_id, n_L);
            }
        }
        if (n_R != -1) {
            if (isOpen(row, col + 1)) {
                quickUnionUF.union(my_id, n_R);
            }
        }
        if (n_T != -1) {
            if (isOpen(row - 1, col)) {
                quickUnionUF.union(my_id, n_T);
            }
        }
        if (n_B != -1) {
            if (isOpen(row + 1, col)) {
                quickUnionUF.union(my_id, n_B);
            }
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > grid_size | col > grid_size) throw new IllegalArgumentException();
        if (row < 1 | col < 1) throw new IllegalArgumentException();

        return grid_open[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > grid_size | col > grid_size) throw new IllegalArgumentException();
        if (row < 1 | col < 1) throw new IllegalArgumentException();

        if (isOpen(row, col)) {
            int my_id = (row - 1) * grid_size + col - 1;

            if (quickUnionUF.connected(my_id, top)) {
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.connected(bottom, top);

    }


    private int map2Dto1D(int row, int col) {
        if (row <= 0 | row > grid_size | col <= 0 | col > grid_size) {
            return -1;  // invalid grid cell
        }
        return (row - 1) * grid_size + col - 1;
    }


    // test client (optional)
    public static void main(String[] args) {
//        Percolation test = new Percolation(3);
//        test.open(1, 1);
//        test.open(2, 1);
//        test.open(2, 3);
//        test.open(3, 3);
//        test.open(2, 2);

//        test.draw();

//        StdOut.println(test.isFull(2, 1));
//        StdOut.println(test.percolates());
    }


//    // draws grid representation of:
//    // 1) open cells
//    // 2) sets (cells belonging to the same set)
//    public void draw() {
//
//        StdOut.println("--------------");
//
//        // draw open cells
//        for (int row = 1; row < grid_size + 1; row++) {
//            for (int col = 1; col < grid_size + 1; col++) {
//                StdOut.print(isOpen(row, col) ? 1 : 0);
//            }
//            StdOut.println();
//        }
//        StdOut.println("--------------");
//
//        // draw linked sets
//        for (int row = 1; row < grid_size + 1; row++) {
//            for (int col = 1; col < grid_size + 1; col++) {
//                StdOut.print(quickUnionUF.find((row - 1) * grid_size + col - 1));
//            }
//            StdOut.println();
//        }
//        StdOut.println("--------------");
//
//    }
}
