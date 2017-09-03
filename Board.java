import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] blocks;

    public Board(int[][] blocks) {
        int n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++){
                this.blocks[i][j] = blocks[i][j];
            }
        }
        // construct a board from an n-by-n array of blocks
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++)
            copy[i] = blocks[i].clone();
        return copy;
    }
    
    private int[][] swap(int[][] grid, int x1, int y1, int x2, int y2) {
        int tmp = grid[x1][y1];
        grid[x1][y1] = grid[x2][y2];
        grid[x2][y2] = tmp;
        return grid;
    }
    
    private int[] blank() {
        int[] blank = new int[2];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++){
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    blank[0] = i;
                    blank[1] = j;
                    return blank;
                }
            }
        }
        return blank;
    }
	
    public int dimension() {
        return blocks.length;
        // board dimension n
    }
    
    public int hamming() {
        int ham = 0;
        int n = blocks.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++){
                int num = blocks[i][j];
                if (num != 0 && num != i * n + j + 1) {
                    ham++;
                }
            }
        }
        return ham;
        // number of blocks out of place
    }
    
    public int manhattan() {
        int man = 0;
        int n = blocks.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++){
                int num = blocks[i][j];
                if (num != 0 && num != i * n + j + 1) {
                    man += Math.abs((num - 1)/n - i) + Math.abs((num - 1)%n - j);
                }
            }
        }
        return man;
        // sum of Manhattan distances between blocks and goal
    }
    
    public boolean isGoal() {
        return hamming() == 0;
        // decide if this board is the goal board
    }
    
    public Board twin() {
        int[][] twin = copy(blocks);
        if (twin[0][0] != 0 && twin[0][1] != 0) {
            return new Board(swap(twin, 0, 0, 0, 1));
        } else if (twin[0][0] != 0 && twin[1][0] != 0) {
            return new Board(swap(twin, 0, 0, 1, 0));
    	} else {
    	    return new Board(swap(twin, 0, 1, 1, 0));
    	}
        // a board that is obtained by exchanging any pair of blocks
    }
    
    public boolean equals(Object y) {
        if (y instanceof Board && ((Board)y).toString().equals(new Board(blocks).toString())) {
            return true;
        } else {
            return false;
        }
        // decide if this board equals y
    }
    
    public Iterable<Board> neighbors() {
        List<Board> list = new ArrayList<>();
    	int x = blank()[0];
        int y = blank()[1];
        if (x > 0) {
            list.add(new Board(swap(copy(blocks), x, y, x - 1, y)));
        }
        if (x < blocks.length - 1) {
            list.add(new Board(swap(copy(blocks), x, y, x + 1, y)));
        }
        if (y > 0) {
            list.add(new Board(swap(copy(blocks), x, y, x, y - 1)));
        }
        if (y < blocks.length - 1) {
            list.add(new Board(swap(copy(blocks), x, y, x, y + 1)));
        }
        return list;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(blocks.length + "\n");
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // unit tests

    }
}
