package fall2018.csc2017;

import java.io.Serializable;

/**
 * General Move object for a game.
 */
public interface Move extends Serializable {
    Move reverseMove();
}
