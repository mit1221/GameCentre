package fall2018.csc2017.sudoku;

import fall2018.csc2017.Game;
import fall2018.csc2017.MenuController;

/**
 * Controller for the Sliding Tiles menu activity.
 */
public class SudokuMenuController extends MenuController {

    /**
     * Customizable options for the game
     */
    private SudokuGameOptions gameOptions = new SudokuGameOptions();

    /**
     * Get the gameOptions.
     *
     * @return game options
     */
    public SudokuGameOptions getGameOptions() {
        return gameOptions;
    }

    /**
     * Set the number of moves to undo in the game options.
     *
     * @param undoMoves number of moves that can be undone
     */
    void setUndoMoves(int undoMoves) {
        gameOptions.setUndoMoves(undoMoves);
    }

    @Override
    public boolean userHasSave() {
        return getUser() != null && getUser().hasSave(Game.SUDOKU);
    }
}
