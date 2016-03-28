import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;

public class Board {
  private int[][] board; 
  private int N = 0; 
  private int posX, posY; // 0' position 

  public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
  {
    N = board.length;
    board = new int[blocks.length][blocks[0].length];
    for (int i = 0; i < blocks.length; i++){
      for (int j = 0; j < blocks[0].length; j++){
        board[i][j] = blocks[i][j];
        if (board[i][j] == 0) {
            posX = i;
            posY = j;
        } 
      }
    }
  }
  
  public int dimension()                 // board dimension N
  {
    return board.length;
  }
  
  public int hamming()                   // number of blocks out of place
  {
    int num_out_of_place = 0;
    for (int i = 0; i < board.length; i++){
      for (int j = 0; j < board[0].length; j++){
        if (board[i][j] != i*dimension() + j + 1 & board[i][j] != 0)
        {
          num_out_of_place++;
        }
      }
    }
    return num_out_of_place;
  }
  
  public int manhattan()                 // sum of Manhattan distances between blocks and goal
  {
    int num_out_of_place = 0;
    for (int i = 0; i < board.length; i++){
      for (int j = 0; j < board[0].length; j++){
        //find the expected i and j values given the board value
        if (board[i][j] != 0)
        {
          int expi = (board[i][j]-1) / dimension();
          int expj = (board[i][j]-1) % dimension();
          //System.out.println(Integer.toString(board[i][j]) + " / " + Integer.toString(dimension()) +  " = " + Integer.toString(expi));
          //System.out.println(Integer.toString(board[i][j]) + "-1 % " + Integer.toString(dimension()) + " = " + Integer.toString(expj));
          num_out_of_place += Math.abs(i-expi) + Math.abs(j-expj);
        }
      }
    }
    return num_out_of_place;
  }
  
  
  public boolean isGoal()                // is this board the goal board?
  {
    return hamming() == 0;
  }
  
  
  public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
  {
    boolean done = false;
    int[][] twinTemp = new int[N][N];
    twinTemp = generateCopyBoard();
    for (int i = 0; i < N; i++){
      for (int j = 0; j < N-1; j++){
        if (twinTemp[i][j] != 0 & twinTemp[i][j+1] != 0)
        {
          swapPos(twinTemp,i,j,i,j+1);
          done = true;
          break;
        }
      }
      if (done) break;
    }
    Board twin = new Board(twinTemp);
    return twin;
  }

  
  public boolean equals(Object y)        // does this board equal y?
  {
    if (y == this) return true; //check if the same object is being compared
    if (y == null) return false; //check for empty object
    if (y.getClass() != this.getClass()) return false; //type check
    Board that = (Board) y;
    return this.toString().equals(that.toString());
  }

  private int[] findValue(int val)
  {
    int[] empty = {0,0}; 
    for (int i = 0; i < board.length; i++){
      for (int j = 0; j < board[0].length; j++){
        if (board[i][j] == val) 
        {
          empty[0] = i;
          empty[1] = j;
          return empty;
        }
      }
    }
    return empty;
  }
  
  private int[][] generateCopyBoard() 
  {
    int[][] twinTemp = new int[board.length][board[0].length];
    for (int i = 0; i < board.length; i++){
      for (int j = 0; j < board[0].length; j++){
        twinTemp[i][j] = board[i][j];
      }
    }
    return twinTemp;
  }
  
  private void swapPos(int[][] board, int i1, int j1, int i2, int j2)
  {
    int temp = board[i1][j1];
    board[i1][j1] = board[i2][j2];
    board[i2][j2] = temp; 
  }
  
  public Iterable<Board> neighbors()     // all neighboring boards
  {
        //Queue<Board> queue = new Queue<Board>();
        ArrayList<Board> list = new ArrayList<Board>();
        int[] dx = {0, 0, -1, 1};
        int[] dy = {1, -1, 0, 0};
        for (int i = 0; i < 4; i++) {
            int x = posX + dx[i];
            int y = posY + dy[i];
            
            if (x < N && x >= 0 && y < N && y >= 0) {
                int tmp = board[posX][posY];
                board[posX][posY] = board[x][y];
                board[x][y] = tmp;
                list.add(new Board(board));
                tmp = board[posX][posY];
                board[posX][posY] = board[x][y];
                board[x][y] = tmp;
            }
        }
        return list;
  }
  
    public String toString()               // string representation of the board (in the output format specified below)
  {
    String output = Integer.toString(dimension());
    for (int i = 0; i < board.length; i++){
      for (int j = 0; j < board[0].length; j++){
        if (j == 0) {output = output + "\n";}
        output = output + Integer.toString(board[i][j]);
      }
    }
    return output;
  } 
  
    private int[][] getCopy(){
    	int[][] result = new int[N][];
    	for (int i=0; i<N; i++){
    		result[i] = Arrays.copyOf(board[i], N);
    	}
    	return result;
    }
  
  public static void main(String[] args)
  {
    int[][] arr0 = {{8, 1, 3},{4, 0, 2},{7,6,5}};
    int[][] arr1 = {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 15, 0}};
    int[][] arr2 = {{8, 1, 3},{4, 0, 2},{7,6,5}};
    Board puzzle = new Board(arr0);
    Board puzzle2 = new Board(arr2);
    //Board twin = puzzle.twin();
    //System.out.println(twin.toString());
    //System.out.println(puzzle.toString());
    
    Iterable<Board> puzzle_neighbors = puzzle.neighbors();
    for (Board neighbors : puzzle_neighbors)
    {
      System.out.println(neighbors.toString());  
    }
   
  }
}
