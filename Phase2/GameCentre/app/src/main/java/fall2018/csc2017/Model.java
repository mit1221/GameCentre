package fall2018.csc2017;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

/**
 * A general board manager interface.
 */
public abstract class Model extends Observable implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * Keep track of moves made
     */
    private UndoMoveList<Move> moves;

    /**
     * The number of moves made
     */
    private int movesMade = 0;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board        the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    public Model(Board board, int maxUndoMoves) {
        this.board = board;
        moves = new UndoMoveList<>(maxUndoMoves);
    }

    /**
     * Create a board manager with max undo moves.
     *
     * @param maxUndoMoves the maximum undos that the user can do
     */
    public Model(int maxUndoMoves) {
        moves = new UndoMoveList<>(maxUndoMoves);
    }

    /**
     * Return the current board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Set the board for the board manager.
     *
     * @param board board to use
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Return the number of moves made
     *
     * @return number of moves made
     */
    public int getMovesMade() {
        return movesMade;
    }

    /**
     * Helper method for generating all the tiles for the board.
     *
     * @param size size of the board
     * @return the generated shuffled tiles
     */
    public abstract List<? extends Tile> generateTiles(int size);

    /**
     * The game specific move to make
     *
     * @param m the move to make
     */
    public abstract void gameMove(Move m);

    /**
     * Apply the move
     * Note: this method is called for both a forward move and an undo move
     *
     * @param m the move to make
     */
    private void move(Move m) {
        gameMove(m);
        movesMade += 1;
        setChanged();
        notifyObservers();
    }

    /**
     * Undo the last move made.
     */
    public void undoLastMove() {
        move(moves.popLastMove().reverseMove());
    }

    /**
     * Return whether the board is solved.
     *
     * @return whether the board is solved
     */
    public abstract boolean puzzleSolved();

    /**
     * Return whether the move is allowed.
     *
     * @param move the move to check
     * @return whether the move is allowed
     */
    public abstract boolean isValidMove(Move move);

    /**
     * Make move and add it to the moves made.
     *
     * @param move move to make
     */
    public void makeMove(Move move) {
        if (!this.puzzleSolved()) {
            move(move);
            moves.addMove(move);
        }
    }
}
