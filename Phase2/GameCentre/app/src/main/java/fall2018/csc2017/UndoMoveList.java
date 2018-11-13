package fall2018.csc2017;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Represents a generic list of undoable moves
 *
 *
 *
 * @param <T> The type of object to represent each list
 */
public class UndoMoveList<T extends Serializable> implements Serializable {

    /**
     * The maximum size of the list. EX: user can undo the last "maxSize" moves
     */
    private int maxSize;

    /**
     * Keep track of moves made.
     */
    private ArrayList<T> moves;

    /**
     * Create a new UndoMoveList with the given maximum size
     *
     * @param maxSize Positive integer for the maximum size of list or -1 to allow unlimited undos
     */
    public UndoMoveList(int maxSize) {
        if (maxSize < -1 || maxSize == 0) {
            throw new IllegalArgumentException("Choose a positive integer or -1");
        }
        this.maxSize = maxSize;
        this.moves = new ArrayList<>();
    }

    /**
     * Add a new move to the list. If the list is already at maxSize, the oldest move is removed.
     *
     * @param move Move to add to list
     */
    public void addMove(T move) {
        if (moves.size() == maxSize) {
            moves.remove(0); // remove oldest move
        }
        moves.add(move);
    }

    /**
     * Removes the last move made by the user and returns it.
     *
     * @return the last move
     */
    public T popLastMove() {
        // throw exception if no move to remove
        if (moves.size() == 0) {
            throw new NoSuchElementException("You cannot undo any more moves");
        }
        return moves.remove(moves.size() - 1);
    }

    /**
     * Return the size of the undo list
     *
     * @return the size of the list
     */
    public int getSize() {
        return moves.size();
    }
}
