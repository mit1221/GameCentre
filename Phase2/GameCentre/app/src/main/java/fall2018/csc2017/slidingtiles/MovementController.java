package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;

import fall2018.csc2017.BoardManager;
import fall2018.csc2017.Move;


public class MovementController {

    private BoardManager boardManager = null;



    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        Move move = SlidingTilesMove.createMove(position, boardManager.getBoard());
        if (boardManager.isValidMove(move)) {
            boardManager.touchMove(move);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
