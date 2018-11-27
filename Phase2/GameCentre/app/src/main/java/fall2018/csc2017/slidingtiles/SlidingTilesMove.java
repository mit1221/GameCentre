package fall2018.csc2017.slidingtiles;

import fall2018.csc2017.Board;
import fall2018.csc2017.Move;

/**
 * Represents a move for the sliding tiles game.
 */
public class SlidingTilesMove implements Move {
    private int row1;
    private int row2;
    private int col1;
    private int col2;

    /**
     * Set the required values for a sliding tiles move.
     *
     * @param row1 Row of first tile to swap
     * @param col1 Column of first tile to swap
     * @param row2 Row of second tile to swap
     * @param col2 Column of second tile to swap
     */
    SlidingTilesMove(int row1, int col1, int row2, int col2) {
        this.row1 = row1;
        this.row2 = row2;
        this.col1 = col1;
        this.col2 = col2;
    }

    int getRow1() {
        return row1;
    }

    int getRow2() {
        return row2;
    }

    int getCol1() {
        return col1;
    }

    int getCol2() {
        return col2;
    }

    /**
     * Return a Move object from the position clicked.
     *
     * @param position the position on the board clicked
     * @param b        the board
     * @return Move to make
     */
    static Move createMove(int position, Board b) {
        SlidingTilesBoard board = (SlidingTilesBoard) b;
        int row = position / board.getSize();
        int col = position % board.getSize();
        int blankId = board.getBlankTileId();

        int rowOfBlankTile = row;
        int colOfBlankTile = col;

        if (col != 0 && board.getTile(row, col - 1).getId() == blankId) {
            // blank tile is to the left
            colOfBlankTile = col - 1;
        } else if (col != board.getSize() - 1 && board.getTile(row, col + 1).getId() == blankId) {
            // blank tile is to the right
            colOfBlankTile = col + 1;
        } else if (row != 0 && board.getTile(row - 1, col).getId() == blankId) {
            // blank tile is above
            rowOfBlankTile = row - 1;
        } else {
            // blank tile has to be below
            rowOfBlankTile = row + 1;
        }
        return new SlidingTilesMove(row, col, rowOfBlankTile, colOfBlankTile);
    }

    @Override
    public Move reverseMove() {
        return new SlidingTilesMove(row2, col2, row1, col1);
    }
}
