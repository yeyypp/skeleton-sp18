package lab11.graphs;



/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private boolean isCycle = false;
    private Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }

    @Override
    public void solve() {
        dfs(0, -1);
        return;
    }

    private void dfs(int v, int parent) {
        if (v == parent) {
            isCycle = true;
        }
        if (isCycle) {
            return;
        }
        marked[v] = true;
        parent = v;
        announce();
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                dfs(w, parent);
            }
        }
    }

}

