package fall2018.csc2017.sudoku;

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

    @Override
    public Move reverseMove() {
        return new SudokuMove(row, col, newNumber, currentNumber);
    }
}
