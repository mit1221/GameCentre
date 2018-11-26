package fall2018.csc2017.slidingtiles;

import java.util.List;

import fall2018.csc2017.Board;
import fall2018.csc2017.Move;

/**
 * The sliding tiles board.
 */
public class SlidingTilesBoard extends Board {

    /**
     * The image for the tiles of the board. If null, numbered tiles are used.
     */
    private byte[] image = null;

    SlidingTilesBoard(int size, List<SlidingTile> tiles, int maxUndoMoves) {
        super(size, tiles, maxUndoMoves);
    }

    SlidingTilesBoard(int size, List<SlidingTile> tiles, int maxUndoMoves, byte[] image) {
        super(size, tiles, maxUndoMoves);
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
     * Switch two tiles in the board
     *
     * @param m move to make
     */
    @Override
    public void gameMove(Move m) {
        SlidingTilesMove move = (SlidingTilesMove) m;
        int row1 = move.getRow1();
        int row2 = move.getRow2();
        int col1 = move.getCol1();
        int col2 = move.getCol2();

        SlidingTile temp = (SlidingTile) tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;
    }
}
