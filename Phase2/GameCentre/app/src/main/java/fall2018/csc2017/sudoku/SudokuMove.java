package fall2018.csc2017.sudoku;

import fall2018.csc2017.Board;
import fall2018.csc2017.Move;

/**
 * Represents a move for the Sudoku game.
 */
public class SudokuMove implements Move {
    /**
     * Row of clicked tile
     */
    private int row;

    /**
     * Column of clicked tile
     */
    private int col;

    /**
     * Current number on the clicked tile
     */
    private int currentNumber;

    /**
     * Number to put on the clicked tile
     */
    private int newNumber;

    /**
     * Set the required values for a Sudoku move.
     *
     * @param row Row of tile
     * @param col Column of tile
     * @param currentNumber The number currently at the tile on (row, column)
     * @param newNumber The number to put at the tile on (row, column)
     */
    SudokuMove(int row, int col, int currentNumber, int newNumber) {
        this.row = row;
        this.col = col;
        this.currentNumber = currentNumber;
        this.newNumber = newNumber;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    int getNewNumber() {
        return newNumber;
    }

    /**
     * Return a Move object from the position clicked on the board and the number entered.
     *
     * @param position  the position on the board clicked
     * @param b         the board
     * @param newNumber the new number to put
     * @return Move to make
     */
    static Move createMove(int position, Board b, int newNumber) {
        SudokuBoard board = (SudokuBoard) b;
        int row = position / board.getSize();
        int col = position % board.getSize();
        SudokuTile tile = (SudokuTile) board.getTile(row, col);

        return new SudokuMove(row, col, tile.getValue(), newNumber);
    }

    @Override
    public Move reverseMove() {
        return new SudokuMove(row, col, newNumber, currentNumber);
    }
}
