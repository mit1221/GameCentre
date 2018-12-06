package fall2018.csc2017;

import java.io.Serializable;

/**
 * General Move object for a game.
 */
public interface Move extends Serializable {
    /**
     * Returns a new move to make that will undo the current move.
     *
     * @return Move to make
     */
    Move reverseMove();
}
