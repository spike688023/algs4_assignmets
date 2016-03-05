/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

//import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] nSegments;
    private int nSeg = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("Null pointer");
        int n = points.length;
        //double now_slop = Double.NEGATIVE_INFINITY;
        Point[] set = new Point[n];
        nSegments = new LineSegment[2];
        for (int i = 0; i < n; i++) 
        {
          set[i] = points[i];
        }

        Arrays.sort(set);

        // do not have the same element
        for (int i = 0; i < n-1; i++)
        {
            for (int j = i+1; j < n; j++)
            {
                if (set[i].compareTo(set[j]) == 0) throw new IllegalArgumentException("Illegal Arguments");
            }
        }

        for (int i = 0; i < n; i++)
        {
          int inARow = 0;
          Point[] slope_sorted = new Point[n];
          //make a copy of set 
          for (int m = 0; m < n; m++)
          {
            if (set[m] == null) throw new NullPointerException("Null pointer");
            slope_sorted[m] = set[m];
          }
          
          Point origin = set[i]; 
          //String stringPoints = origin.toString(); 
          //StdOut.println("i = " + i);
          Arrays.sort(slope_sorted, i+1, n, origin.slopeOrder()); 
          Arrays.sort(slope_sorted, 0,   i, origin.slopeOrder()); 
          for (int j = i+1; j < n-1; j++)
          {
            double tailSlope = origin.slopeTo(slope_sorted[j]);
            double thelast = origin.slopeTo(slope_sorted[j+1]);
            if (tailSlope == thelast) 
            {
              inARow++;
              if (j < n-2) continue;
            }

            if (inARow >= 2)
            {

                boolean theSame = false;
                if (nSeg == nSegments.length) resize(2*nSegments.length); 
                for (int k = 0; k <= i; k++) {
                    double headSlope = origin.slopeTo(slope_sorted[k]);
                    if (headSlope > tailSlope) break;
                    if (headSlope == tailSlope) {
                        theSame = true;
                        break;
                    } 
                }  

                if (!theSame) {
                    if (j == n-2 && tailSlope == thelast) {
                        nSegments[nSeg] = new LineSegment(origin, slope_sorted[j+1]);
                        nSeg++;
                    } 
                    else {
                        nSegments[nSeg] = new LineSegment(origin, slope_sorted[j]);
                        nSeg++;
                    }
                } 
            }
            inARow = 0;
          }
        }
        resize(nSeg);
    }

    public int numberOfSegments() {
        return nSeg;
    }

    public LineSegment[] segments() {
        return nSegments;
    }

    private void resize(int capacity) {
        LineSegment[] temp = new LineSegment[capacity];
        for (int i = 0; i < nSeg; i++)
            temp[i] = nSegments[i];
        nSegments = temp;
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        In in = new In(args[0]);
        int N = in.readInt();
        //StdOut.println("N = " + N);
        Point[] set = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            set[i] = new Point(x, y);
        }  

        // draw the set
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        //set[0].drawTo(set[1]);
        for (Point p : set) {
            p.draw();
        }  

        // print and draw the line nSegments
        FastCollinearPoints collinear = new FastCollinearPoints(set);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            //StdOut.println(collinear.numberOfSegments());
            segment.draw();
        }  
    }
}
