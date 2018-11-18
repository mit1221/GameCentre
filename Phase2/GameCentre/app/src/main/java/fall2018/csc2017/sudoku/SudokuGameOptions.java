package fall2018.csc2017.sudoku;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Game options for the sliding tiles game.
 */
class SudokuGameOptions implements Serializable {
    /**
     * Max number of undo moves allowed
     */
    private int undoMoves;

    public int getUndoMoves() {
        return undoMoves;
    }

    public void setUndoMoves(int undoMoves) {
        this.undoMoves = undoMoves;
    }
}

