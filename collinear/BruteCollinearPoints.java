/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    //private ArrayList<LineSegment> Segments = new ArrayList<LineSegment>();
    private LineSegment[] nSegments;
    private int nSeg = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("Null pointer");
        int n = points.length;
        Point[] set = new Point[n];
        nSegments = new LineSegment[2];
        for (int i = 0; i < n; i++) 
        {
          set[i] = points[i];
        }

        Arrays.sort(set);

        for (int i = 0; i < n-1; i++)
        {
            for (int j = i+1; j < n; j++)
            {
                if (set[i].compareTo(set[j]) == 0) throw new IllegalArgumentException("Illegal Arguments");
            }
        }

        for (int i = 0; i < n - 3; i++)
        {
          for (int j = i + 1; j < n - 2; j++)
          {
            for (int k = j + 1; k < n -1; k++)
            {
              for (int l = k + 1; l < n; l++)
              {
                //here I compare the three slopes
                double slope1 = set[i].slopeTo(set[j]);
                double slope2 = set[i].slopeTo(set[k]);
                double slope3 = set[i].slopeTo(set[l]);
                if (slope1 == slope2 & slope2 == slope3 & slope1 == slope3)
                {
                  //System.out.println (set[i].toString() + " -> " + 
                  //                    set[j].toString() + " -> " + 
                  //                    set[k].toString() + " -> " + 
                  //                    set[l].toString());
                    if (nSeg == nSegments.length) resize(2*nSegments.length); 
                    nSegments[nSeg] = new LineSegment(set[i], set[l]);
                    nSeg++;
                }
              }
            }
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
        //read the N set from a file
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(set);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }  
        
    }
}
