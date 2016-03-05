import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean percolations = false;
    private int grids = 0;
    private boolean[] open;
    private boolean[] full;
    private boolean[] connectTop;
    private boolean[] connectBotton;
    private WeightedQuickUnionUF wUF;

    // create N-by-N grid, with all sites blocked 
    public Percolation(int N) {
        grids = N;
        wUF = new WeightedQuickUnionUF(N*N);
        if (N <= 0) throw new IllegalArgumentException("row index i out of bounds");        
        open = new boolean[N*N];
        full  = new boolean[N*N];
        connectTop = new boolean[N*N];
        connectBotton = new boolean[N*N];
        //percolations = new int[N*N];

        for (int i = 0; i < N*N; i++) {
            open[i] = false;
            full[i] = false;
            connectTop[i] = false;
            connectBotton[i] = false;
            //percolations[i] = i;
        }
      //for (int i = 0; i < N; i++) {
      //    percolations[i] = (N-1)*N+i;
      //} 
    }

    // open site(row i , column j) if it is not open already
    public void open(int i, int j) {
        int p = (i-1) * grids + j -1;
        if (!(i >= 1 && i <= grids && j >= 1 && j <= grids)) {
            throw new IndexOutOfBoundsException("out of index");
        }   

        int rootp = wUF.find(p);
        int rootq = 0;
        open[p] = true;

        if (i == 1) { 
            full[p] = true;
            full[rootp] = true;
            connectTop[p] = true;
            connectTop[rootp] = true;
        }
         
        if (i == grids) { 
            connectBotton[p] = true;
            connectBotton[rootp] = true;
        }
        
        rootp = wUF.find(p);
        // L bounds(i , j-1)
        if (j >= 2 && open[p-1]) {
            rootq = wUF.find(p-1);
            full[rootp] = full[rootp] || full[rootq];
            full[rootq] = full[rootp];
            connectTop[rootp] = connectTop[rootp] || connectTop[rootq];
            connectTop[rootq] = connectTop[rootp];
            connectBotton[rootp] = connectBotton[rootp] || connectBotton[rootq];
            connectBotton[rootq] = connectBotton[rootp];
            wUF.union(p , p - 1);  
        }
        // R bounds( i, j+1)
        if (j <= grids-1 && open[p+1]) {
            rootp = wUF.find(p);
            rootq = wUF.find(p+1);
            full[rootp] = full[rootp] || full[rootq];
            full[rootq] = full[rootp];
            connectTop[rootp] = connectTop[rootp] || connectTop[rootq];
            connectTop[rootq] = connectTop[rootp];
            connectBotton[rootp] = connectBotton[rootp] || connectBotton[rootq];
            connectBotton[rootq] = connectBotton[rootp];
            wUF.union(p, p+1);    
        }
        //U bounds( i-1,j)
        if (i >= 2 && open[p - grids]) {
            rootp = wUF.find(p);
            rootq = wUF.find(p-grids);
            full[rootp] = full[rootp] || full[rootq];
            full[rootq] = full[rootp];
            connectTop[rootp] = connectTop[rootp] || connectTop[rootq];
            connectTop[rootq] = connectTop[rootp];
            connectBotton[rootp] = connectBotton[rootp] || connectBotton[rootq];
            connectBotton[rootq] = connectBotton[rootp];
            wUF.union(p, p - grids); 
        }
        //D bounds( i+1, j)
        if (i <= grids-1 && open[p + grids]) {
            rootp = wUF.find(p);
            rootq = wUF.find(p+grids);
            full[rootp] = full[rootp] || full[rootq];
            full[rootq] = full[rootp];
            connectTop[rootp] = connectTop[rootp] || connectTop[rootq];
            connectTop[rootq] = connectTop[rootp];
            connectBotton[rootp] = connectBotton[rootp] || connectBotton[rootq];
            connectBotton[rootq] = connectBotton[rootp];
            wUF.union(p, p + grids);  
        }

        rootp = wUF.find(p); 
        if ((!percolations) && connectTop[rootp] && connectBotton[rootp]) {
           percolations = true;  
        } 

    }

    // is site(row i , column j) open ?
    public boolean isOpen(int i, int j) {
        if (!(i >= 1 && i <= grids && j >= 1 && j <= grids)) {
            throw new IndexOutOfBoundsException("out of index");
        }   
        return open[(i-1) * grids + j -1];
    }

    // is site(row i , column j ) full ? 
    public boolean isFull(int i, int j) {
        int p = (i-1) * grids + j -1;
        if (!(i >= 1 && i <= grids && j >= 1 && j <= grids)) {
            throw new IndexOutOfBoundsException("out of index");
        }   

        if (i == 1 && open[p]) {
            return true;
        } 
        return full[wUF.find(p)];
    }

    // does the system percolate ? 
    public boolean percolates() {
        if (grids == 1) {
            return open[0];
        } 

        return percolations;
    }

    public static void main(String[] args) {

////////int N = 8;
////////WeightedQuickUnionUF wUF = new WeightedQuickUnionUF(N);
////////wUF.union(0,7);
////////wUF.union(2,3);
////////wUF.union(4,5);
////////wUF.union(1,5);
////////wUF.union(1,2);
////////wUF.union(1,0);
////////int count = wUF.count();
////////System.out.printf("Count = %d\n", count);
        //System.out.printf("args[0] = %d\n", Integer.parseInt(args[0]) );
////////System.out.printf("args[1] = %d\n", Integer.parseInt(args[1]) );
    }
}
