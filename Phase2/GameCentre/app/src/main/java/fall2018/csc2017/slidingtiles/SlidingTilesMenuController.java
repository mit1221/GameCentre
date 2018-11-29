package fall2018.csc2017.slidingtiles;

import android.graphics.Bitmap;

import fall2018.csc2017.MenuController;

/**
 * Controller for the Sliding Tiles menu activity.
 */
public class SlidingTilesMenuController extends MenuController {

    /**
     * Customizable options for the game
     */
    private SlidingTilesGameOptions gameOptions = new SlidingTilesGameOptions();

    /**
     * Get the gameOptions.
     *
     * @return game options
     */
    public SlidingTilesGameOptions getGameOptions() {
        return gameOptions;
    }

    /**
     * Set the board size in the game options.
     *
     * @param size board size
     */
    void setBoardSize(int size) {
        int boardSize;
        switch (size) {
            case R.id.StartButton3:
                boardSize = 3;
                break;
            case R.id.StartButton4:
                boardSize = 4;
                break;
            case R.id.StartButton5:
                boardSize = 5;
                break;
            default:
                boardSize = 3;
                break;
        }
        gameOptions.setSize(boardSize);
    }

    /**
     * Set the number of moves to undo in the game options.
     *
     * @param undoMoves number of moves that can be undone
     */
    void setUndoMoves(int undoMoves) {
        gameOptions.setUndoMoves(undoMoves);
    }

    /**
     * Set the image in the game options.
     *
     * @param image the image
     */
    public void setImage(Bitmap image) {
        gameOptions.setImage(image);
    }
}
