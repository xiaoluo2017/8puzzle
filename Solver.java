import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int moves;
    private List<Board> list;
	
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        MinPQ<node> minpq = new MinPQ<>();
        MinPQ<node> minpqTwin = new MinPQ<>();
        minpq.insert(new node(initial, null, 0));
        minpqTwin.insert(new node(initial.twin(), null, 0));
        list = new ArrayList<>();
        while (!minpq.isEmpty() && !minpqTwin.isEmpty()) {
            node curr = minpq.delMin();
            if (curr.curr.isGoal()) {
                moves = curr.move;
                while (curr != null) {
                    list.add(0, curr.curr);
                    curr = curr.previous;
                }
                break;
            } else {
                for (Board b : curr.curr.neighbors()) {
                    if (curr.prev == null || !curr.prev.equals(b)) {
                        node newNode = new node(b, curr.curr, curr.move + 1);
                        newNode.previous = curr;
                        minpq.insert(newNode);
                    }
                }
            }
            curr = minpqTwin.delMin();
            if (curr.curr.isGoal()) {
                moves = -1;
                break;
            } else {
                for (Board b : curr.curr.neighbors()) {
                    if (curr.prev == null || !curr.prev.equals(b)) {
                        minpqTwin.insert(new node(b, curr.curr, curr.move + 1));
                    }
                }
            }
        }
        // find a solution to the initial board (using the A* algorithm)
    }
    
    private class node implements Comparable<node> {
        private int move;
        private int man;
        private Board curr;
        private Board prev;
        private node previous;
		
        public node(Board curr, Board prev, int move) {
            this.curr = curr;
            this.prev = prev;
            this.move = move;
            this.man = curr.manhattan();
        }
    	
        @Override
        public int compareTo(node that) {
            return this.man + this.move - that.man - that.move;
        }
    }
    
    
    public boolean isSolvable() {
        return moves != -1;
        // decide if the initial board is solvable
    }
    
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return moves;
        // min number of moves to solve initial board; -1 if unsolvable
    }
    
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return list;
        // sequence of boards in a shortest solution; null if unsolvable
    }
    
    public static void main(String[] args) {

        // create initial board from file
        In in = new In("D:/Study/Graduate/coursera/Algorithms, Part I Princeton University/8 Puzzle/8puzzle/puzzle44.txt");  
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
