package fall2018.csc2017.slidingtiles;

import java.util.List;

import fall2018.csc2017.Board;
import fall2018.csc2017.Tile;

/**
 * The sliding tiles board.
 */
public class SlidingTilesBoard extends Board {

    /**
     * The image for the tiles of the board. If null, numbered tiles are used.
     */
    private byte[] image;


    SlidingTilesBoard(int size, List<SlidingTile> tiles, byte[] image) {
        super(size, tiles);
        this.image = image;
    }

    int getBlankTileId() {
        return numTiles() - 1;
    }

    /**
     * Return image of the board
     *
     * @return returns the image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Set the tile at (row, col) to a new tile.
     * @param row row
     * @param col column
     * @param tile new tile to put at (row, col)
     */
    void setTile(int row, int col, Tile tile) {
        tiles[row][col] = tile;
    }
}
