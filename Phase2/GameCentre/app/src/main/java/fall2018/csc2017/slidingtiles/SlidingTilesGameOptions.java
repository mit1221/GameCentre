package fall2018.csc2017.slidingtiles;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Game options for the sliding tiles game.
 */
class SlidingTilesGameOptions implements Serializable {
    /**
     * Size of the board
     */
    private int size;

    /**
     * Max number of undo moves allowed
     */
    private int undoMoves;

    /**
     * Image to use as the background
     */
    private byte[] image;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getUndoMoves() {
        return undoMoves;
    }

    public void setUndoMoves(int undoMoves) {
        this.undoMoves = undoMoves;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        if (image != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 10, stream);
            this.image = stream.toByteArray();
        } else {
            this.image = null;
        }
    }
}

