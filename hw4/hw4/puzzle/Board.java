package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.Set;

public class Board implements WorldState{

    private int[][] myTiles;
    private int size;
    private int[][] answer;
    private int BLANK = 0;

    public Board(int[][] tiles) {

        this.size = tiles.length;
        myTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                myTiles[i][j] = tiles[i][j];
            }
        }
        answer = new int[size][size];
        int start = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                answer[i][j] = start;
                start++;
            }
        }
        answer[size - 1][size - 1] = BLANK;
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > size - 1 || j < 0 || j > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return myTiles[i][j];
    }

    public int size() {
        return size;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;

    }

    public int hamming() {
        int ham = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (myTiles[i][j] == BLANK) {
                    continue;
                }
                if (myTiles[i][j] != answer[i][j]) {
                    ham++;
                }
            }
        }
        return ham;
    }

    public int manhattan() {
        int man = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (myTiles[i][j] == BLANK) {
                    continue;
                }
                if (myTiles[i][j] != answer[i][j]) {
                    int[] cood = findOrigin(myTiles[i][j]);
                    man += bfs(i, j, cood[0], cood[1]);
                }
            }
        }
        return man;
    }

    private int[] findOrigin(int target) {
        int[] ans = new int[2];
        ans[0] = (target - 1) / size;
        ans[1] = (target - 1) % size;
        return ans;
    }


    private int bfs(int i, int j, int targetI, int targetJ) {
        if (!validate(i, j)) {
            return Integer.MAX_VALUE;
        }
        if (i == targetI && j == targetJ) {
            return 0;
        }
        return 1 + Math.min(bfs(i + 1, j, targetI, targetJ), bfs(i, j + 1, targetI, targetJ));
    }

    private boolean validate(int i, int j) {
        if (i < 0 || i > size || j < 0 || j > size) {
            return false;
        }
        return true;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Board)) {
            return false;
        }
        Board tem = (Board) o;
        if (tem.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) != tem.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
