package fall2018.csc2017.sudoku;

import java.util.ArrayList;
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
    private static final Set<Integer> digitSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    /**
     * Create a Sudoku board object with specified maximum number of undos. Its size is always 9.
     *
     * @param tiles        tiles for the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    SudokuBoard(List<SudokuTile> tiles, int maxUndoMoves) {
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
        SudokuEditableTile tile = (SudokuEditableTile) getTile(move.getRow(), move.getCol());
        tile.setValue(move.getNewNumber());
    }

    /**
     * Return whether all the rows on the board are solved.
     * @return whether all the rows on the board are solved
     */
    boolean allRowsSolved() {
        return allListsValid(tiles);
    }

    /**
     * Return whether all the columns on the board are solved.
     * @return whether all the columns on the board are solved
     */
    boolean allColumnsSolved() {
        return allListsValid(getColumns());
    }

    /**
     * Returns the columns of the board.
     *
     * @return the columns of the board
     */
    private Tile[][] getColumns() {
        List<Tile[]> columns = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            columns.add(getColumn(i));
        }
        return columns.toArray(new Tile[0][]);
    }

    /**
     * Get the column given the column index.
     *
     * @param col index of the column
     * @return column
     */
    private Tile[] getColumn(int col) {
        List<Tile> column = new ArrayList<>();
        for (Tile[] row : tiles) {
            column.add(row[col]);
        }
        return column.toArray(new Tile[0]);
    }

    /**
     * Return whether all the sub squares on the board are solved.
     *
     * @return whether all the sub squares on the board are solved
     */
    boolean allSubSquaresSolved() {
        return allListsValid(getSubSquares());
    }

    /**
     * Returns the subsquares of the board.
     *
     * @return the subsquares of the board
     */
    private Tile[][] getSubSquares() {
        List<Tile[]> subsquares = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            subsquares.add(getSubSquare(i));
        }
        return subsquares.toArray(new Tile[0][]);
    }

    /**
     * Return the ith subsquare.
     *
     * @param i the subsquare to get
     * @return the subsquare
     */
    private Tile[] getSubSquare(int i) {
        List<Tile> subsquare = new ArrayList<>();
        int subSquareWidth = (int) Math.sqrt(getSize());
        int startRow = (i / subSquareWidth) * subSquareWidth;
        int startColumn = (i % subSquareWidth) * subSquareWidth;
        Tile[][] rowSlice = Arrays.copyOfRange(tiles, startRow, startRow + subSquareWidth);
        for (Tile[] row : rowSlice) {
            subsquare.addAll(Arrays.asList(row).subList(startColumn, startColumn + subSquareWidth));
        }
        return subsquare.toArray(new Tile[0]);
    }

    /**
     * Check if the given lists are solved.
     *
     * @param lists lists to check
     * @return if the given lists are solved
     */
    private boolean allListsValid(Tile[][] lists) {
        for (Tile[] list : lists) {
            if (notListValid(list)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if list contains all numbers from 1-9 exactly once.
     *
     * @param list list to check
     * @return if list does not contain all numbers from 1-9 exactly once
     */
    private boolean notListValid(Tile[] list) {
        Set<Integer> listSet = new HashSet<>();

        for (Tile t : list) {
            SudokuTile tile = (SudokuTile) t;
            listSet.add(tile.getValue());
        }

        return !listSet.equals(digitSet);
    }
}