package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sx = maze.toX(v);
        int sy = maze.toY(v);
        int tx = maze.toX(t);
        int ty = maze.toY(t);
        return Math.abs(sx - tx) + Math.abs(sy - ty);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        if (!marked[s]) {
            marked[s] = true;
            announce();
            int minDis = Integer.MAX_VALUE;
            int minW = -1;
            for (int w : maze.adj(s)) {
                if (!marked[w]) {
                    distTo[w] = distTo[s] + 1;
                    int temMinDis = distTo[w] + h(w);
                    if (temMinDis < minDis) {
                        minDis = temMinDis;
                        minW = w;
                    }
                }
                edgeTo[w] = s;
                announce();
                astar(w);
            }
        }

    }

    @Override
    public void solve() {
        astar(s);
    }

}

