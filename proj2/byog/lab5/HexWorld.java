package byog.lab5;


import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    // initialize a blank world
    public static void initialTiles(TETile[][] tiles) {
        for (int x = 0; x < tiles.length; x += 1) {
            for (int y = 0; y < tiles[0].length; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    // add a hexagon
    public static void addHexagon(TETile[][] tiles, int size, int x, int y) {
        for (int j = y; j < y + 2 * size; j++) {
            int start = RowStart(size, j - y);
            int width = RowWidth(size, j - y);
            drawLine(tiles, width, x + start, j);
        }
    }

    // compute the start x with the given size and row number
    private static int RowStart(int size, int row) {
        int i = row;
        if (row >= size) {
            i = 2 * size - 1 - i;
        }
        return -i;
    }

    // compute the width of the row with the given size and row number
    private static int RowWidth(int size, int row) {
        int i = row;
        if (row >= size) {
            i = 2 * size - 1 - i;
        }
        return size + 2 * i;
    }

    // draw a line
    private static void drawLine(TETile[][] tiles, int width, int x, int y) {
        for (int i = x; i < x + width; i++) {
            tiles[i][y] = Tileset.FLOWER;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        initialTiles(tiles);
        addHexagon(tiles, 3, 20, 10);

        ter.renderFrame(tiles);
    }

}
