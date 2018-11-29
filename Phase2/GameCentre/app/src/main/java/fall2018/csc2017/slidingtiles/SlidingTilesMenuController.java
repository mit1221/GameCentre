package fall2018.csc2017.slidingtiles;

import fall2018.csc2017.MenuController;

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
    public void setBoardSize(int size) {
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
}
