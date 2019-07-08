package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;


public class Percolation {

    private boolean[] matrix;
    private int numberOfOpenSites;
    private int N;
    private int length;
    private WeightedQuickUnionUF data;
    private static final int[][] XY = new int[][]{new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, -1}, new int[]{0, 1}};


    public Percolation(int N) {
        this.N = N;
        this.numberOfOpenSites = 0;
        this.length = N * (N + 1);
        this.data = new WeightedQuickUnionUF(length + 1);
        this.matrix = new boolean[length];
        Arrays.fill(matrix, false);
    }

    public void open(int row, int col) {
      if (matrix[xyTo1D(row, col)]) {
          return;
      }
      matrix[xyTo1D(row, col)] = true;
      numberOfOpenSites++;
      if (row == 0) {
          data.union(xyTo1D(row, col), length);
      }
      if (row == N - 1) {
          data.union(xyTo1D(row, col), xyTo1D(N, col));
      }
      helper(row, col);
    }

    public boolean isOpen(int row, int col) {
        return matrix[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        return data.connected(xyTo1D(row, col), length);

    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        for (int i = 0; i < N; i++) {
            if (data.connected(xyTo1D(N, i), length)) {
                return true;
            }
        }
        return false;
    }

    private int xyTo1D(int x, int y) {
        return x * N + y;
    }

    private boolean validate(int x, int y) {
        return (x >= 0 && x < N) && (y >= 0 && y < N);
    }

    private void helper(int row, int col) {
        for (int[] array : XY) {
            int newX = array[0] + row;
            int newY = array[1] + col;
            if (validate(newX, newY) && matrix[xyTo1D(newX, newY)]) {
                data.union(xyTo1D(row, col), xyTo1D(newX, newY));
            }
        }
    }

    public static void main(String[] args) {

    }
}
