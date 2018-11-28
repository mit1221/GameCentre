package fall2018.csc2017.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fall2018.csc2017.Board;

/**
 * The Sudoku board.
 */
public class SudokuBoard extends Board {

    /**
     * The constant for the size of a Sudoku board
     */
    static final int SIZE = 9;

    /**
     * The digits allowed on the Sudoku board.
     */
    static final Set<Integer> DIGIT_SET = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    /**
     * Create a Sudoku board object with specified maximum number of undos. Its size is always 9.
     *
     * @param tiles        tiles for the board
     */
    SudokuBoard(List<SudokuTile> tiles) {
        super(SIZE, tiles);
    }
}