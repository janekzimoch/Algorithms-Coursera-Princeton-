import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] points;
    private int numberOfSegments;

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }

        Arrays.sort(points);
        this.points = points;
    }

    public int numberOfSegments() {        // the number of line segments
        return numberOfSegments;
    }

    public LineSegment[] segments() {                // the line segments

        LineSegment[] lineSegments = new LineSegment[1];
        numberOfSegments = 0;


        for (int i = 0; i < points.length; i++) {

            double[] slopes = new double[points.length];

            for (int j = 0; j < points.length; j++) {
                slopes[j] = points[i].slopeTo(points[j]);
            }


            for (int p = 0; p < points.length; p++) {
                int[] collinear = new int[4];
                int noOfCollinear = 0;

                for (int r = 0; r < slopes.length; r++) {
                    if (noOfCollinear >= 4) break; // REDUNDANT - the input won't contain more colinear points than 4
                    if (slopes[p] == slopes[r] || slopes[r] == Double.NEGATIVE_INFINITY) {
                        collinear[noOfCollinear] = r;
                        noOfCollinear++;
                    }
                }

//                StdOut.println(noOfCollinear);

                if (noOfCollinear >= 4) {
                    // resize lineSegments if necessary
                    if (numberOfSegments >= lineSegments.length) {
                        lineSegments = Arrays.copyOf(lineSegments, lineSegments.length + 1);
                    }
                    lineSegments[numberOfSegments] = new LineSegment(points[collinear[0]], points[collinear[3]]);
                    numberOfSegments++;
                }
            }
        }

//        for (LineSegment l : lineSegments) {
//            StdOut.println(l.toString());
//        }

        // now clean up repeating segments
        int number_of_unique_segments = lineSegments.length;
        for (int i = 0; i < lineSegments.length; i++) {
            for (int j = i + 1; j < lineSegments.length; j++) {
                if (lineSegments[i].toString().equals(lineSegments[j].toString())) {
                    lineSegments[i] = null;
                    number_of_unique_segments--;
                    break;
                }
            }
        }

        numberOfSegments = number_of_unique_segments;

        LineSegment[] uniqueLineSegments = new LineSegment[number_of_unique_segments];
        int ind = 0;
        for (LineSegment i : lineSegments) {
            if (i != null) {
                uniqueLineSegments[ind] = i;
                ind++;
            }
        }

        return uniqueLineSegments;
    }


    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        int ind = 0;
        Point[] points = new Point[Integer.parseInt(StdIn.readString())];

        while (!StdIn.isEmpty()) {
            points[ind] = new Point(Integer.parseInt(StdIn.readString()), Integer.parseInt(StdIn.readString()));
            ind++;
        }
        for (Point p : points) {
            StdOut.println(p.toString());
        }

        BruteCollinearPoints solution = new BruteCollinearPoints(points);
        LineSegment[] segments = solution.segments();
        for (LineSegment s : segments) {
            StdOut.println(s);
        }
    }

}


