package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int source;
    private int target;
    private Maze maze;
    private boolean targetFound = false;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        distTo[source] = 0;
        edgeTo[source] = source;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(v);
        while (!targetFound) {
            int cur = queue.poll();
            marked[cur] = true;
            announce();
            if (cur == target) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            for (int w : maze.adj(cur)) {
                if (!marked[w]) {
                    edgeTo[w] = cur;
                    distTo[w] = distTo[cur] + 1;
                    queue.offer(w);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(source);
    }
}

