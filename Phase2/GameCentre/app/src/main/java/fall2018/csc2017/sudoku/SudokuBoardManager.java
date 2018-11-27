package fall2018.csc2017.sudoku;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.Board;
import fall2018.csc2017.BoardManager;
import fall2018.csc2017.Move;
import fall2018.csc2017.Tile;

/**
 * Manage a Sudoku board.
 */
class SudokuBoardManager implements BoardManager {

    /**
     * The Sudoku board being managed.
     */
    private SudokuBoard board;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    SudokuBoardManager(SudokuBoard board) {
        this.board = board;
    }

    @Override
    public SudokuBoard getBoard() {
        return board;
    }

    /**
     * Helper method for generating all the tiles for the Sudoku board.
     *
     * @param size size of the board
     * @return the generated shuffled tiles
     */
    private List<SudokuTile> generateTiles(int size) {
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
    SudokuBoardManager(int maxUndoMoves) {
        this.board = new SudokuBoard(generateTiles(9), maxUndoMoves);
    }

    /**
     * Return whether the Sudoku puzzle is solved.
     *
     * @return whether the Sudoku puzzle is solved
     */
    @Override
    public boolean puzzleSolved() {
        return board.allRowsSolved() && board.allColumnsSolved() && board.allSubSquaresSolved();
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
        return board.getTile(move.getRow(), move.getCol()) instanceof SudokuEditableTile;
    }

    /**
     * Process a touch at position in the board, changing the tile number as appropriate.
     *
     * @param move the move to make
     */
    @Override
    public void touchMove(Move move) {
        if (this.puzzleSolved()) { // don't let user shift tiles if game is finished
            return;
        }
        board.makeMove(move);
    }

    public static void main(String[] args) {
        SudokuBoardManager manager = new SudokuBoardManager(3);
        Board board = manager.board;

        for (Tile tile : board) {
            SudokuTile t = (SudokuTile) tile;
            System.out.println(t.getValue());
        }

        int row = 0;
        int col = 1;
        SudokuTile t1 = (SudokuTile) board.getTile(row, col);

        SudokuMove move = new SudokuMove(row, col, t1.getValue(), 2);
        if (manager.isValidMove(move)) {
            manager.touchMove(move);

            System.out.println("=============================");
            for (Tile tile : board) {
                SudokuTile t = (SudokuTile) tile;
                System.out.println(t.getValue());
            }

            move = new SudokuMove(row, col, t1.getValue(), 6);
            manager.touchMove(move);

            System.out.println("=============================");
            for (Tile tile : board) {
                SudokuTile t = (SudokuTile) tile;
                System.out.println(t.getValue());
            }

            manager.getBoard().undoLastMove();
            System.out.println("=============================");
            for (Tile tile : board) {
                SudokuTile t = (SudokuTile) tile;
                System.out.println(t.getValue());
            }
        }
    }

}
