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
     * Return whether the tap at position is allowed.
     *
     * @param position the tile to check
     * @return whether you can touch the tile or not
     */
    boolean isValidTap(int position);

    /**
     * Process a touch at position in the board.
     *
     * @param position the position
     */
    void touchMove(int position);

}
