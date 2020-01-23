import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    boolean[][] grid_open;
    WeightedQuickUnionUF quickUnionUF;
    int numberOfOpenSites;
    int grid_size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        grid_size = n;
        grid_open = new boolean[n][n];  // [rows, columns]
        quickUnionUF = new WeightedQuickUnionUF(n * n);

        numberOfOpenSites = 0;

    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > grid_size | col > grid_size) throw new IllegalArgumentException();
        if (row < 1 | col < 1) throw new IllegalArgumentException();


        // open cell
        grid_open[row - 1][col - 1] = true;
        numberOfOpenSites++;

        // add cell to a group - i.e. modify 'grid_id'
        // Neighbours
        int n_L = (row - 1) * grid_size + col - 2;
        int n_R = (row - 1) * grid_size + col;
        int n_T = (row - 2) * grid_size + col - 1;
        int n_B = (row) * grid_size + col - 1;

        int my_id = (row - 1) * grid_size + col - 1;


        // corners
        if (row == 1 & col == 1) {  // TOP-LEFT
            //RIGHT
            if (grid_open[row - 1][col]) {
                quickUnionUF.union(n_R, my_id);
            }
            // BOTTOM
            if (grid_open[row][col - 1]) {
                quickUnionUF.union(n_B, my_id);
            }

        } else if (row == grid_size & col == 1) {  // BOTTOM-LEFT
            // RIGHT
            if (grid_open[row - 1][col]) {
                quickUnionUF.union(n_R, my_id);
            }

            // TOP
            if (grid_open[row - 2][col - 1]) {
                quickUnionUF.union(n_T, my_id);
            }

        } else if (row == 1 & col == grid_size) {  // TOP-RIGHT
            // LEFT
            if (grid_open[row - 1][col - 2]) {
                quickUnionUF.union(n_L, my_id);
            }

            // BOTTOM
            if (grid_open[row][col - 1]) {
                quickUnionUF.union(n_B, my_id);
            }

        } else if (row == grid_size & col == grid_size) {  // BOTTOM-RIGHT
            // LEFT
            if (grid_open[row - 1][col - 2]) {
                quickUnionUF.union(n_L, my_id);
            }

            // TOP
            if (grid_open[row - 2][col - 1]) {
                quickUnionUF.union(n_T, my_id);
            }

        }


        // edges
        else if (col == 1) {  // left edge
            // RIGHT
            if (grid_open[row - 1][col]) {
                quickUnionUF.union(n_R, my_id);
            }
            // TOP
            if (grid_open[row - 2][col - 1]) {
                quickUnionUF.union(n_T, my_id);
            }
            // BOTTOM
            if (grid_open[row][col - 1]) {
                quickUnionUF.union(n_B, my_id);
            }

        } else if (col == grid_size) {  // right edge
            // LEFT
            if (grid_open[row - 1][col - 2]) {
                quickUnionUF.union(n_L, my_id);
            }
            // TOP
            if (grid_open[row - 2][col - 1]) {
                quickUnionUF.union(n_T, my_id);
            }
            // BOTTOM
            if (grid_open[row][col - 1]) {
                quickUnionUF.union(n_B, my_id);
            }

        } else if (row == 1) {  // top edge
            // LEFT
            if (grid_open[row - 1][col - 2]) {
                quickUnionUF.union(n_L, my_id);
            }
            // RIGHT
            if (grid_open[row - 1][col]) {
                quickUnionUF.union(n_R, my_id);
            }
            // BOTTOM
            if (grid_open[row][col - 1]) {
                quickUnionUF.union(n_B, my_id);
            }

        } else if (row == grid_size) {  // bottom edge
            // LEFT
            if (grid_open[row - 1][col - 2]) {
                quickUnionUF.union(n_L, my_id);
            }
            // RIGHT
            if (grid_open[row - 1][col]) {
                quickUnionUF.union(n_R, my_id);
            }
            // TOP
            if (grid_open[row - 2][col - 1]) {
                quickUnionUF.union(n_T, my_id);
            }
        }


        // normal case
        else {
            // LEFT
            if (grid_open[row - 1][col - 2]) {
                quickUnionUF.union(n_L, my_id);
            }
            // RIGHT
            if (grid_open[row - 1][col]) {
                quickUnionUF.union(n_R, my_id);
            }
            // TOP
            if (grid_open[row - 2][col - 1]) {
                quickUnionUF.union(n_T, my_id);
            }
            // BOTTOM
            if (grid_open[row][col - 1]) {
                quickUnionUF.union(n_B, my_id);
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

            for (int i = 0; i < grid_size; i++) {
                if (quickUnionUF.connected(my_id, i)) {
                    return true;
                }
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
        for (int col = 1; col < grid_size + 1; col++) {
            if (isFull(grid_size, col)) {
                return true;
            }
        }
        return false;

    }

    // draws grid representation of:
    // 1) open cells
    // 2) sets (cells belonging to the same set)
    public void draw() {

        StdOut.println("--------------");

        // draw open cells
        for (int row = 1; row < grid_size + 1; row++) {
            for (int col = 1; col < grid_size + 1; col++) {
                StdOut.print(isOpen(row, col) ? 1 : 0);
            }
            StdOut.println();
        }
        StdOut.println("--------------");

        // draw linked sets
        for (int row = 1; row < grid_size + 1; row++) {
            for (int col = 1; col < grid_size + 1; col++) {
                StdOut.print(quickUnionUF.find((row - 1) * grid_size + col - 1));
            }
            StdOut.println();
        }
        StdOut.println("--------------");

    }


    // test client (optional)
    public static void main(String[] args) {
        Percolation test = new Percolation(3);
        test.open(1, 1);
        test.open(2, 1);
        test.open(2, 3);
        test.open(3, 3);
        test.open(2, 2);

        test.draw();

        StdOut.println(test.isFull(2, 1));
        StdOut.println(test.percolates());

//
//        test.open(1, 3);
//        StdOut.println(test.quickUnionUF.find(2));


//        test.open(2, 3);
////        test.open(3, 3);
//        boolean c = test.isFull(1, 1);
//        boolean c2 = test.isFull(1, 2);
//        StdOut.println(c);
//        StdOut.println(c2);
//
//        boolean c = test.isFull(1, 1);
//        boolean c2 = test.isFull(1, 2);
//        StdOut.println(c);
//        StdOut.println(c2);

    }
}
