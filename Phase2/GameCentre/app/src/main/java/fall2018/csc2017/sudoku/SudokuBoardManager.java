package fall2018.csc2017.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fall2018.csc2017.Board;
import fall2018.csc2017.BoardManager;
import fall2018.csc2017.Move;
import fall2018.csc2017.Tile;

/**
 * Manage a Sudoku board.
 */
public class SudokuBoardManager extends BoardManager {

    /**
     * Helper method for generating all the tiles for the Sudoku board.
     *
     * @param size size of the board
     * @return the generated shuffled tiles
     */
    @Override
    public List<SudokuTile> generateTiles(int size) {
        List<SudokuTile> tiles = new ArrayList<>();
        BoardGenerator generator = new BoardGenerator(size, 14);
        generator.fillValues();

        int id = 0;
        for (int number : generator) {
            SudokuTile tile;
            tile = number == 0 ? new SudokuEditableTile(id) : new SudokuLockedTile(id, number);
            tiles.add(tile);
            id++;
        }
        return tiles;
    }

    /**
     * Manage a new board with generated tiles.
     *
     * @param maxUndoMoves the maximum undos that the user can do
     */
    public SudokuBoardManager(int maxUndoMoves) {
        super(maxUndoMoves);
        setBoard(new SudokuBoard(generateTiles(SudokuBoard.SIZE)));
    }

    /**
     * Insert number at the given tile.
     *
     * @param m move to make
     */
    @Override
    public void gameMove(Move m) {
        SudokuMove move = (SudokuMove) m;
        SudokuEditableTile tile = (SudokuEditableTile) getBoard().getTile(move.getRow(), move.getCol());
        tile.setValue(move.getNewNumber());
    }

    /**
     * Return whether the Sudoku puzzle is solved.
     *
     * @return whether the Sudoku puzzle is solved
     */
    @Override
    public boolean puzzleSolved() {
        return allRowsSolved() && allColumnsSolved() && allSubSquaresSolved();
    }

    /**
     * Return whether all the rows on the board are solved.
     *
     * @return whether all the rows on the board are solved
     */
    private boolean allRowsSolved() {
        return allListsValid(getBoard().getTiles());
    }

    /**
     * Return whether all the columns on the board are solved.
     *
     * @return whether all the columns on the board are solved
     */
    private boolean allColumnsSolved() {
        return allListsValid(getColumns());
    }

    /**
     * Returns the columns of the board.
     *
     * @return the columns of the board
     */
    private Tile[][] getColumns() {
        List<Tile[]> columns = new ArrayList<>();
        for (int i = 0; i < getBoard().getSize(); i++) {
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
        for (Tile[] row : getBoard().getTiles()) {
            column.add(row[col]);
        }
        return column.toArray(new Tile[0]);
    }

    /**
     * Return whether all the sub squares on the board are solved.
     *
     * @return whether all the sub squares on the board are solved
     */
    private boolean allSubSquaresSolved() {
        return allListsValid(getSubSquares());
    }

    /**
     * Returns the subsquares of the board.
     *
     * @return the subsquares of the board
     */
    private Tile[][] getSubSquares() {
        List<Tile[]> subsquares = new ArrayList<>();
        for (int i = 0; i < getBoard().getSize(); i++) {
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
        int subSquareWidth = (int) Math.sqrt(getBoard().getSize());

        int startRow = (i / subSquareWidth) * subSquareWidth;
        int startColumn = (i % subSquareWidth) * subSquareWidth;

        Tile[][] rowSlice = Arrays.copyOfRange(getBoard().getTiles(), startRow, startRow + subSquareWidth);
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
            if (!listValid(list)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if list contains all numbers from the digit set (1-9) exactly once.
     *
     * @param list list to check
     * @return if list contains all numbers from the digit set exactly once
     */
    private boolean listValid(Tile[] list) {
        Set<Integer> listSet = new HashSet<>();

        for (Tile t : list) {
            SudokuTile tile = (SudokuTile) t;
            listSet.add(tile.getValue());
        }

        return listSet.equals(SudokuBoard.DIGIT_SET);
    }

    /**
     * Return whether the tile can be changed.
     *
     * @param m the move to check
     * @return whether the tile at position can be changed
     */
    @Override
    public boolean isValidMove(Move m) {
        SudokuMove move = (SudokuMove) m;
        return getBoard().getTile(move.getRow(), move.getCol()) instanceof SudokuEditableTile;
    }

    public static void main(String[] args) {
        SudokuBoardManager manager = new SudokuBoardManager(3);
        Board board = manager.getBoard();

        for (Tile tile : board) {
            SudokuTile t = (SudokuTile) tile;
            System.out.println(t.getValue());
        }

        int row = 0;
        int col = 1;
        SudokuTile t1 = (SudokuTile) board.getTile(row, col);

        SudokuMove move = new SudokuMove(row, col, t1.getValue(), 2);
        if (manager.isValidMove(move)) {
            manager.makeMove(move);

            System.out.println("=============================");
            for (Tile tile : board) {
                SudokuTile t = (SudokuTile) tile;
                System.out.println(t.getValue());
            }

            move = new SudokuMove(row, col, t1.getValue(), 6);
            manager.makeMove(move);

            System.out.println("=============================");
            for (Tile tile : board) {
                SudokuTile t = (SudokuTile) tile;
                System.out.println(t.getValue());
            }

            manager.undoLastMove();
            System.out.println("=============================");
            for (Tile tile : board) {
                SudokuTile t = (SudokuTile) tile;
                System.out.println(t.getValue());
            }
        }
    }

}
