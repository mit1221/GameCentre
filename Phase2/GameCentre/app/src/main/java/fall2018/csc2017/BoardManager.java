package fall2018.csc2017;

import java.io.Serializable;

/**
 * A general board manager interface.
 */
public interface BoardManager extends Serializable {

    /**
     * Return the current board.
     */
    Board getBoard();

    /**
     * Return whether the board is solved.
     *
     * @return whether the board is solved
     */
    boolean puzzleSolved();

    /**
     * Return whether the move is allowed.
     *
     * @param move the move to check
     * @return whether the move is allowed
     */
    boolean isValidMove(Move move);

    /**
     * Process a move in the board.
     *
     * @param move the move to make
     */
    void touchMove(Move move);

}
