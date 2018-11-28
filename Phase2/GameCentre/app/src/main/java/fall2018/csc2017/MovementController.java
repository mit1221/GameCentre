package fall2018.csc2017;

import android.content.Context;
import android.widget.Toast;

public class MovementController {

    private BoardManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process a move made on the board.
     *
     * @param move the move to make
     * @return "YOU WIN" if the puzzle is solved, "Invalid Tap" if the tap is not allowed and null otherwise
     */
    public String processMove(Move move) {
        String result = null;
        if (boardManager.isValidMove(move)) {
            boardManager.touchMove(move);
            if (boardManager.puzzleSolved()) {
                result = "YOU WIN!";
            }
        } else {
            result = "Invalid Tap";
        }
        return result;
    }
}
