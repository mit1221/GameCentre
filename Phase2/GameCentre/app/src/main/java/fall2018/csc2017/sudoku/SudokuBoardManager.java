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
            if (number == 0) {
                tile = new SudokuEditableTile(id);
            } else {
                tile = new SudokuLockedTile(id, number);
            }
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
        int row = position / board.getSize();
        int col = position % board.getSize();

        return getBoard().getTile(row, col) instanceof SudokuEditableTile;
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
        SudokuBoardManager manager = new SudokuBoardManager(2);
        Board board = manager.getBoard();

        for (Tile tile : board) {
            System.out.println(tile.getId());
        }

        System.out.println(((SudokuBoard) board).allColumnsSolved());
        System.out.println(((SudokuBoard) board).allRowsSolved());
        System.out.println(((SudokuBoard) board).allSubSquaresSolved());
    }

}
