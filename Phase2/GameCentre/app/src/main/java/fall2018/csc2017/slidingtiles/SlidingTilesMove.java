package fall2018.csc2017.slidingtiles;

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

    @Override
    public Move reverseMove() {
        return new SlidingTilesMove(row2, col2, row1, col1);
    }
}
