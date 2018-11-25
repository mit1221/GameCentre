package fall2018.csc2017.sudoku;

import java.util.List;

import fall2018.csc2017.Board;
import fall2018.csc2017.Move;
import fall2018.csc2017.Tile;

/**
 * The Sudoku board.
 */
public class SudokuBoard extends Board {

    /**
     * Create a Sudoku board object with specified maximum number of undos. Its size is always 9.
     *
     * @param tiles        tiles for the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    SudokuBoard(List<Tile> tiles, int maxUndoMoves) {
        super(9, tiles, maxUndoMoves);
    }

    @Override
    public void gameMove(Move m) {
        insertNumber((SudokuMove) m);
    }

    /**
     * Insert number at the given tile
     *
     * @param move move to make
     */
    private void insertNumber(SudokuMove move) {
        int row = move.getRow();
        int col = move.getCol();
        int number = move.getNewNumber();

        tiles[row][col] = new Tile(number);
    }
}