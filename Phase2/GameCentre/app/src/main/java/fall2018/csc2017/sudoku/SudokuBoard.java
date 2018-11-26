package fall2018.csc2017.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fall2018.csc2017.Board;
import fall2018.csc2017.Move;
import fall2018.csc2017.Tile;

/**
 * The Sudoku board.
 */
public class SudokuBoard extends Board {

    /**
     * The digits allowed on the Sudoku board.
     */
    static final Set<Integer> digitSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    /**
     * Create a Sudoku board object with specified maximum number of undos. Its size is always 9.
     *
     * @param tiles        tiles for the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    SudokuBoard(List<Tile> tiles, int maxUndoMoves) {
        super(9, tiles, maxUndoMoves);
    }

    /**
     * Insert number at the given tile.
     *
     * @param m move to make
     */
    @Override
    public void gameMove(Move m) {
        SudokuMove move = (SudokuMove) m;
        int row = move.getRow();
        int col = move.getCol();
        int number = move.getNewNumber();

        tiles[row][col] = new Tile(number);
    }

    /**
     * Return whether all the rows on the board are solved.
     * @return whether all the rows on the board are solved
     */
    boolean allRowsSolved() {
        for (int i = 0; i < tiles.length; i++) {
            if (!listValid(tiles[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether all the columns on the board are solved.
     * @return whether all the columns on the board are solved
     */
    boolean allColumnsSolved() {
        return true;
    }

    /**
     * Return whether all the sub squares on the board are solved.
     *
     * @return whether all the sub squares on the board are solved
     */
    boolean allSubSquaresSolved() {
        return true;
    }

    /**
     * Check if list contains all numbers from 1-9 exactly once.
     *
     * @param list list to check
     * @return if list contains all numbers from 1-9 exactly once
     */
    private boolean listValid(Tile[] list) {
        Set<Integer> listSet = new HashSet<>();

        for (Tile t : list) {
            listSet.add(t.getId());
        }

        return listSet.equals(digitSet);

    }
}