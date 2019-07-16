package hw4.puzzle;


import edu.princeton.cs.algs4.MinPQ;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ShuaiYe
 * @date 2019/7/15 20:41
 */
public class Solver {
    public Iterable<WorldState> solution;
    public int Moves;

    public Solver(WorldState initial) {
        Set<WorldState> solutionPath = new HashSet<>();
        Moves = 0;

        MinPQ<SearchNode> minPQ = new MinPQ<>();
        SearchNode initialNode = new SearchNode(initial, 0, null);
        minPQ.insert(initialNode);

       while (!minPQ.isEmpty()) {
           SearchNode cur = minPQ.delMin();
           WorldState curState = cur.getState();
           if (curState.isGoal()) {
               Moves = cur.getMoves();
               solutionPath.add(curState);
               break;
           }
           solutionPath.add(curState);
           for (WorldState w : curState.neighbors()) {
               if (cur.previous == null) {
                   minPQ.insert(new SearchNode(w, cur.getMoves() + 1, cur));
               } else if (!w.equals(cur.previous.getState())) {
                   minPQ.insert(new SearchNode(w, cur.getMoves() + 1, cur));
               }
           }
       }
        solution = solutionPath;
    }

    public int moves() {
        return Moves;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }

    private class SearchNode implements Comparable<SearchNode>{
        private WorldState state;
        private int moves;
        private SearchNode previous;
        private int priority;

        public SearchNode(WorldState state, int moves, SearchNode previous) {
            this.state = state;
            this.moves = moves;
            this.previous = previous;
            this.priority = state.estimatedDistanceToGoal() + moves;
        }

        public WorldState getState() {
            return state;
        }

        public int getMoves() {
            return moves;
        }


        @Override
        public int compareTo(SearchNode node) {
            return this.priority - node.priority;
        }



    }
}
